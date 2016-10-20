/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGShape.h"

@implementation NVGShape

- (void)setD:(NSArray*)d
{
  if (d == _d) {
    return;
  }
  [self invalidate];

  _d = d;
}

- (void)dealloc
{
}

- (void)renderTo:(NVGcontext*)context
{
  if ((!self.fill && !self.stroke) || !self.d) {
    return;
  }
  
    nvgBeginPath(context);

NSUInteger count = [self.d count];

#define NEXT_VALUE [self.d[i++] floatValue]

  CGMutablePathRef path = CGPathCreateMutable();
  CGPathMoveToPoint(path, NULL, 0, 0);
  
  NSUInteger i = 0;
    while (i < count) {
      NSUInteger type = [self.d[i++] unsignedIntegerValue];
      switch (type) {
        case 0:
        nvgMoveTo(context, NEXT_VALUE, NEXT_VALUE);
          break;
        case 1:
          nvgClosePath(context);
          break;
        case 2:
          nvgLineTo(context, NEXT_VALUE, NEXT_VALUE);
          break;
        case 3:
          nvgBezierTo(context, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE);
          break;
        case 4:
          nvgArc(context, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE);
          break;
        case 5:
          nvgCircle(context, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE);
          break;
        case 6:
          nvgEllipse(context, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE, NEXT_VALUE);
          break;
        
      }
    }
  
  
  if (self.fill) {
    [self.fill applyFill:context];
  }
  
  if (self.stroke) {
    nvgStrokeWidth(context, self.strokeWidth);
    nvgLineCap(context, self.strokeCap);
    nvgLineJoin(context, self.strokeJoin);

    [self.stroke applyStroke:context];      
  }
}

void CGPathEnumerationCallback(void *info, const CGPathElement *element)
{
    NVGcontext* context = info;
    switch (element->type) {
        case kCGPathElementMoveToPoint: {
            CGPoint point = element ->points[0];
            nvgMoveTo(context, point.x, point.y);
            break;
        }
        case kCGPathElementAddLineToPoint: {
            CGPoint point = element ->points[0];
            nvgLineTo(context, point.x, point.y);
            break;
        }
        case kCGPathElementAddQuadCurveToPoint: {
            CGPoint point1 = element->points[0];
            CGPoint point2 = element->points[1];
            nvgQuadTo(context, point1.x, point1.y, point2.x, point2.y);
            break;
        }
        case kCGPathElementAddCurveToPoint: {
            CGPoint point1 = element->points[0];
            CGPoint point2 = element->points[1];
            CGPoint point3 = element->points[2];
            nvgBezierTo(context, point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
            break;
        }
        case kCGPathElementCloseSubpath: {
            nvgClosePath(context);
            break;
        }
    }
}


@end
