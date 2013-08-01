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

 GMSMarker *marker;
 GMSCameraPosition *camera;
- (void)viewDidLoad
{
    [super viewDidLoad];
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
            _mapView.camera = [GMSCameraPosition cameraWithTarget:target zoom:6];
            
            
        }
    }];
    
    
}


- (IBAction)locateMe:(id)sender {
    NSLog(@"locateMe called");

    [self startStandardUpdates];
    
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
