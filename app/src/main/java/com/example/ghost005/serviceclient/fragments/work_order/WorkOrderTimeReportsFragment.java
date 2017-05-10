package com.example.ghost005.serviceclient.fragments.work_order;

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

/**
 * Created by me on 03.10.15.
 */
public class WorkOrderTimeReportsFragment extends TimeReportsFragment
{
	public static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";

	private int gspDBID;

	public static WorkOrderTimeReportsFragment newInstance(int gspDBID)
	{
		WorkOrderTimeReportsFragment fragment = new WorkOrderTimeReportsFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
	}

	@Override
	public void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkReport workReport = globalServiceProtocol.getWorkReport();

		ArrayList<TimeReport> timeReports;

		if (workReport.getTimeReport() != null)
		{
			timeReports = new ArrayList<>(workReport.getTimeReport().getTimeReports());
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
		bundle.putInt(TimeReportActivity.BUNDLE_TYPE, TimeReportActivity.TYPE_WORK_REPORT);
		bundle.putInt(TimeReportActivity.BUNDLE_WORK_REPORT_DB_ID, workReport.get_id());
		bundle.putInt(TimeReportActivity.BUNDLE_TIME_REPORT_DB_ID, timeReportDBID);
		bundle.putBoolean(TimeReportActivity.BUNDLE_NEW, newTimeReport);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_REFRESH);
	}
}
