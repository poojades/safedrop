//
//  MessageViewController.m
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/5/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "MessageViewController.h"
#import "UIBubbleTableView.h"
#import "UIBubbleTableViewDataSource.h"
#import "NSBubbleData.h"
#import "ViewController.h"
#import "iOSRequest.h"
#import "NSString+WebService.h"

@interface MessageViewController (){
    IBOutlet UIBubbleTableView *bubbleTable;
    IBOutlet UIView *textInputView;
    IBOutlet UITextField *textField;
    NSMutableArray *bubbleData;
}
@end

@implementation MessageViewController



- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void)refreshDataTable
{
<<<<<<< HEAD
    [iOSRequest refreshMessages:[GlobalSettings objectAtIndex:0] andLastRefreshId:@"0" onCompletion:^(NSDictionary *data){
=======
    [iOSRequest refreshMessages:kRequesterUsername andLastRefreshId:@"0" onCompletion:^(NSDictionary *data){
>>>>>>> 169e84116cdeb9922c0f763ff69c3bd389901ff3
        
        dispatch_async(dispatch_get_main_queue(), ^{
            bubbleData = [[NSMutableArray alloc] init];
            NSBubbleData *heyBubble = [NSBubbleData dataWithText:@"Hey, halloween is soon" date:[NSDate dateWithTimeIntervalSinceNow:-300] type:BubbleTypeSomeoneElse];
            heyBubble.avatar = nil;
            
            NSBubbleData *photoBubble = [NSBubbleData dataWithImage:[UIImage imageNamed:@"halloween.jpg"] date:[NSDate dateWithTimeIntervalSinceNow:-290] type:BubbleTypeSomeoneElse];
            photoBubble.avatar = nil;
            NSBubbleData *replyBubble = [NSBubbleData dataWithText:@"Wow.. Really cool picture out there. iPhone 5 has really nice camera, yeah?" date:[NSDate dateWithTimeIntervalSinceNow:-5] type:BubbleTypeMine];
            replyBubble.avatar = nil;
            
            NSLog(@"%@", data);
            NSArray* notifcations = [data objectForKey:@"notifications"];
            bubbleData=[[NSMutableArray alloc] init];
            @try {
                
                for (NSDictionary *dict in notifcations) {
                    
                    NSString *id = [dict objectForKey:@"id"];                    
                    
                    NSString *requestId = [dict objectForKey:@"requestid"];
                    
                    NSString *text = [dict objectForKey:@"text"];
                    
                    NSString *receiver = [dict objectForKey:@"receiver"];
                    
                    NSString *sender = [dict objectForKey:@"sender"];
                           NSDate *created = [dict objectForKey:@"created"];
                    if ([sender isEqualToString:kRequesterUsername]){
                        NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:created type:BubbleTypeMine];
                        replyBubble.avatar = nil;
                        [bubbleData addObject:replyBubble];
                    }
                    else{
                        NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:created type:BubbleTypeSomeoneElse];
                        replyBubble.avatar = nil;
                        [bubbleData addObject:replyBubble];
                    }
             
                    NSString *type = [dict objectForKey:@"type"];
                    
                }
                
            }
            @catch (NSException *exception) {
                NSDictionary *dict = notifcations;
                NSString *requestId = [dict objectForKey:@"requestid"];
                
                NSString *text = [dict objectForKey:@"text"];
                
                NSString *receiver = [dict objectForKey:@"receiver"];
                
                NSString *sender = [dict objectForKey:@"sender"];
                NSDate *created = [dict objectForKey:@"created"];
                NSString *type = [dict objectForKey:@"type"];
                if ([sender isEqualToString:kRequesterUsername]){
                    NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:created type:BubbleTypeMine];
                    replyBubble.avatar = nil;
                    [bubbleData addObject:replyBubble];
                }
                else{
                    NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:created type:BubbleTypeSomeoneElse];
                    replyBubble.avatar = nil;
                    [bubbleData addObject:replyBubble];
                }
                
            }
                       @finally{
<<<<<<< HEAD
                           [bubbleTable reloadData];
=======
                           [bubbleData addObject:nil];
                           [bubbleTable reloadData];

>>>>>>> 169e84116cdeb9922c0f763ff69c3bd389901ff3
                       }
        });
    }];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self refreshDataTable];
    

    bubbleTable.bubbleDataSource = self;
    
    // The line below sets the snap interval in seconds. This defines how the bubbles will be grouped in time.
    // Interval of 120 means that if the next messages comes in 2 minutes since the last message, it will be added into the same group.
    // Groups are delimited with header which contains date and time for the first message in the group.
    
    bubbleTable.snapInterval = 120;
    
    // The line below enables avatar support. Avatar can be specified for each bubble with .avatar property of NSBubbleData.
    // Avatars are enabled for the whole table at once. If particular NSBubbleData misses the avatar, a default placeholder will be set (missingAvatar.png)
    
    bubbleTable.showAvatars = YES;
    
    // Uncomment the line below to add "Now typing" bubble
    // Possible values are
    //    - NSBubbleTypingTypeSomebody - shows "now typing" bubble on the left
    //    - NSBubbleTypingTypeMe - shows "now typing" bubble on the right
    //    - NSBubbleTypingTypeNone - no "now typing" bubble
    
    bubbleTable.typingBubble = NSBubbleTypingTypeSomebody;
    
    [bubbleTable reloadData];
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}


#pragma mark - UIBubbleTableViewDataSource implementation

- (NSInteger)rowsForBubbleTable:(UIBubbleTableView *)tableView
{
    return [bubbleData count];
}

- (NSBubbleData *)bubbleTableView:(UIBubbleTableView *)tableView dataForRow:(NSInteger)row
{
    return [bubbleData objectAtIndex:row];
}


#pragma mark - Actions

- (IBAction)sayPressed:(id)sender
{
    bubbleTable.typingBubble = NSBubbleTypingTypeNobody;
    
    NSBubbleData *sayBubble = [NSBubbleData dataWithText:textField.text date:[NSDate dateWithTimeIntervalSinceNow:0] type:BubbleTypeMine];
    [bubbleData addObject:sayBubble];
    [bubbleTable reloadData];
    
    textField.text = @"";
    [textField resignFirstResponder];
}

- (IBAction)goBacktoSafeDrop:(id)sender {
    
    ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
    
    [self presentViewController:viewController animated:YES completion:nil];
}
@end
