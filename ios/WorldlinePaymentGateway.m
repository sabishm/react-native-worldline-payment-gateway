#import "WorldlinePaymentGateway.h"

@implementation WorldlinePaymentGateway

RCT_EXPORT_MODULE()

// Example method
// See // https://facebook.github.io/react-native/docs/native-modules-ios
RCT_REMAP_METHOD(startPayment,
                 foo:(NSDictionary *) data
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{

  resolve(@"Working");
}

@end
