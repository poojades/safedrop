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

typedef enum  {
    New,
    Archived,
    Done,
    Cancel,
    Pending,
    InProgress,
    Accepted
} RequestStatus;


@interface ViewController : UIViewController <CLLocationManagerDelegate>{CLLocationManager *locationManager;
    
    RequestStatus status;
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


- (IBAction)clickButtoninButtonPanel:(id)sender;


@end
