//
//  RatingViewController.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/4/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AMRatingControl.h"

@interface RatingViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
    NSDictionary *tableData;
}

- (IBAction)goBackToSafeDrop:(id)sender;

@property (nonatomic,retain) NSDictionary *tableData;

@property (weak, nonatomic) IBOutlet UITableView *reusableCellTableView;


@property (weak, nonatomic) IBOutlet UITextField *ratingCommentsText;
@property (weak, nonatomic) IBOutlet UISegmentedControl *ratingValue;

- (IBAction)addRating:(id)sender;


@end
