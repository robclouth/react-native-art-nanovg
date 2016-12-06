/**
 * Copyright (c) 2015-present, Rob Clouth.
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */



package com.robclouth.art_nanovg;

import android.view.View;

import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

/**
 * ViewManager for all shadowed NVG views: Group, Shape and Text. Since these never get rendered
 * into native views and don't need any logic (all the logic is in {@link NVGSurfaceView}), this
 * "stubbed" ViewManager is used for all of them.
 */
public class NVGRenderableViewManager extends ViewManager<View, ReactShadowNode> {

    /* package */ static final String CLASS_GROUP = "NVGGroup";
    /* package */ static final String CLASS_SHAPE = "NVGShape";
    /* package */ static final String CLASS_TEXT = "NVGText";

    private final String mClassName;

    public static NVGRenderableViewManager createNVGGroupViewManager() {
        return new NVGRenderableViewManager(CLASS_GROUP);
    }

    public static NVGRenderableViewManager createNVGShapeViewManager() {
        return new NVGRenderableViewManager(CLASS_SHAPE);
    }

    public static NVGRenderableViewManager createNVGTextViewManager() {
        return new NVGRenderableViewManager(CLASS_TEXT);
    }

    private NVGRenderableViewManager(String className) {
        mClassName = className;
    }

    @Override
    public String getName() {
        return mClassName;
    }

    @Override
    public ReactShadowNode createShadowNodeInstance() {
        if (CLASS_GROUP.equals(mClassName)) {
            return new NVGGroupShadowNode();
        } else if (CLASS_SHAPE.equals(mClassName)) {
            return new NVGShapeShadowNode();
        } else if (CLASS_TEXT.equals(mClassName)) {
            return new NVGTextShadowNode();
        } else {
            throw new IllegalStateException("Unexpected type " + mClassName);
        }
    }

    @Override
    public Class<? extends ReactShadowNode> getShadowNodeClass() {
        if (CLASS_GROUP.equals(mClassName)) {
            return NVGGroupShadowNode.class;
        } else if (CLASS_SHAPE.equals(mClassName)) {
            return NVGShapeShadowNode.class;
        } else if (CLASS_TEXT.equals(mClassName)) {
            return NVGTextShadowNode.class;
        } else {
            throw new IllegalStateException("Unexpected type " + mClassName);
        }
    }

    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {
        throw new IllegalStateException("NVGShape does not map into a native view");
    }

    @Override
    public void updateExtraData(View root, Object extraData) {
        throw new IllegalStateException("NVGShape does not map into a native view");
    }
}
