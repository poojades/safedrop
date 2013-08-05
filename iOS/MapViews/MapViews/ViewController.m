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

#import "RatingViewController.h"


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
@synthesize cancelRequestButton;
@synthesize notificationButton;
@synthesize safeDropIcon;
@synthesize subLocationLabel;

NSString *zipCode;
GMSMarker *SelfMarker;
GMSMarker *OtherMarker;
GMSCameraPosition *camera;

GMSPolyline *polyRoute;
GMSPolygon *polyRegion;


- (void)getDirections {
    NSString *url = [NSString stringWithFormat:@"https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&sensor=false", SelfMarker.position.latitude, SelfMarker.position.longitude, OtherMarker.position.latitude, OtherMarker.position.longitude];
    NSLog(@"%@",url);
    
    
    [iOSRequest requestRESTGET:url onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error || [result isEqualToString:@""]) {
                    NSLog(@"%@",error);
            } else {
                NSDictionary *response = [result JSON];
                
                @try {
                    NSString* encodedString = [[[[response objectForKey:@"routes"] objectAtIndex:0] objectForKey:@"overview_polyline"]objectForKey:@"points"];
                    const char *bytes = [encodedString UTF8String];
                    NSUInteger length = [encodedString lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
                    NSUInteger idx = 0;
                    
                    NSUInteger count = length / 4;
                    CLLocationCoordinate2D *coords = calloc(count, sizeof(CLLocationCoordinate2D));
                    NSUInteger coordIdx = 0;
                    
                    float latitude = 0;
                    float longitude = 0;
                    while (idx < length) {
                        char byte = 0;
                        int res = 0;
                        char shift = 0;
                        
                        do {
                            byte = bytes[idx++] - 63;
                            res |= (byte & 0x1F) << shift;
                            shift += 5;
                        } while (byte >= 0x20);
                        
                        float deltaLat = ((res & 1) ? ~(res >> 1) : (res >> 1));
                        latitude += deltaLat;
                        
                        shift = 0;
                        res = 0;
                        
                        do {
                            byte = bytes[idx++] - 0x3F;
                            res |= (byte & 0x1F) << shift;
                            shift += 5;
                        } while (byte >= 0x20);
                        
                        float deltaLon = ((res & 1) ? ~(res >> 1) : (res >> 1));
                        longitude += deltaLon;
                        
                        float finalLat = latitude * 1E-5;
                        float finalLon = longitude * 1E-5;
                        
                        CLLocationCoordinate2D coord = CLLocationCoordinate2DMake(finalLat, finalLon);
                        coords[coordIdx++] = coord;
                        
                        if (coordIdx == count) {
                            NSUInteger newCount = count + 10;
                            coords = realloc(coords, newCount * sizeof(CLLocationCoordinate2D));
                            count = newCount;
                        }
                    }
                    GMSMutablePath *pathForRegion= [GMSMutablePath path];
                    
                    for (int i = 0; i < coordIdx; i++) {
                        [pathForRegion addLatitude:coords[i].latitude longitude:coords[i].longitude];
                    }
                    
                    [pathForRegion addLatitude:OtherMarker.position.latitude longitude:OtherMarker.position.longitude];
                    
                    if (nil==polyRoute)
                    {
                        polyRoute=[GMSPolyline polylineWithPath:pathForRegion];
                    }
                    else{
                        polyRoute.map=nil;
                        polyRoute=[GMSPolyline polylineWithPath:pathForRegion];
                    }
                    
                    
                    polyRoute.strokeColor = [UIColor blueColor];
                    polyRoute.strokeWidth = 10.f;
                    polyRoute.geodesic = YES;
                    polyRoute.map = _mapView;
                    
                    
                    GMSCoordinateBounds *bounds;
                    bounds = [[GMSCoordinateBounds alloc] initWithCoordinate:SelfMarker.position
                                                                  coordinate:OtherMarker.position];
                    GMSCameraUpdate *update = [GMSCameraUpdate fitBounds:bounds
                                                             withPadding:300.0f];
                    [_mapView animateWithCameraUpdate:update];
                    [self showDistance];

                }
                @catch (NSException *exception) {
                    NSLog(@"%@",exception);
                }
            }
        });}];
    
}

-(void) showDistance{
    @try {
        CLLocation *firstLocation = [[CLLocation alloc] initWithLatitude:SelfMarker.position.latitude longitude:SelfMarker.position.longitude];
        CLLocation *secondLocation = [[CLLocation alloc] initWithLatitude:OtherMarker.position.latitude longitude:OtherMarker.position.longitude];
        CLLocationDistance distance = [firstLocation distanceFromLocation:secondLocation];
        subLocationLabel.text = [NSString stringWithFormat:@"Distance: %.2f miles",distance*0.000621371];
    }
    @catch (NSException *exception) {
        subLocationLabel.text = @"Failed to fetch distance";
    }
 
}


- (void) showZipRegion{
    NSString *url = [NSString stringWithFormat:@"http://maps.googleapis.com/maps/api/geocode/json?address=%@&sensor=false", zipCode];
    NSLog(@"%@",url);
    @try {
        [iOSRequest requestRESTGET:url onCompletion:^(NSString *result, NSError *error){
            dispatch_async(dispatch_get_main_queue(), ^{
                if (error || [result isEqualToString:@""]) {
                    NSLog(@"%@",error);
                } else {
                    NSDictionary *response = [result JSON];
                    NSString* x1 = [[[[[[response objectForKey:@"results"] objectAtIndex:0] objectForKey:@"geometry"]objectForKey:@"bounds"] objectForKey:@"northeast"]objectForKey:@"lat"];
                    NSString* y1 = [[[[[[response objectForKey:@"results"] objectAtIndex:0] objectForKey:@"geometry"]objectForKey:@"bounds"] objectForKey:@"northeast"]objectForKey:@"lng"];
                    NSString* x2 = [[[[[[response objectForKey:@"results"] objectAtIndex:0] objectForKey:@"geometry"] objectForKey:@"bounds"] objectForKey:@"southwest"]objectForKey:@"lat"];
                    NSString* y2 = [[[[[[response objectForKey:@"results"] objectAtIndex:0] objectForKey:@"geometry"] objectForKey:@"bounds"] objectForKey:@"southwest"]objectForKey:@"lng"];
                    
                    GMSMutablePath *pathForRegion= [GMSMutablePath path];
                    
                    [pathForRegion addLatitude:[x1 floatValue] longitude:[y1 floatValue]];
                    [pathForRegion addLatitude:[x2 floatValue] longitude:[y1 floatValue]];
                    [pathForRegion addLatitude:[x2 floatValue] longitude:[y1 floatValue]];
                    [pathForRegion addLatitude:[x2 floatValue] longitude:[y2 floatValue]];
                    [pathForRegion addLatitude:[x2 floatValue] longitude:[y2 floatValue]];
                    [pathForRegion addLatitude:[x1 floatValue] longitude:[y2 floatValue]];
                    [pathForRegion addLatitude:[x1 floatValue] longitude:[y2 floatValue]];
                    [pathForRegion addLatitude:[x1 floatValue] longitude:[y1 floatValue]];
                    
                    
                    if (nil==polyRegion)
                    {
                        polyRegion=[GMSPolygon polygonWithPath:pathForRegion];
                        
                    }
                    else{
                        polyRegion.map=nil;
                        polyRegion=[GMSPolygon polygonWithPath:pathForRegion];
                    }
                    
                    
                    polyRegion.fillColor = [UIColor colorWithRed:0.25 green:0 blue:0 alpha:0.05];
                    polyRegion.strokeColor = [UIColor brownColor];
                    polyRegion.strokeWidth = 2;
                    polyRegion.map = _mapView;
                    
                }
            });}];
    }
    @catch (NSException *exception) {
        NSLog(@"%@",exception);
    }
    
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
            if (status==NotCreated){
                CLLocationCoordinate2D target =
                CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude);
                _mapView.camera = [GMSCameraPosition cameraWithTarget:target zoom:kGMSMaxZoomLevel - 8];
            }
            [self sendLocationToServer];
            [self showZipRegion];
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

- (void) getEContact{
        NSString *basePath = kGetUserInfoURL;
        NSString *fullPath = [basePath stringByAppendingFormat:@"/%@",kRequesterUsername];
        
        NSLog(@"%@",fullPath);
    
    
    
    NSError        *error = nil;
    NSURLResponse  *response = nil;
    
    
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:fullPath]
                                                  cachePolicy:NSURLCacheStorageAllowedInMemoryOnly
                                              timeoutInterval:10];
    
    
    NSData *data =  [NSURLConnection sendSynchronousRequest: request returningResponse: &response error: &error];
    
    NSString* stringReply = (NSString *)[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    
    if (!error) {
        NSLog(@"SUCCESS : %@", stringReply );
        @try {
            NSDictionary *userInfo = [stringReply JSON];

            NSString *econtact = [userInfo objectForKey:@"econtact"];
            [GlobalSettings insertObject:econtact atIndex:2];
        }
        @catch (NSException *exception) {
            NSLog(@"Exception : %@", exception);
            [GlobalSettings insertObject:@"911" atIndex:2];
        }
    } else {
       [GlobalSettings insertObject:@"911" atIndex:2];
    }
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
    [_mapView clear];
    _mapView=nil;
    SelfMarker=nil;
    OtherMarker=nil;
    polyRoute.map=nil;
    polyRoute=nil;
    polyRegion.map=nil;
    polyRegion=nil;
    locationManager=nil;
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

- (IBAction)closeSafeDrop:(id)sender {
    
    UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Close SafeDrop!"
                                                      message:@"Have you reached your destination?"
                                                     delegate:self
                                            cancelButtonTitle:@"Not Yet"
                                            otherButtonTitles:nil];
    [message addButtonWithTitle:@"Reached"];
    [message addButtonWithTitle:@"Emergency"];
    [message show];
}
- (void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"SelfDrop" message:@"Unknown Error" delegate:self cancelButtonTitle:@"OK"otherButtonTitles: nil];
    
	switch (result) {
		case MessageComposeResultCancelled:
			NSLog(@"Cancelled");
			break;
		case MessageComposeResultFailed:
			[alert show];
			break;
		case MessageComposeResultSent:
			break;
		default:
			break;
	}
    [self dismissModalViewControllerAnimated:YES];
}


- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    NSString *title = [alertView buttonTitleAtIndex:buttonIndex];
    if([title isEqualToString:@"Reached"])
    {
        /*
        MFMessageComposeViewController *controller = [[MFMessageComposeViewController alloc] init];
        if([MFMessageComposeViewController canSendText])
        {
            controller.body = @"";
            controller.recipients = [NSArray arrayWithObjects:@"4126239247", nil];
            controller.messageComposeDelegate = self;
            [self presentModalViewController:controller animated:YES];
        }
         */
        [_mapView clear];
        _mapView=nil;
        SelfMarker=nil;
        OtherMarker=nil;
        polyRoute.map=nil;
        polyRoute=nil;
        polyRegion.map=nil;
        polyRegion=nil;
        locationManager=nil;
        RatingViewController *viewController=[[RatingViewController alloc]initWithNibName:@"RatingViewController" bundle:nil];
        
        [self presentViewController:viewController animated:YES completion:nil];

        
        NSLog(@"Reached was selected.");
    }
    else if([title isEqualToString:@"Emergency"])
    {
        [self getEContact];
        NSLog(@"Emergency was selected.");
        NSString *phoneNumber = [GlobalSettings objectAtIndex:2];
        NSString *telString = [NSString stringWithFormat:@"tel:%@,%@", phoneNumber, @""];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:telString]];
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
                         safeDropIcon.hidden=TRUE;
                    }
                    else if ([result isEqualToString:@"P"]){
                        
                        status=Pending;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Pending" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Waiting for confirmation...]";
                         safeDropIcon.hidden=TRUE;
                    }
                    else if ([result isEqualToString:@"A"]){
                        status=Accepted;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Accepted" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Check Notifications...]";
                        safeDropIcon.hidden=TRUE;
                    }
                    else if ([result isEqualToString:@"I"]){
                        
                        status=InProgress;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request inProgress" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[SafeDrop in session...]";
                        
                        safeDropIcon.hidden=FALSE;
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
                        safeDropIcon.hidden=TRUE;

                    }
                    else if ([result isEqualToString:@"R"]){
                        
                        status=Archived;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Archived" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Request has been archived...]";
                        safeDropIcon.hidden=TRUE;

                    }
                    else if ([result isEqualToString:@"C"]){
                        
                        status=Cancel;
                        [cancelRequestButton setHidden:FALSE];
                        [inButtonButtonPanel setTitle:@"Request Cancelled" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:FALSE];
                        inLabelButtonPanel.text=@"[Initimating volunteers...]";
                        safeDropIcon.hidden=TRUE;

                    }
                    else{
                        status=NotCreated;
                        NSLog(@"ERROR: %@",error);
                        [cancelRequestButton setHidden:TRUE];
                        [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
                        [inButtonButtonPanel setEnabled:TRUE];
                        inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
                         safeDropIcon.hidden=TRUE;
                        
                    }
                    
                } else {
                    status=NotCreated;
                    [cancelRequestButton setHidden:TRUE];
                    [inButtonButtonPanel setTitle:@"Pickup Request" forState:UIControlStateNormal];
                    [inButtonButtonPanel setEnabled:TRUE];
                    inLabelButtonPanel.text=@"[Create a SafeDrop Request...]";
                     safeDropIcon.hidden=TRUE;
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
