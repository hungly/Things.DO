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

import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.TaskList;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.view.MainScreen;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Hung on 21/07/14.
 */
public class AsyncTasks extends CommonAsyncTask {
	public AsyncTasks(MainScreen mainScreen) {
		super(mainScreen);
	}

	public static void run(MainScreen mainScreen) {
		new AsyncTasks(mainScreen).execute();
	}

	@Override
	protected void doInBackground() throws IOException {
		Log.v("Things.DO", "Prepare to sync task list with category");

		ArrayList<Category> categories = db.getAllCategories();
		List<TaskList> taskLists = client.tasklists().list().execute().getItems();
		SimpleDateFormat dateFormat = new SimpleDateFormat(mainScreen.getString(R.string.date_format));

		Log.v("Things.DO", "Number of task list need to sync: " + categories.size());

		// upload task list
		for (Category category : categories) {
			if (category.getGoogleID().equals("0")) {
				// this category is not synced yet
				Log.v("Things.DO", "Sync category: " + category.getCategory() + " with task list");

				// upload this category to the server as task list
				TaskList taskList = new TaskList();
				taskList.setTitle(category.getCategory());

				TaskList result = client.tasklists().insert(taskList).execute();

				category.setGoogleID(result.getId());

				mainScreen.getDatabase().updateCategory(category);
			} else {
				Boolean isFound = false;

				// it already been synced before
				// find correspond task list
				for (TaskList taskList : taskLists) {
					if (taskList.getId().equals(category.getGoogleID())) {
						isFound = true;
						break;
					}
				}

				// no task list found in server
				if (!isFound) {
					Log.v("Things.DO", "Re-create task list " + category.getCategory());
					// task list has been deleted on server, sync again
					TaskList taskList = new TaskList();
					taskList.setTitle(category.getCategory());

					TaskList result = client.tasklists().insert(taskList).execute();

					category.setGoogleID(result.getId());

					mainScreen.getDatabase().updateCategory(category);
				}
			}
		}

		// upload tasks
		// get current task list from server
		taskLists = client.tasklists().list().execute().getItems();

		Log.v("Things.DO", "Number of task list from server: " + taskLists.size());

		// for each category on local
		for (Category category : categories) {
			// get task in current category
			ArrayList<Task> tasks = db.getTasksByCategory(category, "ASC");

			// get the task list if of this category
			String taskListID = "@default";
			for (TaskList taskList : taskLists) {
				if (taskList.getTitle().equals(category.getCategory())) {
					Log.v("Things.DO", "Found match task list \"" + taskList.getTitle() + "\" with category \"" +
					                   category.getCategory() + "\"");
					taskListID = taskList.getId();
					break;
				}
			}

			// for each task in current category
			for (Task task : tasks) {
				if (task.getGoogleID().equals("0")) {
					// this has not been synced yet
					Log.v("Things.DO", "Task \"" + task.getTittle() + "\" has not been sync yet");

					// create an empty task
					com.google.api.services.tasks.model.Task t = new com.google.api.services.tasks.model.Task();

					// store info
					t.setTitle(task.getTittle());
					try {
						t.setUpdated(new DateTime(dateFormat.parse(task.getUpdateDate())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					t.setParent(task.getParent());
					t.setNotes(task.getNotes());
					t.setStatus(task.getStatus());
					if (!task.getDueDate().equals("None")) {
						try {
							t.setDue(new DateTime(dateFormat.parse(task.getDueDate())));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (!task.getCompletedDate().equals("")) {
						try {
							t.setCompleted(new DateTime(dateFormat.parse(task.getCompletedDate())));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					// push it to server
					com.google.api.services.tasks.model.Task result = client.tasks().insert(taskListID, t).execute();

					// store ID to database
					task.setGoogleID(result.getId());

					db.updateTask(task);
				} else {
					Log.v("Things.DO", "Task \"" + task.getTittle() + "\" has been sync");

					// try to search for this task
					Boolean isFound = false;

					List<com.google.api.services.tasks.model.Task> taskList = client.tasks().list(taskListID).execute
							().getItems();

					if (taskList != null) {
						for (com.google.api.services.tasks.model.Task t : taskList) {
							if (t.getId().equals(task.getGoogleID())) {
								isFound = true;
								Log.v("Things.DO", "Task is found, ID: " + t.getId());
								break;
							}
						}
					}

					if (isFound) {
						// this already been sync before
						// check update date
						DateTime localDate = new DateTime(Calendar.getInstance().getTime());
						try {
							localDate = new DateTime(dateFormat.parse(task.getUpdateDate()));
						} catch (ParseException e) {
							e.printStackTrace();
						}

						com.google.api.services.tasks.model.Task googleTask = client.tasks().get(category.getGoogleID(), task.getGoogleID()).execute();

						DateTime serverDate = googleTask.getUpdated();

						// update time is different
						if (!serverDate.equals(localDate)) {
							com.google.api.services.tasks.model.Task t = client.tasks().get(taskListID,
							                                                                task.getGoogleID())
							                                                   .execute();

							t.setTitle(task.getTittle());
							t.setUpdated(localDate);
							t.setParent(task.getParent());
							t.setNotes(task.getNotes());
							t.setStatus(task.getStatus());
							if (!task.getDueDate().equals("None")) {
								try {
									t.setDue(new DateTime(dateFormat.parse(task.getDueDate())));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							if (!task.getCompletedDate().equals("")) {
								try {
									t.setCompleted(new DateTime(dateFormat.parse(task.getCompletedDate())));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}

							client.tasks().update(taskListID, task.getGoogleID(), t).execute();
						}
					} else {
						Log.v("Things.DO", "No task found");

						com.google.api.services.tasks.model.Task t = new com.google.api.services.tasks.model.Task();
						t.setTitle(task.getTittle());
						try {
							t.setUpdated(new DateTime(dateFormat.parse(task.getUpdateDate())));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						t.setParent(task.getParent());
						t.setNotes(task.getNotes());
						t.setStatus(task.getStatus());
						if (!task.getDueDate().equals("None")) {
							try {
								t.setDue(new DateTime(dateFormat.parse(task.getDueDate())));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if (!task.getCompletedDate().equals("")) {
							try {
								t.setCompleted(new DateTime(dateFormat.parse(task.getCompletedDate())));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						com.google.api.services.tasks.model.Task result = client.tasks().insert(taskListID, t).execute();

						task.setGoogleID(result.getId());

						db.updateTask(task);
					}
				}
			}
		}

		Log.v("Things.DO", "Finish sync all task list");
	}
}
