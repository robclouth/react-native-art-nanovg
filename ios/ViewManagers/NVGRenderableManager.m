/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGRenderableManager.h"

#import "RCTConvert+NVG.h"

@implementation NVGRenderableManager

RCT_EXPORT_MODULE()

- (NVGRenderable *)node
{
  return [NVGRenderable new];
}

RCT_EXPORT_VIEW_PROPERTY(strokeWidth, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(strokeCap, CGLineCap)
RCT_EXPORT_VIEW_PROPERTY(strokeJoin, CGLineJoin)
RCT_EXPORT_VIEW_PROPERTY(fill, NVGBrush)
RCT_EXPORT_VIEW_PROPERTY(stroke, NVGBrush)
RCT_EXPORT_VIEW_PROPERTY(strokeDash, NVGCGFloatArray)

@end
