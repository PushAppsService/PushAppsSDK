package com.example.pushappsdemo;

import android.app.Application;

public class DemoApplication extends Application {
	public static final String GOOGLE_API_PROJECT_ID = "47811378595"; //your sender id (google API project id)
	public static final String PUSHAPPS_APP_TOKEN = "1a3267ab-aa11-4bb2-8dda-034b3a6566ee"; //your application token from PushApps
	@Override
	public void onCreate() {
		super.onCreate();
		
		//initialize the PushApps configuration with a notification icon resource id
/*		PushAppsConfiguration config = new PushAppsConfiguration(R.drawable.notification_icon);
		
		
		//optional - here you can set the intent which will be fired when the user clicks on the notification.
		config.setNotificationIntent(new Intent(this, MainActivity.class));
		
		//optional - pass an interface to receive GCM callbacks
		config.setInterface(pushAppsInterface, false);
		
		
        //this method must be called
		PushManager.init(getApplicationContext(), GOOGLE_API_PROJECT_ID,
				PUSHAPPS_APP_TOKEN,config);
*/	}
	
}
