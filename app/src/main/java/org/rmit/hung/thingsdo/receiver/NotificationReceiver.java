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

package org.rmit.hung.thingsdo.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.model.Task;
import org.rmit.hung.thingsdo.view.SplashScreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Hung on 16/07/14.
 */
public class NotificationReceiver extends BroadcastReceiver {
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
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String t = preferences.getString("notifications_time", "08:00");

		// merge current date with time get from preference
		String dateTime = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + " " + t;

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

//		Log.v("Test", dateTime);
//		Log.v("Test", currentTime.toString());

		// turn this back on when ready
		if (currentTime.compareTo(time) == 0) {
			Log.v("Things.DO", "It time to notify user");

			DatabaseHandler db = new DatabaseHandler(context);
			SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.date_format_trim_time));

			List<Task> tasks = db.getTasksByDueDate(format.format(currentTime), "=", "ASC");
			int numDue = tasks.size();

			// DO NOT run if numDue <= -1
			if (numDue >= 0) {
				Log.v("Things.DO", "Notification start");

				String notificationTittle = "Things.DO";
				String notificationText = "Great! You have no task today!";

				if (numDue == 1) {
					notificationTittle = "Things.DO task was due";
					notificationText = "You have 1 task for today";
				}
				if (numDue >= 2) {
					notificationTittle = "Things.DO tasks were due";
					notificationText = "You have " + numDue + " tasks for today";
				}

				Intent startThingsDO = new Intent(context, SplashScreen.class);
				PendingIntent pending = PendingIntent.getActivity(context, 5, startThingsDO, 0);

				NotificationCompat.Builder builder =
						new NotificationCompat.Builder(context)
								.setContentTitle(notificationTittle)
								.setContentText(notificationText)
								.setContentInfo("" + db.getTasksCount())
								.setSmallIcon(R.drawable.ic_notification)
								.setContentIntent(pending)
								.setSound(Uri.parse(preferences.getString("notifications_due_ringtone", "content://settings/system/notification_sound")));

				if (numDue > 0) {
					NotificationCompat.InboxStyle taskList = new NotificationCompat.InboxStyle();

					if (numDue == 1)
						taskList.setBigContentTitle("Task dues today:");
					else
						taskList.setBigContentTitle("Tasks due today:");

					for (Task task : tasks) {
						taskList.addLine(task.getTittle());
					}
					builder.setStyle(taskList);
				}

				// Gets an instance of the NotificationManager service
				NotificationManager notificationManager =
						(NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
				// Builds the notification and issues it.
				notificationManager.notify(1, builder.build());

				if (preferences.getBoolean("notifications_vibrate_on_due", true)) {
					Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

					if (v.hasVibrator()) {
						// Star War theme vibrate pattern :)
						v.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);
					}
				}
			}
		}
	}
}
