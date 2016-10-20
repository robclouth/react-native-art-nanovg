/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import <Foundation/Foundation.h>

#import "NVGBrush.h"
#import "NVGCGFloatArray.h"
#import "NVGNode.h"

@interface NVGRenderable : NVGNode

@property (nonatomic, strong) NVGBrush *fill;
@property (nonatomic, strong) NVGBrush *stroke;
@property (nonatomic, assign) CGFloat strokeWidth;
@property (nonatomic, assign) CGLineCap strokeCap;
@property (nonatomic, assign) CGLineJoin strokeJoin;
@property (nonatomic, assign) NVGCGFloatArray strokeDash;

@end
