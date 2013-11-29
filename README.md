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
	PushAppsConfiguration config = new PushAppsConfiguration(R.drawable.notification_icon);
    ``` 
    Optional: in the PushAppsConfiguration you can add an Intent() which will be called when the user clicks on the notification:    
    ```
    config.setNotificationIntent(new Intent(this, MainActivity.class));
    ```
    You can as well register to the PushApps callbacks by implementing the ```PushAppsInterface.class``` interface and calling ```config.setInterface(mPushAppsInterface, false);```

    Specifying the second parameter as true will disable PushApps default showing of the notification.
    Specifying the second parameter as false will show the PushApps default notification anyway.

    After you have set the configuration you need to call:
    ```java
    PushManager.init(getApplicationContext(), DEVELOPER_ID, APP_TOKEN,config);
    ```
    DEVELOPER_ID - your Google API Console Project ID, obtained from https://cloud.google.com/console<br/>
    APP_TOKEN    - your App's App Token in the PushApps admin console<br/>
    Don't forget to import com.groboot.pushapps.PushManager;<br/>
    
3. Add the following lines to the manifest XML file:
    
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


4. Inside the manifest XML file, In your application tag add the following receiver, service and activity:

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
    
    <service android:name="com.groboot.pushapps.GCMBaseIntentServiceImpl" />
    
    <activity
        android:name="com.groboot.pushapps.PushActivity"
        android:configChanges="orientation|keyboardHidden" />

    ``` 

4. Optional : to unregister a user from the PushApps service simply call ``		PushManager.unregister(getApplicationContext());`` and to register the user again to the PushApps service call `` PushManager.register(getApplicationContext());``


iOS:
====

1. Add PushApps.framework to your xcode project. Please make sure that the "Copy items into destination group's folder (if needed)" is NOT checked.

2. Make sure you include AdSupport.framework in your project.

3. Place #import PushApps/PushApps.h in your AppDelegate.h file. If you need, you can also declare on delegation <PushAppsDelegate>, in order to get notified on push and more events.

4. In your application didFinishLaunchingWithOptions method inside the AppDelegate, add the following line:

    ```objective-c
    [[PushAppsManager sharedInstance] setDelegate:self]; // optional
    [[PushAppsManager sharedInstance] startPushAppsWithAppToken:@"YOUR_APP_TOKEN" withLaunchOptions:launchOptions];
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
    
    
    
    
    [![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/PushAppsService/pushappssdk/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
