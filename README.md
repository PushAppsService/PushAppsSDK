PushAppsSDK
===========

Welcome to PushApps SDK's and Tutorials!
Below you can find the integration instructions for Androido and iOS.
For your convenience, you can find our demo projects, in which the SDK is already integrated.

If you have any questions, comments, requests or anything else on your mind - please contact [support@pushapps.mobi](mailto:support@pushapps.mobi)

Android:
========

1. Add the pushapps.jar file to your project libs folder.
2. In your application.java file, inside the method onCreate() write the following line:
    
    ```java
    PushManager.init(getApplicationContext(), DEVELOPER_ID, APP_TOKEN);
    ``` 

Don't forget to import com.groboot.pushapps.PushManager;

3. Add GCMIntentService.java to your root package folder. For example if your package name is com.example.push then the class should be in the src/com/example/push/ folder.
4. Add the following lines to the manifest XML file:
    
    ```xml
    <permission
        android:name="<your package>.permission.C2D_MESSAGE"
        android:protectionLevel="signature" />
   <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    <uses-permission android:name="<your package>.permission.C2D_MESSAGE" />
    ``` 

5. Inside the manifest XML file, In your <application> tag add the following receiver, service and activity:

    ```xml
    <receiver
        android:name="com.groboot.pushapps.GCMBroadcastReceiver"
        android:permission="com.google.android.c2dm.permission.SEND" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
            <category android:name="<your package>" />
        </intent-filter>
    </receiver>
    
    <service android:name=".GCMIntentService" />
    
    <activity
        android:name="com.groboot.pushapps.PushActivity"
        android:configChanges="orientation|keyboardHidden" />

    ``` 


iOS:
====

1. Add PushApps.framework to your xcode project. Please make sure that the "Copy items into destination group's folder (if needed)" is NOT checked.

2. Make sure you include AdSupport.framework in your project.

3. Place #import PushApps/PushApps.h in your AppDelegate.h file. If you need, you can also declare on delegation <PushAppsDelegate>, in order to get notified on push and more events.

4. In your application didFinishLaunchingWithOptions method inside the AppDelegate, add the following line:

    ```objective-c
    [[PushAppsManager sharedInstance] startPushAppsWithAppToken:@"YOUR_APP_TOKEN" withLaunchOptions:launchOptions];
    [[PushAppsManager sharedInstance] setDelegate:self]; // optional
    ``` 
    
5. In your AppDelegate, at the end of the file, at the following line:
    
    ```objective-c
    #pragma push notification

    - (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
        NSString *userToken = [[deviceToken description] stringByTrimmingCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"<>"]];
        userToken = [userToken stringByReplacingOccurrencesOfString:@" " withString:@""];
        [[PushAppsManager sharedInstance] updatePushToken:deviceToken];
    }
    
    - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
        [[PushAppsManager sharedInstance] handlePushMessageOnForeground:userInfo];
    }
    ```
