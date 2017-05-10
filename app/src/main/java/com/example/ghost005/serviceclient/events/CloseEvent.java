package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 08.10.15.
 */
public class CloseEvent
{
	private boolean close;

	public CloseEvent(boolean close)
	{
		this.close = close;
	}

	public boolean isClose()
	{
		return close;
	}
}
