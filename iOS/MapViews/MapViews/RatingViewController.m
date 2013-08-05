//
//  RatingViewController.m
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/4/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "RatingViewController.h"

#import "ViewController.h"

#import "CustomCell.h"

#import "iOSRequest.h"

#import "NSString+WebService.h"
@interface RatingViewController ()

@end



@implementation RatingViewController
@synthesize tableData;
@synthesize ratingValue;
@synthesize ratingCommentsText;



- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{

    // Do any additional setup after loading the view from its nib.

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
    
    //- (void)registerNib:(NSNib *)nib forIdentifier:(NSString *)identifier
    

    /*
    UIImage *dot, *star;
	dot = [UIImage imageNamed:@"dot.png"];
	star = [UIImage imageNamed:@"star.png"];
	AMRatingControl *imagesRatingControl = [[AMRatingControl alloc] initWithLocation:CGPointMake(110, 250)
                                                                          emptyImage:dot
                                                                          solidImage:star
                                                                        andMaxRating:5];
    
     [self.view addSubview:imagesRatingControl];*/
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row%2 == 0) {
        UIColor *altCellColor = [UIColor colorWithWhite:0.7 alpha:0.3];
        cell.backgroundColor = altCellColor;
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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


- (IBAction)goBackToSafeDrop:(id)sender {
    ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
    
    [self presentViewController:viewController animated:YES completion:nil];

}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self.tableData objectForKey:@"ratings" ] count];
}


- (IBAction)addRating:(id)sender {
    
    
   // addRating(@FormParam("requestId") int requestId, @FormParam("requesterEmail") String requesterEmail,
     //         @FormParam("volunteerEmail") String volunteerEmail, @FormParam("text") String text, @FormParam("value") float value)
    
    
    
    NSString *address = kaddRatingsURL;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    
    [params setValue:[GlobalSettings objectAtIndex:0] forKey:@"requestId"];
    
    
    [params setValue:kRequesterUsername forKey:@"requesterEmail"];
    
    [params setValue:[GlobalSettings objectAtIndex:3] forKey:@"volunteerEmail"];
    
    
    [params setValue:ratingCommentsText.text  forKey:@"text"];
    
   
    
    [params setValue: [ratingValue titleForSegmentAtIndex:ratingValue.selectedSegmentIndex] forKey:@"value"];

    
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
            } else {
                NSLog(@"ERROR: %@",error);
            }
        });
    }];

    address=kcloseRequestURL;
    
    params = [[NSMutableDictionary alloc] init];
    [params setValue:[GlobalSettings objectAtIndex:0] forKey:@"requestId"];

    
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"SUCCESS : %@", result);
                ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
                [self presentViewController:viewController animated:YES completion:nil];
            } else {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No network connection"
                                                                message:@"You must be connected to the internet to use this app."
                                                               delegate:nil
                                                      cancelButtonTitle:@"OK"
                                                      otherButtonTitles:nil];
                [alert show];
            }
        });
    }];
    
    
        
   
}
@end
