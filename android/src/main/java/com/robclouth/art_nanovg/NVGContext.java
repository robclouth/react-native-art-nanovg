package com.robclouth.art_nanovg;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.Log;
import android.view.TextureView;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.robclouth.art_nanovg.gles.EglCore;
import com.robclouth.art_nanovg.gles.WindowSurface;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;
import com.robclouth.art_nanovg.nanovg.nanovgConstants;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Rob on 28/11/2016.
 */

public class NVGContext extends ReactContextBaseJavaModule {
    private static final String TAG = "Renderer";

    static {
        System.loadLibrary("nanovg_jni");
    }

    Renderer renderer = new Renderer();

    public NVGContext(ReactApplicationContext reactContext) {
        super(reactContext);

        renderer.start();
    }

    @Override
    public String getName() {
        return "NVGContext";
    }

    public EglCore getGLContext(){
        return renderer.mEglCore;
    }

    public void queueRender(NVGSurfaceViewShadowNode node){
        renderer.queueRender(node);
    }

    private static class Renderer extends Thread {
        private EglCore mEglCore;
        private SWIGTYPE_p_NVGcontext vg;
        private ArrayBlockingQueue<NVGSurfaceViewShadowNode> renderQueue = new ArrayBlockingQueue<>(100);

        public Renderer() {
            super("TextureViewGL Renderer");
        }

        public void queueRender(NVGSurfaceViewShadowNode node){
            if(renderQueue.contains(node))
                renderQueue.remove(node);
            renderQueue.add(node);
        }

        @Override
        public void run() {
            mEglCore = new EglCore(null, 0);

            while (true) {
                try {
                    NVGSurfaceViewShadowNode node = renderQueue.take();
                    node.getWindowSurface().makeCurrent();

                    if(vg == null)
                        vg = nanovg.nvgCreateGLES2(0);

                    node.drawOutput(vg);

                    node.getWindowSurface().swapBuffers();
                } catch (InterruptedException e) {
                    break;
                }
            }

            mEglCore.release();
            Log.d(TAG, "Renderer thread exiting");
        }
    }
}
