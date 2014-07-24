/*
 * AddTaskScreen.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       03/07/2014
 * Date last modified: 03/07/2014
 */

package org.rmit.hung.thingsdo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.controller.EditTaskButtonListeners;
import org.rmit.hung.thingsdo.model.Collaborator;
import org.rmit.hung.thingsdo.model.CollaboratorListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditTaskScreen extends Activity {
	private EditText                taskTittle;
	private EditText                taskNote;
	private Button                  setTaskCategory;
	private Button                  setTaskPriority;
	private Button                  setDueDate;
	private ArrayList<Collaborator> collaborators;
	private CollaboratorListAdapter collaboratorListAdapter;

	public ArrayList<Collaborator> getCollaborators() {
		return collaborators;
	}

	public Button getSetTaskCategory() {
		return setTaskCategory;
	}

	public Button getSetTaskPriority() {
		return setTaskPriority;
	}

	public Button getSetDueDate() {
		return setDueDate;
	}

	public EditText getTaskTittle() {
		return taskTittle;
	}

	public EditText getTaskNote() {
		return taskNote;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Add task screen started");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task_screen);

		final Button addTaskConfirm = (Button) findViewById(R.id.button_save_task);
		setTaskCategory = (Button) findViewById(R.id.button_task_category);
		setTaskPriority = (Button) findViewById(R.id.button_task_priority);
		setDueDate = (Button) findViewById(R.id.button_task_due_date);
		final Button addCollaborators = (Button) findViewById(R.id.button_task_add_collaborator);
		final String collaboratorsString;

		collaborators = new ArrayList<Collaborator>();
		collaboratorListAdapter = new CollaboratorListAdapter(EditTaskScreen.this, collaborators);

		taskTittle = (EditText) findViewById(R.id.text_task_tittle);
		taskNote = (EditText) findViewById(R.id.text_task_notes);

		// get data parse to by main screen
		final Intent values = getIntent();
		final Bundle taskBundle = values.getExtras();

		final int taskID = taskBundle.getInt("Task ID");

		if (taskID != -1) {
			// if this is an old task (ie: edit a task)
			taskTittle.setText(taskBundle.getString("Tittle"));
			taskNote.setText(taskBundle.getString("Notes"));
			collaboratorsString = taskBundle.getString("Collaborators", "");
		} else
			// new task
			collaboratorsString = "";

		populateCollaboratorsList(collaboratorsString);

//		Log.v("Test", collaboratorsString);

		String dueDate = taskBundle.getString("Due Date");

		if (!dueDate.equals("None")) {
			Date date = Calendar.getInstance().getTime();

			try {
				date = (new SimpleDateFormat(getString(R.string.date_format))).parse(dueDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			dueDate = (new SimpleDateFormat(getString(R.string.date_format_display))).format(date);
		}

		EditTaskButtonListeners buttonListeners = new EditTaskButtonListeners(EditTaskScreen.this, taskBundle);

		setTaskCategory.setText(taskBundle.getString("Category", "Personal"));
		setTaskPriority.setText(taskBundle.getString("Parent", "Medium"));

		if (taskBundle.getString("Parent", "Medium").equals("Urgent")) {
			((Button) findViewById(R.id.button_task_priority)).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_priority_urgent), null, null);
		} else {
			((Button) findViewById(R.id.button_task_priority)).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_priority), null, null);
		}

		setDueDate.setText(dueDate);

		addTaskConfirm.setOnClickListener(buttonListeners);

//		addTaskConfirm.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				final Calendar c = Calendar.getInstance();
//				final DateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
//				final String textTaskTittle = taskTittle.getText().toString();
//				final String textTaskNotes = taskNote.getText().toString();
//
//				Log.v("Things.DO", "Confirm add task \"" + textTaskTittle + "\"");
//				Intent resultTask = new Intent(EditTaskScreen.this, MainScreen.class);
//
//				resultTask.putExtra("Task ID", taskBundle.getInt("Task ID"));
//				resultTask.putExtra("Google ID", taskBundle.getString("Google ID"));
//				resultTask.putExtra("Tittle", textTaskTittle);
//				resultTask.putExtra("Update Date", dateFormat.format(c.getTime()));
//				resultTask.putExtra("Parent", "Medium");
//				resultTask.putExtra("Notes", textTaskNotes);
//				resultTask.putExtra("Status", "needsAction");
//				resultTask.putExtra("Due Date", "");
//				resultTask.putExtra("Completed Date", "");
//				resultTask.putExtra("Category", taskBundle.getString("Category"));
//
//				if (taskBundle.getString("Old Category") != null) {
//					resultTask.putExtra("Old Category", taskBundle.getString("Old Category"));
//					resultTask.putExtra("Old Task Position", taskBundle.getInt("Old Task Position"));
//				}
//
//				setResult(RESULT_OK, resultTask);
//
//				finish();
//			}
//		});

		setTaskCategory.setOnClickListener(buttonListeners);
		setDueDate.setOnClickListener(buttonListeners);

//		setDueDate.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				final Calendar c = Calendar.getInstance();
//				int mYear = c.get(Calendar.YEAR);
//				int mMonth = c.get(Calendar.MONTH);
//				int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//				DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskScreen.this, new DatePickerDialog.OnDateSetListener
//						() {
//
//					@Override
//					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//						Date pickedDate = Calendar.getInstance().getTime();
//
//						try {
//							pickedDate = dateFormatInput.parse(year + "/" + monthOfYear + "/" + dayOfMonth +
//							                                   " 00:00:00.000");
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//
//						Log.v("Test", "Date chosen: " + dateFormat.format(pickedDate));
//					}
//				}, mYear, mMonth, mDay);
//
//				datePickerDialog.setTitle(getString(R.string.text_date_picker_tittle));
//				datePickerDialog.setMessage(getString(R.string.text_date_picker_question));
//
//				datePickerDialog.show();
//			}
//		});

		setTaskPriority.setOnClickListener(buttonListeners);

//		setTaskPriority.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskScreen.this);
//
//				final String[] choice = {"Urgent", "High", "Medium", "Low"};
//
//				builder.setIconAttribute(android.R.attr.alertDialogIcon)
//				       .setTitle(getString(R.string.text_priority_tittle))
//				       .setSingleChoiceItems(choice, 0, new DialogInterface.OnClickListener() {
//					                             public void onClick(DialogInterface dialog, int whichButton) {
//						                             Log.v("Test", "Choice: " + choice[whichButton]);
//					                             }
//				                             }
//				                            )
//				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//					       /**
//					        * This method will be invoked when a button in the dialog is clicked.
//					        *
//					        * @param dialog
//					        * 		The dialog that received the click.
//					        * @param which
//					        * 		The button that was clicked (e.g.
//					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
//					        */
//					       @Override
//					       public void onClick(DialogInterface dialog, int which) {
//
//					       }
//				       })
//				       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//					       /**
//					        * This method will be invoked when a button in the dialog is clicked.
//					        *
//					        * @param dialog
//					        * 		The dialog that received the click.
//					        * @param which
//					        * 		The button that was clicked (e.g.
//					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
//					        */
//					       @Override
//					       public void onClick(DialogInterface dialog, int which) {
//
//					       }
//				       })
//				       .create();
//
//				builder.show();
//			}
//		});

		addCollaborators.setOnClickListener(buttonListeners);
	}

	@Override
	protected void onStart() {
		Log.v("Activity", "Add task screen started");

		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.v("Activity", "Add task screen restarted");

		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "Add task screen resumed");

		final ListView collaboratorsList = (ListView) findViewById(R.id.list_collaborators);

		collaboratorsList.setAdapter(collaboratorListAdapter);
		collaboratorsList.setItemsCanFocus(true);

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "Add task screen paused");

		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v("Activity", "Add task screen stopped");

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v("Activity", "Add task screen destroyed");

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
		getMenuInflater().inflate(R.menu.edit_task_screen_menu, menu);
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
			Log.v("Things.DO", "\"Settings\" selected, start settings screen");

			Intent menuScreen = new Intent(EditTaskScreen.this, SettingsScreen.class);

			startActivity(menuScreen);

			return true;
		}

		if (id == R.id.action_clear) {
			Log.v("Things.DO", "\"Clear\" selected, clear all input text boxes");

			clearAllInput();
		}
		if (id == R.id.action_exit) {
			Log.v("Things.DO", "\"Exit\" selected, end application now");

			EditTaskScreen.this.finish();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void clearAllInput() {
		taskTittle.setText("");
		taskNote.setText("");
		collaborators.clear();
		collaboratorListAdapter.notifyDataSetChanged();
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
			if (requestCode == 2) {
				Uri result = data.getData();

				final String id = result.getLastPathSegment();

				Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

				String name = "N/A";
				String phoneID = "";

				Log.v("Test", "Number of phone number for this contact: " + cursor.getCount());

				if (cursor.moveToFirst()) {
					name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

					// try to query this contact default number
					do {
						phoneID = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
					}
					while (cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY)) == 0 && cursor.moveToNext());
				}

				cursor.close();

				Log.v("Things.DO", "Got a result: " + name + ", id: " + id + ", phone id: " + phoneID);

//				if (preferences.getBoolean("sms_send", true)) {
				if (phoneID.equals("")) {
					Log.v("Things.DO", "No phone number for this contact, gonna ask user");

					Cursor contactName = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, ContactsContract.Contacts._ID + "=?", new String[]{id}, null);

					if (contactName != null)
						contactName.moveToFirst();

					assert contactName != null;
					final String tempName = contactName.getString(contactName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					final String tempPhoneID = phoneID;

					contactName.close();

					AlertDialog.Builder dialog = new AlertDialog.Builder(EditTaskScreen.this);

					dialog.setTitle("No phone contact");
					dialog.setMessage("This contact does not have any phone number. Things.DO will not send message to this contact.\nDo you want to continues?");
					dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Collaborator collaborator = new Collaborator(id, tempName, tempPhoneID, "1", "0");

							collaborators.add(collaborator);

							collaboratorListAdapter.notifyDataSetChanged();
						}
					});
					dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					dialog.setCancelable(true);

					dialog.show();
				} else {
					Log.v("Things.DO", "Phone number found for this contact, gonna add to list");

					Collaborator collaborator = new Collaborator(id, name, phoneID, "1", "0");

					collaborators.add(collaborator);

					collaboratorListAdapter.notifyDataSetChanged();
				}
			}
//			}
		} else {
			Log.v("Things.DO", "Uhm!! Something just went wrong.");
		}
	}

	public void populateCollaboratorsList(String collaboratorsString) {
//		Log.v("Test", "Collaborators list received from data: " + collaboratorsString);

		collaborators.clear();
		// ony run if collaborators list is NOT empty
		if (!collaboratorsString.equals("")) {
			if (collaboratorsString.contains("|")) {
				String[] collaboratorsStringArray = collaboratorsString.split("\\|");

				for (String s : collaboratorsStringArray) {
					collaborators.add(getCollaboratorObject(s));
				}

//				Log.v("Test", "Number of collaborator: " + collaboratorsStringArray.length);
			} else {
				collaborators.add(getCollaboratorObject(collaboratorsString));

//				Log.v("Test", "Number of collaborator: 1");
			}
//			Log.v("Test", collaboratorsString);

			collaboratorListAdapter.notifyDataSetChanged();
		}
	}

	public Collaborator getCollaboratorObject(String collaboratorsString) {
		String[] collaboratorsInfo = collaboratorsString.split(",");

		Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + "=?", new String[]{collaboratorsInfo[2]}, null);

		String name = "";

		if (cursor.moveToFirst()) {
			name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}

		String phoneNo = collaboratorsInfo.length < 4 ? "" : collaboratorsInfo[3];

		return new Collaborator(collaboratorsInfo[2], name, phoneNo, collaboratorsInfo[0], collaboratorsInfo[1]);
	}

	public void showContactPicker() {
		Intent contactPicker = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(contactPicker, 2);
	}

	public void removeCollaborator(int position) {
		collaborators.remove(position);

		collaboratorListAdapter.notifyDataSetChanged();
	}
}
