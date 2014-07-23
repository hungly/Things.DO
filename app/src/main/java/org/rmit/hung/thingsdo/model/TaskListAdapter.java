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
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.AddTaskButtonListener;
import org.rmit.hung.thingsdo.controller.RemoveTaskButtonListener;
import org.rmit.hung.thingsdo.controller.TaskItemClickListener;
import org.rmit.hung.thingsdo.controller.TaskItemListener;

import java.util.ArrayList;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 *          <p/>
 *          Reference:
 *          -   http://www.vogella.com/tutorials/AndroidListView/article.html#listviewselection
 */
public class TaskListAdapter extends BaseExpandableListAdapter {
	private final Activity                    activity;
	private final Intent                      addTask;
	private       ArrayList<CategoryListItem> categoryListItemArrayList;
	private       LayoutInflater              inflater;

	public TaskListAdapter(Activity activity, Intent addTask, ArrayList<CategoryListItem> categoryListItemArrayList) {
		this.categoryListItemArrayList = categoryListItemArrayList;
		this.activity = activity;
		this.addTask = addTask;
		this.inflater = activity.getLayoutInflater();
	}

	public void swapCategoryItemArrayList(ArrayList<CategoryListItem> categoryListItemArrayList) {
		this.categoryListItemArrayList = categoryListItemArrayList;
	}

	/**
	 * Gets the number of groups.
	 *
	 * @return the number of groups
	 */
	@Override
	public int getGroupCount() {
		return categoryListItemArrayList.size();
	}

	/**
	 * Gets the number of children in a specified group.
	 *
	 * @param groupPosition
	 * 		the position of the group for which the children
	 * 		count should be returned
	 *
	 * @return the children count in the specified group
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return categoryListItemArrayList.get(groupPosition).getTask().size();
	}

	/**
	 * Gets the data associated with the given group.
	 *
	 * @param groupPosition
	 * 		the position of the group
	 *
	 * @return the data child for the specified group
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return categoryListItemArrayList.get(groupPosition);
	}

	/**
	 * Gets the data associated with the given child within the given group.
	 *
	 * @param groupPosition
	 * 		the position of the group that the child resides in
	 * @param childPosition
	 * 		the position of the child with respect to other
	 * 		children in the group
	 *
	 * @return the data of the child
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return categoryListItemArrayList.get(groupPosition).getTask().get(childPosition);
	}

	/**
	 * Gets the ID for the group at the given position. This group ID must be
	 * unique across groups. The combined ID (see
	 * {@link #getCombinedGroupId(long)}) must be unique across ALL items
	 * (groups and all children).
	 *
	 * @param groupPosition
	 * 		the position of the group for which the ID is wanted
	 *
	 * @return the ID associated with the group
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	/**
	 * Gets the ID for the given child within the given group. This ID must be
	 * unique across all children within the group. The combined ID (see
	 * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
	 * (groups and all children).
	 *
	 * @param groupPosition
	 * 		the position of the group that contains the child
	 * @param childPosition
	 * 		the position of the child within the group for which
	 * 		the ID is wanted
	 *
	 * @return the ID associated with the child
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	/**
	 * Indicates whether the child and group IDs are stable across changes to the
	 * underlying data.
	 *
	 * @return whether or not the same ID always refers to the same object
	 *
	 * @see android.widget.Adapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return false;
	}

	/**
	 * Gets a View that displays the given group. This View is only for the
	 * group--the Views for the group's children will be fetched using
	 * {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
	 *
	 * @param groupPosition
	 * 		the position of the group for which the View is
	 * 		returned
	 * @param isExpanded
	 * 		whether the group is expanded or collapsed
	 * @param convertView
	 * 		the old view to reuse, if possible. You should check
	 * 		that this view is non-null and of an appropriate type before
	 * 		using. If it is not possible to convert this view to display
	 * 		the correct data, this method can create a new view. It is not
	 * 		guaranteed that the convertView will have been previously
	 * 		created by
	 * 		{@link #getGroupView(int, boolean, android.view.View, android.view.ViewGroup)}.
	 * @param parent
	 * 		the parent that this view will eventually be attached to
	 *
	 * @return the View corresponding to the group at the specified position
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_category_list_item, null);

			CategoryViewHolder tempCategoryViewHolder = new CategoryViewHolder();

			tempCategoryViewHolder.textCategory = (CheckedTextView) convertView.findViewById(R.id.text_category_list_item_name);
			tempCategoryViewHolder.buttonAddTask = (Button) convertView.findViewById(R.id.button_category_list_item_add_task);

			convertView.setTag(tempCategoryViewHolder);
		}

		CategoryViewHolder categoryViewHolder = (CategoryViewHolder) convertView.getTag();
		CategoryListItem categoryListItem = (CategoryListItem) getGroup(groupPosition);

		categoryViewHolder.textCategory.setText(categoryListItem.getCategory());
		categoryViewHolder.textCategory.setChecked(isExpanded);

		categoryViewHolder.buttonAddTask.setOnClickListener(new AddTaskButtonListener(activity, addTask, categoryListItem));

		return convertView;
	}

	/**
	 * Gets a View that displays the data for the given child within the given
	 * group.
	 *
	 * @param groupPosition
	 * 		the position of the group that contains the child
	 * @param childPosition
	 * 		the position of the child (for which the View is
	 * 		returned) within the group
	 * @param isLastChild
	 * 		Whether the child is the last child within the group
	 * @param convertView
	 * 		the old view to reuse, if possible. You should check
	 * 		that this view is non-null and of an appropriate type before
	 * 		using. If it is not possible to convert this view to display
	 * 		the correct data, this method can create a new view. It is not
	 * 		guaranteed that the convertView will have been previously
	 * 		created by
	 * 		{@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
	 * @param parent
	 * 		the parent that this view will eventually be attached to
	 *
	 * @return the View corresponding to the child at the specified position
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final String taskItem = ((Task) getChild(groupPosition, childPosition)).getTittle();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_task_list_item, null);

			TaskViewHolder tempTaskViewHolder = new TaskViewHolder();

			tempTaskViewHolder.hasDueDate = (TextView) convertView.findViewById(R.id.text_has_due_date);
			tempTaskViewHolder.textView = (TextView) convertView.findViewById(R.id.text_task_list_item_name);
			tempTaskViewHolder.buttonEditTask = (Button) convertView.findViewById(R.id.button_task_list_item_edit_task);
			tempTaskViewHolder.buttonRemoveTask = (Button) convertView.findViewById(R.id
					                                                                        .button_task_list_item_remove_task);

			convertView.setTag(tempTaskViewHolder);
		}

		TaskViewHolder taskViewHolder = (TaskViewHolder) convertView.getTag();

		taskViewHolder.textView.clearComposingText();
		taskViewHolder.textView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
		taskViewHolder.textView.setPaintFlags(Paint.HINTING_ON);

		if (categoryListItemArrayList.get(groupPosition).getTask().get(childPosition).getDueDate().equals("None")){
			taskViewHolder.hasDueDate.setVisibility(View.INVISIBLE);
		}else {
			taskViewHolder.hasDueDate.setVisibility(View.VISIBLE);
		}

		if (categoryListItemArrayList.get(groupPosition).getTask().get(childPosition).getParent().equals("Urgent")){
			taskViewHolder.textView.setTextColor(convertView.getResources().getColor(R.color.text_urgent));
		}else {
			taskViewHolder.textView.setTextColor(convertView.getResources().getColor(R.color.text_normal_list_item));
		}

		if (categoryListItemArrayList.get(groupPosition).getTask().get(childPosition).getStatus().equals("completed")) {
			taskViewHolder.textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}

		taskViewHolder.textView.setText(taskItem);
		taskViewHolder.textView.setOnTouchListener(new TaskItemListener(activity, addTask,
		                                                                categoryListItemArrayList.get(groupPosition), childPosition));
		taskViewHolder.buttonEditTask.setOnClickListener(new TaskItemClickListener(activity, addTask, categoryListItemArrayList.get(groupPosition), childPosition));
		taskViewHolder.buttonRemoveTask.setOnClickListener(new RemoveTaskButtonListener(activity, categoryListItemArrayList.get(groupPosition), childPosition));

		return convertView;
	}

	/**
	 * Whether the child at the specified position is selectable.
	 *
	 * @param groupPosition
	 * 		the position of the group that contains the child
	 * @param childPosition
	 * 		the position of the child within the group
	 *
	 * @return whether the child is selectable.
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private static class CategoryViewHolder {
		public CheckedTextView textCategory;
		public Button          buttonAddTask;
	}

	private static class TaskViewHolder {
		public TextView hasDueDate;
		public TextView textView;
		public Button   buttonEditTask;
		public Button   buttonRemoveTask;
	}
}
