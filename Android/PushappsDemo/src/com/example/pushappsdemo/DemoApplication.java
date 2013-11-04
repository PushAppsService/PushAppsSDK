package com.example.pushappsdemo;

import com.groboot.pushapps.PushManager;

import android.app.Application;

public class DemoApplication extends Application {
	public static final String SENDER_ID = "47811378595"; //your sender id (google api project id)
	public static final String APP_TOKEN = "1A3267AB-AA11-4BB2-8DDA-034B3A6566EE"; //your application token
	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.init(getApplicationContext(), SENDER_ID,
				APP_TOKEN);
	}
}
