/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "NVGBrush.h"

#import "RCTDefines.h"

@implementation NVGBrush

- (instancetype)initWithArray:(NSArray *)data
{
  return [super init];
}

RCT_NOT_IMPLEMENTED(- (instancetype)init)

- (void)applyFill:(NVGcontext*)context
{
  nvgFill(context);
}

- (void)applyStroke:(NVGcontext*)context
{
  nvgStroke(context);
}

@end
