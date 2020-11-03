//
//  MessageFormatter.h
//  WorldlineipgObjc
//
//  Created by WorldlineMacbook2 on 21/03/18.
//  Copyright © 2018 WorldlineMacbook2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "PaymentResponse.h"

typedef enum : NSUInteger {
    StandardPaymentRequest,
    CardCapturePaymentRequest,
} TransactionRequestType;

typedef enum : NSUInteger {
    CreditCard,
    DebitCard,
} PaymentType;

typedef enum : NSUInteger {
    PreAuth,
    Sale,
} TransactionType;

typedef enum : NSUInteger {
    Production,
    SIT,
} EnvironmentType;

// IPG Response Delegates
@protocol IPGResponseDelegate <NSObject>

-(void)didReceivePaymentResponse:(PaymentResponse *)response;
-(void)didReceiveInputError:(NSString *)errorMessage;

@end

// Request Format Delegate
@protocol IPGRequestDelegate <NSObject>

-(void)requestFailedError:(NSString *)errorMessage;

@end

@interface WorldlineIPG : NSObject

@property(nonatomic,weak) id<IPGResponseDelegate> worldlineIPGDelegate;

-(id)initWithEnvironmentType:(EnvironmentType) EnvType;

-(void)getTransactionStatus:(NSString *)orderID transactionRefNumber:(NSString *)txnRefNumber merchantID:(NSString *)mid encryptionString:(NSString *)encString statusResponseDelegate:(id<IPGResponseDelegate>)delegate;


-(void)cancelTransaction:(NSString *)orderID transactionRefNumber:(NSString *)txnRefNumber merchantID:(NSString *)mid encryptionString:(NSString *)encString cancelResponseDelegate:(id<IPGResponseDelegate>)delegate;


-(void)refundTransaction:(NSString *)orderID transactionRefNumber:(NSString *)txnRefNumber transactionAmount:(NSString *)amount merchantID:(NSString *)mid encryptionString:(NSString *)encString refundResponseDelegate:(id<IPGResponseDelegate>)delegate;



-(NSURLRequest *)getCardCapturePaymentRequest:(NSString *)orderID
                            transactionAmount:(NSString *)txnAmount
                            transactionCurrency:(NSString *)txnCurrency
                            transactionDescription:(NSString *)txnDescription
                            transactionType:(TransactionType)txnType
                            cardHolderName:(NSString *)cardHolderName
                            cardNumber:(NSString *)cardNumber
                            cardExpiryDate:(NSString *)cardExpiry
                            cvvNumber:(NSString *)cvvNumber
                            paymentType:(PaymentType)paymentType
                            additionalField1:(NSString *)additionalField1
                            additionalField2:(NSString *)additionalField2
                            additionalField3:(NSString *)additionalField3
                            additionalField4:(NSString *)additionalField4
                            additionalField5:(NSString *)additionalField5
                            additionalField6:(NSString *)additionalField6
                            additionalField7:(NSString *)additionalField7
                            additionalField8:(NSString *)additionalField8
                            additionalField9:(NSString *)additionalField9
                            additionalField10:(NSString *)additionalField10
                            mid:(NSString *)mid
                            encryptionString:(NSString *)encString
                            requestDelegate:(id<IPGRequestDelegate>)delegate;



-(NSURLRequest *)getStandardPaymentRequest:(NSString *)orderID
               transactionAmount:(NSString *)txnAmount
             transactionCurrency:(NSString *)txnCurrency
          transactionDescription:(NSString *)txnDescription
                 transactionType:(TransactionType)txnType
                additionalField1:(NSString *)additionalField1
                additionalField2:(NSString *)additionalField2
                additionalField3:(NSString *)additionalField3
                additionalField4:(NSString *)additionalField4
                additionalField5:(NSString *)additionalField5
                additionalField6:(NSString *)additionalField6
                additionalField7:(NSString *)additionalField7
                additionalField8:(NSString *)additionalField8
                additionalField9:(NSString *)additionalField9
                additionalField10:(NSString *)additionalField10
                             mid:(NSString *)mid
                encryptionString:(NSString *)encString
                 requestDelegate:(id<IPGRequestDelegate>)delegate;



@end
