package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 15.09.15.
 */
public class NextEvent
{
	private boolean next;

	public NextEvent(boolean next)
	{
		this.next = next;
	}

	public boolean isNext()
	{
		return next;
	}
}
