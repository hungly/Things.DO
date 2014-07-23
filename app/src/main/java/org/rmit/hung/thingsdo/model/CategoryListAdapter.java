/*
 * CategoryListAdapter.java
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

package org.rmit.hung.thingsdo.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.RemoveCategoryButtonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hung on 07/07/14.
 */
public class CategoryListAdapter extends ArrayAdapter {
	final Context             context;
	final ArrayList<Category> categories;

	/**
	 * Constructor
	 *
	 * @param context
	 * 		The current context.
	 * @param objects
	 * 		The objects to represent in the ListView.
	 */
	public CategoryListAdapter(Context context, List<Category> objects) {
		super(context, R.layout.layout_category_list_item, objects);

		this.context = context;
		categories = (ArrayList<Category>) objects;
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
			convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_category_list_item_category_manager, null);

			ViewHolder tempHolder = new ViewHolder();

			tempHolder.textCategoryName = (TextView) convertView.findViewById(R.id.text_category_list_item_name);
			tempHolder.buttonRemoveCategory = (Button) convertView.findViewById(R.id.button_category_list_delete_category);

			convertView.setTag(tempHolder);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		holder.textCategoryName.setText(categories.get(position).getCategory());
		holder.buttonRemoveCategory.setOnClickListener(new RemoveCategoryButtonListener((Activity) context, categories, position));

		return convertView;
	}

	private static class ViewHolder {
		TextView textCategoryName;
		Button   buttonRemoveCategory;
	}
}
