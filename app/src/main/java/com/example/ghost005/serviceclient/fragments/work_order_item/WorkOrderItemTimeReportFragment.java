package com.example.ghost005.serviceclient.fragments.work_order_item;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.fragments.base.TimeReportFragment;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.model.types.TimeReports;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * Created by me on 03.10.15.
 */
public class WorkOrderItemTimeReportFragment extends TimeReportFragment
{
	private static final String BUNDLE_WORK_REPORT_DB_ID = "bundle_work_report_db_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";

	private int workReportDBID;
	private int workReportItemDBID;

	public static WorkOrderItemTimeReportFragment newInstance(int workReportDBId, int workReportItemDBId, int timeReportDBID, boolean newReport)
	{
		WorkOrderItemTimeReportFragment fragment = new WorkOrderItemTimeReportFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_REPORT_DB_ID, workReportDBId);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBId);
		args.putInt(BUNDLE_TIME_REPORT_DB_ID, timeReportDBID);
		args.putBoolean(BUNDLE_NEW, newReport);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		newReport = getArguments().getBoolean(BUNDLE_NEW);
		workReportDBID = getArguments().getInt(BUNDLE_WORK_REPORT_DB_ID);
		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
		timeReportDBID = getArguments().getInt(BUNDLE_TIME_REPORT_DB_ID);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_delete:
			{
				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
					WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
					TimeReport timeReport = databaseAdapter.getTimeReport(timeReportDBID);
					databaseAdapter.deleteTimeReport(timeReport);

					if (workReportItem.getTimeReport().getTimeReports().size() == 0)
					{
						TimeReports timeReports = workReportItem.getTimeReport();
						databaseAdapter.deleteTimeReports(timeReports);
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();

				return true;
			}
			default:
				return false;
		}
	}

	@Override
	public void save() throws SQLException
	{
		TimeReport timeReport = generateTimeReport();

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		if (newReport)
		{
			WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

			if (workReportItem.getTimeReport() != null)
			{
				TimeReports timeReports = workReportItem.getTimeReport();
				timeReport.setTimeReports(timeReports);
				databaseAdapter.createOrUpdateTimeReport(timeReport);
			}
			else
			{
				TimeReports timeReports = new TimeReports();
				ArrayList<TimeReport> timeReportList = new ArrayList<>();

				timeReport.setTimeReports(timeReports);
				timeReportList.add(timeReport);
				timeReports.setTimeReports(timeReportList);
				workReportItem.setTimeReport(timeReports);

				databaseAdapter.createEmployee(timeReport.getEmployee());
				databaseAdapter.createOrUpdateTimeReport(timeReport);
				databaseAdapter.createOrUpdateTimeReports(timeReports);
				databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
			}
		}
		else
		{
			databaseAdapter.createOrUpdateTimeReport(timeReport);
		}
	}

	@Override
	public ArrayList<Employee> loadEmployees() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReport workReport = databaseAdapter.getWorkReport(workReportDBID);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		ArrayList<Employee> employees = new ArrayList<>();

		if (workReport.getResponsibleEmployee() != null)
		{
			employees.add(workReport.getResponsibleEmployee());
		}

		if (workReportItem.getStaff() != null)
		{
			employees.addAll(workReportItem.getStaff().getEmployees());
		}

		return employees;
	}
}
