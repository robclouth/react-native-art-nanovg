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
    private static final String TAG = "NVGSurfaceView";

    public NVGSurfaceView(ThemedReactContext context) {
        super(context);
        setOpaque(false);
    }
}
