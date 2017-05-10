package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class TimePickerDialogSetEvent
{
	private int hourOfDay;
	private int minute;

	public TimePickerDialogSetEvent(int hourOfDay, int minute)
	{
		this.hourOfDay = hourOfDay;
		this.minute = minute;
	}

	public int getHourOfDay()
	{
		return hourOfDay;
	}

	public int getMinute()
	{
		return minute;
	}
}
