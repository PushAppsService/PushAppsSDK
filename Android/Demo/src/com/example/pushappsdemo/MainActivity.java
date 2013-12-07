package com.example.pushappsdemo;

import com.groboot.pushapps.PushManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			if (bundle.containsKey(PushManager.NOTIFICATION_MESSAGE_KEY))  {
				Log.d("DemoApplication", "Message: " + this.getIntent().getExtras().getString(PushManager.NOTIFICATION_MESSAGE_KEY));
			}
			if (bundle.containsKey(PushManager.NOTIFICATION_TITLE_KEY))  {
				Log.d("DemoApplication", "Title: " + this.getIntent().getExtras().getString(PushManager.NOTIFICATION_TITLE_KEY));
			}
			if (bundle.containsKey(PushManager.NOTIFICATION_LINK_KEY))  {
				Log.d("DemoApplication", "Link: " + this.getIntent().getExtras().getString(PushManager.NOTIFICATION_LINK_KEY));
			}
			if (bundle.containsKey(PushManager.NOTIFICATION_SOUND_KEY))  {
				Log.d("DemoApplication", "Sound: " + this.getIntent().getExtras().getString(PushManager.NOTIFICATION_SOUND_KEY));
			}
		}
	}

}
