package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 08.10.15.
 */
public class RefreshEvent
{
	public static final int REFRESH_ATTACHMENTS = 1;

	private int refresh;

	public RefreshEvent(int refresh)
	{
		this.refresh = refresh;
	}

	public int getRefresh()
	{
		return refresh;
	}
}
