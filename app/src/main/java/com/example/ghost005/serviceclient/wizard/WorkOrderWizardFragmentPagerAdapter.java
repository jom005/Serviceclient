package com.example.ghost005.serviceclient.wizard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Custom FragmentStatePagerAdapter for the wizard.
 */
public class WorkOrderWizardFragmentPagerAdapter extends FragmentStatePagerAdapter
{
	private List<Fragment> fragments;
	private List<String> titles;

	public WorkOrderWizardFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles)
	{
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position)
	{
		return fragments.get(position);
	}

	@Override
	public int getCount()
	{
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return titles.get(position);
	}
}
