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
@property (nonatomic, assign) NSInteger lastRefreshId;

- (NSUInteger)countOfList;
- (Notification *)objectInListAtIndex:(NSUInteger)theIndex;
- (void)refreshNotifications:(NSString *)userEmail andLastRefreshId:(NSInteger *)lastRefreshId;
- (void)addNotification:(Notification *)notification;
- (NSInteger)getLastRefreshId;
- (void)setLastRefreshId:(NSInteger)id;
@end
