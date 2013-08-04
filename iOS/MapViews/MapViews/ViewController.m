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

#import "NSString+WebService.h"

@interface ViewController ()

@end



@implementation ViewController {
    GMSMapView *_mapView;
}


@synthesize uiLabel;
@synthesize mapViewOnScreen;
@synthesize infoPanel;
@synthesize buttonPanel;
@synthesize notificationPanel;
@synthesize infoLabel;
@synthesize inButtonButtonPanel;
@synthesize inLabelButtonPanel;
@synthesize cancelRequestButton;
@synthesize notificationButton;


NSString *zipCode;
GMSMarker *SelfMarker;
GMSMarker *OtherMarker;
GMSCameraPosition *camera;


- (void)getDirections {
    
    
    NSString *url = [NSString stringWithFormat:@"https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&sensor=false", SelfMarker.position.latitude, SelfMarker.position.longitude, OtherMarker.position.latitude, OtherMarker.position.longitude];
    NSLog(url);
    
    
    [iOSRequest requestRESTGET:url onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error || [result isEqualToString:@""]) {
                NSLog(@"---------------------> %@",error);
            } else {
                NSDictionary *response = [result JSON];
                
                
                
                int points_count = 0;
                if ([[response objectForKey:@"routes"] count])
                    points_count = [[[[[[response objectForKey:@"routes"] objectAtIndex:0] objectForKey:@"legs"] objectAtIndex:0] objectForKey:@"steps"] count];
                
                
                 CLLocationCoordinate2D points[points_count * 2];
                
                GMSMutablePath *path = [GMSMutablePath path];
                
                int j = 0;
                NSArray *steps = nil;
                if (points_count && [[[[response objectForKey:@"routes"] objectAtIndex:0] objectForKey:@"legs"] count])
                    steps = [[[[[response objectForKey:@"routes"] objectAtIndex:0] objectForKey:@"legs"] objectAtIndex:0] objectForKey:@"steps"];
                for (int i = 0; i < points_count; i++) {
                    
                    double st_lat = [[[[steps objectAtIndex:i] objectForKey:@"start_location"] valueForKey:@"lat"] doubleValue];
                    double st_lon = [[[[steps objectAtIndex:i] objectForKey:@"start_location"] valueForKey:@"lng"] doubleValue];
                    //NSLog(@"lat lon: %f %f", st_lat, st_lon);
                        points[j] = CLLocationCoordinate2DMake(st_lat, st_lon);
                        j++;
                    double end_lat = [[[[steps objectAtIndex:i] objectForKey:@"end_location"] valueForKey:@"lat"] doubleValue];
                    double end_lon = [[[[steps objectAtIndex:i] objectForKey:@"end_location"] valueForKey:@"lng"] doubleValue];
                        points[j] = CLLocationCoordinate2DMake(end_lat, end_lon);
//                        endCoordinate = CLLocationCoordinate2DMake(end_lat, end_lon);
                        j++;
                   // NSLog(@"%d",j);
                }
                
                for (int i = 0; i < points_count*2; i++) {
                    [path addLatitude:points[i].latitude longitude:points[i].longitude];
                }
//                MKPolyline *polyline = [MKPolyline polylineWithCoordinates:points count:points_count * 2];
//                [_mapView addOverlay:polyline];
                GMSPolyline *polyline = [GMSPolyline polylineWithPath:path];
                polyline.strokeColor = [UIColor greenColor];
                polyline.strokeWidth = 10.f;
                polyline.geodesic = YES;
                polyline.map = _mapView;

            }
        });}];
    
    

    
    
    
}




- (void)viewDidLoad
{
   
    [super viewDidLoad];
    [self startStandardUpdates];
    [self getRequestUpdates];

    
    
       
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
    
   
    
    if (!_mapView){
        _mapView = [GMSMapView mapWithFrame:mapViewOnScreen.bounds camera:camera];
        _mapView.myLocationEnabled = YES;
    }
    
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
            
            SelfMarker.position = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
            SelfMarker.title = @"Your Location";
            SelfMarker.snippet =[NSString stringWithFormat:@"%@ %@ [%@]", [placemark performSelector:NSSelectorFromString(@"name")],[placemark performSelector:NSSelectorFromString(@"locality")],[placemark performSelector:NSSelectorFromString(@"postalCode")]];
            SelfMarker.infoWindowAnchor = CGPointMake(0, 0);
            SelfMarker.icon = [UIImage imageNamed:@"startMarker"];
            SelfMarker.map = _mapView;
            zipCode = [placemark performSelector:NSSelectorFromString(@"postalCode")];
            
            CLLocationCoordinate2D target =
            CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
            _mapView.camera = [GMSCameraPosition cameraWithTarget:target zoom:kGMSMaxZoomLevel - 8];
            [self sendLocationToServer];
            if (status==InProgress){
                [self getOtherLocationFromServer];
                
            }
        }
    }];
    
    
}

- (void) sendLocationToServer {
    NSString *address = kSetUserInfoURL;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    NSString *tmp = [[NSString alloc] initWithFormat:@"%f", SelfMarker.position.latitude];
    
    [params setValue:kRequesterUsername forKey:@"email"];
    [params setValue:tmp forKey:@"lastlat"];
    
    tmp = [[NSString alloc] initWithFormat:@"%f", SelfMarker.position.longitude];
    
    [params setValue:tmp forKey:@"lastlong"];
    
    [params setValue:zipCode forKey:@"zip"];
    
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

- (void) getOtherLocationFromServer{
        NSString *basePath = kgetOtherUserInfoURL;
    
        NSString* requestId= [GlobalSettings objectAtIndex:0];
        NSString *fullPath = [basePath stringByAppendingFormat:@"/%@/%@",kRequesterUsername,requestId];
        
        NSLog(@"%@",fullPath);
    
    
    [iOSRequest requestRESTGET:fullPath onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error || [result isEqualToString:@""]) {
                    NSLog(@"---------------------> %@",error);
            } else {
                NSDictionary *otherLocation = [result JSON];
                NSLog(@"---------------------> %@",otherLocation);
                NSString *lat = [otherLocation objectForKey:@"lastlat"];
                NSString *longitude = [otherLocation objectForKey:@"lastlong"];
                
                
                CLLocation *LocationAtual = [[CLLocation alloc] initWithLatitude:[lat floatValue] longitude:[longitude floatValue]];
                if (nil == OtherMarker){
                    OtherMarker  = [[GMSMarker alloc] init];
                }
                
                OtherMarker.position = CLLocationCoordinate2DMake(LocationAtual.coordinate.latitude, LocationAtual.coordinate.longitude);
                  OtherMarker.infoWindowAnchor = CGPointMake(0, 0);
                OtherMarker.title = @"Other Person's Location";
            OtherMarker.map = _mapView;
                [self getDirections];
            }
        });}];
            
}


- (void) createPickupRequestToServer {
    inLabelButtonPanel.text=@"[Creating New Request]";
    NSString *address = kRequestPickupURL;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    [params setValue:kRequesterUsername forKey:@"email"];
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
                @try {
                    [GlobalSettings replaceObjectAtIndex:0 withObject:result];
                    NSLog(@"%@",GlobalSettings);
                    status=New;
                    [self getRequestUpdates];
                }
                @catch (NSException *exception) {
                    NSLog(@"Exception : %@", exception);
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

    if (!status)
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
    
    if (status!=InProgress){
        UIStoryboard *sb = [UIStoryboard storyboardWithName:@"NotificationStoryBoard" bundle:nil];
        UIViewController *vc = [sb instantiateViewControllerWithIdentifier:@"MasterViewController"];
        vc.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
        [self presentViewController:vc animated:YES completion:NULL];        
    }
    else{
        UIStoryboard *sb = [UIStoryboard storyboardWithName:@"MessageStoryBoard" bundle:nil];
        UIViewController *vc = [sb instantiateViewControllerWithIdentifier:@"MsgMasterViewController"];
        vc.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
        [self presentViewController:vc animated:YES completion:NULL];
    }
    
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
    if (nil == SelfMarker){
        SelfMarker  = [[GMSMarker alloc] init];
    }
  
}

- (void) getRequestUpdates
{
    
    NSLog(@"getRequestUpdates called");
    NSString* requestId;
    @try {
        requestId= [GlobalSettings objectAtIndex:0];
        if ([requestId isEqualToString:@"0"]) {
            status=NotCreated;
            [cancelRequestButton setHidden:TRUE];
            [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
            [inButtonButtonPanel setEnabled:TRUE];
            inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
            return;
        }
        NSString *address = [NSString stringWithFormat:@"%@/%@",kRequestStatusURL,requestId];
        NSLog(address);
        NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
        [params setValue:kRequesterUsername forKey:@"email"];
        [iOSRequest requestRESTGET:address onCompletion:^(NSString *result, NSError *error){
            dispatch_async(dispatch_get_main_queue(), ^{
                if (!error) {
                    NSLog(@"SUCCESS : %@", result);
                    if ([result isEqualToString:@"N"]){
                        status = New;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Created" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Asking nearby volunteers...]";
                    }
                    else if ([result isEqualToString:@"P"]){
                        
                        status=Pending;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Pending" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Waiting for confirmation...]";
                    }
                    else if ([result isEqualToString:@"A"]){
                        status=Accepted;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Accepted" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Check Notifications...]";
                    }
                    else if ([result isEqualToString:@"I"]){
                        
                        status=InProgress;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request inProgress" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[SafeDrop in session...]";
                        
                        NSString *msgButtonImageName = @"msg";
                        
                        UIImage *slImage = [UIImage imageNamed:msgButtonImageName];
                        
                        [self.notificationButton setImage:slImage forState:UIControlStateNormal];
                    }
                    else if ([result isEqualToString:@"D"]){
                        
                        status=Done;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Done" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Request Completed...]";
                    }
                    else if ([result isEqualToString:@"R"]){
                        
                        status=Archived;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Archived" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Request has been archived...]";
                    }
                    else if ([result isEqualToString:@"C"]){
                        
                        status=Cancel;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Cancelled" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Initimating volunteers...]";
                    }
                    else{
                        status=NotCreated;
                        NSLog(@"ERROR: %@",error);
                        [cancelRequestButton setHidden:TRUE];
                        [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:TRUE];
                        inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
                        
                    }
                    
                } else {
                    status=NotCreated;
                    [cancelRequestButton setHidden:TRUE];
                    [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
                    [inButtonButtonPanel setEnabled:TRUE];
                    inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
                }
            });
        }];

    }
    @catch (NSException *exception) {
        status=NotCreated;
        NSLog(@"ERROR: %@",exception);
        [cancelRequestButton setHidden:TRUE];
        [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
        [inButtonButtonPanel setEnabled:TRUE];
        inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
    }
    
    
}



@end
