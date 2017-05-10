package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 15.09.15.
 */
public class FinishEvent
{
	private boolean finish;

	public FinishEvent(boolean finish)
	{
		this.finish = finish;
	}

	public boolean isFinish()
	{
		return finish;
	}
}
