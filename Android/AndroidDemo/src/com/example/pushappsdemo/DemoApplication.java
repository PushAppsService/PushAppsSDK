package com.example.pushappsdemo;

import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.groboot.pushapps.PushAppsMessageInterface;
import com.groboot.pushapps.PushAppsNotification;
import com.groboot.pushapps.PushAppsRegistrationInterface;
import com.groboot.pushapps.PushManager;

public class DemoApplication extends Application {
	public static final String GOOGLE_API_PROJECT_NUMBER = "47811378595"; // your
																		// sender
																		// id
																		// (google
																		// API
																		// project
																		// id)
	public static final String PUSHAPPS_APP_TOKEN = "1a3267ab-aa11-4bb2-8dda-034b3a6566ee"; // your
																							// application
																							// token
																							// from
																							// PushApps

	// public static final String PUSHAPPS_APP_TOKEN =
	// "82a12c6a-e179-4e9d-a089-a1674fd7baef";
	@Override
	public void onCreate() {
		super.onCreate();
		// first we initialize the push manager, you can also initialize the
		// PushManager in your main activity.
		PushManager.init(getBaseContext(), GOOGLE_API_PROJECT_NUMBER, PUSHAPPS_APP_TOKEN);
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
				PushAppsNotification notification = new PushAppsNotification();
			//	notification.setMessage("blah").setTitle("mmm").setIcon(R.drawable.ic_launcher);
				//PushManager.buildNotification(intent.getExtras(), ctx, notification, 56);
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
	}

}
