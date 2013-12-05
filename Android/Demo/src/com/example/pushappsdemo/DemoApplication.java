package com.example.pushappsdemo;

import com.groboot.pushapps.PushAppsRegistrationInterface;
import com.groboot.pushapps.PushManager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class DemoApplication extends Application {
	
	public static final String GOOGLE_API_PROJECT_ID = ""; //your sender id (google API project id)
	public static final String PUSHAPPS_APP_TOKEN = ""; //your application token from PushApps
		
	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.init(getApplicationContext(), GOOGLE_API_PROJECT_ID, PUSHAPPS_APP_TOKEN);
		PushManager.getInstance(getApplicationContext()).registerForRegistrationEvents(new PushAppsRegistrationInterface() {
			
			@Override
			public void onUnregistered(Context paramContext, String paramString) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRegistered(Context paramContext, String paramString) {
				Log.d("DemoApplication", "REGISTERED: " + paramString);
				
			}
		});
	}
	
}
