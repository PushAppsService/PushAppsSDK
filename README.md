PushAppsSDK
===========

Welcome to PushApps SDK's and Tutorials!

Android:
========

In order to integrate the PushApps SDK for Android you need to:

1. Add the pushapps.jar file to your project libs folder.
2. In your application.java file, inside the method onCreate() write the following line:
    
    ```java
    PushManager.init(getApplicationContext(),appId,appToken);
    ``` 

3. Add GCMIntentService.class to your root package folder. For example if your package name is com.example.push then the class should be in the src/com/example/push/ folder.
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
        android:name="com.groboot.grobootpushlibrary.GCMBroadcastReceiver"
        android:permission="com.google.android.c2dm.permission.SEND" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
            <category android:name="<your package>" />
        </intent-filter>
    </receiver>
    
    <service android:name=".GCMIntentService" />
    
    <activity
        android:name="com.groboot.grobootpushlibrary.GrobootPushActivity"
        android:configChanges="orientation|keyboardHidden" />

    ``` 
6. 
    

iOS:
====

