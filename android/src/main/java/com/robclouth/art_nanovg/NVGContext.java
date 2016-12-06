package com.robclouth.art_nanovg;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.robclouth.art_nanovg.gles.EglCore;
import com.robclouth.art_nanovg.nanovg.SWIGTYPE_p_NVGcontext;
import com.robclouth.art_nanovg.nanovg.nanovg;
import com.robclouth.art_nanovg.nanovg.nanovgConstants;

/**
 * Created by Rob on 28/11/2016.
 */

public class NVGContext extends ReactContextBaseJavaModule {
    static {
        System.loadLibrary("nanovg_jni");
    }

    EglCore mEglCore = new EglCore(null, 0);
    SWIGTYPE_p_NVGcontext vg = nanovg.nvgCreateGLES2(0);

    public NVGContext(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "NVGContext";
    }
}
