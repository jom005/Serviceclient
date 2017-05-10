package com.example.ghost005.serviceclient.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.DatePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.DatePickerDialogSetEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 27.08.15.
 */
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.dialog_clear), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				EventBus.getDefault().post(new DatePickerDialogResetEvent());
			}
		});

		return dialog;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		EventBus.getDefault().post(new DatePickerDialogSetEvent(year, monthOfYear, dayOfMonth));
	}

	/**
	 * Interface which the fragment that started the dialog must implement to receive the date.
	 */
	public interface DatePickerDialogEventListener
	{
		void onEvent(DatePickerDialogSetEvent event);
		void onEvent(DatePickerDialogResetEvent event);
	}
}
