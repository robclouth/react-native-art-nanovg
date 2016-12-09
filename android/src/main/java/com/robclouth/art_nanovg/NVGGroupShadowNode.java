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
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Shadow node for virtual NVGGroup view
 */
public class NVGGroupShadowNode extends NVGVirtualNode {

    protected
    @Nullable
    RectF mClipping;

    @ReactProp(name = "clipping")
    public void setClipping(@Nullable ReadableArray clippingDims) {
        float[] clippingData = PropHelper.toFloatArray(clippingDims);
        if (clippingData != null) {
            mClipping = createClipping(clippingData);
            markUpdated();
        }
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public void draw(SWIGTYPE_p_NVGcontext vg, float opacity) {
        opacity *= mOpacity;
        if (opacity > MIN_OPACITY_FOR_DRAW) {
            saveAndSetupNVGContext(vg);

            if (mClipping != null) {
                nanovg.nvgScissor(vg, mClipping.left * mScale,
                        mClipping.top * mScale,
                        mClipping.width() * mScale,
                        mClipping.height() * mScale);
            }

            for (int i = 0; i < getChildCount(); i++) {
                NVGVirtualNode child = (NVGVirtualNode) getChildAt(i);
                child.draw(vg, opacity);
                child.markUpdateSeen();
            }

            restoreNVGContext(vg);
        }
    }

    /**
     * Creates a {@link RectF} from an array of dimensions
     * (e.g. [x, y, width, height])
     *
     * @param data the array of dimensions
     * @return the {@link RectF} that can used to clip the canvas
     */
    private static RectF createClipping(float[] data) {
        if (data.length != 4) {
            throw new JSApplicationIllegalArgumentException(
                    "Clipping should be array of length 4 (e.g. [x, y, width, height])");
        }
        RectF clippingRect = new RectF(
                data[0], data[1], data[0] + data[2], data[1] + data[3]);
        return clippingRect;
    }
}
