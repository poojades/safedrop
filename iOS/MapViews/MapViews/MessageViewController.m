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


NSTimer *timer;

-(void)refreshDataTable
{

    [iOSRequest refreshMessages:[GlobalSettings objectAtIndex:0] andLastRefreshId:@"0" onCompletion:^(NSDictionary *data){
        dispatch_async(dispatch_get_main_queue(), ^{

            int i=0;
            NSLog(@"%@", data);
            NSArray* notifcations = [data objectForKey:@"notifications"];
            bubbleData=[[NSMutableArray alloc] init];
            @try {
                
                for (NSDictionary *dict in notifcations) {
                    /*
                   
                    NSString *id = [dict objectForKey:@"id"];
                    
                    NSString *requestId = [dict objectForKey:@"requestid"];
                    
                    NSString *receiver = [dict objectForKey:@"receiver"]; 
                    
                    NSString *type = [dict objectForKey:@"type"];
                     */
                    
                    /*
                    NSDateFormatter* dateFormatter = [[NSDateFormatter alloc] init];
                    
                    [dateFormatter setDateFormat:@"yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"];
                    
                    NSDate* created = [dateFormatter dateFromString:[dict objectForKey:@"created"]];
                
                    */
                    NSString *sender = [dict objectForKey:@"sender"];
                    
                    NSString *text = [dict objectForKey:@"text"];
                    
                    if ([sender isEqualToString:kRequesterUsername]){
                        NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:[NSDate dateWithTimeIntervalSinceNow:-i*100] type:BubbleTypeMine];
                        replyBubble.avatar = nil;
                        [bubbleData addObject:replyBubble];
                    }
                    else{
                        NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:[NSDate dateWithTimeIntervalSinceNow:-i*100] type:BubbleTypeSomeoneElse];
                        replyBubble.avatar = nil;
                        [bubbleData addObject:replyBubble];
                    }
                    
                }
                
            }
            @catch (NSException *exception) {
                NSDictionary *dict = notifcations;
                
                /*
                 
                 NSString *id = [dict objectForKey:@"id"];
                 
                 NSString *requestId = [dict objectForKey:@"requestid"];
                 
                 NSString *receiver = [dict objectForKey:@"receiver"]; 
                
                 NSString *type = [dict objectForKey:@"type"];
                 
                 */
                
                /*
                 NSDateFormatter* dateFormatter = [[NSDateFormatter alloc] init];
                 
                 [dateFormatter setDateFormat:@"yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"];
                 
                 NSDate* created = [dateFormatter dateFromString:[dict objectForKey:@"created"]];
                 
                 */
                NSString *sender = [dict objectForKey:@"sender"];
                NSString *text = [dict objectForKey:@"text"];
               
                if ([sender isEqualToString:kRequesterUsername]){
                    NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:[NSDate dateWithTimeIntervalSinceNow:-i*100] type:BubbleTypeMine];
                    replyBubble.avatar = nil;
                    [bubbleData addObject:replyBubble];
                }
                else{
                    NSBubbleData *replyBubble = [NSBubbleData dataWithText:text date:[NSDate dateWithTimeIntervalSinceNow:-i*100] type:BubbleTypeSomeoneElse];
                    replyBubble.avatar = nil;
                    [bubbleData addObject:replyBubble];
                }
                
            }
            ++i;
        });
    }];
}

- (void)createTimer {
    if (nil==timer){
        timer=[NSTimer scheduledTimerWithTimeInterval:5.0 target:self selector:@selector(timerTicked:) userInfo:nil repeats:YES];
    }
}

- (void)timerTicked:(NSTimer*)timer {
    
    [self refreshDataTable];
    [bubbleTable reloadData];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self refreshDataTable];
    [bubbleTable reloadData];
    
    [self createTimer];
    
    bubbleTable.bubbleDataSource = self;
    bubbleTable.snapInterval = 120;
    bubbleTable.showAvatars = NO;   
    
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
    
    NSString *address = ksendMessageURL;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    [params setValue:kRequesterUsername forKey:@"from"];
    [params setValue:[GlobalSettings objectAtIndex:3] forKey:@"to"];
    
    [params setValue:textField.text forKey:@"message"];
    
    [params setValue:[GlobalSettings objectAtIndex:0] forKey:@"requestId"];
    
    
    [iOSRequest requestRESTPOST:address withParams:params onCompletion:^(NSString *result, NSError *error){
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                NSLog(@"%@",result);
                NSBubbleData *sayBubble = [NSBubbleData dataWithText:textField.text date:[NSDate dateWithTimeIntervalSinceNow:0] type:BubbleTypeMine];
                [bubbleData addObject:sayBubble];
                [bubbleTable reloadData];
                
                textField.text = @"";
                [textField resignFirstResponder];
            } else {
                NSLog(@"ERROR: %@",error);
            }
        });
    }];

    
    
    
   }

- (IBAction)goBacktoSafeDrop:(id)sender {
    ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
    [self presentViewController:viewController animated:YES completion:nil];
}


@end
