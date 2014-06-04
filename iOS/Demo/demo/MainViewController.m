//
//  MainViewController.m
//  demo
//
//  Created by Asaf Ron on 10/8/13.
//  Copyright (c) 2013 PushApps. All rights reserved.
//

#import "MainViewController.h"
#import <PushApps/PushApps.h>

typedef enum : NSInteger {
    PickerTypeString = 2,
    PickerTypeInteger,
} PickerType;

@interface MainViewController () <UITextFieldDelegate, UIAlertViewDelegate, UIPickerViewDataSource, UIPickerViewDelegate>

@property (weak, nonatomic) IBOutlet UITextView *textView;
@property (weak, nonatomic) IBOutlet UITextField *tagTextField;
@property (weak, nonatomic) IBOutlet UIPickerView *picker;
@property (weak, nonatomic) IBOutlet UIDatePicker *datePicker;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *pickerContainerViewVerticalConstraint;
@property (nonatomic) PickerType pickerType;
@end

@implementation MainViewController

#pragma mark - View Life Cycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(newPush:) name:@"newData" object:nil];
    
    self.tagTextField.delegate = self;
}

- (void)newPush:(NSNotification *)notification
{
    id data = [notification object];
    
    [self updateTextView:[data description]];
}

#pragma mark - IBActions

- (IBAction)pickerOKPressed:(id)sender
{
    if (self.picker.hidden) {
        
        [self completeSendingDateTag:[self.datePicker date]];
        
    } else if (self.pickerType == PickerTypeString) {
        
        [self completeSendingString:[self pickerStrings][[self.picker selectedRowInComponent:0]]];
        
    } else if (self.pickerType == PickerTypeInteger) {
        
        [self completeSendingInteger:[self pickerIntegers][[self.picker selectedRowInComponent:0]]];
    }
    self.pickerContainerViewVerticalConstraint.constant = -214;
    [UIView animateWithDuration:0.25f animations:^{
        
        [self.view layoutIfNeeded];
    } completion:^(BOOL finished) {
        
        self.picker.hidden = NO;
        self.datePicker.hidden = NO;
        self.picker.delegate = nil;
        self.picker.dataSource = nil;
    }];
}
- (IBAction)pickerCancelPressed:(id)sender
{
    self.pickerContainerViewVerticalConstraint.constant = -214;
    [UIView animateWithDuration:0.25f animations:^{
        
        [self.view layoutIfNeeded];
    } completion:^(BOOL finished) {
        
        self.picker.hidden = NO;
        self.datePicker.hidden = NO;
        self.picker.delegate = nil;
        self.picker.dataSource = nil;
    }];
    
    [self updateTextView:@"operatin canceld"];
}

// unregisters this device from push notifications.
- (IBAction)unregisterNotifications:(id)sender
{
    [[PushAppsManager sharedInstance] unregisterFromPushNotificationsByDeviceId];
    [self updateTextView:[NSString stringWithFormat:@"unregistered device:%@", [[PushAppsManager sharedInstance] getDeviceId]]];
}

// reregisters this device to push notifications
- (IBAction)reregisterNotifications:(id)sender
{
    [[PushAppsManager sharedInstance] startPushAppsWithAppToken:@"1a3267ab-aa11-4bb2-8dda-034b3a6566ee" withLaunchOptions:nil];
}

- (IBAction)sendBooleanTag:(id)sender
{
    if ([self.tagTextField.text length]) {
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Boolean Tag" message:@"What value should we send?" delegate:self cancelButtonTitle:@"Abort" otherButtonTitles:@"YES", @"NO", nil];
        alert.tag = 1;
        [alert show];
        
    } else {
        
        [self updateTextView:@"cant sent tag without name"];
    }
}
- (IBAction)sendDateTag:(id)sender
{
    if ([self.tagTextField.text length]) {
        
        self.pickerContainerViewVerticalConstraint.constant = 0;
        self.picker.hidden = YES;
        [UIView animateWithDuration:0.25f animations:^{
            
            [self.view layoutIfNeeded];
        }];
        
    } else {
        
        [self updateTextView:@"cant sent tag without name"];
    }
}

- (IBAction)sendStringTag:(id)sender
{
    self.pickerType = PickerTypeString;
    self.picker.delegate = self;
    self.picker.dataSource = self;
    
    if ([self.tagTextField.text length]) {
        
        self.pickerContainerViewVerticalConstraint.constant = 0;
        self.datePicker.hidden = YES;
        [UIView animateWithDuration:0.25f animations:^{
            
            [self.view layoutIfNeeded];
        }];
        
    } else {
        
        self.picker.delegate = nil;
        self.picker.dataSource = nil;
        [self updateTextView:@"cant sent tag without name"];
    }
}

- (IBAction)sendIntegerTag:(id)sender
{
    self.pickerType = PickerTypeInteger;
    self.picker.delegate = self;
    self.picker.dataSource = self;
    
    if ([self.tagTextField.text length]) {
        
        self.pickerContainerViewVerticalConstraint.constant = 0;
        self.datePicker.hidden = YES;
        [UIView animateWithDuration:0.25f animations:^{
            
            [self.view layoutIfNeeded];
        }];
        
    } else {
        
        self.picker.delegate = nil;
        self.picker.dataSource = nil;
        [self updateTextView:@"cant sent tag without name"];
    }
}
- (IBAction)removeTag:(id)sender
{
    if ([self.tagTextField.text length]) {
    
        [self completeRemovingTagByName:self.tagTextField.text];
        
    } else {
        
        [self updateTextView:@"cant remove tag without name"];
    }
}

#pragma mark - Tags

- (void)completeSendingBooleanTag:(BOOL)boolean
{
    [[PushAppsManager sharedInstance] addTagForBoolean:boolean withIdentifierForTag:self.tagTextField.text andOperationStatus:^(BOOL success, NSString *msg) {
        
        [self updateTextView:[NSString stringWithFormat:@"Boolean tag: %@, msg:%@", success ? @"operation status: succes" : @"operation status: faild", msg]];
    }];
}

- (void)completeSendingDateTag:(NSDate *)date
{
    [[PushAppsManager sharedInstance] addTagForDate:date withIdentifierForTag:self.tagTextField.text andOperationStatus:^(BOOL success, NSString *msg) {
        
        [self updateTextView:[NSString stringWithFormat:@"Date tag: %@, msg:%@", success ? @"operation status: succes" : @"operation status: faild", msg]];
    }];
}

- (void)completeSendingInteger:(NSNumber *)number
{
    [[PushAppsManager sharedInstance] addTagForNumber:number withIdentifierForTag:self.tagTextField.text andOperationStatus:^(BOOL success, NSString *msg) {
        
        [self updateTextView:[NSString stringWithFormat:@"Integer tag: %@, msg:%@", success ? @"operation status: succes" : @"operation status: faild", msg]];
    }];
}

- (void)completeSendingString:(NSString *)string
{
    [[PushAppsManager sharedInstance] addTagForString:string withIdentifierForTag:self.tagTextField.text andOperationStatus:^(BOOL success, NSString *msg) {
        
        [self updateTextView:[NSString stringWithFormat:@"String tag: %@, msg:%@", success ? @"operation status: succes" : @"operation status: faild", msg]];
    }];
}

- (void)completeRemovingTagByName:(NSString *)tagName
{
    [[PushAppsManager sharedInstance] removeTagWithIdentifier:tagName andOperationStatus:^(BOOL success, NSString *msg) {
        
        [self updateTextView:[NSString stringWithFormat:@"String tag: %@, msg:%@", success ? @"operation status: succes" : @"operation status: faild", msg]];
    }];
}

#pragma mark - UIAlertViewDelegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch (alertView.tag) {
        case 1:
        {
            if (buttonIndex == 1) {
                
                [self completeSendingBooleanTag:YES];
                
            } else if (buttonIndex == 2) {
                
                [self completeSendingBooleanTag:NO];
            }
        }
            break;
        default:
            // do nothing :)
            break;
    }
}

#pragma mark - Class Methods

- (void)updateTextView:(NSString *)string
{
    self.textView.text = [NSString stringWithFormat:@"%@\n%@", string, self.textView.text];
}
- (IBAction)clearLog:(id)sender
{
    self.textView.text = @"";
}

#pragma mark - UITextFieldDelegate

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

#pragma mark - UIPickerViewDataSource

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (self.pickerType == PickerTypeInteger) {
        
        return [[self pickerIntegers] count];
        
    } else if (self.pickerType == PickerTypeString) {
        
        return [[self pickerStrings] count];
        
    } else {
        
        return 0;
    }
}

#pragma mark - UIPickerViewDelegate

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if (self.pickerType == PickerTypeInteger) {
        
        return [[self pickerIntegers][row] description];
        
    } else if (self.pickerType == PickerTypeString) {
        
        return [[self pickerStrings][row] description];
        
    } else {
        
        return @"";
    }
}

#pragma mark - Helpers

- (NSArray *)pickerStrings
{
    return @[@"New York", @"Amsterdam", @"Paris", @"London", @"BeiJing"];
}

- (NSArray *)pickerIntegers
{
    return @[@(122), @(2233), @(25566), @(77753)];
}

@end
