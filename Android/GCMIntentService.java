package com.groboot.zimmerportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.groboot.pushapps.GCMBaseIntentService;
import com.groboot.pushapps.PushManager;
import com.groboot.zimmerportal.activities.SplashActivity;

public class GCMIntentService extends GCMBaseIntentService {
	  
    private final static int NOTIFICATION_ID = 9999;
  
    public static final String TAG = "GCMIntentService";
  
    public GCMIntentService() {
        super("699397886418");
    }
  
    @Override
    protected void onRegistered(Context context, String registrationId) {
    	Log.d(TAG, "Device onRegistered " + registrationId);
    }
  
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.d(TAG, "Device onUnregistered " + registrationId);
    }
  
    @Override
    protected void onMessage(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        if (data != null) {
            /*
             * String alert = data.getString(Configuration.GCMValues.ALERT); String
             * title = context.getString(R.string.app_name); if
             * (data.containsKey(Configuration.GCMValues.TITLE)) { title =
             * data.getString(Configuration.GCMValues.TITLE); }
             * createNotification(alert,title, context);
             */
            Intent notificationIntent = new Intent();
            notificationIntent.setClass(context, SplashActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PushManager.buildNotification(intent.getExtras(), context,
                    NOTIFICATION_ID, R.drawable.ic_launcher, notificationIntent);
        }
  
    }
  
  
    @Override
    protected void onDeletedMessages(Context context, int total) {
    }
  
    @Override
    public void onError(Context context, String errorId) {
        //CustomLogger.log(TAG, "Device onError " + errorId);
    }
  
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }
}

