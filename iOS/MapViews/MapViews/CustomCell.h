//
//  CustomCell.h
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/5/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CustomCell : UITableViewCell


@property (weak, nonatomic) IBOutlet UILabel *byField;
@property (weak, nonatomic) IBOutlet UILabel *textField;
@property (weak, nonatomic) IBOutlet UISlider *ratingValue;

@end
