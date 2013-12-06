//
//  PushManager.h
//
//  Created by Asaf Ron on 7/18/13.
//  Copyright (c) 2013 Groboot. All rights reserved.
// 

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class PushAppsManager;
@protocol PushAppsDelegate <NSObject>

@optional
- (void)pushApps:(PushAppsManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground;

@optional
- (void)pushApps:(PushAppsManager *)manager didUpdateUserToken:(NSString *)pushToken;


@end

@interface PushAppsManager : NSObject

@property (nonatomic, strong) id<PushAppsDelegate> delegate;

+ (PushAppsManager *)sharedInstance;

- (void)startPushAppsWithAppToken:(NSString *)appToken withLaunchOptions:(NSDictionary *)launchOptions;
- (void)updatePushToken:(NSData *)data;
- (void)handlePushMessageOnForeground:(NSDictionary *)launchOptions;

- (void)clearApplicationBadge;

- (NSString *)getDeviceId;

@end