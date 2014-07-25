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

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.AddCategoryButtonListener;
import org.rmit.hung.thingsdo.database.DatabaseHandler;
import org.rmit.hung.thingsdo.model.AsyncTasks;
import org.rmit.hung.thingsdo.model.Category;
import org.rmit.hung.thingsdo.model.CategoryListItem;
import org.rmit.hung.thingsdo.model.ConnectionDetector;
import org.rmit.hung.thingsdo.model.NotificationReceiver;
import org.rmit.hung.thingsdo.model.SMSReceiver;
import org.rmit.hung.thingsdo.model.Task;
import org.rmit.hung.thingsdo.model.TaskListAdapter;
import org.rmit.hung.thingsdo.model.WidgetProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Main "Things.DO" screen.
 *
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 *          <p/>
 *          References:
 *          Splash screen:
 *          - http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
 *          - http://www.codeproject.com/Articles/113831/An-Advanced-Splash-Screen-for-Android-App
 *          - http://idroidsoftwareinc.blogspot.com/2013/09/android-splash-screen-example-tutorial.html
 *          - http://gmariotti.blogspot.com.es/2013/03/snippet-google-picker-account.html
 */
public class MainScreen extends Activity {
	private final ArrayList<CategoryListItem> categoryListItems = new ArrayList<CategoryListItem>();
	private final HttpTransport               httpTransport     = AndroidHttp.newCompatibleTransport();
	private final JsonFactory                 jsonFactory       = GsonFactory.getDefaultInstance();
	private TaskListAdapter          tasks;
	private DatabaseHandler          db;
	private SharedPreferences        preferences;
	private SharedPreferences.Editor editor;
	private String                   groupBy;
	private String                   sortOrder;
	private PendingIntent            pendingIntent;
	private AlarmManager             manager;
	private Intent                   alarmIntent;
	private SMSReceiver              smsReceiver;
	private Tasks                    client;

	public ArrayList<CategoryListItem> getCategoryListItems() {
		return categoryListItems;
	}

	public DatabaseHandler getDatabase() {
		return db;
	}

	public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
		runOnUiThread(new Runnable() {
			public void run() {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(connectionStatusCode, MainScreen.this, 3);
				dialog.show();
			}
		});
	}

	public Tasks getClient() {
		return client;
	}

	public String getGroupBy() {
		return groupBy;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Main screen created");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		Intent addTask = new Intent(MainScreen.this, EditTaskScreen.class);

		preferences = getSharedPreferences("org.rmit.hung.thingsdo_preferences", Context.MODE_PRIVATE);

		// write notification time to preference
		editor = preferences.edit();
//		editor.putString("notifications_time","09:00");
//		editor.apply();

		db = new DatabaseHandler(MainScreen.this);

		Log.v("Things.DO", "Reading database");

		// insert default category or load from database
		if (db.getCategoriesCount() == 0) {
			Log.v("Things.DO", "Category list is empty, adding default items");

			db.addCategory(new Category("0", "Study"));
			db.addCategory(new Category("0", "Personal"));
			db.addCategory(new Category("0", "Work"));
		} else
			Log.v("Things.DO", "Loading category from database");

		Log.v("Database", "Total task: " + db.getTasksCount());

		tasks = new TaskListAdapter(MainScreen.this, addTask, categoryListItems);

		ExpandableListView taskList = (ExpandableListView) findViewById(R.id.list_category);

		taskList.setAdapter(tasks);
		taskList.setItemsCanFocus(true);

		final Button buttonNewCategory = (Button) findViewById(R.id.button_add_category);
		buttonNewCategory.setOnClickListener(new AddCategoryButtonListener(MainScreen.this));

		alarmIntent = new Intent(MainScreen.this, NotificationReceiver.class);
		manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		setNotification();

//		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainScreen.this,4,alarmIntent,0);
//
//		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000,pendingIntent);
//		Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
//
//		manager.cancel(pendingIntent);
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

		groupBy = preferences.getString("group_by", "N/A");
		sortOrder = preferences.getString("sort_order", "ASC");

		if (groupBy.equals("N/A")) {
			// no group by preference yet
			Log.v("Things.DO", "No preference available for group by option, set to default");

			editor = preferences.edit();

			editor.putString("group_by", "category");

			editor.apply();
		}

		// try to get preference again
		groupBy = preferences.getString("group_by", "N/A");

		if (groupBy.equals("category")) {
			Log.v("Things.DO", "Group task by category order " + sortOrder);
		}
		if (groupBy.equals("priority")) {
			Log.v("Things.DO", "Group task by priority order " + sortOrder);
		}
		if (groupBy.equals("due_date")) {
			Log.v("Things.DO", "Group task by due date order " + sortOrder);
		}

		registerReceiver();

		getTaskGroupBy();

		setNotification();

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Main screen paused");

		unregisterReceiver(smsReceiver);

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
		getMenuInflater().inflate(R.menu.things_do_main_screen_menu, menu);

		if (groupBy.equals("category") || groupBy == null || groupBy.equals("N/A"))
			(menu.findItem(R.id.action_group_by_category)).setChecked(true);
		if (groupBy.equals("due_date"))
			(menu.findItem(R.id.action_group_by_priority)).setChecked(true);
		if (groupBy.equals("priority"))
			(menu.findItem(R.id.action_group_by_priority)).setChecked(true);

		if (!sortOrder.equals("ASC")) {
			(menu.findItem(R.id.action_group_order)).setChecked(true);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Log.v("Things.DO", "Menu item selected");

		switch (id) {
			case R.id.action_categories_manager:
				Log.v("Things.DO", "\"Category Manager\" selected, start category manager screen");

				Intent categoryManager = new Intent(MainScreen.this, CategoryManagerScreen.class);

				startActivity(categoryManager);

				return true;
			case R.id.action_settings:
				Log.v("Things.DO", "\"Settings\" selected, start settings screen");

				Intent menuScreen = new Intent(MainScreen.this, SettingsScreen.class);

				startActivity(menuScreen);

				return true;
			case R.id.action_sync:
				Log.v("Things.DO", "\"Sync\" selected, start sync task process");

				syncTasks();

				return true;
			case R.id.action_log_out:
				Log.v("Things.DO", "\"Log out\" selected, logging out");

				editor.putString("google_account", "");

				editor.apply();

				return true;
			case R.id.action_exit:
				Log.v("Things.DO", "\"Exit\" selected, end application now");

				MainScreen.this.finish();

				return true;
			case R.id.action_group_order:
				Log.v("Things.DO", "\"Reverse order\" selected, reverse task list order");

				editor = preferences.edit();

				if (preferences.getString("sort_order", "ASC").equals("ASC")) {
					editor.putString("sort_order", "DESC");
					item.setChecked(true);
				} else {
					editor.putString("sort_order", "ASC");
					item.setChecked(false);
				}

				editor.apply();

				onResume();

				return true;
			case R.id.action_group_by_category:
				Log.v("Things.DO", "\"Group by Category\" selected, rebuild task list");

				editor = preferences.edit();

				editor.putString("group_by", "category");

				editor.apply();

				item.setChecked(true);

				findViewById(R.id.button_add_category).setVisibility(View.VISIBLE);

				onResume();

				return true;
			case R.id.action_group_by_due_date:
				Log.v("Things.DO", "\"Group by Due date\" selected, rebuild task list");

				editor = preferences.edit();

				editor.putString("group_by", "due_date");

				editor.apply();

				item.setChecked(true);

				findViewById(R.id.button_add_category).setVisibility(View.INVISIBLE);

				onResume();

				return true;
			case R.id.action_group_by_priority:
				Log.v("Things.DO", "\"Group by Priority\" selected, rebuild task list");

				editor = preferences.edit();

				editor.putString("group_by", "priority");

				editor.apply();

				item.setChecked(true);

				findViewById(R.id.button_add_category).setVisibility(View.INVISIBLE);

				onResume();

				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional
	 * data from it.  The <var>resultCode</var> will be
	 * {@link #RESULT_CANCELED} if the activity explicitly returned that,
	 * didn't return any result, or crashed during its operation.
	 * <p/>
	 * <p>You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 *
	 * @param requestCode
	 * 		The integer request code originally supplied to
	 * 		startActivityForResult(), allowing you to identify who this
	 * 		result came from.
	 * @param resultCode
	 * 		The integer result code returned by the child activity
	 * 		through its setResult().
	 * @param data
	 * 		An Intent, which can return result data to the caller
	 * 		(various data can be attached to Intent "extras").
	 *
	 * @see #startActivityForResult
	 * @see #createPendingResult
	 * @see #setResult(int)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			final int taskID = data.getIntExtra("Task ID", -1);
			final String googleID = data.getStringExtra("Google ID");
			final String textTaskTittle = data.getStringExtra("Tittle");
			final String textUpdateDate = data.getStringExtra("Update Date");
			final String textParent = data.getStringExtra("Parent");
			final String textTaskNotes = data.getStringExtra("Notes");
			final String textStatus = data.getStringExtra("Status");
			final String textDueDate = data.getStringExtra("Due Date");
			final String textCompletedDate = data.getStringExtra("Completed Date");
			final String textCategory = data.getStringExtra("Category");
			String textCollaborators = data.getStringExtra("Collaborators");

			// run only when preference option is enable
			if (requestCode != 2 && preferences.getBoolean("sms_send", false) && !textCollaborators.equals("") && !textTaskTittle.equals("")) {
				textCollaborators = sendSMS(textTaskTittle, textCategory, textParent, textDueDate, textCollaborators);
			}

			Task task = null;

			if (requestCode == 0 && !textTaskTittle.equals("")) {
				Log.v("Things.DO", "Create new task");

//				Log.v("Test", "Collaborators add: " + textCollaborators);

				task = new Task(taskID, googleID, textTaskTittle, textUpdateDate, textParent, textTaskNotes, textStatus, textDueDate, textCompletedDate, textCategory, textCollaborators);

				db.addTask(task);

				task = db.getTask(textTaskTittle);

				Log.v("Database", "Total task: " + db.getTasksCount());
			}

			if (requestCode == 1 && !textTaskTittle.equals("")) {
				Log.v("Things.DO", "Edit a task");

//				Log.v("Test", "Completed date: " + textCompletedDate);

				task = db.getTask(taskID);

				task.setGoogleID(googleID);
				task.setTittle(textTaskTittle);
				task.setUpdateDate(textUpdateDate);
				task.setParent(textParent);
				task.setNotes(textTaskNotes);
				task.setStatus(textStatus);
				task.setDueDate(textDueDate);
				task.setCompletedDate(textCompletedDate);
				task.setCategory(textCategory);
				task.setCollaborators(textCollaborators);

//				Log.v("Test", textCategory);
//				Log.v("Test", task.getCategory());

				db.updateTask(task);

				// remove old task item from list view
				for (CategoryListItem c : categoryListItems) {
					int position = data.getIntExtra("Old Task Position", -1);
					if (c.getCategory().equals(data.getStringExtra("Old Category")) && position != -1) {
						c.getTask().remove(position);
						break;
					}
				}
			}

			if (requestCode == 2) {
				String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				Toast.makeText(this, "Account Name = " + accountName, Toast.LENGTH_SHORT).show();

				editor.putString("google_account", accountName);

				editor.apply();

				syncTasks();
			}

			if (requestCode != 2) {
				// add task to list view
				for (CategoryListItem c : categoryListItems) {
					if (c.getCategory().equals(textCategory)) {
						c.getTask().add(task);
						break;
					}
				}
				tasks.notifyDataSetChanged();

				updateWidget();
			}

			tasks.notifyDataSetChanged();
//			getTaskGroupBy();
		}
	}

	public String sendSMS(String textTaskTittle, String textCategory, String textParent, String textDueDate,
	                      String collaboratorsList) {
		String newCollaboratorsList = "";

		Log.v("Things.DO", "Collaborators list received from edit task: " + collaboratorsList);

		SmsManager sms = SmsManager.getDefault();

		String textSMS = "\"Things.DO\" This person had added you to his/her task with tittle: \"" + textTaskTittle +
		                 "\" under category: \"" + textCategory + "\" at priority: \"" + textParent +
		                 (textDueDate.equals("None") ? "\" and has no due date" :
		                  "\" and due on: " + textDueDate.substring(0, 10)) + ".";

		Log.v("Test", "Text size: " + textSMS.length());

		// split to individual contact
		String[] contacts = collaboratorsList.split("\\|");

		for (String contact : contacts) {
//			Log.v("Test", contact);
			// split to sub contact's info
			String[] info = contact.split(",");

			String sent = "0";

			if (info.length == 4 && info[0].equals("1") && info[1].equals("0")) {

				Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,
				                                           new String[]{Phone._ID, Phone.CONTACT_ID, Phone.NUMBER},
				                                           Phone.CONTACT_ID + "=? AND " + Phone._ID + "=?",
				                                           new String[]{info[2], info[3]}, null);

				if (cursor.moveToFirst()) {
					String phone = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));

					try {
//						Log.v("Test", textSMS);
						sms.sendTextMessage(phone, null, textSMS, null, null);
						Toast.makeText(getApplicationContext(), "Your sms has successfully sent!",
						               Toast.LENGTH_LONG).show();
						sent = "1";
					} catch (Exception ex) {
						Toast.makeText(getApplicationContext(), "Your sms has failed...", Toast.LENGTH_LONG).show();
						ex.printStackTrace();
					}
				}
			}

			// rebuild collaborators list with new data
			newCollaboratorsList += newCollaboratorsList.equals("") ? "" : "|";
			newCollaboratorsList += info[0] + ",";
			newCollaboratorsList += sent + ",";
			newCollaboratorsList += info[2] + "," + (info.length == 4 ? info[3] : "");
		}

		return newCollaboratorsList;
	}

	public void updateWidget() {
		Intent intent = new Intent(MainScreen.this, WidgetProvider.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));

		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		sendBroadcast(intent);
	}

	public void syncTasks() {
		String account = preferences.getString("google_account", "None");

		if (account.equals("None") || account.equals("")) {
			displayAccountPicker();
		} else {
			Log.v("Things.DO", "Now sync task using account: " + account);

			GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(MainScreen.this, Collections.singleton(TasksScopes.TASKS));
			credential.setSelectedAccountName(preferences.getString("google_account", null));

			client = new Tasks.Builder(httpTransport, jsonFactory, credential).setApplicationName("Things.DO").build();

			if (ConnectionDetector.isConnectingToInternet(MainScreen.this)) {
				// sync tasks with server
				AsyncTasks.run(MainScreen.this);
			} else {
				Toast.makeText(MainScreen.this, "No connection to the internet", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void displayAccountPicker() {
		Intent accountPicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);

		startActivityForResult(accountPicker, 2);
	}

	protected void getTaskGroupBy() {
		// get data
		categoryListItems.clear();

		if (groupBy.equals("category")) {
			// group by category
			ArrayList<Category> categories = db.getAllCategories();
			for (Category c : categories) {
				ArrayList<Task> tasks = db.getTasksByCategory(c, sortOrder);
				categoryListItems.add(new CategoryListItem(c.getCategory(), tasks));
			}
		}
		if (groupBy.equals("priority")) {
			// group by priority
			String[] priorities = {"Urgent", "High", "Medium", "Low"};
			for (String p : priorities) {
				ArrayList<Task> tasks = db.getTasksByPriority(p, sortOrder);
				categoryListItems.add(new CategoryListItem(p, tasks));
			}
		}
		if (groupBy.equals("due_date")) {
			// group by due date

			SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format_trim_time));

			Calendar c = Calendar.getInstance();

			Date date = c.getTime();
			String currentDate = dateFormat.format(date);

			Date tomorrow = new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
			String tomorrowDate = dateFormat.format(tomorrow);

			String[] dates = {"Past", "Today", "Tomorrow", "Future", "Someday"};

			for (String d : dates) {
				if (d.equals("Past")) {
					ArrayList<Task> tasks = db.getTasksByDueDate(currentDate, "<", sortOrder);
					categoryListItems.add(new CategoryListItem(d, tasks));
				}
				if (d.equals("Today")) {
					ArrayList<Task> tasks = db.getTasksByDueDate(currentDate, "=", sortOrder);
					categoryListItems.add(new CategoryListItem(d, tasks));
				}
				if (d.equals("Tomorrow")) {
					ArrayList<Task> tasks = db.getTasksByDueDate(tomorrowDate, "=", sortOrder);
					categoryListItems.add(new CategoryListItem(d, tasks));
				}
				if (d.equals("Future")) {
					ArrayList<Task> tasks = db.getTasksByDueDate(tomorrowDate, ">", sortOrder);
					categoryListItems.add(new CategoryListItem(d, tasks));
				}
				if (d.equals("Someday")) {
					ArrayList<Task> tasks = db.getTasksByDueDate("None", "=", sortOrder);
					categoryListItems.add(new CategoryListItem(d, tasks));
				}
			}
		}

		tasks.swapCategoryItemArrayList(categoryListItems);

		tasks.notifyDataSetChanged();
	}

	public void registerReceiver() {
		IntentFilter filter = new IntentFilter("SMS Read");
		filter.addCategory(Intent.CATEGORY_DEFAULT);

		smsReceiver = new SMSReceiver();

		registerReceiver(smsReceiver, filter);
	}

	protected void setNotification() {
		if (preferences.getBoolean("notifications_on_due", true)) {
			Log.v("Things.DO", "Notification on task due is on");

			pendingIntent = PendingIntent.getBroadcast(MainScreen.this, 50, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// broadcast every 1 minute
			manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent);
		} else {
			Log.v("Things.DO", "Notification on task due is off");

			pendingIntent = PendingIntent.getBroadcast(MainScreen.this, 50, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			manager.cancel(pendingIntent);
		}
	}

	public void removeTask(CategoryListItem categoryListItem, int taskPosition) {
		Log.v("Things.DO", "Task: \"" + categoryListItem.getTask().get(taskPosition).getTittle() + "\"");
		Log.v("Things.DO", "Category: \"" + categoryListItem.getCategory() + "\"");

		Log.v("Things.DO", "Remove task from database");
		db.deleteTask(categoryListItem.getTask().get(taskPosition));

		Log.v("Things.DO", "Remove task from list view");
		categoryListItem.getTask().remove(taskPosition);

		Log.v("Things.DO", "Remove finished, refresh list view");
		tasks.notifyDataSetChanged();

		updateWidget();
	}

	public void addCategory(String categoryName) {
		Log.v("Things.DO", "Category: \"" + categoryName + "\"");

		Log.v("Things.DO", "Add category to database");
		db.addCategory(new Category("0", categoryName));

		Log.v("Things.DO", "Add category to list view");
		categoryListItems.add(new CategoryListItem(categoryName, new ArrayList<Task>()));

		Log.v("Things.DO", "Add finished, refresh list view");
		tasks.notifyDataSetChanged();
	}

	public void updateTask(CategoryListItem categoryListItem, Task task) {
		Log.v("Things.DO", "Task: \"" + task.getTittle() + "\"");
		Log.v("Things.DO", "Category: \"" + categoryListItem.getCategory() + "\"");

		db.updateTask(task);
		tasks.notifyDataSetChanged();
	}

	public void refreshList(String category) {
		for (CategoryListItem categoryListItem : categoryListItems) {
			if (categoryListItem.getCategory().equals(category)) {
				ArrayList<Task> taskArrayList = (ArrayList<Task>) categoryListItem.getTask();

				taskArrayList.clear();

				taskArrayList = db.getTasksByCategory(new Category("0", category), "ASC");

				categoryListItem.setTask(taskArrayList);
				tasks.notifyDataSetChanged();
			}
		}
	}

	public String[] getCategoryList() {
		ArrayList<Category> categories = db.getAllCategories();

		String[] categoryList = new String[categories.size()];

		for (int i = 0; i < categories.size(); i++) {
			categoryList[i] = categories.get(i).getCategory();
		}

		return categoryList;
	}
}
