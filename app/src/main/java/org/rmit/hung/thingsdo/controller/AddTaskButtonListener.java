/*
 * AddTaskButtonListener.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       02/07/2014
 * Date last modified: 02/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.rmit.hung.thingsdo.model.CategoryListItem;

/**
 * Created by Hung on 02/07/14.
 */
public class AddTaskButtonListener implements View.OnClickListener {
	private final Activity         activity;
	private final Intent           addTask;
	private final CategoryListItem categoryListItem;

	public AddTaskButtonListener(Activity activity, Intent addTask, CategoryListItem categoryListItem) {
		this.activity = activity;
		this.addTask = addTask;
		this.categoryListItem = categoryListItem;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		Bundle taskBundle = new Bundle();

		taskBundle.putInt("Task ID", -1);
		taskBundle.putString("Google ID", "0");
		taskBundle.putString("Parent", categoryListItem.getCategory());

		addTask.putExtras(taskBundle);

		Log.v("Things.DO", "Add button for \"" + categoryListItem.getCategory() + "\" clicked");

		Log.v("Activity", "Going to start result activity for \"" + activity.getClass().getSimpleName() + "\"");

		activity.startActivityForResult(addTask, 0);
	}
}
