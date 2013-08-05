//
//  CustomCell.m
//  MapViews
//
//  Created by Sankha Subhra Pathak on 8/5/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "CustomCell.h"

@implementation CustomCell

@synthesize byField;
@synthesize textField;
@synthesize ratingValue;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
