//
//  DetailViewController.m
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "DetailViewController.h"
#import "Notification.h"

@interface DetailViewController ()
- (void)configureView;
@end

@implementation DetailViewController

#pragma mark - Managing the detail item

- (void)setNotification:(Notification *) newNotification
{
    if (_notification != newNotification) {
        _notification = newNotification;
        
        // Update the view.
        [self configureView];
    }
}



- (void)configureView
{
    // Update the user interface for the detail item.
    Notification *theNotification = self.notification;
    
    static NSDateFormatter *formatter = nil;
    if (formatter == nil) {
        formatter = [[NSDateFormatter alloc] init];
        [formatter setDateStyle:NSDateFormatterMediumStyle];
    }
    if (theNotification) {
        self.idLabel.text = theNotification.id;
        self.senderLabel.text=theNotification.sender;
        self.receiverLabel.text=theNotification.receiver;
        self.requestIdLabel.text=theNotification.requestId;
        self.textLabel.text=theNotification.text;
        self.createdLabel.text = [formatter stringFromDate:(NSDate *)theNotification.created];
        self.typeLabel.text=theNotification.type;
    }
}




- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self configureView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}




- (IBAction)accept:(id)sender {

    //if user is requester
    NSString* requestId = [GlobalSettings objectAtIndex:0];
    
    if (![requestId isEqualToString:@"0"]) {
        NSString *address = kAcceptVolunteerURL;
        NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
        [params setValue:self.notification.sender forKey:@"volunteerEmail"];
        [params setValue:requestId forKey:@"requestId"];
        
        [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
            dispatch_async(dispatch_get_main_queue(), ^{
                if (!error) {
                    NSLog(@"SUCCESS : %@", result);
                    @try {
                        status=InProgress;
                        [GlobalSettings insertObject:@"0" atIndex:1];
                    }
                    @catch (NSException *exception) {
                        NSLog(@"Exception : %@", exception);
                        //show alert
                    }
                } else {
                    //show alert
                    NSLog(@"ERROR: %@",error);
                }
            });
        }];

    }
    else{
        NSString *address = kAcceptRequesterURL;
        NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
        requestId = self.notification.requestId;
        [params setValue:kRequesterUsername forKey:@"volunteerEmail"];
        [params setValue:requestId forKey:@"requestId"];
        
        [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
            dispatch_async(dispatch_get_main_queue(), ^{
                if (!error) {
                    NSLog(@"SUCCESS : %@", result);
                    @try {
                        status=Accepted;
                        [GlobalSettings insertObject:self.notification.requestId  atIndex:0];
                        [GlobalSettings insertObject:@"0" atIndex:1];
                    }
                    @catch (NSException *exception) {
                        NSLog(@"Exception : %@", exception);
                        //show alert
                    }
                } else {
                    //show alert
                    NSLog(@"ERROR: %@",error);
                }
            });
        }];
    }
    
    
}
@end
