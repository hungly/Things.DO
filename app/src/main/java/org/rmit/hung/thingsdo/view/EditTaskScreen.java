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
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.rmit.hung.myapplication.R;
import org.rmit.hung.thingsdo.controller.EditTaskButtonListeners;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTaskScreen extends Activity {
	public EditText getTaskTittle() {
		return taskTittle;
	}

	public EditText getTaskNote() {
		return taskNote;
	}

	private EditText taskTittle;
	private EditText taskNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("Activity", "Add task screen started");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task_screen);

		final Button addTaskConfirm = (Button) findViewById(R.id.button_save_task);
		final Button setTaskPriority = (Button) findViewById(R.id.button_task_priority);
		final Button setDueDate = (Button) findViewById(R.id.button_task_due_date);
		final Button addCollaborators = (Button) findViewById(R.id.button_task_add_collaborator);

		final TextView textDueDate = (TextView) findViewById(R.id.text_due_date);

		taskTittle = (EditText) findViewById(R.id.text_task_tittle);
		taskNote = (EditText) findViewById(R.id.text_task_notes);

		final Intent values = getIntent();
		final Bundle taskBundle = values.getExtras();

		final int taskID = taskBundle.getInt("Task ID");

		if (taskID != -1) {
			taskTittle.setText(taskBundle.getString("Tittle"));
			taskNote.setText(taskBundle.getString("Notes"));
		}

		EditTaskButtonListeners buttonListeners = new EditTaskButtonListeners(EditTaskScreen.this, taskBundle);

		String dueDate = taskBundle.getString("Due Date");

		if (!dueDate.equals("None")) {
			dueDate= dueDate.substring(0, 10);
		}

		textDueDate.setText(dueDate);

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
			Log.v("Things.DO", "\"Setting\" selected, start settings screen");

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
	}
}
