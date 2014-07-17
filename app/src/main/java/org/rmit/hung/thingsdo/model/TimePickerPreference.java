/*
 * TimePickerPreference.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       17/07/2014
 * Date last modified: 17/07/2014
 */

package org.rmit.hung.thingsdo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import org.rmit.hung.thingsdo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hung on 17/07/14.
 */
public class TimePickerPreference extends DialogPreference {
	private static final int     TIME_PICKER_INTERVAL = 15;
	private              boolean mIgnoreEvent         = false;
	private TimePicker timePicker;

	public TimePickerPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setPositiveButtonText(context.getText(R.string.preference_time_picker_positive_button));
	}

	public TimePickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Creates the content view for the dialog (if a custom content view is
	 * required). By default, it inflates the dialog layout resource if it is
	 * set.
	 *
	 * @return The content View for the dialog.
	 *
	 * @see #setLayoutResource(int)
	 */
	@Override
	protected View onCreateDialogView() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		String time = preferences.getString("notifications_time", "12:00");
		Date date = Calendar.getInstance().getTime();
		try {
			date = (new SimpleDateFormat("HH:mm")).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		timePicker = new TimePicker(getContext());

		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(date.getHours());
		timePicker.setCurrentMinute(date.getMinutes());

		return timePicker;
	}

	/**
	 * Called when the dialog is dismissed and should be used to save data to
	 * the {@link android.content.SharedPreferences}.
	 *
	 * @param positiveResult
	 * 		Whether the positive button was clicked (true), or
	 * 		the negative button was clicked or the dialog was canceled (false).
	 */
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// trigger save value on "OK" button click
		if (positiveResult) {
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

			Date date = new Date();
			date.setHours(timePicker.getCurrentHour());
			date.setMinutes(timePicker.getCurrentMinute());

			String pickTime = (new SimpleDateFormat("HH:mm")).format(date);

//			Log.v("Test", pickTime);

			editor.putString("notifications_time", pickTime);

			editor.apply();

			setSummary(pickTime);
		}

		super.onDialogClosed(positiveResult);
	}
}
