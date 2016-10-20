/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGLinearGradient.h"

#import "RCTConvert+NVG.h"
#import "RCTLog.h"

@implementation NVGLinearGradient
{
  CGPoint _startPoint;
  CGPoint _endPoint;
  float _colours[8];
}

- (instancetype)initWithArray:(NSArray<NSNumber *> *)array
{
  if ((self = [super initWithArray:array])) {
    if (array.count < 15) {
      RCTLogError(@"-[%@ %@] expects 15 elements, received %@",
                  self.class, NSStringFromSelector(_cmd), array);
      return nil;
    }
    _startPoint = [RCTConvert CGPoint:array offset:1];
    _endPoint = [RCTConvert CGPoint:array offset:3];
    
    for(int i = 0; i < 8; i++)
        _colours[i] = [array[i + 5] doubleValue];
    
  }
  return self;
}

- (void)dealloc
{

}

- (void)applyFill:(NVGcontext*)context
{
  NVGpaint paint = nvgLinearGradient(context, _startPoint.x, _startPoint.y, _endPoint.x, _endPoint.y, nvgRGBAf(_colours[0], _colours[1], _colours[2], _colours[3]), nvgRGBAf(_colours[4], _colours[5], _colours[6], _colours[7] ));
  nvgFillPaint(context, paint);
  [super applyFill:context];
}

- (void)applyStroke:(NVGcontext*)context
{
  NVGpaint paint = nvgLinearGradient(context, _startPoint.x, _startPoint.y, _endPoint.x, _endPoint.y, nvgRGBAf(_colours[0], _colours[1], _colours[2], _colours[3]), nvgRGBAf(_colours[4], _colours[5], _colours[6], _colours[7] ));
  nvgStrokePaint(context, paint);
  [super applyStroke:context];

}

@end
