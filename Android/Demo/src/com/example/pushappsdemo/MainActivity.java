package com.example.pushappsdemo;

import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.groboot.pushapps.PushManager;

public class MainActivity extends Activity {
	public static final String GOOGLE_API_PROJECT_ID = ""; // your
																		// sender
																		// id
																		// (google
																		// API
																		// project
																		// id)
	public static final String PUSHAPPS_APP_TOKEN = ""; // your
																							// application
																							// token
																							// from
																							// PushApps
	EditText actionText;
	Button register, unregister, actionSend;
	Button clear;
	TextView message, title, json, sound;
	SharedPreferences sharedPrefs;
	TableLayout messageLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//used for demo purpose
		sharedPrefs = getSharedPreferences("pushappsdemo", MODE_PRIVATE);
		
		//first we initialize the push manager
		PushManager.init(getBaseContext(), GOOGLE_API_PROJECT_ID, PUSHAPPS_APP_TOKEN);
		//these methods are both optional and used for the notification customization 
		PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.notification_icon);
		PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(false);
		
		setContentView(R.layout.activity_main);
		message = (TextView) findViewById(R.id.message);
		title = (TextView) findViewById(R.id.title);
		json = (TextView) findViewById(R.id.json);
		sound = (TextView) findViewById(R.id.sound);
		clear = (Button) findViewById(R.id.clear);
		actionText = (EditText) findViewById(R.id.send_text);
		actionSend = (Button) findViewById(R.id.send);
		register = (Button) findViewById(R.id.register);
		messageLayout = (TableLayout)findViewById(R.id.message_layout);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();
			}
		});
		
		
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			//for the demo purpose only, we clear the previous message data
			clear();
			messageLayout.setVisibility(View.VISIBLE);
			clear.setVisibility(View.VISIBLE);
			sharedPrefs.edit().putBoolean("has_notification", true).commit();
			if (bundle.containsKey(PushManager.NOTIFICATION_TITLE_KEY)) {
				title.setText(bundle.getString(PushManager.NOTIFICATION_TITLE_KEY));
				//for the demo purpose we store the title
				sharedPrefs.edit().putString("title", bundle.getString(PushManager.NOTIFICATION_TITLE_KEY)).commit();
			}
			if (bundle.containsKey(PushManager.NOTIFICATION_MESSAGE_KEY)) {
				message.setText(bundle.getString(PushManager.NOTIFICATION_MESSAGE_KEY));
				//for the demo purpose we store the message
				sharedPrefs.edit().putString("message", bundle.getString(PushManager.NOTIFICATION_MESSAGE_KEY)).commit();
			}
			if (bundle.containsKey(PushManager.EXTRA_DATA)) {
				json.setText(PushManager.EXTRA_DATA + ": " + bundle.getString(PushManager.EXTRA_DATA));
				//for the demo purpose we store the data
				sharedPrefs.edit().putString("data", bundle.getString(PushManager.EXTRA_DATA)).commit();
			} else {
				for (String key : bundle.keySet()) {
					try {
						//a way to check a valid json
						JSONObject jsonObject = new JSONObject(bundle.get(key).toString());
						json.setText(key + ": " + bundle.get(key).toString());
						//for the demo purpose we store the data
						sharedPrefs.edit().putString("data", key + ": " + bundle.get(key).toString()).commit();
						break;
					} catch (Exception e) {

					}
				}
			}
			if (bundle.containsKey(PushManager.NOTIFICATION_SOUND_KEY)) {
				sound.setText(bundle.getString(PushManager.NOTIFICATION_SOUND_KEY));
				//for the demo purpose we store the sound
				sharedPrefs.edit().putString("sound", bundle.getString(PushManager.NOTIFICATION_SOUND_KEY)).commit();
			}
			//for demo purposes we display the previous message which was stored previously.
		} else {
			if (sharedPrefs.getBoolean("has_notification", false)) {
				message.setText(sharedPrefs.getString("message", ""));
				title.setText(sharedPrefs.getString("title", ""));
				json.setText(sharedPrefs.getString("data", ""));
				sound.setText(sharedPrefs.getString("sound", ""));
			} else {
				clear.setVisibility(View.GONE);
				messageLayout.setVisibility(View.GONE);
			}
		}
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PushManager.getInstance(getApplicationContext()).register();
			}
		});
		unregister = (Button) findViewById(R.id.unregister);
		unregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PushManager.getInstance(getApplicationContext()).unregister();
			}
		});
		actionSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (actionText.getText().toString().trim().length() > 0) {
					PushManager.getInstance(getBaseContext()).sendEvent(actionText.getText().toString().trim());
					actionText.setText("");
				}
			}
		});
	}

	//for demo purpose we clear the previous stored data
	private void clear() {
		sharedPrefs.edit().putBoolean("has_notification", false).putString("message", "").putString("title", "").putString("sound", "")
				.putString("data", "").commit();
		messageLayout.setVisibility(View.GONE);
		clear.setVisibility(View.GONE);
	}

}
