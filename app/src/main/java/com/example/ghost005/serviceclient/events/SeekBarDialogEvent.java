package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class SeekBarDialogEvent
{
	private int value;

	public SeekBarDialogEvent(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
