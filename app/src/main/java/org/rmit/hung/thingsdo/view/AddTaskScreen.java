/*
 * AddTaskScreen.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       03/07/2014
 * Date last modified: 03/07/2014
 */

package org.rmit.hung.thingsdo.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.rmit.hung.myapplication.R;

public class AddTaskScreen extends Activity {
	private EditText taskTittle;
	private Button   addTaskConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Add task screen started");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task_screen);

		taskTittle = (EditText) findViewById(R.id.text_add_task_tittle);
		addTaskConfirm = (Button) findViewById(R.id.button_add_new_task);

		addTaskConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String textTaskTittle = taskTittle.getText().toString();

				Log.v("Things.DO", "Confirm add task \"" + textTaskTittle + "\"");
				Intent category = getIntent();
				Intent resultAddTask = new Intent(AddTaskScreen.this, MainScreen.class);

				resultAddTask.putExtra("Category", category.getStringExtra("Category"));
				resultAddTask.putExtra("Tittle", textTaskTittle);

				setResult(RESULT_OK, resultAddTask);

				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		Log.v("Activity", "Add task screen started");

		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.v("Activity", "Add task screen restarted");

		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "Add task screen resumed");

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Add task screen paused");

		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v("Activity", "Add task screen stopped");

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v("Activity", "Add task screen destroyed");

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
		getMenuInflater().inflate(R.menu.add_task_screen_menu, menu);
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
		if (id == R.id.action_clear) {
			Log.v("Things.DO", "\"Clear\" selected, clear all input text boxes");

			clearAllInput();
		}
		if (id == R.id.action_exit) {
			Log.v("Things.DO", "\"Exit\" selected, end application now");

			AddTaskScreen.this.finish();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void clearAllInput() {
	}
}
