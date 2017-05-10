package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class DatePickerDialogSetEvent
{
	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	public DatePickerDialogSetEvent(int year, int monthOfYear, int dayOfMonth)
	{
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
	}

	public int getYear()
	{
		return year;
	}

	public int getMonthOfYear()
	{
		return monthOfYear;
	}

	public int getDayOfMonth()
	{
		return dayOfMonth;
	}
}
