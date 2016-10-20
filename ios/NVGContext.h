
#import <UIKit/UIKit.h>

#import "RCTBridge.h"

@interface NVGContext : NSObject <RCTBridgeModule>

- (EAGLContext *) getContext;

@end


@interface RCTBridge (NVGContext)

@property (nonatomic, readonly) NVGContext *nvgContext;

@end
