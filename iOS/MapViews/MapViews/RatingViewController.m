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
@interface RatingViewController ()

@end



@implementation RatingViewController
@synthesize tableData;


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
    
    tableData = [NSArray arrayWithObjects:@"Egg Benedict", @"Mushroom Risotto", @"Full Breakfast", @"Hamburger", @"Ham and Egg Sandwich", @"Creme Brelee", @"White Chocolate Donut", @"Starbucks Coffee", @"Vegetable Curry", @"Instant Noodle with Egg", @"Noodle with BBQ Pork", @"Japanese Noodle with Pork", @"Green Tea", @"Thai Shrimp Cake", @"Angry Birds Cake", @"Ham and Cheese Panini", nil];



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



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *myIdentifier = @"CustomCell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:myIdentifier];
    NSString *theName = [self.tableData objectAtIndex:indexPath.row];
    
    [[(CustomCell *)cell byField] setText:theName];
     return cell;
}


- (IBAction)goBackToSafeDrop:(id)sender {
    ViewController *viewController=[[ViewController alloc]initWithNibName:@"ViewController" bundle:nil];
    
    [self presentViewController:viewController animated:YES completion:nil];

}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [tableData count];
}


@end
