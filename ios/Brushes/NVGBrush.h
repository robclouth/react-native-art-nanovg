/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import <CoreGraphics/CoreGraphics.h>
#import <Foundation/Foundation.h>
#import "nanovg.h"

@interface NVGBrush : NSObject

/* @abstract */
- (instancetype)initWithArray:(NSArray *)data NS_DESIGNATED_INITIALIZER;

- (void)applyFill:(NVGcontext*)context;

- (void)applyStroke:(NVGcontext*)context;

@end
