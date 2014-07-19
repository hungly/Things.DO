/*
 * SMSService.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       19/07/2014
 * Date last modified: 19/07/2014
 */

package org.rmit.hung.thingsdo.model;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Hung on 19/07/14.
 */
public class SMSService extends IntentService {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 */
	public SMSService() {
		super("Things.DO Service");
	}

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name
	 * 		Used to name the worker thread, important only for debugging.
	 */
	public SMSService(String name) {
		super(name);
	}

	/**
	 * This method is invoked on the worker thread with a request to process.
	 * Only one Intent is processed at a time, but the processing happens on a
	 * worker thread that runs independently from other application logic.
	 * So, if this code takes a long time, it will hold up other requests to
	 * the same IntentService, but it will not hold up anything else.
	 * When all requests have been handled, the IntentService stops itself,
	 * so you should not call {@link #stopSelf}.
	 *
	 * @param intent
	 * 		The value passed to {@link
	 * 		android.content.Context#startService(android.content.Intent)}.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences preferences = getSharedPreferences("org.rmit.hung.thingsdo_preferences", MODE_PRIVATE);

		if (preferences.getBoolean("sms_read", false)) {
			String message = intent.getStringExtra("Message");
			String sender = intent.getStringExtra("Sender");
			String taskTittle;
			String taskCategory;
			String taskPriority;
			String taskDueDate;

			if (message.startsWith("\"Things.DO\" This person had added you to his/her task with tittle: \"") &&
			    message.indexOf("\" under category: \"") < message.indexOf("\" at priority: \"")) {

				taskTittle = message.substring(("\"Things.DO\" This person had added you to his/her task with tittle: " +
				                                "\"").length(), message.indexOf("\"", ("\"Things.DO\" This person had added you to his/her task with tittle: \"").length()));

				taskCategory = message.substring(message.indexOf("\" under category: \"") + ("\" under category: \"")
						.length(), message.indexOf("\"", message.indexOf("\" under category: \"") + ("\" under category: \"").length()));

				taskPriority = message.substring(message.indexOf("\" at priority: \"") + ("\" at priority: \"")
						.length(), message.indexOf("\"", message.indexOf("\" at priority: \"") + ("\" at priority: " +
				                                                                                  "\"").length()) - 1);

				if (message.contains("\" and has no due date")) {
					taskDueDate = "None";
				} else {
					taskDueDate = message.substring(message.indexOf("\" and due on: ") + ("\" and due on: ")
							.length(), message.length());
				}

//				Log.v("Test", "1: " + (message.indexOf("\" under category: \"") + ("\" under category: \"").length()));
//				Log.v("Test", "2: " + message.indexOf("\"", message.indexOf("\" under category: \"") + ("\" under category: \"").length()));

				Log.v("Test", "Tittle: " + taskTittle);
				Log.v("Test", "Category: " + taskCategory);
				Log.v("Test", "Priority: " + taskPriority);
				Log.v("Test", "Due date: " + taskDueDate);

				Log.v("Test", "Message: " + message);
				Log.v("Test", "Sender: " + sender);
			}
		}
	}
}
