 //
//  MasterViewController.m
//  NotificationView
//
//  Created by Sankha Subhra Pathak on 8/2/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "MasterViewController.h"

#import "DetailViewController.h"

#import "NotificationsDataController.h"
#import "Notification.h"

#import "iOSRequest.h"

#import "DetailViewController.h"

#import "ViewController.h"

#import "Globals.h"




@implementation MasterViewController




- (void)awakeFromNib
{
    [super awakeFromNib];
    self.dataController = [[NotificationsDataController alloc] init];
       
}



- (void)viewDidLoad
{
    UIRefreshControl *refresh = [[UIRefreshControl alloc] init];
    refresh.attributedTitle = [[NSAttributedString alloc] initWithString:@"Pull to Refresh"];
    [refresh addTarget:self
                action:@selector(refreshView:)
      forControlEvents:UIControlEventValueChanged];
    self.refreshControl = refresh;
    [self refreshDataTable:TRUE];
    [super viewDidLoad];
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}


#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
return [self.dataController countOfList];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"NotificationCell";
    
    static NSDateFormatter *formatter = nil;
    if (formatter == nil) {
        formatter = [[NSDateFormatter alloc] init];
        [formatter setDateStyle:NSDateFormatterMediumStyle];
    }
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    Notification *notiAtIndex = [self.dataController objectInListAtIndex:indexPath.row];
    [[cell textLabel] setText:notiAtIndex.sender];
    [[cell detailTextLabel] setText:notiAtIndex.text];
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return NO;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"ShowNotificationDetails"]) {
        DetailViewController *detailViewController = [segue destinationViewController];
        
        detailViewController.notification = [self.dataController objectInListAtIndex:[self.tableView indexPathForSelectedRow].row];
    }
}
-(void)refreshView:(UIRefreshControl *)refresh {
    refresh.attributedTitle = [[NSAttributedString alloc] initWithString:@"Refreshing data..."];
    [self refreshDataTable:TRUE];
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"MMM d, h:mm a"];
    NSString *lastUpdated = [NSString stringWithFormat:@"Last updated on %@",
                             [formatter stringFromDate:[NSDate date]]];
    refresh.attributedTitle = [[NSAttributedString alloc] initWithString:lastUpdated];
    [refresh endRefreshing];
}

-(void)refreshDataTable:(BOOL) withTableRefresh{
    
    
    
    
        NSString *lastRefreshID = [GlobalSettings objectAtIndex:1];
    [iOSRequest refreshNotifications:kRequesterUsername andLastRefreshId:lastRefreshID onCompletion:^(NSDictionary *data){

               dispatch_async(dispatch_get_main_queue(), ^{
            NSArray* notifcations = [data objectForKey:@"notifications"];
                   if ([lastRefreshID isEqualToString:@"0"]){
                       if (notifcations==nil){
                        self.dataController.masterNotificationList=[[NSMutableArray alloc] init];
                       }
                   }
                   @try {
                       
                       for (NSDictionary *dict in notifcations) {
                           
                           NSLog(@"DICT: %@", dict);
                           Notification *notification;
                           
                           NSString *id = [dict objectForKey:@"id"];
                        
                           
                           if ([id integerValue]>[lastRefreshID integerValue])
                           {
                                   [GlobalSettings insertObject:id atIndex:1];
                           }
                        
                           
                           NSString *requestId = [dict objectForKey:@"requestid"];
                           
                           NSString *text = [dict objectForKey:@"text"];
                           
                           NSString *receiver = [dict objectForKey:@"receiver"];
                           
                           NSString *sender = [dict objectForKey:@"sender"];
                           NSDate *created = [dict objectForKey:@"created"];
                           NSString *type = [dict objectForKey:@"type"];
                           
                           notification = [[Notification alloc] initWithId:id text:text requestId:requestId receiver:receiver sender:sender type:type created:created];
                           
                           [self.dataController addNotification:notification];
                       }
                       if (withTableRefresh){
                           [[self tableView] reloadData];
                       }
                   }
                   @catch (NSException *exception) {
                       NSDictionary *dict = notifcations;
                           
                           NSLog(@"DICT: %@", dict);
                           Notification *notification;

                       NSString *id =[dict objectForKey:@"id"];
                           if ([id integerValue]>[lastRefreshID integerValue])
                           {
                               [GlobalSettings insertObject:id atIndex:1];
                               
                           }
                           
                           NSString *requestId = [dict objectForKey:@"requestid"];
                           
                           NSString *text = [dict objectForKey:@"text"];
                           
                           NSString *receiver = [dict objectForKey:@"receiver"];
                           
                           NSString *sender = [dict objectForKey:@"sender"];
                           NSDate *created = [dict objectForKey:@"created"];
                           NSString *type = [dict objectForKey:@"type"];
                           
                           notification = [[Notification alloc] initWithId:id text:text requestId:requestId receiver:receiver sender:sender type:type created:created];
                           
                           [self.dataController addNotification:notification];
                       
                       if (withTableRefresh){
                           [[self tableView] reloadData];
                       }
                   }
        });
    }];
}

- (IBAction)goBackToSafeDrop:(id)sender {
    ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
    
    [self presentViewController:viewController animated:YES completion:nil];
}
@end
