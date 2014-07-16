/*
 * SplashScreen.java
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
import android.os.Handler;
import android.util.Log;

import org.rmit.hung.thingsdo.R;

/**
 * Splash screen for "Things.DO" application.
 *
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 *          <p/>
 *          References:
 *          -   http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
 *          -   http://www.codeproject.com/Articles/113831/An-Advanced-Splash-Screen-for-Android-App
 *          -   http://idroidsoftwareinc.blogspot.com/2013/09/android-splash-screen-example-tutorial.html
 */
public class SplashScreen extends Activity {

	// set how long the splash screen will wait to start fade out
	// will be replaced by actual operation instead of timer
	private final int SPLASH_SCREEM_DELAY = 3 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Splash screen created");

		// create and display the splash screen
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// put code to perform desire tasks here replacing the following codes
		// begin replaceable codes
		// create a runnable object
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// make an intent to start main screen
				Intent startMainScreen = new Intent(SplashScreen.this, MainScreen.class);

				// start main screen
				startActivity(startMainScreen);

				// close splash screen
				SplashScreen.this.finish();

				// overridden transition animations with custom fade out animation
				SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out);
			}
		}, SPLASH_SCREEM_DELAY);
		// end replaceable codes
	}

	@Override
	protected void onStart() {
		Log.v("Activity", "Splash screen started");

		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.v("Activity", "Splash screen restarted");

		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "Splash screen resumed");

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Splash screen paused");

		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v("Activity", "Splash screen stopped");

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v("Activity", "Splash screen destroyed");

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
}
