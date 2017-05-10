package com.example.ghost005.serviceclient.fragments.work_order_item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.activities.TimeReportActivity;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.fragments.base.TimeReportsFragment;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * Created by me on 04.10.15.
 */
public class WorkOrderItemTimeReportsFragment extends TimeReportsFragment
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_db_id";

	private int gspDBID;
	private int workOrderItemDBID;

	public static WorkOrderItemTimeReportsFragment newInstance(int gspDBID, int workReportItemDBID)
	{
		WorkOrderItemTimeReportsFragment fragment = new WorkOrderItemTimeReportsFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		workOrderItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
	}

	@Override
	public void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workOrderItemDBID);

		ArrayList<TimeReport> timeReports;

		if (workReportItem.getTimeReport() != null)
		{
			timeReports = new ArrayList<>(workReportItem.getTimeReport().getTimeReports());
		}
		else
		{
			timeReports = new ArrayList<>();
		}

		adapter.setTimeReports(timeReports);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.floating_button_add:
			{
				try
				{
					startTimeReportActivity(-1, true);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}
	}

	@Override
	public void onItemClick(View view, int position)
	{
		TimeReport timeReport = adapter.getItem(position);

		try
		{
			startTimeReportActivity(timeReport.getId(), false);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void startTimeReportActivity(int timeReportDBID, boolean newTimeReport) throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkReport workReport = globalServiceProtocol.getWorkReport();

		Intent intent = new Intent(getActivity(), TimeReportActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(TimeReportActivity.BUNDLE_TYPE, TimeReportActivity.TYPE_WORK_REPORT_ITEM);
		bundle.putInt(TimeReportActivity.BUNDLE_WORK_REPORT_DB_ID, workReport.get_id());
		bundle.putInt(TimeReportActivity.BUNDLE_WORK_REPORT_ITEM_DB_ID, workOrderItemDBID);
		bundle.putInt(TimeReportActivity.BUNDLE_TIME_REPORT_DB_ID, timeReportDBID);
		bundle.putBoolean(TimeReportActivity.BUNDLE_NEW, newTimeReport);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_REFRESH);
	}
}
