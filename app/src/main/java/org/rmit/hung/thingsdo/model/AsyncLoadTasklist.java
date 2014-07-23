/*
 * AsyncLoadTask.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       21/07/2014
 * Date last modified: 21/07/2014
 */

package org.rmit.hung.thingsdo.model;

import android.util.Log;

import com.google.api.services.tasks.model.TaskList;

import org.rmit.hung.thingsdo.view.MainScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hung on 21/07/14.
 */
public class AsyncLoadTasklist extends CommonAsyncTask {
	public AsyncLoadTasklist(MainScreen mainScreen) {
		super(mainScreen);
	}

	public static void run(MainScreen mainScreen) {
		new AsyncLoadTasklist(mainScreen).execute();
	}

	@Override
	protected void doInBackground() throws IOException {
		Log.v("Test", "Begin download task from server");

		if (client.tasklists().list().execute().isEmpty()) {
			Log.v("Test", "Nothing in the list");
		} else {
			Log.v("Test", "There something on the server");
			List<String> result = new ArrayList<String>();

			List<TaskList> taskList = client.tasklists().list().execute().getItems();

			Log.v("Test", "Got data from server");
			if (taskList != null) {
				for (TaskList list : taskList) {
					result.add(list.getTitle());
				}
			} else {
				result.add("No tasks.");
			}

			Log.v("Test", "Finished download task from server, now display list");

			for (String s : result) {
				Log.v("Test", s);
			}
		}
	}
}