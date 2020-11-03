//
//  WebviewController.h
//  DemoIPG
//
//  Created by WorldlineMacbook2 on 17/04/18.
//  Copyright © 2018 WorldlineMacbook2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PaymentResponse.h"

@protocol IPGPaymentControllerDelegate <NSObject>

-(void)didTransactionComplete:(PaymentResponse *)response;
-(void)didTransactionCancelledByUser;

@end

@interface WebviewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *lblNavBarTitle;
@property (weak, nonatomic) IBOutlet UIWebView *ipgWebView;
@property (weak, nonatomic) IBOutlet UIView *navBarView;
@property (weak, nonatomic) id<IPGPaymentControllerDelegate> delegate;

@property (nonatomic, strong) NSURLRequest *request;

- (IBAction)cancelButtonPressed:(id)sender;

@end
