package com.robclouth.art_nanovg.brushes;

import com.facebook.react.bridge.ReadableArray;
import com.robclouth.art_nanovg.nanovg.NVGcolor;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;

/**
 * Created by Rob on 04/01/2017.
 */

public class NVGSolidColour extends NVGBrush{
    private NVGcolor colour = null;

    public NVGSolidColour(ReadableArray array){
        super(array);

        try {
            colour = nanovg.nvgRGBAf(
                    (float) mArray.getDouble(1),
                    (float) mArray.getDouble(2),
                    (float) mArray.getDouble(3),
                    mArray.size() > 4 ? (float) mArray.getDouble(4) : 1
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void applyFill(SWIGTYPE_p_NVGcontext vg) {
        if(colour != null)
            nanovg.nvgFillColor(vg, colour);
    }

    @Override
    public void applyStroke(SWIGTYPE_p_NVGcontext vg) {
        if(colour != null)
            nanovg.nvgStrokeColor(vg, colour);
    }
}
