package com.example.ghost005.serviceclient.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.TimePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogSetEvent;
import de.greenrobot.event.EventBus;

/**
 * DialogFragment for time picking.
 */
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.dialog_clear), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				EventBus.getDefault().post(new TimePickerDialogResetEvent());
			}
		});

		return dialog;
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		EventBus.getDefault().post(new TimePickerDialogSetEvent(hourOfDay, minute));
	}

	/**
	 * Interface which the fragment that started the dialog must implement to receive the time.
	 */
	public interface TimePickerDialogEventListener
	{
		void onEvent(TimePickerDialogSetEvent event);
		void onEvent(TimePickerDialogResetEvent event);
	}
}
