package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class LongEditTextDialogEvent
{
	private String text;

	public LongEditTextDialogEvent(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}
}
