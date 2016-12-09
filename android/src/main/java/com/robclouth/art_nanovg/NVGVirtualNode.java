/**
 * Copyright (c) 2015-present, Rob Clouth.
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.robclouth.art_nanovg;

import javax.annotation.Nullable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ReactShadowNode;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Base class for NVGView virtual nodes: {@link NVGGroupShadowNode}, {@link NVGShapeShadowNode} and
 * indirectly for {@link NVGTextShadowNode}.
 */
public abstract class NVGVirtualNode extends ReactShadowNode {

    protected static final float MIN_OPACITY_FOR_DRAW = 0.01f;

    private static final float[] sMatrixData = new float[9];

    protected float mOpacity = 1f;

    protected final float mScale;

    public NVGVirtualNode() {
        mScale = DisplayMetricsHolder.getWindowDisplayMetrics().density;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public abstract void draw(SWIGTYPE_p_NVGcontext vg, float opacity);

    protected final void saveAndSetupNVGContext(SWIGTYPE_p_NVGcontext vg) {
        nanovg.nvgSave(vg);
        nanovg.nvgTransform(vg,
                sMatrixData[0],
                sMatrixData[1],
                sMatrixData[2],
                sMatrixData[3],
                sMatrixData[4],
                sMatrixData[5]);
    }

    protected void restoreNVGContext(SWIGTYPE_p_NVGcontext vg) {
        nanovg.nvgRestore(vg);
    }

    @ReactProp(name = "opacity", defaultFloat = 1f)
    public void setOpacity(float opacity) {
        mOpacity = opacity;
        markUpdated();
    }

    @ReactProp(name = "transform")
    public void setTransform(@Nullable ReadableArray transformArray) {
        if (transformArray != null) {
            int matrixSize = PropHelper.toFloatArray(transformArray, sMatrixData);
            if (matrixSize != 6 && matrixSize != -1) {
                throw new JSApplicationIllegalArgumentException("Transform matrices must be of size 6");
            }
        }

        markUpdated();
    }

}
