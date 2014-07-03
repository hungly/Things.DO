/*
 * ThingsDO.java
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

package org.rmit.hung.thingsdo.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.model.CategoryListItem;
import org.rmit.hung.thingsdo.model.TaskListAdapter;

import java.util.ArrayList;

/**
 * Main "Things.DO" screen.
 *
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 *          <p/>
 *          References:
 *          Splash screen:
 *          -   http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
 *          -   http://www.codeproject.com/Articles/113831/An-Advanced-Splash-Screen-for-Android-App
 *          -   http://idroidsoftwareinc.blogspot.com/2013/09/android-splash-screen-example-tutorial.html
 */
public class MainScreen extends Activity {
	private final ArrayList<CategoryListItem> categoryListItems = new ArrayList<CategoryListItem>();
	private TaskListAdapter tasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Main screen created");
		Intent addTask = new Intent(MainScreen.this, AddTaskScreen.class);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		final ArrayList<String> personalTasks = new ArrayList<String>();
		personalTasks.add("Task 1");
		personalTasks.add("Task 2");
		personalTasks.add("Task 3");
		personalTasks.add("Task 4");
		personalTasks.add("Task 5");

		final ArrayList<String> workTasks = new ArrayList<String>();
		workTasks.add("Task 1");

		final ArrayList<String> studyTasks = new ArrayList<String>();
		studyTasks.add("Task 1");
		studyTasks.add("Task 2");
		studyTasks.add("Task 3");
		studyTasks.add("Task 4");
		studyTasks.add("Task 5");
		studyTasks.add("Task 6");
		studyTasks.add("Task 7");
		studyTasks.add("Task 8");
		studyTasks.add("Task 9");
		studyTasks.add("Task 10");

		categoryListItems.add(new CategoryListItem("Personal", personalTasks));
		categoryListItems.add(new CategoryListItem("Work", workTasks));
		categoryListItems.add(new CategoryListItem("Study", studyTasks));

		tasks = new TaskListAdapter(MainScreen.this, addTask, categoryListItems);

		ExpandableListView taskList = (ExpandableListView) findViewById(R.id.list_category);

		taskList.setAdapter(tasks);
		taskList.setItemsCanFocus(true);
	}

	@Override
	protected void onStart() {
		Log.v("Activity", "Main screen started");

		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.v("Activity", "Main screen restarted");

		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "Main screen resumed");

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Main screen paused");

		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v("Activity", "Main screen stopped");

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v("Activity", "Main screen destroyed");

		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.screenWidthDp > newConfig.screenHeightDp)
			Log.v("Device", "Orientation changed to landscape");

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT || newConfig.screenWidthDp < newConfig.screenHeightDp)
			Log.v("Device", "Orientation changed to portrait");

		if (newConfig.keyboard == Configuration.KEYBOARDHIDDEN_YES)
			Log.v("Device", "Virtual keyboard hidden");

		if (newConfig.keyboard == Configuration.KEYBOARDHIDDEN_NO)
			Log.v("Device", "Virtual keyboard showed");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.things_do_main_screen_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Log.v("Things.DO", "Menu item selected");

		if (id == R.id.action_settings) {
			Log.v("Things.DO", "\"Setting\" selected, start settings screen");

			return true;
		}
		if (id == R.id.action_exit) {
			Log.v("Things.DO", "\"Exit\" selected, end application now");

			MainScreen.this.finish();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional
	 * data from it.  The <var>resultCode</var> will be
	 * {@link #RESULT_CANCELED} if the activity explicitly returned that,
	 * didn't return any result, or crashed during its operation.
	 * <p/>
	 * <p>You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 *
	 * @param requestCode
	 * 		The integer request code originally supplied to
	 * 		startActivityForResult(), allowing you to identify who this
	 * 		result came from.
	 * @param resultCode
	 * 		The integer result code returned by the child activity
	 * 		through its setResult().
	 * @param data
	 * 		An Intent, which can return result data to the caller
	 * 		(various data can be attached to Intent "extras").
	 *
	 * @see #startActivityForResult
	 * @see #createPendingResult
	 * @see #setResult(int)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			final String textCategory = data.getStringExtra("Category");
			String textTaskTittle = data.getStringExtra("Tittle");

			for (CategoryListItem c : categoryListItems) {
				if (c.getCategory().equals(textCategory)) {
					c.getTask().add(textTaskTittle);
					break;
				}
			}

			tasks.notifyDataSetChanged();
		}
	}
}
