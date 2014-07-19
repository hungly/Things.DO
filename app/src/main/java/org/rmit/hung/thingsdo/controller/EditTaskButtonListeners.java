/*
 * EditTaskButtonListeners.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       10/07/2014
 * Date last modified: 10/07/2014
 */

package org.rmit.hung.thingsdo.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import org.rmit.hung.thingsdo.R;
import org.rmit.hung.thingsdo.view.EditTaskScreen;
import org.rmit.hung.thingsdo.view.MainScreen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hung on 10/07/14.
 */
public class EditTaskButtonListeners implements View.OnClickListener {
	EditTaskScreen editTaskScreen;
	Bundle         taskBundle;

	public EditTaskButtonListeners(EditTaskScreen editTaskScreen, Bundle taskBundle) {
		this.editTaskScreen = editTaskScreen;
		this.taskBundle = taskBundle;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		final DateFormat dateFormat = new SimpleDateFormat(editTaskScreen.getString(R.string.date_format));
		final DateFormat dateFormatInput = new SimpleDateFormat(editTaskScreen.getString(R.string.date_format_input));

		Calendar c;

		switch (v.getId()) {
			case (R.id.button_save_task):
				// confirm task information button
				c = Calendar.getInstance();

				final String textTaskTittle = editTaskScreen.getTaskTittle().getText().toString();
				final String textTaskNotes = editTaskScreen.getTaskNote().getText().toString();

				final String textTaskDueDate = taskBundle.getString("Due Date", "");
				final String textPriority = taskBundle.getString("Parent");

				Log.v("Things.DO", "Confirm add task \"" + textTaskTittle + "\"");
				Intent resultTask = new Intent(editTaskScreen, MainScreen.class);

				resultTask.putExtra("Task ID", taskBundle.getInt("Task ID"));
				resultTask.putExtra("Google ID", taskBundle.getString("Google ID"));
				resultTask.putExtra("Tittle", textTaskTittle);
				resultTask.putExtra("Update Date", dateFormat.format(c.getTime()));
				resultTask.putExtra("Parent", textPriority);
				resultTask.putExtra("Notes", textTaskNotes);
				resultTask.putExtra("Status", "needsAction");
				resultTask.putExtra("Due Date", textTaskDueDate);
				resultTask.putExtra("Completed Date", "");
				resultTask.putExtra("Category", taskBundle.getString("Category"));

				String collaborators = "";
//				String collaborators = taskBundle.getString("Collaborators");

//				Log.v("Test", "Current collaborator size: " + editTaskScreen.getCollaborators().size());

				for (int i = 0; i < editTaskScreen.getCollaborators().size(); i++) {
					collaborators += editTaskScreen.getCollaborators().get(i).exportForSQLite(i);
				}

				resultTask.putExtra("Collaborators", collaborators);

//				Log.v("Test", collaborators);

				if (taskBundle.getString("Old Category") != null) {
					resultTask.putExtra("Old Category", taskBundle.getString("Old Category"));
					resultTask.putExtra("Old Task Position", taskBundle.getInt("Old Task Position"));
				}

				editTaskScreen.setResult(Activity.RESULT_OK, resultTask);

				editTaskScreen.finish();
				break;

			case (R.id.button_task_due_date):
				// edit due date button
				final String oldDueDate = taskBundle.getString("Due Date");
				c = Calendar.getInstance();

				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);

				// if task is not a new task and has a due date
				if (!taskBundle.getString("Due Date", "None").equals("None")) {
					try {
						Date oldDate = dateFormat.parse(taskBundle.getString("Due Date"));

						Calendar oldCalendar = Calendar.getInstance();
						oldCalendar.setTime(oldDate);

						mYear = oldCalendar.get(Calendar.YEAR);
						mMonth = oldCalendar.get(Calendar.MONTH);
						mDay = oldCalendar.get(Calendar.DAY_OF_MONTH);

//						Log.v("Test", mYear + "/" + mMonth + "/" + mDay);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				DatePickerDialog datePickerDialog = new DatePickerDialog(editTaskScreen, new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Date pickedDate = Calendar.getInstance().getTime();

						try {
							pickedDate = dateFormatInput.parse(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth +
							                                   " 00:00:00.000");
						} catch (ParseException e) {
							e.printStackTrace();
						}

						Log.v("Things.DO", "Date chosen: " + dateFormat.format(pickedDate));
						taskBundle.putString("Due Date", dateFormat.format(pickedDate));

						String dueDate = taskBundle.getString("Due Date");

						if (!dueDate.equals("None")) {
							Date date = Calendar.getInstance().getTime();

							try {
								date = (new SimpleDateFormat(editTaskScreen.getString(R.string.date_format))).parse(dueDate);
							} catch (ParseException e) {
								e.printStackTrace();
							}

							dueDate = (new SimpleDateFormat(editTaskScreen.getString(R.string.date_format_display))).format(date);
						}

						editTaskScreen.getSetDueDate().setText(dueDate);
					}
				}, mYear, mMonth, mDay);

				datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						Log.v("Things.DO", "Date picker cancel");
						taskBundle.putString("Due Date", oldDueDate);

						editTaskScreen.getSetDueDate().setText(oldDueDate);
					}
				});

				datePickerDialog.setCancelable(true);
				datePickerDialog.setCanceledOnTouchOutside(true);
				datePickerDialog.setMessage(editTaskScreen.getString(R.string.text_date_picker_question));

				datePickerDialog.show();
				break;

			case (R.id.button_task_priority):
				// edit priority button
				// get dialog builder
				AlertDialog.Builder builder = new AlertDialog.Builder(editTaskScreen);

				// priority options
				final String[] choice = {"Urgent", "High", "Medium", "Low"};

				// try to get task priority
				final String oldPriority = taskBundle.getString("Parent", "Medium");

				int currentChoiceIndex = 0;

				// set default choice
				for (int i = 0; i < choice.length; i++) {
					if (choice[i].equals(oldPriority)) {
						currentChoiceIndex = i;
						break;
					}
				}

				// display dialog
				builder.setIconAttribute(android.R.attr.alertDialogIcon)
				       .setTitle(editTaskScreen.getString(R.string.text_priority_tittle))
				       .setSingleChoiceItems(choice, currentChoiceIndex, new DialogInterface.OnClickListener() {
					                             public void onClick(DialogInterface dialog, int whichButton) {
						                             Log.v("Things.DO", "Choice: " + choice[whichButton]);
						                             taskBundle.putString("Parent", choice[whichButton]);

						                             editTaskScreen.getSetTaskPriority().setText(choice[whichButton]);
					                             }
				                             }
				                            )
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					       /**
					        * This method will be invoked when a button in the dialog is clicked.
					        *
					        * @param dialog
					        * 		The dialog that received the click.
					        * @param which
					        * 		The button that was clicked (e.g.
					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
					        */
					       @Override
					       public void onClick(DialogInterface dialog, int which) {
					       }
				       })
				       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					       /**
					        * This method will be invoked when a button in the dialog is clicked.
					        *
					        * @param dialog
					        * 		The dialog that received the click.
					        * @param which
					        * 		The button that was clicked (e.g.
					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
					        */
					       @Override
					       public void onClick(DialogInterface dialog, int which) {
						       taskBundle.putString("Parent", oldPriority);

						       editTaskScreen.getSetTaskPriority().setText(oldPriority);
					       }
				       })
				       .create();

				builder.show();
				break;

			case (R.id.button_task_category):
				// edit category
				// get dialog builder
				builder = new AlertDialog.Builder(editTaskScreen);

				// category options
				final String[] categories = taskBundle.getStringArray("Category List");

				// try to get task priority
				final String oldCategory = taskBundle.getString("Category", "Personal");

				currentChoiceIndex = 0;

				// set default choice
				for (int i = 0; i < categories.length; i++) {
					if (categories[i].equals(oldCategory)) {
						currentChoiceIndex = i;
						break;
					}
				}

				// display dialog
				builder.setIconAttribute(android.R.attr.alertDialogIcon)
				       .setTitle(editTaskScreen.getString(R.string.text_priority_tittle))
				       .setSingleChoiceItems(categories, currentChoiceIndex, new DialogInterface.OnClickListener() {
					                             public void onClick(DialogInterface dialog, int whichButton) {
						                             Log.v("Things.DO", "Choice: " + categories[whichButton]);
						                             taskBundle.putString("Category", categories[whichButton]);

						                             editTaskScreen.getSetTaskCategory().setText(categories[whichButton]);
					                             }
				                             }
				                            )
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					       /**
					        * This method will be invoked when a button in the dialog is clicked.
					        *
					        * @param dialog
					        * 		The dialog that received the click.
					        * @param which
					        * 		The button that was clicked (e.g.
					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
					        */
					       @Override
					       public void onClick(DialogInterface dialog, int which) {
					       }
				       })
				       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					       /**
					        * This method will be invoked when a button in the dialog is clicked.
					        *
					        * @param dialog
					        * 		The dialog that received the click.
					        * @param which
					        * 		The button that was clicked (e.g.
					        * 		{@link android.content.DialogInterface#BUTTON1}) or the position
					        */
					       @Override
					       public void onClick(DialogInterface dialog, int which) {
						       taskBundle.putString("Category", oldCategory);

						       editTaskScreen.getSetTaskCategory().setText(oldCategory);
					       }
				       })
				       .create();

				builder.show();
				break;

			case (R.id.button_task_add_collaborator):
//				Log.v("Test", "Add collaborators clicked, show contact chooser");

				editTaskScreen.showContactPicker();

				break;
		}
	}
}
