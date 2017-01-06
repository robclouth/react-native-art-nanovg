package com.robclouth.art_nanovg.brushes;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;

/**
 * Created by Rob on 04/01/2017.
 */

public abstract class NVGBrush {
    protected ReadableArray mArray;

    public static NVGBrush createBrush(ReadableArray array){
        if(array == null)
            return null;

        int colorType = array.getInt(0);
        switch (colorType) {
            case 0:
                return new NVGSolidColour(array);
            case 1:
                return new NVGLinearGradient(array);
            default:
                FLog.w(ReactConstants.TAG, "NVG: Color type " + colorType + " not supported!");
                return null;
        }
    }

    public NVGBrush(ReadableArray array){
        mArray = array;
    }

    public abstract void applyFill(SWIGTYPE_p_NVGcontext vg);
    public abstract void applyStroke(SWIGTYPE_p_NVGcontext vg);
}
