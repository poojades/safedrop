//
//  Notification.h
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Notification : NSObject

@property (nonatomic) NSString* id;
@property (nonatomic, copy) NSString *text;
@property (nonatomic) NSString* requestId;
@property (nonatomic, copy) NSString *receiver;
@property (nonatomic, copy) NSString *sender;
@property (nonatomic, copy) NSString *type;
@property (nonatomic, strong) NSDate *created;

-(id)initWithId:(NSString *)id text:(NSString *)text  requestId:(NSString *)requestId  receiver:(NSString *)receiver  sender:(NSString *)sender type:(NSString *)type created:(NSDate *)created;



@end
