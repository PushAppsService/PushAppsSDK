//
//  MainViewController.m
//  demo
//
//  Created by Asaf Ron on 10/8/13.
//  Copyright (c) 2013 PushApps. All rights reserved.
//

#import "MainViewController.h"

@interface MainViewController ()
@property (weak, nonatomic) IBOutlet UILabel *pushContent;
@property (weak, nonatomic) IBOutlet UIButton *pushLink;

@end

@implementation MainViewController

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
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updatePushDetails:) name:@"NewPush" object:nil];
}

- (void)updatePushDetails:(NSNotification *)notification
{
    NSDictionary *pushMessage = [[notification userInfo] copy];
    
    // update UI
    self.pushContent.text = [[pushMessage objectForKey:@"aps"] objectForKey:@"alert"];
    [self.pushLink setTitle:[pushMessage objectForKey:@"L"] forState:UIControlStateNormal];
    [self.pushLink setTitle:[pushMessage objectForKey:@"L"] forState:UIControlStateHighlighted];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)moveToPushLink:(id)sender
{
    UIButton *button = (UIButton *)sender;
    NSString *buttonTitle = [button titleForState:UIControlStateNormal];
    if ([buttonTitle length] > 0) {
        if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:buttonTitle]]) {
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:buttonTitle]];
        }
    }
}

@end
