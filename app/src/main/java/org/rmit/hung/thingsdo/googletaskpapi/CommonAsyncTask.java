/*
 * SyncTask.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       21/07/2014
 * Date last modified: 21/07/2014
 */

package org.rmit.hung.thingsdo.googletaskpapi;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.tasks.Tasks;

import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.view.MainScreen;

import java.io.IOException;

/**
 * Created by Hung on 21/07/14.
 * <p/>
 * Reference:
 * - https://code.google.com/p/google-api-java-client/source/browse/?repo=samples#hg%2Ftasks-android-sample%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgoogle%2Fapi%2Fservices%2Fsamples%2Ftasks%2Fandroid
 */
public abstract class CommonAsyncTask extends AsyncTask<Void, Void, Boolean> {
	final MainScreen      mainScreen;
	final Tasks           client;
	final DatabaseHandler db;

	public CommonAsyncTask(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
		this.client = mainScreen.getClient();
		db = new DatabaseHandler(mainScreen);
	}

	/**
	 * Override this method to perform a computation on a background thread. The
	 * specified parameters are the parameters passed to {@link #execute}
	 * by the caller of this task.
	 * <p/>
	 * This method can call {@link #publishProgress} to publish updates
	 * on the UI thread.
	 *
	 * @param params
	 * 		The parameters of the task.
	 *
	 * @return A result, defined by the subclass of this task.
	 *
	 * @see #onPreExecute()
	 * @see #onPostExecute
	 * @see #publishProgress
	 */
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			doInBackground();
			return true;
		} catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
			mainScreen.showGooglePlayServicesAvailabilityErrorDialog(availabilityException.getConnectionStatusCode());
		} catch (UserRecoverableAuthIOException userRecoverableException) {
			mainScreen.startActivityForResult(userRecoverableException.getIntent(), 4);
		} catch (IOException e) {
			Utils.logAndShow(mainScreen, "Things.DO", e);
		}
		return false;
	}

	abstract protected void doInBackground() throws IOException;
}
