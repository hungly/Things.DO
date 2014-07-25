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
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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
public class SplashScreen extends Activity implements Animation.AnimationListener {
	private Animation dotAppear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Splash screen created");

		// create and display the splash screen
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		final TextView splashTextThings = (TextView) findViewById(R.id.text_view_splash_things);
		final TextView splashTextDot = (TextView) findViewById(R.id.text_view_splash_dot);
		final TextView splashTextDo = (TextView) findViewById(R.id.text_view_splash_do);

		final Animation thingsAppear = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_screen_things);
		final Animation doAppear = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_screen_do);
		dotAppear = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_screen_dot);

		dotAppear.setAnimationListener(SplashScreen.this);

		splashTextThings.startAnimation(thingsAppear);
		splashTextDot.startAnimation(dotAppear);
		splashTextDo.startAnimation(doAppear);
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

	/**
	 * <p>Notifies the start of the animation.</p>
	 *
	 * @param animation
	 * 		The started animation.
	 */
	@Override
	public void onAnimationStart(Animation animation) {

	}

	/**
	 * <p>Notifies the end of the animation. This callback is not invoked
	 * for animations with repeat count set to INFINITE.</p>
	 *
	 * @param animation
	 * 		The animation which reached its end.
	 */
	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == dotAppear) {
			Intent startMainScreen = new Intent(SplashScreen.this, MainScreen.class);

			// start main screen
			startActivity(startMainScreen);

			// close splash screen
			SplashScreen.this.finish();
		}
	}

	/**
	 * <p>Notifies the repetition of the animation.</p>
	 *
	 * @param animation
	 * 		The animation which was repeated.
	 */
	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}
