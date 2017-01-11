/**
 * Copyright (c) 2015-present, Rob Clouth.
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.robclouth.art_nanovg;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.robclouth.art_nanovg.brushes.NVGBrush;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

import javax.annotation.Nullable;

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
    float[] mStrokeDash;

    private
    @Nullable
    NVGBrush mFillBrush;

    private
    @Nullable
    NVGBrush mStrokeBrush;

    private float mStrokeWidth = 1;
    private int mStrokeCap = CAP_ROUND;
    private int mStrokeJoin = JOIN_ROUND;

    @ReactProp(name = "d")
    public void setShapePath(@Nullable ReadableArray shapePath) {
        mShapePath = shapePath;
        markUpdated();
    }

    @ReactProp(name = "stroke")
    public void setStroke(@Nullable ReadableArray strokeArray) {
        mStrokeBrush = NVGBrush.createBrush(strokeArray);
        markUpdated();
    }

    @ReactProp(name = "strokeDash")
    public void setStrokeDash(@Nullable ReadableArray strokeDash) {
        mStrokeDash = PropHelper.toFloatArray(strokeDash);
        markUpdated();
    }

    @ReactProp(name = "fill")
    public void setFill(@Nullable ReadableArray fillArray) {
        mFillBrush = NVGBrush.createBrush(fillArray);
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
            try {
                int type = mShapePath.getInt(i++);
                switch (type) {
                    case 0:
                        nanovg.nvgMoveTo(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        break;
                    case 1:
                        nanovg.nvgClosePath(vg);
                        if (mShapePath.getBoolean(i++))
                            nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                    case 2:
                        nanovg.nvgLineTo(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        break;
                    case 3:
                        nanovg.nvgBezierTo(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        break;
                    case 4:
                        nanovg.nvgArc(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++),
                                (float) mShapePath.getDouble(i++),
                                mShapePath.getInt(i++)
                        );
                        break;
                    case 5:
                        nanovg.nvgCircle(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        if (mShapePath.getBoolean(i++))
                            nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                    case 6:
                        nanovg.nvgEllipse(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        if (mShapePath.getBoolean(i++))
                            nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                    case 7:
                        nanovg.nvgRect(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        if (mShapePath.getBoolean(i++))
                            nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                    case 8:
                        nanovg.nvgRoundedRectVarying(vg,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale,
                                (float) mShapePath.getDouble(i++) * mScale
                        );
                        if (mShapePath.getBoolean(i++))
                            nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                    case 9:
                        nanovg.nvgPathWinding(vg, NVG_HOLE.swigValue());
                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void drawStroke(SWIGTYPE_p_NVGcontext vg, float opacity) {
        if (mStrokeWidth == 0 || mStrokeBrush == null) {
            return;
        }

        nanovg.nvgLineCap(vg, mStrokeCap);
        nanovg.nvgLineJoin(vg, mStrokeJoin);
        nanovg.nvgStrokeWidth(vg, mStrokeWidth * mScale);

        mStrokeBrush.applyStroke(vg);

        if (mStrokeDash != null && mStrokeDash.length > 0) {
            FLog.w(ReactConstants.TAG, "NVG: Dashes are not supported yet!");
        }

        nanovg.nvgStroke(vg);
    }

    protected void drawFill(SWIGTYPE_p_NVGcontext vg, float opacity) {
        if (mFillBrush != null) {
            mFillBrush.applyFill(vg);
            nanovg.nvgFill(vg);
        }
    }
}
