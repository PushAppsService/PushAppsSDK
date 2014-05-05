package com.example.pushappsdemo;

import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.groboot.pushapps.PushAppsMessageInterface;
import com.groboot.pushapps.PushAppsRegistrationInterface;
import com.groboot.pushapps.PushManager;

public class DemoApplication extends Application {
	public static final String GOOGLE_API_PROJECT_ID = ""; //sender id(google api project id)
																		 
																		 
	public static final String PUSHAPPS_APP_TOKEN = ""; //application token from PushApps
																						
	@Override
	public void onCreate() {
		super.onCreate();
		// first we initialize the push manager, you can also initialize the
		// PushManager in your main activity.
		PushManager.init(getBaseContext(), GOOGLE_API_PROJECT_ID, PUSHAPPS_APP_TOKEN);
		PushManager.getInstance(getApplicationContext()).setShouldStartIntentAsNewTask(false);
		// these methods are both optional and used for the notification
		// customization
		PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.notification_icon);

		PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(true);

		// optional - register for message events

		PushManager.getInstance(getApplicationContext()).registerForMessagesEvents(new PushAppsMessageInterface() {

			@Override
			public void onMessage(Context ctx, Intent intent) {
				PushManager.buildNotification(intent.getExtras(), ctx, (new Random()).nextInt(290) + 1);
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
		});
	}

}
