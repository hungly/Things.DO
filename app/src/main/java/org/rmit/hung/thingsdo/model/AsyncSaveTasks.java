/*
 * AsyncSaveTasks.java
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

import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.view.MainScreen;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hung on 21/07/14.
 */
public class AsyncSaveTasks extends CommonAsyncTask {
	public AsyncSaveTasks(MainScreen mainScreen) {
		super(mainScreen);
	}

	public static void run(MainScreen mainScreen) {
		new AsyncSaveTasks(mainScreen).execute();
	}

	@Override
	protected void doInBackground() throws IOException {
		DatabaseHandler db = mainScreen.getDatabase();

		ArrayList<TaskList> taskLists = new ArrayList<TaskList>();
		for (TaskList taskList : taskLists) {
			client.tasklists().delete(taskList.getId()).execute();
		}

		Log.v("Things.DO", "Clear task list completed");

		ArrayList<Category> categories = db.getAllCategories();

		for (Category category : categories) {
			TaskList taskList = new TaskList();

			taskList.setTitle(category.getCategory());

			TaskList result = client.tasklists().insert(taskList).execute();

			Log.v("Things.DO", "Task list successfully added: " + result.getId());
		}
	}
}
