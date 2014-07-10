/*
 * TaskItemClickedListener.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       03/07/2014
 * Date last modified: 03/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.rmit.hung.thingsdo.model.CategoryListItem;
import org.rmit.hung.thingsdo.model.Task;

/**
 * Created by Hung on 03/07/14.
 */
public class TaskItemClickListener implements View.OnClickListener {
	private final Activity         activity;
	private final Intent           editTask;
	private final CategoryListItem categoryListItem;
	private final int              taskPosition;

	public TaskItemClickListener(Activity activity, Intent editTask, CategoryListItem categoryListItem, int taskPosition) {
		this.activity = activity;
		this.editTask = editTask;
		this.categoryListItem = categoryListItem;
		this.taskPosition = taskPosition;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		Task selectedTask = categoryListItem.getTask().get(taskPosition);

		Log.v("Things.DO", "Task \"" + selectedTask.getTittle() + "\" clicked, now opening edit screen for this task");

		Bundle taskBundle = new Bundle();
		taskBundle.putInt("Task ID", selectedTask.getID());
		taskBundle.putString("Google ID", selectedTask.getGoogleID());
		taskBundle.putString("Tittle", selectedTask.getTittle());
		taskBundle.putString("Update Date", selectedTask.getUpdateDate());
		taskBundle.putString("Parent", selectedTask.getParent());
		taskBundle.putString("Notes", selectedTask.getNotes());
		taskBundle.putString("Status", selectedTask.getStatus());
		taskBundle.putString("Due Date", selectedTask.getDueDate());
		taskBundle.putString("Completed Date", selectedTask.getCompletedDate());
		taskBundle.putString("Category", selectedTask.getCategory());

		taskBundle.putString("Old Category", selectedTask.getParent());
		taskBundle.putInt("Old Task Position", taskPosition);

		editTask.putExtras(taskBundle);

		activity.startActivityForResult(editTask, 1);
	}
}
