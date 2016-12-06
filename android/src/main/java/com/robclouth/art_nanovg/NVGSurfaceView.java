/**
 * Copyright (c) 2015-present, Rob Clouth.
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.robclouth.art_nanovg;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.robclouth.art_nanovg.gles.EglCore;
import com.robclouth.art_nanovg.gles.WindowSurface;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Custom {@link View} implementation that draws an NVGSurface React view and its children.
 */
public class NVGSurfaceView extends TextureView {
    private static final String TAG = "TextureView";

    // Experiment with allowing TextureView to release the SurfaceTexture from the callback vs.
    // releasing it explicitly ourselves from the draw loop.  The latter seems to be problematic
    // in 4.4 (KK) -- set the flag to "false", rotate the screen a few times, then check the
    // output of "adb shell ps -t | grep `pid grafika`".
    //
    // Must be static or it'll get reset on every Activity pause/resume.
    private static volatile boolean sReleaseInCallback = true;

    Renderer renderer = new Renderer(this);
    NVGContext nvgContext;

    public NVGSurfaceView(ThemedReactContext context) {
        super(context);

        nvgContext = context.getNativeModule(NVGContext.class);

        setSurfaceTextureListener(renderer);

        renderer.start();
    }

    private static class Renderer extends Thread implements TextureView.SurfaceTextureListener {
        private Object mLock = new Object();        // guards mSurfaceTexture, mDone
        private SurfaceTexture mSurfaceTexture;
        private EglCore mEglCore;
        private boolean mDone;
        private SWIGTYPE_p_NVGcontext vg;

        public Renderer(NVGSurfaceView parent) {
            super("TextureViewGL Renderer");
        }

        @Override
        public void run() {
            while (true) {
                SurfaceTexture surfaceTexture = null;

                // Latch the SurfaceTexture when it becomes available.  We have to wait for
                // the TextureView to create it.
                synchronized (mLock) {
                    while (!mDone && (surfaceTexture = mSurfaceTexture) == null) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException ie) {
                            throw new RuntimeException(ie);     // not expected
                        }
                    }
                    if (mDone) {
                        break;
                    }
                }
                Log.d(TAG, "Got surfaceTexture=" + surfaceTexture);

                // Create an EGL surface for our new SurfaceTexture.  We're not on the same
                // thread as the SurfaceTexture, which is a concern for the *consumer*, which
                // wants to call updateTexImage().  Because we're the *producer*, i.e. the
                // one generating the frames, we don't need to worry about being on the same
                // thread.
                mEglCore = new EglCore(null, EglCore.FLAG_TRY_GLES3);
                WindowSurface windowSurface = new WindowSurface(mEglCore, mSurfaceTexture);
                windowSurface.makeCurrent();

                vg = nanovg.nvgCreateGLES2(0);

                // Render frames until we're told to stop or the SurfaceTexture is destroyed.
                doAnimation(windowSurface);

                windowSurface.release();
                mEglCore.release();
                if (!sReleaseInCallback) {
                    Log.i(TAG, "Releasing SurfaceTexture in renderer thread");
                    surfaceTexture.release();
                }
            }

            Log.d(TAG, "Renderer thread exiting");
        }

        /**
         * Draws updates as fast as the system will allow.
         * <p>
         * In 4.4, with the synchronous buffer queue queue, the frame rate will be limited.
         * In previous (and future) releases, with the async queue, many of the frames we
         * render may be dropped.
         * <p>
         * The correct thing to do here is use Choreographer to schedule frame updates off
         * of vsync, but that's not nearly as much fun.
         */
        private void doAnimation(WindowSurface eglSurface) {
            int width = eglSurface.getWidth();
            int height = eglSurface.getHeight();

            Log.d(TAG, "Animating " + width + "x" + height + " EGL surface");

            while (true) {
                // Check to see if the TextureView's SurfaceTexture is still valid.
                synchronized (mLock) {
                    SurfaceTexture surfaceTexture = mSurfaceTexture;
                    if (surfaceTexture == null) {
                        Log.d(TAG, "doAnimation exiting");
                        return;
                    }
                }

                // Still alive, render a frame.
                GLES20.glClearColor(1, 0, 0, 1.0f);
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_STENCIL_BUFFER_BIT);

//                nanovg.nvgBeginFrame(vg, width, height, 1);
//                nanovg.nvgBeginPath(vg);
//                nanovg.nvgMoveTo(vg, 10, 10);
//                nanovg.nvgLineTo(vg, 20, 20);
//                nanovg.nvgClosePath(vg);
//                nanovg.nvgFillColor(vg, nanovg.nvgRGBAf(1,0,0,1));
//                nanovg.nvgFill(vg);
//                nanovg.nvgEndFrame(vg);
            }
        }

        /**
         * Tells the thread to stop running.
         */
        public void halt() {
            synchronized (mLock) {
                mDone = true;
                mLock.notify();
            }
        }

        @Override   // will be called on UI thread
        public void onSurfaceTextureAvailable(SurfaceTexture st, int width, int height) {
            Log.d(TAG, "onSurfaceTextureAvailable(" + width + "x" + height + ")");
            synchronized (mLock) {
                mSurfaceTexture = st;
                mLock.notify();
            }
        }

        @Override   // will be called on UI thread
        public void onSurfaceTextureSizeChanged(SurfaceTexture st, int width, int height) {
            Log.d(TAG, "onSurfaceTextureSizeChanged(" + width + "x" + height + ")");
            // TODO: ?
        }

        @Override   // will be called on UI thread
        public boolean onSurfaceTextureDestroyed(SurfaceTexture st) {
            Log.d(TAG, "onSurfaceTextureDestroyed");

            // We set the SurfaceTexture reference to null to tell the Renderer thread that
            // it needs to stop.  The renderer might be in the middle of drawing, so we want
            // to return false here so that the caller doesn't try to release the ST out
            // from under us.
            //
            // In theory.
            //
            // In 4.4, the buffer queue was changed to be synchronous, which means we block
            // in dequeueBuffer().  If the renderer has been running flat out and is currently
            // sleeping in eglSwapBuffers(), it's going to be stuck there until somebody
            // tears down the SurfaceTexture.  So we need to tear it down here to ensure
            // that the renderer thread will break.  If we don't, the thread sticks there
            // forever.
            //
            // The only down side to releasing it here is we'll get some complaints in logcat
            // when eglSwapBuffers() fails.
            synchronized (mLock) {
                mSurfaceTexture = null;
            }
            if (sReleaseInCallback) {
                Log.i(TAG, "Allowing TextureView to release SurfaceTexture");
            }
            return sReleaseInCallback;
        }

        @Override   // will be called on UI thread
        public void onSurfaceTextureUpdated(SurfaceTexture st) {
            //Log.d(TAG, "onSurfaceTextureUpdated");
        }
    }
}
