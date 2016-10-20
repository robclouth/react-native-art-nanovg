
#import "NVGContext.h"
#import "RCTConvert.h"
#import "RCTLog.h"


@implementation NVGContext
{
  EAGLContext *_context;
}

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

- (void)setBridge:(RCTBridge *)bridge
{
  _context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];
  if (!_context) {
    RCTLogError(@"Failed to initialize OpenGLES 2.0 context");
  }
}

- (EAGLContext *) getContext
{
  return _context;
}

@end

@implementation RCTBridge (NVGContext)

- (NVGContext *)nvgContext
{
  return [self moduleForClass:[NVGContext class]];
}

@end
