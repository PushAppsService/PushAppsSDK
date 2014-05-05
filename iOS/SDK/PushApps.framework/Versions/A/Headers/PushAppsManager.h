//
//  PushManager.h
//
//  Created by Asaf Ron on 7/18/13.
//  Copyright (c) 2013 Groboot. All rights reserved.
// 

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

typedef void (^fetchComplitionHandlerResualt)(UIBackgroundFetchResult backgroundFetchResualt);
typedef void(^tagStatus)(BOOL success, NSString *msg);

@class PushAppsManager;

@protocol PushAppsDelegate <NSObject>

@optional
- (void)pushApps:(PushAppsManager *)manager didReceiveRemoteNotification:(NSDictionary *)pushNotification whileInForeground:(BOOL)inForeground;

@optional
- (void)pushApps:(PushAppsManager *)manager didUpdateUserToken:(NSString *)pushToken;

@end

@interface PushAppsManager : NSObject

@property (nonatomic, strong) id<PushAppsDelegate> delegate;

#pragma mark - Initialization

/**
 *  Designated method to create a shared PushAppsManager.
 *
 *  @return A shared instance of PushAppsManager.
 */
+ (PushAppsManager *)sharedInstance;

#pragma mark - Handle Push Actions

/**
 *  A method to identify an application with PushApps. Method is critical since it starts the registration to Push Notification message services.
 *
 *  @param appToken an NSString "hard coded" token retrived from PushApps web site, under "My Apps", "setting". each app token is a unique token, designated to be used in a single application.
 *  @param launchOptions An NSDictionary object passed as a parameter with in the delegate method application:didFinishLaunchingWithOptions.
 *
 */
- (void)startPushAppsWithAppToken:(NSString *)appToken withLaunchOptions:(NSDictionary *)launchOptions;

/**
 *  A method to finalize the registration to PushApps. Method is critical since it provides the device ID.
 *
 *  @param data an NSData object which encapsulates device data.
 *
 */
- (void)updatePushToken:(NSData *)data;

/**
 *  A method to unregister a device by it's device ID (UDID).
 *
 *  @param deviceID an NSString containing the device UDID uniqu identifier.
 *
 */
- (void)unregisterFromPushNotificationsByDeviceId:(NSString *)deviceID;

/**
 *  A method to report a new push notification event.
 *
 *  @param eventId an NSString value which serves as an ID to identify a new push notification event.
 *
 *  @discussion This method is deprecated. It is still valid to be used, but we encourage using the 'Tag' methods.
 */
- (void)reportEventwithEventId:(NSString *)eventId __deprecated_msg("Warrning! depricated method");

/**
 *  A method to Handle a Notification Received while app in foreground
 *
 *  @param launchOptions an NSDictionary containing user info.
 *
 */
- (void)handlePushMessageOnForeground:(NSDictionary *)launchOptions;

/**
 *  Method to update a Push Notification status
 *
 *  @param options an NSDictionary object that holds the NSRemoteNotification data.
 *
 *  @discussion Use this method to update the status of an NSNotification sent to a client. Updating the status of the notification will be visible via PushApps web interface.
 */
- (void)updateNotificationReadStatus:(NSDictionary *)options;

///**
// *  Method to handle 'Silent Push'
// *
// *  @param userInfo an NSDictionary object that holds the NSRemoteNotification data.
// *
// *  @param fetchComplitionHandlerResualt a Block of Code that represents a complition handler for UIBackgroundFetchResult.
// *
// *  @discussion Use this method to Handle 'Silent Push'. Method takes care of the fetchComplitionHandlerResualt when it finishes.
// */
//- (void)handlePushMessageForUserInfo:(NSDictionary *)userInfo WithFetchComplitionHandlerResualt:(fetchComplitionHandlerResualt)fetchComplitionHandlerResualt;

#pragma mark - Tags

- (void)addTags:(NSArray *)tags andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by date
 *
 *  @param date an NSDate object to be used a the tag value.
 *
 *  @param identifier an NSString object to be used as the identifier, or key for the tag.
 *
 *  @discussion Use this method to tag a date that will be added to your web admin console, and will be assigned to a user by it's device identifier.
 */
- (void)addTagForDate:(NSDate *)date withIdentifierForTag:(NSString *)identifier andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by date
 *
 *  @param dates an NSArray object that holds @{key : value} objects
 *
 *  @discussion Use this method to tag dates with identifiers, so that you could easly add more then one tag at a time. The method expects to recive an array of NSDictionary objects. Each NSDictionary object holds one key : value pair.
 */
- (void)addTagForDates:(NSArray *)dates andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by string
 *
 *  @param strings an NSSting object to be used a the tag value.
 *
 *  @param identifier an NSString object to be used as the identifier, or key for the tag.
 *
 *  @discussion Use this method to tag a string that will be added to your web admin console, and will be assigned to a user by it's device identifier.
 */
- (void)addTagForString:(NSString *)string withIdentifierForTag:(NSString *)identifier andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by string
 *
 *  @param strings an NSArray object that holds @{key : value} objects
 *
 *  @discussion Use this method to tag strings with identifiers, so that you could easly add more then one tag at a time. The method expects to recive an array of NSDictionary objects. Each NSDictionary object holds one key : value pair.
 */
- (void)addTagForStrings:(NSArray *)strings andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by number
 *
 *  @param number an NSNumber object to be used a the tag value.
 *
 *  @param identifier an NSString object to be used as the identifier, or key for the tag.
 *
 *  @discussion Use this method to tag a number that will be added to your web admin console, and will be assigned to a user by it's device identifier.
 */
- (void)addTagForNumber:(NSNumber *)number withIdentifierForTag:(NSString *)identifier andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by number
 *
 *  @param numbers an NSArray object that holds @{key : value} objects
 *
 *  @discussion Use this method to tag numbers with identifiers, so that you could easly add more then one tag at a time. The method expects to recive an array of NSDictionary objects. Each NSDictionary object holds one key : value pair.
 */
- (void)addTagForNumbers:(NSArray *)numbers andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by BOOL
 *
 *  @param boolean a BOOL primitive to be used a the tag value.
 *
 *  @param identifier an NSString object to be used as the identifier, or key for the tag.
 *
 *  @discussion Use this method to tag a BOOL that will be added to your web admin console, and will be assigned to a user by it's device identifier.
 */
- (void)addTagForBoolean:(BOOL)boolean withIdentifierForTag:(NSString *)identifier andOperationStatus:(tagStatus)status;

/**
 *  Method to tag a device by BOOL
 *
 *  @param booleans an NSArray object that holds @{key : value} objects. BOOLs are to be stored in NSNumber style @(YES).
 *
 *  @discussion Use this method to tag BOOLs with identifiers, so that you could easly add more then one tag at a time. The method expects to recive an array of NSDictionary objects. Each NSDictionary object holds one key : value pair.
 */
- (void)addTagForBooleans:(NSArray *)booleans andOperationStatus:(tagStatus)status;

/**
 *  Method to remove a tag for a device by a tag identifier
 *
 *  @param tagIdentifier an NSString object to be used as the identifier, or key for the tag.
 *
 *  @discussion Use this method to remove a device from being included in a tag with by a tag identifier.
 */
- (void)removeTagWithIdentifier:(NSString *)tagIdentifier andOperationStatus:(tagStatus)status;

/**
 *  Method to remove tags for a device by a tag identifier
 *
 *  @param tagIdentifiers an NSArray that contains a list of tag identifiers.
 *
 *  @discussion Use this method to remove a device from being included in an array of tags, by a givven identifier.
 */
- (void)removeTagsWithIdentifiers:(NSArray *)tagIdentifiers andOperationStatus:(tagStatus)status;

#pragma mark - Helper Methods

/**
 *  A method to clear all application badges.
 *
 */
- (void)clearApplicationBadge;

/**
 *  Method to retrive the device UDID
 *
 *  @return an NSString containing the device UDID.
 */
- (NSString *)getDeviceId;

/**
 *  Method to retrive the device name
 *
 *  @return an NSString containing the device name (i.e. iPhone5c).
 */
- (NSString *)getDeviceName;

/**
 *  Method to retrive the SDK version of PushApps SDK
 *
 *  @return an NSNumber object, with a float representation of the SDK version.
 */
- (NSNumber *)getSDKVersion;

/**
 *  Method to retrive the AppVersion of the SDK.
 *
 *  @return an NSString containing the app version.
 */
- (NSString *)getAppVersion;

@end