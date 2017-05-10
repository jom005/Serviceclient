package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class SpinnerDialogEvent
{
	private String text;
	private int position;

	public SpinnerDialogEvent(String text, int position)
	{
		this.text = text;
		this.position = position;
	}

	public String getText()
	{
		return text;
	}

	public int getPosition()
	{
		return position;
	}
}
