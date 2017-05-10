package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.fragments.EquipmentsFragment;
import com.example.ghost005.serviceclient.fragments.MaterialsFragment;
import com.example.ghost005.serviceclient.fragments.MeasurementsFragment;
import com.example.ghost005.serviceclient.fragments.WorkEquipmentsFragment;
import com.example.ghost005.serviceclient.wizard.WorkOrderItemWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderItemWorkFragment extends Fragment
		implements WorkOrderItemWizardFragment.OnStatusSaveListener
{
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";

	private TabLayout tabLayout;
	private ViewPager viewPager;

	public static WorkOrderItemWorkFragment newInstance(int workReportItemDatabaseId, int position)
	{
		WorkOrderItemWorkFragment fragment = new WorkOrderItemWorkFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_POSITION, position);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID, workReportItemDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_item_work, container, false);

		tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
		viewPager = (ViewPager) view.findViewById(R.id.pager);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		viewPager.setAdapter(new WorkItemPagerAdapter(getChildFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
	}

	@Override
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public boolean save()
	{
		return true;
	}

	public class WorkItemPagerAdapter extends FragmentPagerAdapter
	{
		public static final int ITEM_COUNT = 4;

		public WorkItemPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

			switch (position)
			{
				case 0:
				{
					return EquipmentsFragment.newInstance(workReportItemDatabaseId);
				}
				case 1:
				{
					return WorkEquipmentsFragment.newInstance(workReportItemDatabaseId);
				}
				case 2:
				{
					return MaterialsFragment.newInstance(workReportItemDatabaseId);
				}
				case 3:
				{
					return MeasurementsFragment.newInstance(workReportItemDatabaseId);
				}
				default:
					return null;
			}
		}

		@Override
		public int getCount()
		{
			return ITEM_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position)
			{
				case 0:
					return getResources().getString(R.string.work_item_equipment);
				case 1:
					return getResources().getString(R.string.work_item_work_equipments);
				case 2:
					return getResources().getString(R.string.work_item_material);
				case 3:
					return getResources().getString(R.string.work_item_measurement);
				default:
					return super.getPageTitle(position);
			}
		}
	}

	public void onEvent(SaveEvent event)
	{
		int position = getArguments().getInt(BUNDLE_POSITION);

		if (event.getPosition() == position)
		{
			if (save())
			{
				EventBus.getDefault().post(new NextEvent(true));
			}
		}
	}
}
