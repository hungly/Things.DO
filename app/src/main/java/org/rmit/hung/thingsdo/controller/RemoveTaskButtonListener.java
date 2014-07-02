/*
 * RemoveTaskButtonListener.java
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

import android.util.Log;
import android.view.View;

import org.rmit.hung.thingsdo.model.CategoryListItem;

/**
 * Created by Hung on 02/07/14.
 */
public class RemoveTaskButtonListener implements View.OnClickListener {

	private final CategoryListItem categoryListItem;
	private final int              taskPosition;

	public RemoveTaskButtonListener(CategoryListItem categoryListItem, int taskPosition) {
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
		Log.v("Things.DO", "Remove button for \"" + categoryListItem.getTask().get(taskPosition) + "\" clicked");
	}
}