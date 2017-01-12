/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.robclouth.art_nanovg;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.view.TextureView;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.robclouth.art_nanovg.gles.WindowSurface;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

import javax.annotation.Nullable;

/**
 * Shadow node for NVG virtual tree root - NVGSurfaceView
 */
public class NVGSurfaceViewShadowNode extends LayoutShadowNode
        implements TextureView.SurfaceTextureListener {

    private @Nullable WindowSurface windowSurface;

    public NVGSurfaceViewShadowNode(){
    }

    public WindowSurface getWindowSurface(){
        return windowSurface;
    }


    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public boolean isVirtualAnchor() {
        return true;
    }

    @Override
    public void onCollectExtraUpdates(UIViewOperationQueue uiUpdater) {
        super.onCollectExtraUpdates(uiUpdater);
        queueRender();
        uiUpdater.enqueueUpdateExtraData(getReactTag(), this);
    }

    private void queueRender(){
        if (windowSurface == null) {
            markChildrenUpdatesSeen(this);
            return;
        }

        getThemedContext().getNativeModule(NVGContext.class).queueRender(this);
    }

    public void drawOutput(SWIGTYPE_p_NVGcontext vg) {
        GLES20.glClearColor(0, 0, 0, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_STENCIL_BUFFER_BIT);

        if(windowSurface.getWidth() < 0 || windowSurface.getHeight() < 0)
            return;

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        GLES20.glViewport(0, 0, windowSurface.getWidth(), windowSurface.getHeight());

        float pixelDensity = getThemedContext().getResources().getDisplayMetrics().density;

        nanovg.nvgReset(vg);
        nanovg.nvgBeginFrame(vg, windowSurface.getWidth(), windowSurface.getHeight(), pixelDensity);

        for (int i = 0; i < getChildCount(); i++) {
            NVGVirtualNode child = (NVGVirtualNode) getChildAt(i);
            child.draw(vg, 1f);
            child.markUpdateSeen();
        }

        nanovg.nvgEndFrame(vg);
    }

    private void markChildrenUpdatesSeen(ReactShadowNode shadowNode) {
        for (int i = 0; i < shadowNode.getChildCount(); i++) {
            ReactShadowNode child = shadowNode.getChildAt(i);
            child.markUpdateSeen();
            markChildrenUpdatesSeen(child);
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        NVGContext nvgContext = getThemedContext().getNativeModule(NVGContext.class);

        synchronized (nvgContext.getLock()) {
            windowSurface = new WindowSurface(nvgContext.getGLContext(), surface);
            queueRender();
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        NVGContext nvgContext = getThemedContext().getNativeModule(NVGContext.class);

        synchronized (nvgContext.getLock()) {
            if(windowSurface != null){
                windowSurface.release();
                windowSurface = null;
            }
            
            return true;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        NVGContext nvgContext = getThemedContext().getNativeModule(NVGContext.class);

        synchronized (nvgContext.getLock()) {
            queueRender();
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
}
