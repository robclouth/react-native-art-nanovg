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
import android.graphics.Path;
import android.graphics.RectF;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.robclouth.art_nanovg.nanovg.NVGcolor;
import com.robclouth.art_nanovg.nanovg.NVGlineCap;
import com.robclouth.art_nanovg.nanovg.NVGsolidity;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;
import com.robclouth.art_nanovg.nanovg.nanovgConstants;

import static com.robclouth.art_nanovg.nanovg.NVGsolidity.NVG_HOLE;

/**
 * Shadow node for virtual NVGShape view
 */
public class NVGShapeShadowNode extends NVGVirtualNode {

    private static final int CAP_BUTT = 0;
    private static final int CAP_ROUND = 1;
    private static final int CAP_SQUARE = 2;
    private static final int CAP_BEVEL = 3;
    private static final int CAP_MITER = 4;

    private static final int JOIN_BEVEL = 2;
    private static final int JOIN_MITER = 0;
    private static final int JOIN_ROUND = 1;

    private static final int PATH_TYPE_ARC = 4;
    private static final int PATH_TYPE_CLOSE = 1;
    private static final int PATH_TYPE_CURVETO = 3;
    private static final int PATH_TYPE_LINETO = 2;
    private static final int PATH_TYPE_MOVETO = 0;

    protected
    @Nullable
    ReadableArray mShapePath;

    private
    @Nullable
    float[] mStrokeColor;

    private
    @Nullable
    float[] mFillColor;

    private
    @Nullable
    float[] mStrokeDash;

    private float mStrokeWidth = 1;
    private int mStrokeCap = CAP_ROUND;
    private int mStrokeJoin = JOIN_ROUND;

    @ReactProp(name = "d")
    public void setShapePath(@Nullable ReadableArray shapePath) {
        mShapePath = shapePath;
        markUpdated();
    }

    @ReactProp(name = "stroke")
    public void setStroke(@Nullable ReadableArray strokeColors) {
        mStrokeColor = PropHelper.toFloatArray(strokeColors);
        markUpdated();
    }

    @ReactProp(name = "strokeDash")
    public void setStrokeDash(@Nullable ReadableArray strokeDash) {
        mStrokeDash = PropHelper.toFloatArray(strokeDash);
        markUpdated();
    }

    @ReactProp(name = "fill")
    public void setFill(@Nullable ReadableArray fillColors) {
        mFillColor = PropHelper.toFloatArray(fillColors);
        markUpdated();
    }

    @ReactProp(name = "strokeWidth", defaultFloat = 1f)
    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        markUpdated();
    }

    @ReactProp(name = "strokeCap", defaultInt = CAP_ROUND)
    public void setStrokeCap(int strokeCap) {
        mStrokeCap = strokeCap;
        markUpdated();
    }

    @ReactProp(name = "strokeJoin", defaultInt = JOIN_ROUND)
    public void setStrokeJoin(int strokeJoin) {
        mStrokeJoin = strokeJoin;
        markUpdated();
    }

    @Override
    public void draw(SWIGTYPE_p_NVGcontext vg, float opacity) {
        opacity *= mOpacity;
        if (opacity > MIN_OPACITY_FOR_DRAW) {
            saveAndSetupNVGContext(vg);

            setupPath(vg);

            drawFill(vg, opacity);
            drawStroke(vg, opacity);

            restoreNVGContext(vg);
        }
        markUpdateSeen();
    }

    private void setupPath(SWIGTYPE_p_NVGcontext vg){
        nanovg.nvgBeginPath(vg);

        int i = 0;
        while (i < mShapePath.size()) {
            int type = mShapePath.getInt(i++);
            switch (type) {
                case 0:
                    nanovg.nvgMoveTo(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    break;
                case 1:
                    nanovg.nvgClosePath(vg);
                    if(mShapePath.getBoolean(i++))
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
                case 2:
                    nanovg.nvgLineTo(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    break;
                case 3:
                    nanovg.nvgBezierTo(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    break;
                case 4:
                    nanovg.nvgArc(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            mShapePath.getInt(i++)
                    );
                    break;
                case 5:
                    nanovg.nvgCircle(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    if(mShapePath.getBoolean(i++))
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
                case 6:
                    nanovg.nvgEllipse(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    if(mShapePath.getBoolean(i++))
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
                case 7:
                    nanovg.nvgRect(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    if(mShapePath.getBoolean(i++))
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
                case 8:
                    nanovg.nvgRoundedRectVarying(vg,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale,
                            (float)mShapePath.getDouble(i++) * mScale
                    );
                    if(mShapePath.getBoolean(i++))
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
                case 9:
                    nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                    break;
            }
        }
    }

    protected void drawStroke(SWIGTYPE_p_NVGcontext vg, float opacity) {
        if (mStrokeWidth == 0 || mStrokeColor == null || mStrokeColor.length == 0) {
            return;
        }

        nanovg.nvgLineCap(vg, mStrokeCap);
        nanovg.nvgLineJoin(vg, mStrokeJoin);
        nanovg.nvgStrokeWidth(vg, mStrokeWidth * mScale);

        int colorType = (int) mStrokeColor[0];
        switch (colorType) {
            case 0:
                nanovg.nvgStrokeColor(vg, nanovg.nvgRGBAf(
                        mStrokeColor[1],
                        mStrokeColor[2],
                        mStrokeColor[3],
                        mStrokeColor.length > 4 ? mStrokeColor[4] * opacity : opacity
                ));
                break;
            default:
                // TODO(6352048): Support gradients etc.
                FLog.w(ReactConstants.TAG, "NVG: Color type " + colorType + " not supported!");
        }

        if (mStrokeDash != null && mStrokeDash.length > 0) {
            // TODO(6352067): Support dashes
            FLog.w(ReactConstants.TAG, "NVG: Dashes are not supported yet!");
        }

        nanovg.nvgStroke(vg);
    }

    protected void drawFill(SWIGTYPE_p_NVGcontext vg, float opacity) {
        if (mFillColor != null && mFillColor.length > 0) {
            int colorType = (int) mFillColor[0];
            switch (colorType) {
                case 0:
                    nanovg.nvgFillColor(vg, nanovg.nvgRGBAf(
                            mFillColor[1],
                            mFillColor[2],
                            mFillColor[3],
                            mFillColor.length > 4 ? mFillColor[4] * opacity : opacity
                    ));
                    break;
                default:
                    // TODO(6352048): Support gradients etc.
                    FLog.w(ReactConstants.TAG, "NVG: Color type " + colorType + " not supported!");
            }

            nanovg.nvgFill(vg);
        }
    }
}
