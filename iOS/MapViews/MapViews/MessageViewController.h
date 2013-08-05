//
//  MessageViewController.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/5/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIBubbleTableViewDataSource.h"

@interface MessageViewController : UIViewController <UIBubbleTableViewDataSource>
- (IBAction)goBacktoSafeDrop:(id)sender;

@end
