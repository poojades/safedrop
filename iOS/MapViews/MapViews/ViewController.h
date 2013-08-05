//
//  ViewController.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 7/31/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <MapKit/MapKit.h>
#import <MessageUI/MessageUI.h>
#import "Globals.h"




@interface ViewController : UIViewController <CLLocationManagerDelegate,UIAlertViewDelegate,MFMessageComposeViewControllerDelegate,UINavigationControllerDelegate>{CLLocationManager *locationManager;
}
@property (weak, nonatomic) IBOutlet UIView *mapViewOnScreen;
@property (weak, nonatomic) IBOutlet UIView *infoPanel;
@property (weak, nonatomic) IBOutlet UIView *buttonPanel;
@property (weak, nonatomic) IBOutlet UIView *notificationPanel;
@property (weak, nonatomic) IBOutlet UILabel *infoLabel;
@property (weak, nonatomic) IBOutlet UIButton *inButtonButtonPanel;
@property (weak, nonatomic) IBOutlet UILabel *inLabelButtonPanel;
@property (nonatomic) RequestStatus status;
@property (weak, nonatomic) IBOutlet UIButton *cancelRequestButton;
@property (weak, nonatomic) IBOutlet UILabel *subLocationLabel;

@property (weak, nonatomic) IBOutlet UIButton *safeDropIcon;

@property (weak, nonatomic) IBOutlet UIButton *notificationButton;


@property (weak, nonatomic) NSTimer *timer;

- (IBAction)clickButtoninButtonPanel:(id)sender;

- (IBAction)clickButtoninNotificationPanel:(id)sender;
- (IBAction)closeSafeDrop:(id)sender;

@end
