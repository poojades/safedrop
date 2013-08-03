//
//  MasterViewController.h
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>

@class NotificationsDataController;

@interface MasterViewController : UITableViewController

@property (strong, nonatomic) NotificationsDataController *dataController;
- (IBAction)goBackToSafeDrop:(id)sender;




@end
