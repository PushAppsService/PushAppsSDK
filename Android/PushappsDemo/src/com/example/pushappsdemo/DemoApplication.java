package com.example.pushappsdemo;

import com.groboot.pushapps.PushManager;

import android.app.Application;

public class DemoApplication extends Application {
	public static final String SENDER_ID = ""; //your sender id (google api project id)
	public static final String APP_TOKEN = ""; //your application token
	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.init(getApplicationContext(), SENDER_ID,
				APP_TOKEN);
	}
}
