/*
 * WidgetService.java
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

package org.rmit.hung.thingsdo.model;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Hung on 22/07/14.
 *
 * Reference:
 * - http://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
 */
public class WidgetService extends RemoteViewsService {
	/**
	 * To be implemented by the derived service to generate appropriate factories for
	 * the data.
	 *
	 * @param intent
	 */
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		return (new ListProvider(this.getApplicationContext(), intent));
	}
}
