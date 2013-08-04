//
//  NotificationsDataController.h
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Notification;
@interface NotificationsDataController : NSObject

@property (nonatomic, copy) NSMutableArray *masterNotificationList;


- (NSUInteger)countOfList;
- (Notification *)objectInListAtIndex:(NSUInteger)theIndex;
- (void)refreshNotifications:(NSString *)userEmail andLastRefreshId:(NSString *)lastRefreshId;
- (void)addNotification:(Notification *)notification;

@end
