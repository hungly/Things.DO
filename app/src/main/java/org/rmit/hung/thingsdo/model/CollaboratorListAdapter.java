/*
 * CollaboratorListAdapter.java
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

package org.rmit.hung.thingsdo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.CollaboratorListeners;
import org.rmit.hung.thingsdo.view.EditTaskScreen;

import java.util.ArrayList;

/**
 * Created by Hung on 17/07/14.
 */
public class CollaboratorListAdapter extends ArrayAdapter {
	private EditTaskScreen editTaskScreen;
	private SharedPreferences preferences = getContext().getSharedPreferences("org.rmit.hung.thingsdo_preferences",
	                                                                          Context.MODE_PRIVATE);

	public CollaboratorListAdapter(EditTaskScreen editTaskScreen, ArrayList<Collaborator> collaborators) {
		super(editTaskScreen, R.layout.layout_collaborator_list_item, collaborators);
		this.editTaskScreen = editTaskScreen;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = editTaskScreen.getLayoutInflater();
			convertView = inflater.inflate(R.layout.layout_collaborator_list_item, null);

			ViewHolder tempHolder = new ViewHolder();
			tempHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box_collaborator_list_item);
			tempHolder.collaboratorName = (TextView) convertView.findViewById(R.id.text_collaborator_list_item_name);
			tempHolder.buttonRemoveCollaborator = (Button) convertView.findViewById(R.id.button_collaborator_list_item_remove_collaborator);

			if (preferences.getBoolean("sms_send", true))
				tempHolder.checkBox.setEnabled(true);
			else
				tempHolder.checkBox.setEnabled(false);

			convertView.setTag(tempHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.collaboratorName.setText(editTaskScreen.getCollaborators().get(position).getName());

		CollaboratorListeners collaboratorListeners = new CollaboratorListeners(editTaskScreen, position);

		viewHolder.checkBox.setOnClickListener(collaboratorListeners);
		viewHolder.buttonRemoveCollaborator.setOnClickListener(collaboratorListeners);

		if (preferences.getBoolean("sms_send", false)) {
			if (editTaskScreen.getCollaborators().get(position).getNotify().equals("1")) {
				viewHolder.checkBox.setChecked(true);
			} else {
				viewHolder.checkBox.setChecked(false);
			}
		}

		return convertView;
	}

	private class ViewHolder {
		public CheckBox checkBox;
		public TextView collaboratorName;
		public Button   buttonRemoveCollaborator;
	}
}
