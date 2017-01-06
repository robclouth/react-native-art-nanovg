package com.robclouth.art_nanovg.brushes;

import com.facebook.react.bridge.ReadableArray;
import com.robclouth.art_nanovg.nanovg.NVGpaint;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Created by Rob on 04/01/2017.
 */

public abstract class NVGPaint extends NVGBrush {

    public NVGPaint(ReadableArray array) {
        super(array);
    }

    protected abstract NVGpaint setupPaint(SWIGTYPE_p_NVGcontext vg);

    @Override
    public void applyFill(SWIGTYPE_p_NVGcontext vg) {
        NVGpaint paint = setupPaint(vg);

        if(paint != null)
            nanovg.nvgFillPaint(vg, paint);
    }

    @Override
    public void applyStroke(SWIGTYPE_p_NVGcontext vg) {
        NVGpaint paint = setupPaint(vg);

        if(paint != null)
            nanovg.nvgStrokePaint(vg, paint);
    }
}
