//
//  PushAppsDemoAppDelegate.m
//  demo
//
//  Created by Asaf Ron on 10/7/13.
//  Copyright (c) 2013 PushApps. All rights reserved.
//

#import "PushAppsDemoAppDelegate.h"

@implementation PushAppsDemoAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [[PushAppsManager sharedInstance] startPushAppsWithAppToken:@"18d943b9-677a-4b95-a29c-5d77b1e3a684" withLaunchOptions:launchOptions];
    [[PushAppsManager sharedInstance] setDelegate:self];
    
    [[UINavigationBar appearance] setBackgroundColor:[UIColor colorWithRed:206.0/255.0 green:222.0/255.0 blue:230.0/255.0 alpha:1.0]];
    
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    
    [UIApplication sharedApplication].applicationIconBadgeNumber = 0;
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

#pragma push notification

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    NSString *userToken = [[deviceToken description] stringByTrimmingCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"<>"]];
    userToken = [userToken stringByReplacingOccurrencesOfString:@" " withString:@""];
    [[PushAppsManager sharedInstance] updatePushToken:deviceToken];
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    [[PushAppsManager sharedInstance] handlePushMessageOnForeground:userInfo];
}

#pragma push apps delegate

- (void)pushApps:(PushAppsManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground
{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"NewPush" object:nil userInfo:pushNotification];
}

@end
