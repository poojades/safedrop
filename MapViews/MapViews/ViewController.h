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
@interface ViewController : UIViewController <CLLocationManagerDelegate>{CLLocationManager *locationManager;
}
@property (weak, nonatomic) IBOutlet UIView *mapViewOnScreen;
@property (weak, nonatomic) IBOutlet UIView *infoPanel;
@property (weak, nonatomic) IBOutlet UIView *buttonPanel;
@property (weak, nonatomic) IBOutlet UIView *notificationPanel;
@property (weak, nonatomic) IBOutlet UILabel *infoLabel;
- (IBAction)locateMe:(id)sender;

@end
