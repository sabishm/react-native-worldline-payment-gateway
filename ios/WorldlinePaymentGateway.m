#import "WorldlinePaymentGateway.h"

@implementation WorldlinePaymentGateway

RCT_EXPORT_MODULE()

// Example method
// See // https://facebook.github.io/react-native/docs/native-modules-ios
RCT_REMAP_METHOD(startPayment,
                 foo:(NSDictionary *) jsonObject
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
//  NSNumber *result = @([a floatValue] * [b floatValue]);
//
    
    dispatch_async(dispatch_get_main_queue(), ^{
        AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
        
        
        WebviewController *webViewController = [[WebviewController alloc] initWithNibName:@"WebviewController" bundle:[NSBundle bundleForClass:WebviewController.self]];
           
           webViewController.delegate = self;
           TransactionType txnType = Sale;
           NSString *orderID = @"938239829";
           WorldlineIPG *worldlineIPGObj = [[WorldlineIPG alloc] initWithEnvironmentType:Production];
           NSURLRequest *request = [worldlineIPGObj getStandardPaymentRequest:orderID transactionAmount:@"1000" transactionCurrency:@"INR" transactionDescription:@"This is test transaction" transactionType:txnType additionalField1:@"Info 1" additionalField2:@"Into 2" additionalField3:@"Info 3" additionalField4:@"" additionalField5:@"" additionalField6:@"" additionalField7:@"" additionalField8:@"" additionalField9:@"" additionalField10:@"" mid:@"" encryptionString:@"" requestDelegate:self];
           webViewController.request = request;
           
           [delegate.window.rootViewController presentViewController:webViewController animated:true completion:^{
               
           }];
    });
    
//    WebviewController *webViewController=[[WebviewController alloc] initWithNibName:@"WebviewController" bundle:[NSBundle bundleForClass:WebviewController.self]];
//    webViewController.delegate = self;
////
////
//    TransactionType txnType = Sale;
//    NSString *orderId = @"";
//    WorldlineIPG *worldLineIPGObj = [[WorldlineIPG alloc] initWithEnvironmentType:Production];
//    NSURLRequest *requestForStandardController = [worldLineIPGObj getStandardPaymentRequest:orderId
//                                                                          transactionAmount:@"1000"
//                                                                        transactionCurrency:@"INR"
//                                                                            requestDelegate:self];
//    webViewController.request = requestForStandardController;

//    [self presendViewController:webViewController animated:true completion:^{
//
//    }];
    resolve(@"Fine");
}


- (void)didTransactionComplete:(PaymentResponse *)response
{
    
}

- (void)didTransactionCancelledByUser
{
    
}

- (void)requestFailedError:(NSString *)errorMessage
{
    
}

@end
