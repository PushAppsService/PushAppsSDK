//
//  PushAppsCustomJsonManager.h
//  demo
//
//  Created by Raz Elkayam on 1/20/14.
//  Copyright (c) 2014 PushApps. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface PushAppsCustomJsonManager : NSObject

/*
 
 *** how to use this class ***
 
 1. add this line: #import "PushAppsCustomJsonManager.h" to your AppDelegate.m file.
 
 2. in the AppDelegate.m file, where you impement the PushAppsManager Delegate, inside the delegate method, add the following:
 
        - (void)pushApps:(PushAppsManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground
        {
 
        // you will need to pass a pushDictionary and the customJsonKey you have specified in the web service.
        [PushAppsCustomJsonManager extractInfoFromCustomJson:pushNotification withCustomJsonKey:@"data"];
 
        }
 
 3. inside one a view controller that needs the info recieved inside the custom push json, depending on the time it needs it, you can do something like this:
 
    A. add this line to the view controller: #import "PushAppsCustomJsonManager.h"
 
    B. if you are loading a URL, then the best place would be inside viewDidLoad:
 
        - (void)viewDidLoad
        {
 
        [super viewDidLoad];
 
        // register to in-app notification system
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(getLastPushNotification) name:@"PushAppsNewPushReceivedNotification" object:nil];

        }
 
    C. implement the @selector(getLastPushNotification)
 
        // gets called by NSNotificationCenter which PushAppsCustomJsonManager posts notifications with it.
        - (void)getLastPushNotification
        {
 
        NSDictionary *customJson = [PushAppsCustomJsonManager loadLastCustomJsonMsgReceived];
        NSString *myFirstUrl = customJson[@"url_one"];
        NSString *mySecondUrl = customJson[@"url_two"];
        // here you would padd the arguments to a webView, to load a url request.
        NSLog(@"first url: %@ second url: %@", myFirstUrl, mySecondUrl);
 
    }

*/



// method to save data from PushApps received push notification, by the key inserted in the web service.
+ (void)extractInfoFromCustomJson:(NSDictionary *)pushDictionary withCustomJsonKey:(NSString *)customJsonKey;

// returns an NSDictionary representaion the custom JSON extracted from the last received push notification.
+ (NSDictionary *)loadLastCustomJsonMsgReceived;

@end
