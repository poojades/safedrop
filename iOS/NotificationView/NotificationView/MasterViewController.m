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



/*
@interface MasterViewController () {
    NSMutableArray *_objects;
}
@end
*/





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
    
    
    
    [iOSRequest refreshNotifications:@"sankhasp@cmu.edu" andLastRefreshedId:0 onCompletion:^(NSDictionary *data){
        dispatch_async(dispatch_get_main_queue(), ^{
            NSArray* notifcations = [data objectForKey:@"notifications"];
            for (NSDictionary *dict in notifcations) {
                
                NSLog(@"DICT: %@", dict);
                Notification *notification;
                NSInteger *id = [[dict objectForKey:@"id"] integerValue];
                NSInteger *requestId = [[dict objectForKey:@"requestid"] integerValue];
                
                NSString *text = [dict objectForKey:@"text"];
                
                NSString *receiver = [dict objectForKey:@"receiver"];
                
                NSString *sender = [dict objectForKey:@"sender"];
                NSDate *created = [dict objectForKey:@"created"];
                NSString *type = [dict objectForKey:@"type"];
                
                notification = [[Notification alloc] initWithId:id text:text requestId:requestId receiver:receiver sender:sender type:type created:created];
                
                [self.dataController addNotification:notification];
            }
            [[self tableView] reloadData];

        });
        
    }];
     
    [super viewDidLoad];
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*

- (void)insertNewObject:(id)sender
{
    if (!_objects) {
        _objects = [[NSMutableArray alloc] init];
    }
    [_objects insertObject:[NSDate date] atIndex:0];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
    [self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}
 
 */

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
    [[cell detailTextLabel] setText:notiAtIndex.text];// [formatter stringFromDate:(NSDate *)sightingAtIndex.date]];
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return NO;
}


/*
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [_objects removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

 */
/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"ShowNotificationDetails"]) {
        DetailViewController *detailViewController = [segue destinationViewController];
        
        detailViewController.notification = [self.dataController objectInListAtIndex:[self.tableView indexPathForSelectedRow].row];
    }
}
-(void)refreshView:(UIRefreshControl *)refresh {
    refresh.attributedTitle = [[NSAttributedString alloc] initWithString:@"Refreshing data..."];
     
    [self.dataController refreshNotifications:@"sankhasp@cmu.edu" andLastRefreshId:0];
    [[self tableView] reloadData];
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"MMM d, h:mm a"];
    NSString *lastUpdated = [NSString stringWithFormat:@"Last updated on %@",
                             [formatter stringFromDate:[NSDate date]]];
    refresh.attributedTitle = [[NSAttributedString alloc] initWithString:lastUpdated];
    [refresh endRefreshing];
}

@end
