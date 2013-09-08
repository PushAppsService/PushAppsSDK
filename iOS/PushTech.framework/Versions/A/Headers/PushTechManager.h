//
//  PushManager.h
//  PushTech
//
//  Created by Asaf Ron on 7/18/13.
//  Copyright (c) 2013 Groboot. All rights reserved.
// 

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class PushTechManager;
@protocol PushTechDelegate <NSObject>

@optional
- (void)pushTech:(PushTechManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground;

@end

@interface PushTechManager : NSObject

@property (nonatomic, strong) id<PushTechDelegate> delegate;

+ (PushTechManager *)sharedInstance;

- (void)startPushTechWithAppToken:(NSString *)appToken andAppId:(NSUInteger)appId withLaunchOptions:(NSDictionary *)launchOptions;
- (void)updatePushToken:(NSData *)data;
- (void)handlePushMessageOnForeground:(NSDictionary *)launchOptions;

@end