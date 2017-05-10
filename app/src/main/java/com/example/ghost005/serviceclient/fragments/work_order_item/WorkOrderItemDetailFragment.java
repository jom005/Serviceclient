package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.sql.SQLException;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.fragments.CommentsFragment;
import com.example.ghost005.serviceclient.model.types.Comments;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderItemDetailFragment extends Fragment implements ViewPager.OnPageChangeListener
{
	private static final String BUNDLE_WORK_ORDER_ITEM_DATABASE_ID = "bundle_work_order_item_database_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";
	private static final String STATE_ADAPTER_POSITION = "state_adapter_position";

	private TabLayout tabLayout;
	private ViewPager pager;
	private DetailPagerAdapter pagerAdapter;

	private int selectedPosition;

	private int workOrderItemCommentsId;
	private int workReportItemCommentsId;

	public static WorkOrderItemDetailFragment newInstance(int workOrderItemDatabaseId, int workReportItemDatabaseId)
	{
		WorkOrderItemDetailFragment fragment = new WorkOrderItemDetailFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID, workOrderItemDatabaseId);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID, workReportItemDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_item_detail, container, false);

		Display display = ((WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE))
				.getDefaultDisplay();

		int rotation = display.getRotation();

		if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
		{
			tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
			pager = (ViewPager) view.findViewById(R.id.pager);
			pagerAdapter = new DetailPagerAdapter(getChildFragmentManager());
			pager.setAdapter(pagerAdapter);
			tabLayout.setupWithViewPager(pager);
			tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null)
		{
			selectedPosition = savedInstanceState.getInt(STATE_ADAPTER_POSITION);
		}
		else
		{
			selectedPosition = 0;

		}

		try
		{
			loadComments();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		Display display = ((WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE))
				.getDefaultDisplay();

		int rotation = display.getRotation();

		if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
		{
			int workOrderItemDatabaseId = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
			int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

			FragmentManager manager = getChildFragmentManager();
			WorkOrderItemInfoFragment infoFragment = WorkOrderItemInfoFragment.newInstance(workOrderItemDatabaseId, workReportItemDatabaseId);
			CommentsFragment commentsFragment = CommentsFragment.newInstance(workOrderItemCommentsId, workReportItemCommentsId);
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.content_frame_work_order_item_info, infoFragment);
			transaction.replace(R.id.content_frame_comments, commentsFragment);
			transaction.commit();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_ADAPTER_POSITION, selectedPosition);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	@Override
	public void onPageSelected(int position)
	{
		selectedPosition = position;
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	private class DetailPagerAdapter extends FragmentPagerAdapter
	{
		public static final int PAGE_COUNT = 2;

		public DetailPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int i)
		{
			switch (i)
			{
				case 0:
				{
					int workOrderItemDatabaseId = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
					int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

					return WorkOrderItemInfoFragment.newInstance(workOrderItemDatabaseId, workReportItemDatabaseId);
				}
				case 1:
				{
					return CommentsFragment.newInstance(workOrderItemCommentsId, workReportItemCommentsId);
				}
				default:
					return null;
			}
		}

		@Override
		public int getCount()
		{
			return PAGE_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position)
			{
				case 0:
					return getString(R.string.info);
				case 1:
					return getString(R.string.comments);
				default:
					return super.getPageTitle(position);
			}
		}
	}

	private void loadComments() throws SQLException
	{
		int workOrderItemDatabaseId = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
		int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDatabaseId);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDatabaseId);

		Comments workOrderItemComments = workOrderItem.getComments();
		Comments workReportItemComments = workReportItem.getComments();

		workOrderItemCommentsId = -1;
		workReportItemCommentsId = -1;

		if (workOrderItemComments != null)
		{
			workOrderItemCommentsId = workOrderItemComments.getId();
		}

		if (workReportItemComments != null)
		{
			workReportItemCommentsId = workReportItemComments.getId();
		}
		else
		{
			/*
			 * Create an empty comments object for work report so it can be passed to the
			 * CommentsFragment.
			 */
			Comments comments = new Comments();
			workReportItem.setComments(comments);
			databaseAdapter.createOrUpdateComments(comments);
			databaseAdapter.createOrUpdateWorkReportItem(workReportItem);

			workReportItemCommentsId = comments.getId();
		}
	}
}
