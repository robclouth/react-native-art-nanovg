/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "UIView+React.h"
#import "nanovg.h"

/**
 * NVG nodes are implemented as empty UIViews but this is just an implementation detail to fit
 * into the existing view management. They should also be shadow views and painted on a background
 * thread.
 */

@interface NVGNode : UIView

@property (nonatomic, assign) CGFloat opacity;

- (void)invalidate;
- (void)renderTo:(NVGcontext*)context;
- (void)renderLayerTo:(NVGcontext*)context;

@end
