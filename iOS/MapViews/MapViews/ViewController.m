//
//  ViewController.m
//  MapViews
//
//  Created by Sankha Subhra Pathak on 7/31/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "ViewController.h"
#import <GoogleMaps/GoogleMaps.h>
#import <QuartzCore/QuartzCore.h>
#import "iOSRequest.h"

@interface ViewController ()

@end



@implementation ViewController {
    GMSMapView *_mapView;
}

@synthesize mapViewOnScreen;
@synthesize infoPanel;
@synthesize buttonPanel;
@synthesize notificationPanel;
@synthesize infoLabel;
@synthesize inButtonButtonPanel;
@synthesize inLabelButtonPanel;
@synthesize status;
@synthesize cancelRequestButton;


NSString *zipCode;
GMSMarker *marker;
GMSCameraPosition *camera;
int requestId;

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self startStandardUpdates];
    status=New;
    
	// Do any additional setup after loading the view, typically from a nib.
    
    
     camera = [GMSCameraPosition cameraWithLatitude:-33.86
                                                            longitude:151.20
                                                                 zoom:6];
    
    
    
       
    // border radius
    [mapViewOnScreen.layer setCornerRadius:10.0f];
    
    // border
    [mapViewOnScreen.layer setBorderColor:[UIColor lightGrayColor].CGColor];
    [mapViewOnScreen.layer setBorderWidth:1.5f];
    
    // drop shadow
    [mapViewOnScreen.layer setShadowColor:[UIColor blackColor].CGColor];
    [mapViewOnScreen.layer setShadowOpacity:0.8];
    [mapViewOnScreen.layer setShadowRadius:3.0];
    [mapViewOnScreen.layer setShadowOffset:CGSizeMake(2.0, 2.0)];
    
   
    
    _mapView = [GMSMapView mapWithFrame:mapViewOnScreen.bounds camera:camera];
    _mapView.myLocationEnabled = YES;
    
    // border radius
    [_mapView.layer setCornerRadius:10.0f];
    
    // border
    [_mapView.layer setBorderColor:[UIColor lightGrayColor].CGColor];
    [_mapView.layer setBorderWidth:1.5f];
    
    // drop shadow
    [_mapView.layer setShadowColor:[UIColor blackColor].CGColor];
    [_mapView.layer setShadowOpacity:0.8];
    [_mapView.layer setShadowRadius:3.0];
    [_mapView.layer setShadowOffset:CGSizeMake(2.0, 2.0)];
    
    
    // border radius
    [infoPanel.layer setCornerRadius:5.0f];
    

    
    // drop shadow
    [infoPanel.layer setShadowColor:[UIColor blackColor].CGColor];
    [infoPanel.layer setShadowOpacity:0.8];
    [infoPanel.layer setShadowRadius:3.0];
    [infoPanel.layer setShadowOffset:CGSizeMake(2.0, 2.0)];
    
    
    
    // border radius
    [buttonPanel.layer setCornerRadius:5.0f];
    

    // drop shadow
    [buttonPanel.layer setShadowColor:[UIColor blackColor].CGColor];
    [buttonPanel.layer setShadowOpacity:0.8];
    [buttonPanel.layer setShadowRadius:3.0];
    [buttonPanel.layer setShadowOffset:CGSizeMake(2.0, 2.0)];

    
    // border radius
    [notificationPanel.layer setCornerRadius:5.0f];
    

    
    // drop shadow
    [notificationPanel.layer setShadowColor:[UIColor blackColor].CGColor];
    [notificationPanel.layer setShadowOpacity:0.8];
    [notificationPanel.layer setShadowRadius:3.0];
    [notificationPanel.layer setShadowOffset:CGSizeMake(2.0, 2.0)];
    
    
    [self.mapViewOnScreen addSubview:_mapView];
    [self.mapViewOnScreen insertSubview:infoPanel aboveSubview:mapViewOnScreen];


    
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSUInteger) supportedInterfaceOrientations {
    // Return a bitmask of supported orientations. If you need more,
    // use bitwise or (see the commented return).
    return UIInterfaceOrientationMaskPortrait;
    // return UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskPortraitUpsideDown;
}

- (UIInterfaceOrientation) preferredInterfaceOrientationForPresentation {
    // Return the orientation you'd prefer - this is what it launches to. The
    // user can still rotate. You don't have to implement this method, in which
    // case it launches in the current orientation
    return UIDeviceOrientationPortrait;
}

- (BOOL)shouldAutorotate{
    return NO;
}

- (void)locationManager:(CLLocationManager *)manager
     didUpdateLocations:(NSArray *)locations {
    NSLog(@"update locations called");
    CLLocation* location = [locations lastObject];
    NSString *msg = [[NSString alloc]initWithFormat:@"Lat %+.6f, Long %+.6f\n",location.coordinate.latitude,location.coordinate.longitude];
    CLGeocoder * geoCoder = [[CLGeocoder alloc] init];
    
    
    [geoCoder reverseGeocodeLocation:location completionHandler:^(NSArray *placemarks, NSError *error) {
        for (CLPlacemark * placemark in placemarks) {
            infoLabel.text=[NSString stringWithFormat:@"%@",[placemark performSelector:NSSelectorFromString(@"locality")]];
            
            marker.position = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
            
            marker.position = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
            marker.title = @"Your Location";
            marker.snippet =[NSString stringWithFormat:@"%@ %@ [%@]", [placemark performSelector:NSSelectorFromString(@"name")],[placemark performSelector:NSSelectorFromString(@"locality")],[placemark performSelector:NSSelectorFromString(@"postalCode")]];
            marker.infoWindowAnchor = CGPointMake(0, 0);
            marker.icon = [UIImage imageNamed:@"startMarker"];
            marker.map = _mapView;
            
            
            CLLocationCoordinate2D target =
            CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
            _mapView.camera = [GMSCameraPosition cameraWithTarget:target zoom:kGMSMaxZoomLevel - 8];
            [self sendLocationToServer];
            
        }
    }];
    
    
}

- (void) sendLocationToServer {
    NSString *address = @"http://128.2.204.85:6080/SafeDropServices/rest/service/setUserInfo";
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    NSString *tmp = [[NSString alloc] initWithFormat:@"%f", marker.position.latitude];
    
     [params setValue:@"sankhasp@cmu.edu" forKey:@"email"];
    [params setValue:tmp forKey:@"lastlat"];
    
    tmp = [[NSString alloc] initWithFormat:@"%f", marker.position.longitude];
    
    [params setValue:tmp forKey:@"lastlong"];
    
    [params setValue:@"15213" forKey:@"zip"];
    
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
            } else {
                NSLog(@"ERROR: %@",error);
            }
        });
    }];

}


- (void) createPickupRequestToServer {
    inLabelButtonPanel.text=@"[Creating New Request]";
    NSString *address = @"http://128.2.204.85:6080/SafeDropServices/rest/service/requestPickup";
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    [params setValue:@"sankhasp@cmu.edu" forKey:@"email"];
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
                @try {
                   requestId = result.integerValue;
                    status = New;
                    [cancelRequestButton setHidden:FALSE];
                    [inButtonButtonPanel setTitle:@"Request Created" forState:UIControlStateNormal];
                    [inButtonButtonPanel setEnabled:FALSE];
                    inLabelButtonPanel.text=@"[Asking nearby volunteers...]";
                }
                @catch (NSException *exception) {
                    inLabelButtonPanel.text=@"[Failed to Create New Request]";
                }
                
            } else {
                inLabelButtonPanel.text=@"[Failed to Create New Request]";
                NSLog(@"ERROR: %@",error);
            }
        });
    }];
    
}


- (IBAction)clickButtoninButtonPanel:(id)sender {

    if (status)
    {
        status=New;
    }
    switch (status) {
        case New:
            [self createPickupRequestToServer];
            break;
        default:
            break;
    }

}

- (IBAction)clickButtoninNotificationPanel:(id)sender {
    UIStoryboard *sb = [UIStoryboard storyboardWithName:@"NotificationStoryBoard" bundle:nil];
    UIViewController *vc = [sb instantiateViewControllerWithIdentifier:@"MasterViewController"];
    vc.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    [self presentViewController:vc animated:YES completion:NULL];
    
}


- (void)startStandardUpdates
{
    
      NSLog(@"startStandardUpdates called");
    if (nil == locationManager){
        locationManager = [[CLLocationManager alloc] init];
        locationManager.delegate = self;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest;
        locationManager.distanceFilter = 0;
        [locationManager startUpdatingLocation];
    }
    if (nil == marker){
        marker  = [[GMSMarker alloc] init];
    }
}




@end
