/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGRenderable.h"

@implementation NVGRenderable

- (void)setFill:(NVGBrush *)fill
{
  [self invalidate];
  _fill = fill;
}

- (void)setStroke:(NVGBrush *)stroke
{
  [self invalidate];
  _stroke = stroke;
}

- (void)setStrokeWidth:(CGFloat)strokeWidth
{
  [self invalidate];
  _strokeWidth = strokeWidth;
}

- (void)setStrokeCap:(CGLineCap)strokeCap
{
  [self invalidate];
  _strokeCap = strokeCap;
}

- (void)setStrokeJoin:(CGLineJoin)strokeJoin
{
  [self invalidate];
  _strokeJoin = strokeJoin;
}

- (void)setStrokeDash:(NVGCGFloatArray)strokeDash
{
  if (strokeDash.array == _strokeDash.array) {
    return;
  }
  if (_strokeDash.array) {
    free(_strokeDash.array);
  }
  [self invalidate];
  _strokeDash = strokeDash;
}

- (void)dealloc
{
  if (_strokeDash.array) {
    free(_strokeDash.array);
  }
}

@end
