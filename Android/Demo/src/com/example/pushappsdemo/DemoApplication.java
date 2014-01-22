package com.example.pushappsdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.groboot.pushapps.PushAppsMessageInterface;
import com.groboot.pushapps.PushAppsRegistrationInterface;
import com.groboot.pushapps.PushManager;

public class DemoApplication extends Application {
	public static final String GOOGLE_API_PROJECT_ID = "47811378595"; //your sender id (google API project id)
	public static final String PUSHAPPS_APP_TOKEN = "1a3267ab-aa11-4bb2-8dda-034b3a6566ee"; //your application token from PushApps
	@Override
	public void onCreate() {
		super.onCreate();
		//first we initialize the push manager, you can also initialize the PushManager in your main activity.
		PushManager.init(getBaseContext(), GOOGLE_API_PROJECT_ID, PUSHAPPS_APP_TOKEN);
		//these methods are both optional and used for the notification customization 
		PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.notification_icon);
		PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(false);

		/*//optional - register for message events
		PushManager.getInstance(getApplicationContext()).registerForMessagesEvents(new PushAppsMessageInterface() {
			
			@Override
			public void onMessage(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		//optional - register for registration and unregistration events
		PushManager.getInstance(getApplicationContext()).registerForRegistrationEvents(new PushAppsRegistrationInterface() {
			
			@Override
			public void onUnregistered(Context arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRegistered(Context arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
