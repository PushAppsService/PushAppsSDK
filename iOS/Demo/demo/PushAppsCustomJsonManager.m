//
//  PushAppsCustomJsonManager.m
//  demo
//
//  Created by Raz Elkayam on 1/20/14.
//  Copyright (c) 2014 PushApps. All rights reserved.
//

#import "PushAppsCustomJsonManager.h"

#define LAST_CUSTOM_JSON_KEY @"my.last.custom.json.PushApps"

@implementation PushAppsCustomJsonManager

+ (void)extractInfoFromCustomJson:(NSDictionary *)pushDictionary withCustomJsonKey:(NSString *)customJsonKey
{
    NSString *customJsonString = pushDictionary[customJsonKey];
    
    NSData *customJsonData = [customJsonString dataUsingEncoding:NSUTF8StringEncoding];
    
    id json = [NSJSONSerialization JSONObjectWithData:customJsonData options:0 error:nil];
    
    NSDictionary *customJson;
    
    if ([json isKindOfClass:[NSDictionary class]]) {
        
        customJson = (NSDictionary *)json;
        
    } else {
        
        customJson = @{};
    }
    
    [[NSUserDefaults standardUserDefaults] setObject:customJson forKey:LAST_CUSTOM_JSON_KEY];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"PushAppsNewPushReceivedNotification" object:nil];
}


+ (NSDictionary *)loadLastCustomJsonMsgReceived
{
    return [[NSUserDefaults standardUserDefaults] objectForKey:LAST_CUSTOM_JSON_KEY];
}


@end
