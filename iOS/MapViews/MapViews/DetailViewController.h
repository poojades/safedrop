//
//  DetailViewController.h
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Globals.h"
#import "iOSRequest.h"

@class Notification;


@interface DetailViewController : UIViewController

@property (strong, nonatomic) Notification *notification;
@property (weak, nonatomic) IBOutlet UILabel *senderLabel;
@property (weak, nonatomic) IBOutlet UILabel *receiverLabel;
@property (weak, nonatomic) IBOutlet UILabel *textLabel;
@property (weak, nonatomic) IBOutlet UILabel *idLabel;
@property (weak, nonatomic) IBOutlet UILabel *requestIdLabel;
@property (weak, nonatomic) IBOutlet UILabel *typeLabel;
@property (weak, nonatomic) IBOutlet UILabel *createdLabel;

- (IBAction)acceptVolunteer:(id)sender;

@end
