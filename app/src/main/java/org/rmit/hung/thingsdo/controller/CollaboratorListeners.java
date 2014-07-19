/*
 * CollaboratorListeners.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       17/07/2014
 * Date last modified: 17/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.view.EditTaskScreen;

/**
 * Created by Hung on 17/07/14.
 */
public class CollaboratorListeners implements View.OnClickListener {
	private EditTaskScreen editTaskScreen;
	private int            position;

	public CollaboratorListeners(EditTaskScreen editTaskScreen, int position) {
		this.editTaskScreen = editTaskScreen;
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
		switch (v.getId()) {
			case (R.id.check_box_collaborator_list_item):
				CheckBox c = (CheckBox) v;

				if (!c.isChecked()) {
					c.setChecked(false);

					editTaskScreen.getCollaborators().get(position).setNotify("0");
					Log.v("Test", "OFF");
				} else {
					c.setChecked(true);

					editTaskScreen.getCollaborators().get(position).setNotify("1");
					Log.v("Test", "ON");
				}

				break;
			case (R.id.button_collaborator_list_item_remove_collaborator):
				editTaskScreen.removeCollaborator(position);

				break;
		}
	}
}
