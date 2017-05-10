package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 16.09.15.
 */
public class ShortEditTextDialogEvent
{
	private String text;

	public ShortEditTextDialogEvent(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}
}
