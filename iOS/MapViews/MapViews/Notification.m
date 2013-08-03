//
//  Notification.m
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "Notification.h"


@implementation Notification

-(id)initWithId:(NSInteger *)id text:(NSString *)text  requestId:(NSInteger *)requestId  receiver:(NSString *)receiver  sender:(NSString *)sender type:(NSString *)type created:(NSDate *)created{
    self = [super init];
    if (self) {
        _id=id;
        _text = text;
        _requestId = requestId;
        _receiver = receiver;
        _sender = sender;
        _type = type;
        _created = created;
        return self;
    }
    return nil;
}



@end
