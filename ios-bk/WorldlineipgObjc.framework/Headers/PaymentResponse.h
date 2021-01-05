//
//  PaymentResponse.h
//  DemoIPG
//
//  Created by WorldlineMacbook2 on 18/04/18.
//  Copyright © 2018 WorldlineMacbook2. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PaymentResponse : NSObject

@property(nonatomic,strong) NSString *transactionReferenceNo;
@property(nonatomic,strong) NSString *orderID;
@property(nonatomic,strong) NSString *amount;
@property(nonatomic,strong) NSString *statusCode;
@property(nonatomic,strong) NSString *statusDescription;
@property(nonatomic,strong) NSString *rrn;
@property(nonatomic,strong) NSString *authnCode;
@property(nonatomic,strong) NSString *authzCode;
@property(nonatomic,strong) NSString *authNStatus;
@property(nonatomic,strong) NSString *authZStatus;
@property(nonatomic,strong) NSString *responseCode;
@property(nonatomic,strong) NSString *captureStatus;
@property(nonatomic,strong) NSString *pgRefCancelReqID;
@property(nonatomic,strong) NSString *refundAmount;
@property(nonatomic,strong) NSString *transactionDateTime;
@property(nonatomic,strong) NSString *addtionalField1;
@property(nonatomic,strong) NSString *addtionalField2;
@property(nonatomic,strong) NSString *addtionalField3;
@property(nonatomic,strong) NSString *addtionalField4;
@property(nonatomic,strong) NSString *addtionalField5;
@property(nonatomic,strong) NSString *addtionalField6;
@property(nonatomic,strong) NSString *addtionalField7;
@property(nonatomic,strong) NSString *addtionalField8;
@property(nonatomic,strong) NSString *addtionalField9;
@property(nonatomic,strong) NSString *addtionalField10;


@end
