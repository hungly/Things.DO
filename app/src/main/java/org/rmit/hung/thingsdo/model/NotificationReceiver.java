/*
 * NotificationReceiver.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       16/07/2014
 * Date last modified: 16/07/2014
 */

package org.rmit.hung.thingsdo.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.rmit.hung.thingsdo.view.MainScreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hung on 16/07/14.
 */
public class NotificationReceiver extends BroadcastReceiver {
	private MainScreen mainScreen;
	private Intent alarmIntent;

	public NotificationReceiver() {
		super();
	}

	public NotificationReceiver(MainScreen mainScreen, Intent alarmIntent) {
		this.mainScreen = mainScreen;
		this.alarmIntent = alarmIntent;
	}

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
	 * {@link android.content.Context#bindService(android.content.Intent, android.content.ServiceConnection, int)}.  If
	 * you wish
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d HH:mm");

		// get current time
		Calendar c = Calendar.getInstance();

//		String t = alarmIntent.getStringExtra("Time");
		// merge current date with time get from preference
		String dateTime = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + " 08:00";

		// get the date time for notifications
		Date time = c.getTime();
		try {
			time = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// clear current time second part
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date currentTime = c.getTime();

		Log.v("Test", dateTime);
		Log.v("Test", currentTime.toString());

		// turn this back on when ready
//		if (currentTime.compareTo(time) == 0) {
		Toast.makeText(context, "Running", Toast.LENGTH_SHORT).show();
//		}
	}
}
