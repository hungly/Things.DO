/*
 * CategoryAdapter.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       01/07/2014
 * Date last modified: 01/07/2014
 */

package org.rmit.hung.thingsdo.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.rmit.hung.myapplication.R;

import java.util.zip.Inflater;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 */
public class CategoryAdapter extends ArrayAdapter<String> {
	private final Activity  context;
	private final String[] categories;

	public CategoryAdapter(Activity context, String[] categories) {
		super(context, R.layout.layout_category_list_item, categories);
		this.context = context;
		this.categories = categories;
	}

	static class ViewHolder {
		public Button buttonExpandable;
		public TextView textCategory;
		public Button buttonAddTask;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View category = convertView;

		if (category == null){
			LayoutInflater inflater = context.getLayoutInflater();
			category = inflater.inflate(R.layout.layout_category_list_item,null);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.buttonExpandable = (Button) category.findViewById(R.id.button_expandable);
			viewHolder.textCategory = (TextView) category.findViewById(R.id.text_category);
			viewHolder.buttonAddTask = (Button) category.findViewById(R.id.button_add_task);

			category.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) category.getTag();
		holder.textCategory.setText(categories[position]);

		return category;
	}
}
