/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGNode.h"

#import "NVGContainer.h"

@implementation NVGNode

- (void)insertReactSubview:(UIView *)subview atIndex:(NSInteger)atIndex
{
  [super insertReactSubview:subview atIndex:atIndex];
  [self insertSubview:subview atIndex:atIndex];
  [self invalidate];
}

- (void)removeReactSubview:(UIView *)subview
{
  [super removeReactSubview:subview];
  [self invalidate];
}

- (void)didUpdateReactSubviews
{
  // Do nothing, as subviews are inserted by insertReactSubview:
}

- (void)setOpacity:(CGFloat)opacity
{
  [self invalidate];
  _opacity = opacity;
}

- (void)setTransform:(CGAffineTransform)transform
{
  [self invalidate];
  super.transform = transform;
}

- (void)invalidate
{
  id<NVGContainer> container = (id<NVGContainer>)self.superview;
  [container invalidate];
}

- (void)renderTo:(NVGcontext*)context;
{
  // apply transformations
  nvgSave(context);
  nvgTransform(context,
    self.transform.a,
    self.transform.b,
    self.transform.c,
    self.transform.d,
    self.transform.tx,
    self.transform.ty);
  
  [self renderLayerTo:context];
  nvgRestore(context);
}

- (void)renderLayerTo:(CGContextRef)context
{
  // abstract
}

@end
