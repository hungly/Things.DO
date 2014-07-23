/*
 * ListProvider.java
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
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.database.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Hung on 22/07/14.
 * <p/>
 * Reference:
 * - http://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
	private ArrayList<String> listItemList = new ArrayList<String>();
	private Context           context      = null;
	private int appWidgetId;

	public ListProvider(Context context, Intent intent) {
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
		                                 AppWidgetManager.INVALID_APPWIDGET_ID);

		populateListItem();
	}

	private void populateListItem() {
		DatabaseHandler db = new DatabaseHandler(context);

		String today = (new SimpleDateFormat(context.getString(R.string.date_format_trim_time))).format(Calendar.getInstance().getTime());

		ArrayList<Task> taskArrayList = db.getTasksByDueDate(today, "=", "ASC");

		listItemList.clear();

		for (Task t : taskArrayList)
			listItemList.add(t.getTittle());
	}

	/**
	 * Called when your factory is first constructed. The same factory may be shared across
	 * multiple RemoteViewAdapters depending on the intent passed.
	 */
	@Override
	public void onCreate() {

	}

	/**
	 * Called when notifyDataSetChanged() is triggered on the remote adapter. This allows a
	 * RemoteViewsFactory to respond to data changes by updating any internal references.
	 * <p/>
	 * Note: expensive tasks can be safely performed synchronously within this method. In the
	 * interim, the old data will be displayed within the widget.
	 *
	 * @see android.appwidget.AppWidgetManager#notifyAppWidgetViewDataChanged(int[], int)
	 */
	@Override
	public void onDataSetChanged() {
		populateListItem();
	}

	/**
	 * Called when the last RemoteViewsAdapter that is associated with this factory is
	 * unbound.
	 */
	@Override
	public void onDestroy() {

	}

	/**
	 * See {@link android.widget.Adapter#getCount()}
	 *
	 * @return Count of items.
	 */
	@Override
	public int getCount() {
		return listItemList.size();
	}

	/**
	 * See {@link android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)}.
	 * <p/>
	 * Note: expensive tasks can be safely performed synchronously within this method, and a
	 * loading view will be displayed in the interim. See {@link #getLoadingView()}.
	 *
	 * @param position
	 * 		The position of the item within the Factory's data set of the item whose
	 * 		view we want.
	 *
	 * @return A RemoteViews object corresponding to the data at the specified position.
	 */
	@Override
	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);

		String task = listItemList.get(position);

		remoteViews.setTextViewText(android.R.id.text1, task);
		return remoteViews;
	}

	/**
	 * This allows for the use of a custom loading view which appears between the time that
	 * {@link #getViewAt(int)} is called and returns. If null is returned, a default loading
	 * view will be used.
	 *
	 * @return The RemoteViews representing the desired loading view.
	 */
	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	/**
	 * See {@link android.widget.Adapter#getViewTypeCount()}.
	 *
	 * @return The number of types of Views that will be returned by this factory.
	 */
	@Override
	public int getViewTypeCount() {
		return 0;
	}

	/**
	 * See {@link android.widget.Adapter#getItemId(int)}.
	 *
	 * @param position
	 * 		The position of the item within the data set whose row id we want.
	 *
	 * @return The id of the item at the specified position.
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * See {@link android.widget.Adapter#hasStableIds()}.
	 *
	 * @return True if the same id always refers to the same object.
	 */
	@Override
	public boolean hasStableIds() {
		return false;
	}
}
