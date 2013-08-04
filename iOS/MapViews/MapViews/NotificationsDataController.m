//
//  NotificationsDataController.m
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "NotificationsDataController.h"

#import "Notification.h"
#import "iOSRequest.h"

@interface NotificationsDataController ()
- (void)initializeDefaultDataList;
@end

@implementation NotificationsDataController

/*
- (void)initializeDefaultDataList {
    NSMutableArray *notificationList = [[NSMutableArray alloc] init];
    self.masterNotificationList = notificationList;
    Notification *notification;
    NSDate *today = [NSDate date];
    
    notification = [[Notification alloc] initWithId:10 text:@"10" requestId:12 recevier:@"hi" sender:@"hi" type:@"N" created:today];
    
    
    [self addNotification:notification];
}

 */

- (void)initializeDefaultDataList {
    self.masterNotificationList = [[NSMutableArray alloc] init];
}

- (void)setMasterNotificationList:(NSMutableArray *)newList {
    if (_masterNotificationList != newList) {
        _masterNotificationList = [newList mutableCopy];
    }
}

-(void)refreshNotifications:(NSString *)userEmail andLastRefreshId:(NSString *)lastRefreshId
{
    [iOSRequest refreshNotifications:kRequesterUsername andLastRefreshId:lastRefreshId onCompletion:^(NSDictionary *data){
        NSArray* notifcations = [data objectForKey:@"notifications"];
            for (NSDictionary *dict in notifcations) {
                
                NSLog(@"DICT: %@", dict);
                Notification *notification;
                NSString *id = [dict objectForKey:@"id"];
                NSString *requestId = [dict objectForKey:@"requestid"];
                
                NSString *text = [dict objectForKey:@"text"];
                
                NSString *receiver = [dict objectForKey:@"receiver"];
                
                NSString *sender = [dict objectForKey:@"sender"];
                NSDate *created = [dict objectForKey:@"created"];
                NSString *type = [dict objectForKey:@"type"];
                
                notification = [[Notification alloc] initWithId:id text:text requestId:requestId receiver:receiver sender:sender type:type created:created];
                
                //[[Notification alloc] initWithId:id text:text requestId:requestId  recevier:receiver sender:sender type:type created:created];
                [self addNotification:notification];
            }
    }];
}

- (id)init {
    if (self = [super init]) {
        [self initializeDefaultDataList];
        return self;
    }
    return nil;
}

- (NSUInteger)countOfList {
    return [self.masterNotificationList count];
}

- (Notification *)objectInListAtIndex:(NSUInteger)theIndex {
    return [self.masterNotificationList objectAtIndex:theIndex];
}

- (void)addNotification:(Notification *)notification {
    [self.masterNotificationList addObject:notification];
}


@end
