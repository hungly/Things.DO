/*
 * ConnectionDetector.java
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

package org.rmit.hung.thingsdo.misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Check for valid internet connection
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 * Reference:
 * - http://www.androidhive.info/2012/07/android-detect-internet-connection-status/
 */
public class ConnectionDetector {
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (NetworkInfo anInfo : info)
					if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
}
