//
//  ViewController.m
//  SafeDrop
//
//  Created by Sankha Subhra Pathak on 8/1/13.
//  Copyright (c) 2013 Self. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)returned:(UIStoryboardSegue *)segue {
    NSLog(@"return clicked");
}

@end
