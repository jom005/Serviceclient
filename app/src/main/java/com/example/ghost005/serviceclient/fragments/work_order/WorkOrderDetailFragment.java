package com.example.ghost005.serviceclient.fragments.work_order;


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
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderDetailFragment extends Fragment implements ViewPager.OnPageChangeListener
{
	private static final String BUNDLE_GSP_DATABASE_ID = "bundle_gsp_database_id";
	private static final String STATE_ADAPTER_POSITION = "state_adapter_position";

	private TabLayout tabLayout;
	private ViewPager pager;
	private DetailPagerAdapter pagerAdapter;

	private int selectedPosition;

	private int workOrderCommentsId;
	private int workReportCommentsId;

	public static WorkOrderDetailFragment newInstance(int gspDatabaseId)
	{
		WorkOrderDetailFragment fragment = new WorkOrderDetailFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DATABASE_ID, gspDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_detail, container, false);

		Display display = ((WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE))
				.getDefaultDisplay();

		int rotation = display.getRotation();

		if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
		{
			tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
			pager = (ViewPager) view.findViewById(R.id.pager);
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
			int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

			FragmentManager manager = getChildFragmentManager();
			WorkOrderInfoFragment infoFragment = WorkOrderInfoFragment.newInstance(gspDatabaseId);
			CommentsFragment commentsFragment = CommentsFragment.newInstance(workOrderCommentsId, workReportCommentsId);
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.content_frame_work_order_info, infoFragment);
			transaction.replace(R.id.content_frame_comments, commentsFragment);
			transaction.commit();
		}
		else if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
		{
			pagerAdapter = new DetailPagerAdapter(getChildFragmentManager());
			pager.setAdapter(pagerAdapter);
			pager.addOnPageChangeListener(this);
			tabLayout.setupWithViewPager(pager);
			tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
			pager.setCurrentItem(selectedPosition);
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// TODO: Check orientation change before uncomment
		// If no comments were added, delete the empty comments from the database.
		/*int workReportDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_DATABASE_ID);

		try
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			WorkReport workReport = databaseAdapter.getWorkReport(workReportDatabaseId);

			if (workReport != null)
			{
				Comments comments = workReport.getComments();

				if (comments != null && comments.getComments().size() == 0)
				{
					databaseAdapter.deleteComments(comments);
					workReport.setComments(null);
					databaseAdapter.createOrUpdateWorkReport(workReport);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}*/
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
					int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

					return WorkOrderInfoFragment.newInstance(gspDatabaseId);
				}
				case 1:
				{
					return CommentsFragment.newInstance(workOrderCommentsId, workReportCommentsId);
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
		int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDatabaseId);
		WorkOrder workOrder = globalServiceProtocol.getWorkOrder();
		WorkReport workReport = globalServiceProtocol.getWorkReport();

		Comments workOrderComments = workOrder.getComments();
		Comments workReportComments = workReport.getComments();

		workOrderCommentsId = -1;
		workReportCommentsId = -1;

		if (workOrderComments != null)
		{
			workOrderCommentsId = workOrderComments.getId();
		}

		if (workReportComments != null)
		{
			workReportCommentsId = workReportComments.getId();
		}
		else
		{
			/*
			 * Create an empty comments object for work report so it can be passed to the
			 * CommentsFragment.
			 */
			Comments comments = new Comments();
			workReport.setComments(comments);
			databaseAdapter.createOrUpdateComments(comments);
			databaseAdapter.createOrUpdateWorkReport(workReport);

			workReportCommentsId = comments.getId();
		}
	}
}
