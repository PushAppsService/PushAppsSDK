package com.example.pushappsdemo;

import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pushappsdemo.dev.R;
import com.groboot.pushapps.DeviceIDTypes;
import com.groboot.pushapps.PushAppsMessageInterface;
import com.groboot.pushapps.PushAppsNotification;
import com.groboot.pushapps.PushAppsRegistrationInterface;
import com.groboot.pushapps.PushManager;

public class DemoApplication extends Application {
	public static final String GOOGLE_API_PROJECT_NUMBER = "google project number"; // your sender id (google API project number )
	public static final String PUSHAPPS_APP_TOKEN = "app token here"; // your app token from PushApps Admin Console
	
	@Override
	public void onCreate() {
		super.onCreate();

		PushManager.getInstance(getApplicationContext()).setDeviceIDType(DeviceIDTypes.ANDROID_ID);
		// optional - register for message events. the default notification will be fired if you don't register for this event
		PushManager.getInstance(getApplicationContext()).registerForMessagesEvents(new PushAppsMessageInterface() {

			@Override
			public void onMessage(Context ctx, Intent intent) {
				PushManager.buildNotification(intent.getExtras(), ctx, (new Random()).nextInt(290) + 1);
				//here is an example for creating custom notifications
			//	PushAppsNotification notification = new PushAppsNotification();
			//	notification.setMessage("message").setTitle("title").setIcon(R.drawable.ic_launcher).setHasVibrate(true);
			//  notification.setVibrationPattern(new long[] {0 ,250,100,250});
			//  PushManager.buildNotification(intent.getExtras(), ctx, notification, 56);
			}
		});

		// optional - register for registration and unregistration events
		PushManager.getInstance(getApplicationContext()).registerForRegistrationEvents(new PushAppsRegistrationInterface() {

			@Override
			public void onUnregistered(Context arg0, String arg1) {
				Log.d("PushAppsDemo", "arg1 " + arg1);

			}

			@Override
			public void onRegistered(Context arg0, String arg1) {
				Log.d("PushAppsDemo", "arg1 " + arg1);
			}

			@Override
			public void onError(Context paramContext, String errorMessage) {
				Log.d("PushAppsDemo", "PUSHAPPS ERROR: " + errorMessage);
			}
		});
		
		//some extra configuration ( optional ) :

		//default is false
		PushManager.getInstance(getApplicationContext()).setShouldStartIntentAsNewTask(false);
		
		//default will be the application icon 
		PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.notification_icon);

		//default will be false ( If you decide to set setShouldStackNotifications to true then make sure you override the onNewIntent(Intent intent) method in your activity to handle the notification click)
		PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(false);
		
		//default is true
		PushManager.getInstance(getApplicationContext()).setVibrationEnabled(true);
		//example to a vibration pattern
		PushManager.getInstance(getApplicationContext()).setVibrationPattern(new long[] {0,300,200,300});
		
		// initialize the push manager, you can also initialize the
		// PushManager in your main activity.
		PushManager.init(getBaseContext(), GOOGLE_API_PROJECT_NUMBER, PUSHAPPS_APP_TOKEN);
	}

}
