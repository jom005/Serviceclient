package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 15.09.15.
 */
public class SaveEvent
{
	private int position;

	public SaveEvent(int position)
	{
		this.position = position;
	}

	public int getPosition()
	{
		return position;
	}
}
