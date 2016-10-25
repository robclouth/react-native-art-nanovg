
#import <UIKit/UIKit.h>
#import <GLKit/GLKit.h>

#import "RCTBridge.h"
#import "nanovg.h"

@interface NVGContext : NSObject <RCTBridgeModule>

- (EAGLContext *) getContext;
- (NVGcontext *) getNVGContext;
@end


@interface RCTBridge (NVGContext)

@property (nonatomic, readonly) NVGContext *nvgContext;

@end
