/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGSurfaceViewManager.h"

#import "NVGSurfaceView.h"

@implementation NVGSurfaceViewManager

RCT_EXPORT_MODULE()

- (UIView *)view
{

  NVGSurfaceView * v;
  v = [[NVGSurfaceView alloc] initWithBridge:self.bridge];
  return v;
}

@end
