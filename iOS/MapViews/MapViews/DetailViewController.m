//
//  DetailViewController.m
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "DetailViewController.h"
#import "Notification.h"
#import "NSString+WebService.h"
#import "CustomCell.h"

@interface DetailViewController ()
- (void)configureView;
@end

@implementation DetailViewController
@synthesize tableData;
@synthesize ratingValue;
@synthesize ratingCommentsText;

#pragma mark - Managing the detail item

- (void)setNotification:(Notification *) newNotification
{
    if (_notification != newNotification) {
        _notification = newNotification;
        
        // Update the view.
        [self configureView];
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *myIdentifier = @"CustomCell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:myIdentifier];
    
    
    @try {
        NSLog(@"%@",[self.tableData objectForKey:@"ratings"]);
        
        NSString *theName = [[[self.tableData objectForKey:@"ratings" ] objectAtIndex:indexPath.row] objectForKey:@"requester"];
        
        NSString *rtext = [[[self.tableData objectForKey:@"ratings" ] objectAtIndex:indexPath.row] objectForKey:@"text"];
        
        NSString *rvalue = [[[self.tableData objectForKey:@"ratings" ] objectAtIndex:indexPath.row] objectForKey:@"value"];
        
        
        [[(CustomCell *)cell byField] setText:theName];
        [[(CustomCell *)cell textField] setText:rtext];
        
        
        UIImage *dot, *star;
        dot = [UIImage imageNamed:@"dot.png"];
        star = [UIImage imageNamed:@"starred.png"];
        AMRatingControl *ratingControl = [[AMRatingControl alloc] initWithLocation:CGPointMake(210, 10) emptyImage:dot solidImage:star andMaxRating:5];
        ratingControl.rating = [ rvalue integerValue];
        ratingControl.enabled=NO;
        
        
        [cell.contentView addSubview:ratingControl];
        
        return cell;
        
    }
    @catch (NSException *exception) {
        NSString *theName = [self.tableData objectForKey:@"requester"];
        
        NSString *rtext = [self.tableData objectForKey:@"text"];
        
        NSString *rvalue = [self.tableData objectForKey:@"value"];
        
        
        [[(CustomCell *)cell byField] setText:theName];
        [[(CustomCell *)cell textField] setText:rtext];
        
        
        UIImage *dot, *star;
        dot = [UIImage imageNamed:@"dot.png"];
        star = [UIImage imageNamed:@"starred.png"];
        AMRatingControl *ratingControl = [[AMRatingControl alloc] initWithLocation:CGPointMake(210, 10) emptyImage:dot solidImage:star andMaxRating:5];
        ratingControl.rating = [ rvalue integerValue];
        ratingControl.enabled=NO;
        
        
        [cell.contentView addSubview:ratingControl];
        
        return cell;
        
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
    
    
    NSString *basePath = kgetRatingsURL;
    
    NSString *requesterAccepted = [GlobalSettings objectAtIndex:2];
    //    NSString *requesterAccepted = @"poojadesai@cmu.edu";
    NSString *fullPath = [basePath stringByAppendingFormat:@"/%@",requesterAccepted];
    
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
            tableData= [stringReply JSON];
        }
        @catch (NSException *exception) {
            NSLog(@"Exception : %@", exception);
            tableData=nil;
        }
    } else {
        tableData=nil;
    }
    
    
    
    
    [super viewDidLoad];
    
    
    NSString *myIdentifier = @"CustomCell";
    [self.reusableCellTableView registerNib:[UINib nibWithNibName:@"CustomCell" bundle:nil]
                     forCellReuseIdentifier:myIdentifier];
    
    
    
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
        NSString *address = kacceptVolunteerURL;
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
        NSString *address = kacceptRequesterURL;
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
