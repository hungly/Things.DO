/*
 * AddCategoryButtonListener.java
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.view.MainScreen;

/**
 * Created by Hung on 07/07/14.
 */
public class AddCategoryButtonListener implements View.OnClickListener {
	private final Activity activity;

	public AddCategoryButtonListener(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View viewNewCategory = inflater.inflate(R.layout.layout_dialog_new_category, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setView(viewNewCategory);
		builder.setTitle(R.string.text_new_category_tittle);
		builder.setMessage(R.string.text_new_category_question);

		final EditText textNewCategoryName = (EditText) viewNewCategory.findViewById(R.id.text_new_category_name);

		builder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Log.v("Test", textNewCategoryName.getText().toString());

						((MainScreen) activity).addCategory(textNewCategoryName.getText().toString());
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = builder.create();

		alertDialog.show();
	}
}
