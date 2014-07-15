/*
 * OnSwipeListener.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       10/07/2014
 * Date last modified: 10/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.model.CategoryListItem;
import org.rmit.hung.thingsdo.model.Task;
import org.rmit.hung.thingsdo.view.MainScreen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Hung on 10/07/14.
 */
public class TaskItemListener implements View.OnTouchListener {
	private final Activity         activity;
	private final Intent           editTask;
	private final GestureDetector  gestureDetector;
	private final CategoryListItem categoryListItem;
	private final int              taskPosition;

	public TaskItemListener(Context context, Intent editTask, CategoryListItem categoryListItem, int taskPosition) {
		gestureDetector = new GestureDetector(context, new GestureListener());
		this.activity = (Activity) context;
		this.editTask = editTask;
		this.categoryListItem = categoryListItem;
		this.taskPosition = taskPosition;
	}

	/**
	 * Called when a touch event is dispatched to a view. This allows listeners to
	 * get a chance to respond before the target view.
	 *
	 * @param v
	 * 		The view the touch event has been dispatched to.
	 * @param event
	 * 		The MotionEvent object containing full information about
	 * 		the event.
	 *
	 * @return True if the listener has consumed the event, false otherwise.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final int SWIPE_THRESHOLD          = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX > 0) {
							onSwipeRight();
						} else {
							onSwipeLeft();
						}
					}
				} else {
					if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		public void onSwipeRight() {
			final DateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.date_format));
			final Task task = categoryListItem.getTask().get(taskPosition);

			Log.v("Things.DO", "Swipe right for task");

			if (task.getStatus().equals("needsAction")) {
				task.setStatus("completed");

				Calendar c = Calendar.getInstance();

				task.setCompletedDate(dateFormat.format(c.getTime()));

				((MainScreen) activity).updateTask(categoryListItem, task);
			}
		}

		public void onSwipeLeft() {
			final DateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.date_format));
			final Task task = categoryListItem.getTask().get(taskPosition);

			Log.v("Things.DO", "Swipe left for task");

			if (task.getStatus().equals("completed")) {
				task.setStatus("needsAction");

				Calendar c = Calendar.getInstance();

				task.setCompletedDate(dateFormat.format(c.getTime()));

				((MainScreen) activity).updateTask(categoryListItem, task);
			}
		}

		public void onSwipeTop() {
		}

		public void onSwipeBottom() {
		}
	}
}
