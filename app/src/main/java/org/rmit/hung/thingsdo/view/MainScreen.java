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
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.model.CategoryAdapter;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Main screen created");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		final String[] categories = {"Personal", "Work", "Study", "Misc"};

		final CategoryAdapter categoryAdapter = new CategoryAdapter(MainScreen.this, categories);

		ListView list = (ListView) findViewById(R.id.list_category);

		list.setAdapter(categoryAdapter);
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
		getMenuInflater().inflate(R.menu.things_do_menu, menu);
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
}
