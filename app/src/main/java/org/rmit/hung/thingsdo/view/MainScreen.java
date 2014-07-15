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
import android.widget.Button;
import android.widget.ExpandableListView;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.controller.AddCategoryButtonListener;
import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.model.Category;
import org.rmit.hung.thingsdo.model.CategoryListItem;
import org.rmit.hung.thingsdo.model.Task;
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
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Main screen created");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		Intent addTask = new Intent(MainScreen.this, EditTaskScreen.class);

		db = new DatabaseHandler(MainScreen.this);

		Log.v("Things.DO", "Reading database");

		// insert dummy data
		if (db.getCategoriesCount() == 0) {
			Log.v("Things.DO", "Category list is empty, adding default items");

			db.addCategory(new Category("Study"));
			db.addCategory(new Category("Personal"));
			db.addCategory(new Category("Work"));
		} else
			Log.v("Things.DO", "Loading category from database");

		Log.v("Database", "Total task: " + db.getTasksCount());

		tasks = new TaskListAdapter(MainScreen.this, addTask, categoryListItems);

		ExpandableListView taskList = (ExpandableListView) findViewById(R.id.list_category);

		taskList.setAdapter(tasks);
		taskList.setItemsCanFocus(true);

		final Button buttonNewCategory = (Button) findViewById(R.id.button_add_category);
		buttonNewCategory.setOnClickListener(new AddCategoryButtonListener(MainScreen.this));
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

		// get data
		ArrayList<Category> categories = db.getAllCategories();

		categoryListItems.clear();

		for (Category c : categories) {
			ArrayList<Task> tasks = db.getTasksByCategory(c);
			categoryListItems.add(new CategoryListItem(c.getCategory(), tasks));
		}

		tasks.notifyDataSetChanged();

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

		if (id == R.id.action_categories_manager) {
			Log.v("Things.DO", "\"Category Manager\" selected, start category manager screen");

			Intent categoryManager = new Intent(MainScreen.this, CategoryManagerScreen.class);

			startActivity(categoryManager);

			return true;
		}
		if (id == R.id.action_settings) {
			Log.v("Things.DO", "\"Settings\" selected, start settings screen");

			Intent menuScreen = new Intent(MainScreen.this, SettingsScreen.class);

			startActivity(menuScreen);

			return true;
		}
		if (id == R.id.action_sync) {
			Log.v("Things.DO", "\"Sync\" selected, start sync task process");

			return true;
		}
		if (id == R.id.action_log_out) {
			Log.v("Things.DO", "\"Log out\" selected, logging out");

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
			final int taskID = data.getIntExtra("Task ID", -1);
			final String googleID = data.getStringExtra("Google ID");
			final String textTaskTittle = data.getStringExtra("Tittle");
			final String textUpdateDate = data.getStringExtra("Update Date");
			final String textParent = data.getStringExtra("Parent");
			final String textTaskNotes = data.getStringExtra("Notes");
			final String textStatus = data.getStringExtra("Status");
			final String textDueDate = data.getStringExtra("Due Date");
			final String textCompletedDate = data.getStringExtra("Completed Date");
			final String textCategory = data.getStringExtra("Category");

			Task task = null;

			if (requestCode == 0) {
				Log.v("Things.DO", "Create new task");

				task = new Task(taskID, googleID, textTaskTittle, textUpdateDate, textParent, textTaskNotes, textStatus, textDueDate, textCompletedDate, textCategory);

				db.addTask(task);

				task = db.getTask(textTaskTittle);

				Log.v("Database", "Total task: " + db.getTasksCount());
			}

			if (requestCode == 1) {
				Log.v("Things.DO", "Edit a task");

				Log.v("Test", "Completed date: " + textCompletedDate);

				task = db.getTask(taskID);

				task.setGoogleID(googleID);
				task.setTittle(textTaskTittle);
				task.setUpdateDate(textUpdateDate);
				task.setParent(textParent);
				task.setNotes(textTaskNotes);
				task.setStatus(textStatus);
				task.setDueDate(textDueDate);
				task.setCompletedDate(textCompletedDate);
				task.setCategory(textCategory);

				db.updateTask(task);

				// remove old task item from list view
				for (CategoryListItem c : categoryListItems) {
					int position = data.getIntExtra("Old Task Position", -1);
					if (c.getCategory().equals(data.getStringExtra("Old Category")) && position != -1) {
						c.getTask().remove(position);
						break;
					}
				}
			}

			// add task to list view
			for (CategoryListItem c : categoryListItems) {
				if (c.getCategory().equals(textParent)) {
					c.getTask().add(task);
					break;
				}
			}
			tasks.notifyDataSetChanged();
		}
	}

	public void removeTask(CategoryListItem categoryListItem, int taskPosition) {
		Log.v("Things.DO", "Task: \"" + categoryListItem.getTask().get(taskPosition).getTittle() + "\"");
		Log.v("Things.DO", "Category: \"" + categoryListItem.getCategory() + "\"");

		Log.v("Things.DO", "Remove task from database");
		db.deleteTask(categoryListItem.getTask().get(taskPosition));

		Log.v("Things.DO", "Remove task from list view");
		categoryListItem.getTask().remove(taskPosition);

		Log.v("Things.DO", "Remove finished, refresh list view");
		tasks.notifyDataSetChanged();
	}

	public void addCategory(String categoryName) {
		Log.v("Things.DO", "Category: \"" + categoryName + "\"");

		Log.v("Things.DO", "Add category to database");
		db.addCategory(new Category(categoryName));

		Log.v("Things.DO", "Add category to database");
		categoryListItems.add(new CategoryListItem(categoryName, new ArrayList<Task>()));

		Log.v("Things.DO", "Add finished, refresh list view");
		tasks.notifyDataSetChanged();
	}

	public void updateTask(CategoryListItem categoryListItem, Task task) {
		Log.v("Things.DO", "Task: \"" + task.getTittle() + "\"");
		Log.v("Things.DO", "Category: \"" + categoryListItem.getCategory() + "\"");

		db.updateTask(task);
		tasks.notifyDataSetChanged();
	}
}
