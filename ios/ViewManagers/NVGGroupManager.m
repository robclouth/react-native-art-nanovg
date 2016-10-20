/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGGroupManager.h"

#import "NVGGroup.h"
#import "RCTConvert+NVG.h"

@implementation NVGGroupManager

RCT_EXPORT_MODULE()

- (NVGNode *)node
{
  return [NVGGroup new];
}

RCT_EXPORT_VIEW_PROPERTY(clipping, CGRect)

@end
