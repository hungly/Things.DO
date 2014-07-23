/*
 * CategoryManagerScreen.java
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

package org.rmit.hung.thingsdo.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.AddCategoryButtonListener;
import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.model.Category;
import org.rmit.hung.thingsdo.model.CategoryListAdapter;
import org.rmit.hung.thingsdo.model.Task;

import java.util.ArrayList;

public class CategoryManagerScreen extends Activity {
	private DatabaseHandler     db;
	private ArrayList<Category> categories;
	private CategoryListAdapter categoryListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Category manager screen created");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_manager_screen);

		db = new DatabaseHandler(CategoryManagerScreen.this);
		categories = db.getAllCategories();

		categoryListAdapter = new CategoryListAdapter(CategoryManagerScreen.this, categories);

		ListView categoryList = (ListView) findViewById(R.id.list_category);

		categoryList.setAdapter(categoryListAdapter);

		final Button buttonAddCategory = (Button) findViewById(R.id.button_category_manager_add_category);

		buttonAddCategory.setOnClickListener(new AddCategoryButtonListener(CategoryManagerScreen.this));
	}

	@Override
	protected void onStart() {
		Log.v("Activity", "Category manager screen started");

		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.v("Activity", "Category manager screen restarted");

		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "Category manager screen resumed");

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Category manager screen paused");

		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v("Activity", "Category manager screen stopped");

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v("Activity", "Category manager screen destroyed");

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
		getMenuInflater().inflate(R.menu.category_manager_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			Log.v("Things.DO", "\"Exit\" selected, end activity now");

			CategoryManagerScreen.this.finish();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addCategory(String categoryName) {
		Log.v("Things.DO", "Category: \"" + categoryName + "\"");

		Category c = new Category("0", categoryName);

		Log.v("Things.DO", "Add category to database");
		db.addCategory(c);

		Log.v("Things.DO", "Add category to list view");
		categories.add(c);

		Log.v("Things.DO", "Add finished, refresh list view");
		categoryListAdapter.notifyDataSetChanged();
	}

	public void removeCategory(ArrayList<Category> categories, int position) {
		Log.v("Things.DO", "Category: \"" + categories.get(position).getCategory() + "\"");

		Log.v("Things.DO", "Remove task belong to this category from database");
		ArrayList<Task> tasks = db.getTasksByCategory(categories.get(position), "");
		for (Task t : tasks) {
			db.deleteTask(t);
		}
		db.deleteCategory(categories.get(position));

		Log.v("Things.DO", "Remove category from list view");
		categories.remove(position);

		Log.v("Things.DO", "Remove finished, refresh list view");
		categoryListAdapter.notifyDataSetChanged();
	}
}
