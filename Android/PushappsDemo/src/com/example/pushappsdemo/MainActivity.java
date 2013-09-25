package com.example.pushappsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.groboot.pushapps.PushManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushManager.init(getApplicationContext(), "699397886418",
				"53701AEA-EFD0-488E-960C-879AF646E992");
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
