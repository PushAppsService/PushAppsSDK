//
//  PushAppsDemoAppDelegate.m
//  demo
//
//  Created by Asaf Ron on 10/7/13.
//  Copyright (c) 2013 PushApps. All rights reserved.
//

#define APP_TOKEN @"REPLACE_WITH_YOUR_APP_TOKEN"

#import "PushAppsDemoAppDelegate.h"

@implementation PushAppsDemoAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // 1.
    // register as PushApps delegate.
    [[PushAppsManager sharedInstance] setDelegate:self];
    
    // 2.
    // register to PushApps & handle a remote notification.

    [[PushAppsManager sharedInstance] startPushAppsWithAppToken:APP_TOKEN withLaunchOptions:launchOptions];
    
    // 3.
    // UI / display oriented action.
    if ([launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey]) {
        
        NSDictionary *pushNotificationData = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
        [[NSUserDefaults standardUserDefaults] setObject:pushNotificationData forKey:@"LastPushNotification"];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
    
    NSLog(@"App version: %@", [[PushAppsManager sharedInstance] getAppVersion]);
    NSLog(@"Device ID: %@", [[PushAppsManager sharedInstance] getDeviceId]);
    NSLog(@"SDK version: %@", [[PushAppsManager sharedInstance] getSDKVersion]);
    
    // 4.
    // customizing colors of navigation bar.
    [self setNavigationBarColors];
    
    return YES;
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    
    // clear the app icon badge.
    [[PushAppsManager sharedInstance] clearApplicationBadge];

}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
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

#pragma mark - Push Notifications

#ifdef __IPHONE_8_0
- (void)application:(UIApplication *)application didRegisterUserNotificationSettings:(UIUserNotificationSettings *)notificationSettings
{
    [[PushAppsManager sharedInstance] didRegisterUserNotificationSettings:notificationSettings];
}

- (void)application:(UIApplication *)application handleActionWithIdentifier:(NSString *)identifier forRemoteNotification:(NSDictionary *)userInfo completionHandler:(void(^)())completionHandler
{
    [[PushAppsManager sharedInstance] handleActionWithIdentifier:identifier forRemoteNotification:userInfo completionHandler:completionHandler];
}
#endif

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    // notify PushApps of a successful registration.
    [[PushAppsManager sharedInstance] updatePushToken:deviceToken];
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    [[PushAppsManager sharedInstance] updatePushError:error];
}

// gets called when a remote notification is recieved while app is in the foreground.
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
    [[PushAppsManager sharedInstance] handlePushMessageOnForeground:userInfo];
}

#pragma mark - PushAppsDelegate

- (void)pushApps:(PushAppsManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground
{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"newData" object:pushNotification];
}

- (void)pushApps:(PushAppsManager *)manager didUpdateUserToken:(NSString *)pushToken
{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"newData" object:[NSString stringWithFormat:@"This is your push notification token = %@", pushToken]];
}

- (void)pushApps:(PushAppsManager *)manager handleActionWithIdentifier:(NSString *)identifier forRemoteNotification:(NSDictionary *)userInfo completionHandler:(void (^)())completionHandler
{
    NSLog(@"Got an action with identifier: %@", identifier);
    NSLog(@"Action dictionary: %@", userInfo);
    
    completionHandler();
}

#pragma mark - Customization

- (void)setNavigationBarColors
{
    [[UINavigationBar appearance] setBackgroundColor:[UIColor colorWithRed:206.0/255.0 green:222.0/255.0 blue:230.0/255.0 alpha:1.0]];
}

@end
