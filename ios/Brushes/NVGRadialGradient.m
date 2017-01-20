/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGRadialGradient.h"

#import "RCTConvert+NVG.h"
#import <React/RCTLog.h>

@implementation NVGRadialGradient
{
//  CGGradientRef _gradient;
//  CGPoint _focusPoint;
//  CGPoint _centerPoint;
//  CGFloat _radius;
//  CGFloat _radiusRatio;
}

- (instancetype)initWithArray:(NSArray<NSNumber *> *)array
{
  if ((self = [super initWithArray:array])) {
    if (array.count < 7) {
      RCTLogError(@"-[%@ %@] expects 7 elements, received %@",
                  self.class, NSStringFromSelector(_cmd), array);
      return nil;
    }
//    _radius = [RCTConvert CGFloat:array[3]];
//    _radiusRatio = [RCTConvert CGFloat:array[4]] / _radius;
//    _focusPoint.x = [RCTConvert CGFloat:array[1]];
//    _focusPoint.y = [RCTConvert CGFloat:array[2]] / _radiusRatio;
//    _centerPoint.x = [RCTConvert CGFloat:array[5]];
//    _centerPoint.y = [RCTConvert CGFloat:array[6]] / _radiusRatio;
//    _gradient = CGGradientRetain([RCTConvert CGGradient:array offset:7]);
  }
  return self;
}

- (void)dealloc
{
}

- (void)applyFill:(NVGcontext*)context
{
//  if(stops > 2)
//        return;
//    
//    CGFloat x1 = [(NSString *)[self.points objectAtIndex:0] floatValue];
//    CGFloat y1 = [(NSString *)[self.points objectAtIndex:1] floatValue];
//    CGFloat x2 = [(NSString *)[self.points objectAtIndex:2] floatValue];
//    CGFloat y2 = [(NSString *)[self.points objectAtIndex:3] floatValue];
//    
//    NVGpaint paint = nvgLinearGradient(context, x1, y1, x2, y2, nvgRGBAf([arr[0] doubleValue],[arr[1] doubleValue], [arr[2] doubleValue], [arr[3] doubleValue]), nvgRGBAf([arr[4] doubleValue], [arr[5] doubleValue], [arr[6] doubleValue], [arr[7] doubleValue]));
//    
//    nvgFillPaint(context, paint);
  [super applyFill:context];

}

- (void)applyStroke:(NVGcontext*)context
{
//  const CGFloat *components = CGColorGetComponents(_color);
//  nvgStrokeColor(context, nvgRGBAf(components[0], components[1], components[2], components[3]));
  [super applyStroke:context];

}

@end
