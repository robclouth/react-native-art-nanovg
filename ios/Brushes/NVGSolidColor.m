/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGSolidColor.h"

#import "RCTConvert+NVG.h"
#import <React/RCTLog.h>

@implementation NVGSolidColor
{
  CGColorRef _color;
}

- (instancetype)initWithArray:(NSArray<NSNumber *> *)array
{
  if ((self = [super initWithArray:array])) {
    _color = CGColorRetain([RCTConvert CGColor:array offset:1]);
  }
  return self;
}

- (void)dealloc
{
  CGColorRelease(_color);
}

- (void)applyFill:(NVGcontext*)context
{
  const CGFloat *components = CGColorGetComponents(_color);
  nvgFillColor(context, nvgRGBAf(components[0], components[1], components[2], components[3]));
  [super applyFill:context];
}

- (void)applyStroke:(NVGcontext*)context
{
  const CGFloat *components = CGColorGetComponents(_color);
  nvgStrokeColor(context, nvgRGBAf(components[0], components[1], components[2], components[3]));
  [super applyStroke:context];
}

@end
