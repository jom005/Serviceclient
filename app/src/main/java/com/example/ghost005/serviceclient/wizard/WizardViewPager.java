package com.example.ghost005.serviceclient.wizard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Custom ViewPager which disables touch swipe.
 */
public class WizardViewPager extends ViewPager
{
	public WizardViewPager(Context context)
	{
		super(context);
	}

	public WizardViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		return false;
	}
}
