package com.robclouth.art_nanovg.brushes;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.robclouth.art_nanovg.nanovg.NVGpaint;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Created by Rob on 04/01/2017.
 */

public class NVGLinearGradient extends NVGPaint {
    public NVGLinearGradient(ReadableArray array) {
        super(array);
    }

    @Override
    protected NVGpaint setupPaint(SWIGTYPE_p_NVGcontext vg) {
        if(mArray.size() < 15) {
            FLog.w(ReactConstants.TAG, "Linear gradient expects 15 elements");
            return null;
        }
        float scale = nanovg.nvgDevicePixelRatio(vg);

        try {
            return nanovg.nvgLinearGradient(vg,
                    (float) mArray.getDouble(1) * scale, (float) mArray.getDouble(2) * scale,
                    (float) mArray.getDouble(3) * scale, (float) mArray.getDouble(4) * scale,
                    nanovg.nvgRGBAf((float) mArray.getDouble(5), (float) mArray.getDouble(6), (float) mArray.getDouble(7), (float) mArray.getDouble(8)),
                    nanovg.nvgRGBAf((float) mArray.getDouble(9), (float) mArray.getDouble(10), (float) mArray.getDouble(11), (float) mArray.getDouble(12)));
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
