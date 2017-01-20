
#import "NVGContext.h"
#import <React/RCTConvert.h>
#import <React/RCTLog.h>

#define NANOVG_GLES2_IMPLEMENTATION
#import "nanovg_gl.h"
#import "nanovg_gl_utils.h"

@implementation NVGContext
{
  EAGLContext *_context;
  NVGcontext* vg;
}

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

- (void)setBridge:(RCTBridge *)bridge
{
  _context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];
  
  [EAGLContext setCurrentContext:_context];
  vg = nvgCreateGLES2(NVG_ANTIALIAS);
  
  if (!_context) {
    RCTLogError(@"Failed to initialize OpenGLES 2.0 context");
  }
}


-(void)dealloc {
  if(vg)
    nvgDeleteGLES2(vg);
}

- (EAGLContext *) getContext
{
  return _context;
}

- (NVGcontext *) getNVGContext
{
  return vg;
}

@end

@implementation RCTBridge (NVGContext)

- (NVGContext *)nvgContext
{
  return [self moduleForClass:[NVGContext class]];
}

@end
