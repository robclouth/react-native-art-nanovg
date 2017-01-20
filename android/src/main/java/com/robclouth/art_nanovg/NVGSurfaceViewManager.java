/**
 * Copyright (c) 2015-present, Rob Clouth.
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.robclouth.art_nanovg;

import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNodeAPI;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * ViewManager for NVGSurfaceView React views. Renders as a {@link NVGSurfaceView} and handles
 * invalidating the native view on shadow view updates happening in the underlying tree.
 */
public class NVGSurfaceViewManager extends
        BaseViewManager<NVGSurfaceView, NVGSurfaceViewShadowNode> {

    private static final String REACT_CLASS = "NVGSurfaceView";

    private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
        @Override
        public long measure(
                YogaNodeAPI node,
                float width,
                YogaMeasureMode widthMode,
                float height,
                YogaMeasureMode heightMode) {
            throw new IllegalStateException("SurfaceView should have explicit width and height set");
        }
    };

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public NVGSurfaceViewShadowNode createShadowNodeInstance() {
        NVGSurfaceViewShadowNode node = new NVGSurfaceViewShadowNode();
        node.setMeasureFunction(MEASURE_FUNCTION);
        return node;
    }

    @Override
    public Class<NVGSurfaceViewShadowNode> getShadowNodeClass() {
        return NVGSurfaceViewShadowNode.class;
    }

    @Override
    protected NVGSurfaceView createViewInstance(ThemedReactContext reactContext) {
        return new NVGSurfaceView(reactContext);
    }

    @Override
    public void updateExtraData(NVGSurfaceView root, Object extraData) {
        root.setSurfaceTextureListener((NVGSurfaceViewShadowNode) extraData);
    }
}
