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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.CollaboratorListeners;
import org.rmit.hung.thingsdo.view.EditTaskScreen;

import java.util.ArrayList;

/**
 * Created by Hung on 17/07/14.
 */
public class CollaboratorListAdapter extends ArrayAdapter {
	private EditTaskScreen editTaskScreen;

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
			tempHolder.checkedTextView = (CheckedTextView) convertView.findViewById(R.id.text_collaborator_list_item_name);
			tempHolder.buttonRemoveCollaborator = (Button) convertView.findViewById(R.id.button_collaborator_list_item_remove_collaborator);

			convertView.setTag(tempHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.checkedTextView.setText(editTaskScreen.getCollaborators().get(position).getName());
		if (editTaskScreen.getCollaborators().get(position).getNotify().equals("1"))
			viewHolder.checkedTextView.setChecked(true);
		else
			viewHolder.checkedTextView.setChecked(false);

		CollaboratorListeners collaboratorListeners = new CollaboratorListeners(editTaskScreen, position);

		viewHolder.checkedTextView.setOnClickListener(collaboratorListeners);
		viewHolder.buttonRemoveCollaborator.setOnClickListener(collaboratorListeners);

		return convertView;
	}

	private class ViewHolder {
		public CheckedTextView checkedTextView;
		public Button          buttonRemoveCollaborator;
	}
}
