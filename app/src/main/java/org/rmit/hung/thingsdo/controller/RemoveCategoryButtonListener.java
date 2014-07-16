/*
 * RemoveCategoryButtonListener.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       07/07/2014
 * Date last modified: 07/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import org.rmit.hung.thingsdo.model.Category;
import org.rmit.hung.thingsdo.view.CategoryManagerScreen;

import java.util.ArrayList;

/**
 * Created by Hung on 07/07/14.
 */
public class RemoveCategoryButtonListener implements View.OnClickListener {
	private final Activity            activity;
	private final ArrayList<Category> categories;
	private final int                 position;

	public RemoveCategoryButtonListener(Activity activity, ArrayList<Category> categories, int position) {
		this.activity = activity;
		this.categories = categories;
		this.position = position;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		Log.v("Things.DO", "Remove button for category \"" + categories.get(position).getCategory() + "\" clicked");

		new AlertDialog.Builder(activity)
				.setTitle("Delete category")
				.setMessage("Are you sure you want to delete this category?\nAll task under this category will also be deleted.")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						((CategoryManagerScreen) activity).removeCategory(categories, position);
					}
				})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				})
				.show();
	}
}
