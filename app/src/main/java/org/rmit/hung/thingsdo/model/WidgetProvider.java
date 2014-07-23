/*
 * WidgetReceiver.java
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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.rmit.hung.thingsdo.R;

/**
 * Created by Hung on 22/07/14.
 * <p/>
 * Reference:
 * - http://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
 */
public class WidgetProvider extends AppWidgetProvider {
	/**
	 * Called in response to the {@link android.appwidget.AppWidgetManager#ACTION_APPWIDGET_UPDATE} broadcast when
	 * this AppWidget provider is being asked to provide {@link android.widget.RemoteViews RemoteViews}
	 * for a set of AppWidgets.  Override this method to implement your own AppWidget functionality.
	 * <p/>
	 * {@more}
	 *
	 * @param context
	 * 		The {@link android.content.Context Context} in which this receiver is
	 * 		running.
	 * @param appWidgetManager
	 * 		A {@link android.appwidget.AppWidgetManager} object you can call {@link
	 * 		android.appwidget.AppWidgetManager#updateAppWidget} on.
	 * @param appWidgetIds
	 * 		The appWidgetIds for which an update is needed.  Note that this
	 * 		may be all of the AppWidget instances for this provider, or just
	 * 		a subset of them.
	 *
	 * @see android.appwidget.AppWidgetManager#ACTION_APPWIDGET_UPDATE
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int widgetID : appWidgetIds) {
			RemoteViews remoteViews = updateWidgetListView(context, widgetID);

			Intent intent = new Intent(context, WidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			remoteViews.setOnClickPendingIntent(R.id.widget_text, pendingIntent);

			appWidgetManager.updateAppWidget(widgetID, remoteViews);

			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
		//which layout to show on widget
		RemoteViews remoteViews = new RemoteViews(
				context.getPackageName(), R.layout.today_list_widget);

		//RemoteViews Service needed to provide adapter for ListView
		Intent svcIntent = new Intent(context, WidgetService.class);
		//passing app widget id to that RemoteViews Service
		svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		//setting a unique Uri to the intent
		//don't know its purpose to me right now
		svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
		//setting adapter to listview of the widget
		remoteViews.setRemoteAdapter(R.id.widget_list_view, svcIntent);

		return remoteViews;
	}
}
