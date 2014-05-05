package com.example.pushappsdemo;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groboot.pushapps.PushManager;
import com.groboot.pushapps.SendTagResponseListener;
import com.groboot.pushapps.Tag;

public class MainActivity extends Activity implements SendTagResponseListener {

	EditText actionText;
	Button register, unregister, actionSend;
	Button clear;
	TextView message, title, json, sound;
	SharedPreferences sharedPrefs;
	TableLayout messageLayout;
	Dialog datePicker;
	Button sendBoolTag, sendDateTag, sendIntTag;
	EditText intTag, tagName;
	Button removeTag;

	private void handleMessage(Bundle bundle) {
		clear();
		messageLayout.setVisibility(View.VISIBLE);
		clear.setVisibility(View.VISIBLE);
		sharedPrefs.edit().putBoolean("has_notification", true).commit();
		if (bundle.containsKey(PushManager.NOTIFICATION_TITLE_KEY)) {
			title.setText(bundle.getString(PushManager.NOTIFICATION_TITLE_KEY));
			// for the demo purpose we store the title
			sharedPrefs.edit().putString("title", bundle.getString(PushManager.NOTIFICATION_TITLE_KEY)).commit();
		}
		if (bundle.containsKey(PushManager.NOTIFICATION_MESSAGE_KEY)) {
			message.setText(bundle.getString(PushManager.NOTIFICATION_MESSAGE_KEY));
			// for the demo purpose we store the message
			sharedPrefs.edit().putString("message", bundle.getString(PushManager.NOTIFICATION_MESSAGE_KEY)).commit();
		}
		if (bundle.containsKey(PushManager.EXTRA_DATA)) {
			json.setText(PushManager.EXTRA_DATA + ": " + bundle.getString(PushManager.EXTRA_DATA));
			// for the demo purpose we store the data
			sharedPrefs.edit().putString("data", bundle.getString(PushManager.EXTRA_DATA)).commit();
		} else {
			for (String key : bundle.keySet()) {
				try {
					// a way to check a valid json
					JSONObject jsonObject = new JSONObject(bundle.get(key).toString());
					json.setText(key + ": " + bundle.get(key).toString());
					// for the demo purpose we store the data
					sharedPrefs.edit().putString("data", key + ": " + bundle.get(key).toString()).commit();
					break;
				} catch (Exception e) {

				}
			}
		}
		if (bundle.containsKey(PushManager.NOTIFICATION_SOUND_KEY)) {
			sound.setText(bundle.getString(PushManager.NOTIFICATION_SOUND_KEY));
			// for the demo purpose we store the sound
			sharedPrefs.edit().putString("sound", bundle.getString(PushManager.NOTIFICATION_SOUND_KEY)).commit();
		}
	}

	private String getTagName() {
		return tagName.getText().toString().trim();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// used for demo purpose
		sharedPrefs = getSharedPreferences("pushappsdemo", MODE_PRIVATE);

		setContentView(R.layout.activity_main);
		message = (TextView) findViewById(R.id.message);
		title = (TextView) findViewById(R.id.title);
		json = (TextView) findViewById(R.id.json);
		sound = (TextView) findViewById(R.id.sound);
		clear = (Button) findViewById(R.id.clear);
		actionText = (EditText) findViewById(R.id.send_text);
		actionSend = (Button) findViewById(R.id.send);
		register = (Button) findViewById(R.id.register);
		messageLayout = (TableLayout) findViewById(R.id.message_layout);
		sendDateTag = (Button) findViewById(R.id.send_date);
		sendBoolTag = (Button) findViewById(R.id.send_boolean);
		removeTag = (Button) findViewById(R.id.remove_tag);
		removeTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getTagName().length() > 0)
					PushManager.getInstance(getApplicationContext()).removeTag(MainActivity.this, getTagName());
			}
		});
		sendBoolTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getTagName().length() == 0) {
					return;
				}
				(new AlertDialog.Builder(MainActivity.this)).setMessage("Send boolean tag")
						.setPositiveButton("True", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								PushManager.getInstance(getApplicationContext()).sendTag(MainActivity.this, new Tag(getTagName(), true));
							}
						}).setNeutralButton("False", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								PushManager.getInstance(getApplicationContext()).sendTag(MainActivity.this, new Tag(getTagName(), false));
							}
						}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).create().show();
			}
		});
		sendDateTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getTagName().length() == 0) {
					return;
				}

				Calendar c = Calendar.getInstance();
				datePicker = new DatePickerDialog(MainActivity.this, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar c = Calendar.getInstance();
						c.set(Calendar.YEAR, year);
						c.set(Calendar.MONTH, monthOfYear);
						c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						PushManager.getInstance(getApplicationContext()).sendTag(MainActivity.this, new Tag(getTagName(), c.getTime()));
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				datePicker.show();
			}
		});
		tagName = (EditText) findViewById(R.id.tag_name);
		intTag = (EditText) findViewById(R.id.send_int);
		sendIntTag = (Button) findViewById(R.id.send_int_btn);
		sendIntTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getTagName() != null && getTagName().length() > 0 && intTag.getText().toString().trim().length() > 0) {
					PushManager.getInstance(getBaseContext()).sendTag(MainActivity.this,
							new Tag(getTagName(), Integer.parseInt(intTag.getText().toString().trim())));
					intTag.setText("");

					PushManager.getInstance(getApplicationContext()).sendTag(null, new Tag("my_int_tag_name", 12),
							new Tag("another_tag_name", "stringvalue"), new Tag("my_boolean_tag_name", true),
							new Tag("my_date_tag_name", new Date()));
				}
			}
		});
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();
				message.setText("");
				title.setText("");
				json.setText("");
				sound.setText("");
			}
		});

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {

			// for the demo purpose only, we clear the previous message data
			handleMessage(bundle);
			// for demo purposes we display the previous message which was
			// stored previously.
		} else {
			if (sharedPrefs.getBoolean("has_notification", false)) {
				message.setText(sharedPrefs.getString("message", ""));
				title.setText(sharedPrefs.getString("title", ""));
				json.setText(sharedPrefs.getString("data", ""));
				sound.setText(sharedPrefs.getString("sound", ""));
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
				if (getTagName() != null && getTagName().length() > 0 && actionText.getText().toString().trim().length() > 0) {
					PushManager.getInstance(getBaseContext()).sendTag(MainActivity.this,
							new Tag(getTagName(), actionText.getText().toString().trim()));
					actionText.setText("");
				}
			}
		});
	}

	// for demo purpose we clear the previous stored data
	private void clear() {
		sharedPrefs.edit().putBoolean("has_notification", false).putString("message", "").putString("title", "").putString("sound", "")
				.putString("data", "").commit();
		
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("MainActivity", "onNewIntent");
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			handleMessage(bundle);
		}
	}

	@Override
	public void response(boolean success, final String message) {
		Log.d("PushappsDemo", message);
		if (success) {
			// handle success
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG).show();
				}
			});
		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
				}
			});
			// handle failure
		}
	}

}
