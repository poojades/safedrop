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
        self.idLabel.text = [NSString stringWithFormat:@"%d",theNotification.id];
        self.senderLabel.text=theNotification.sender;
        self.receiverLabel.text=theNotification.receiver;
        self.requestIdLabel.text=[NSString stringWithFormat:@"%d", [theNotification requestId]];
        self.textLabel.text=theNotification.text;
        NSLog(@"%@",theNotification.created);
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

- (IBAction)acceptVolunteer:(id)sender {
    NSString *address = kAcceptRequestURL;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    @try {
        NSString* requestId= [GlobalSettings objectAtIndex:0];
        [params setValue:kRequesterUsername forKey:@"volunteerEmail"];
        [params setValue:requestId forKey:@"requestId"];
    }
    @catch (NSException *exception) {
        <#handler#>
    }
    @finally {
        <#statements#>
    }
    
    NSString* requestId= [GlobalSettings objectAtIndex:0];

    
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
                @try {
                    [GlobalSettings replaceObjectAtIndex:0 withObject:result];
                    NSLog(@"%@",GlobalSettings);
                    status=Accepted;
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
@end
