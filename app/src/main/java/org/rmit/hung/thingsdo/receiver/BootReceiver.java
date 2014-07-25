/*
 * ThingsDOReceiver.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       25/07/2014
 * Date last modified: 25/07/2014
 */

package org.rmit.hung.thingsdo.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Hung on 25/07/14.
 */
public class BootReceiver extends BroadcastReceiver {
	/**
	 * This method is called when the BroadcastReceiver is receiving an Intent
	 * broadcast.  During this time you can use the other methods on
	 * BroadcastReceiver to view/modify the current result values.  This method
	 * is always called within the main thread of its process, unless you
	 * explicitly asked for it to be scheduled on a different thread using
	 * {@link android.content.Context#registerReceiver(android.content.BroadcastReceiver,
	 * android.content.IntentFilter, String, android.os.Handler)}. When it runs on the main
	 * thread you should
	 * never perform long-running operations in it (there is a timeout of
	 * 10 seconds that the system allows before considering the receiver to
	 * be blocked and a candidate to be killed). You cannot launch a popup dialog
	 * in your implementation of onReceive().
	 * <p/>
	 * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
	 * then the object is no longer alive after returning from this
	 * function.</b>  This means you should not perform any operations that
	 * return a result to you asynchronously -- in particular, for interacting
	 * with services, you should use
	 * {@link android.content.Context#startService(android.content.Intent)} instead of
	 * {@link android.content.Context#bindService(android.content.Intent, android.content.ServiceConnection, int)}.  If you wish
	 * to interact with a service that is already running, you can use
	 * {@link #peekService}.
	 * <p/>
	 * <p>The Intent filters used in {@link android.content.Context#registerReceiver}
	 * and in application manifests are <em>not</em> guaranteed to be exclusive. They
	 * are hints to the operating system about how to find suitable recipients. It is
	 * possible for senders to force delivery to specific recipients, bypassing filter
	 * resolution.  For this reason, {@link #onReceive(android.content.Context, android.content.Intent) onReceive()}
	 * implementations should respond only to known actions, ignoring any unexpected
	 * Intents that they may receive.
	 *
	 * @param context
	 * 		The Context in which the receiver is running.
	 * @param intent
	 * 		The Intent being received.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("Things.DO", "Start broadcast on boot");

		Intent alarmIntent = new Intent(context.getApplicationContext(), NotificationReceiver.class);
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		SharedPreferences preferences = context.getSharedPreferences("org.rmit.hung.thingsdo_preferences", Context.MODE_PRIVATE);

		PendingIntent pendingIntent;

		if (preferences.getBoolean("notifications_on_due", true)) {
			Log.v("Things.DO", "Notification on task due is on");

			pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 50, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// broadcast every 1 minute
			manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent);
		} else {
			Log.v("Things.DO", "Notification on task due is off");

			pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 50, alarmIntent,
			                                           PendingIntent.FLAG_UPDATE_CURRENT);

			manager.cancel(pendingIntent);
		}


	}
}
