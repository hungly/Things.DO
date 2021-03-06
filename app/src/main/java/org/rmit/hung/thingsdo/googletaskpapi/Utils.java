/*
 * Utils.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       22/07/2014
 * Date last modified: 22/07/2014
 */

package org.rmit.hung.thingsdo.googletaskpapi;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 * <p/>
 * Reference:
 * - https://code.google.com/p/google-api-java-client/source/browse/?repo=samples#hg%2Ftasks-android-sample%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgoogle%2Fapi%2Fservices%2Fsamples%2Ftasks%2Fandroid
 */
public class Utils {

	/**
	 * Logs the given throwable and shows an error alert dialog with its message.
	 *
	 * @param activity
	 * 		activity
	 * @param tag
	 * 		log tag to use
	 * @param t
	 * 		throwable to log and show
	 */
	public static void logAndShow(Activity activity, String tag, Throwable t) {
		Log.e(tag, "Error", t);
		String message = t.getMessage();
		if (t instanceof GoogleJsonResponseException) {
			GoogleJsonError details = ((GoogleJsonResponseException) t).getDetails();
			if (details != null) {
				message = details.getMessage();
			}
		} else if (t.getCause() instanceof GoogleAuthException) {
			message = t.getCause().getMessage();
		}
		showError(activity, message);
	}

	/**
	 * Shows an error alert dialog with the given message.
	 *
	 * @param activity
	 * 		activity
	 * @param message
	 * 		message to show or {@code null} for none
	 */
	public static void showError(Activity activity, String message) {
		String errorMessage = getErrorMessage(activity, message);
		showErrorInternal(activity, errorMessage);
	}

	private static void showErrorInternal(final Activity activity, final String errorMessage) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
			}
		});
	}

	private static String getErrorMessage(Activity activity, String message) {
		Resources resources = activity.getResources();
		if (message == null) {
			return "Error";
		}
		return "Error";
	}

	/**
	 * Logs the given message and shows an error alert dialog with it.
	 *
	 * @param activity
	 * 		activity
	 * @param tag
	 * 		log tag to use
	 * @param message
	 * 		message to log and show or {@code null} for none
	 */
	public static void logAndShowError(Activity activity, String tag, String message) {
		String errorMessage = getErrorMessage(activity, message);
		Log.e(tag, errorMessage);
		showErrorInternal(activity, errorMessage);
	}
}
