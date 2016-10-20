/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGShapeManager.h"

#import "NVGShape.h"
#import "RCTConvert+NVG.h"

@implementation NVGShapeManager

RCT_EXPORT_MODULE()

- (NVGRenderable *)node
{
  return [NVGShape new];
}

RCT_EXPORT_VIEW_PROPERTY(d, NSArray*)

@end
