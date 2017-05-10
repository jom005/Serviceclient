package com.example.ghost005.serviceclient.fragments.work_order_item;

import android.os.Bundle;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.fragments.base.StaffFragment;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.Employees;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * Created by me on 30.09.15.
 */
public class WorkOrderItemStaffFragment extends StaffFragment
{
	private static final String BUNDLE_WORK_ORDER_ITEM_DB_ID = "bundle_work_order_item_db_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";

	private int workOrderItemDBID;
	private int workReportItemDBID;

	public static WorkOrderItemStaffFragment newInstance(int gspDBID, int workOrderItemDBID, int workReportItemDBID, int position)
	{
		WorkOrderItemStaffFragment fragment = new WorkOrderItemStaffFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DB_ID, workOrderItemDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBID);
		args.putInt(BUNDLE_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		workOrderItemDBID = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DB_ID);
		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
		position = getArguments().getInt(BUNDLE_POSITION);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvResponsibleEmployeeHeader.setVisibility(View.GONE);
		cvResponsibleEmployee.setVisibility(View.GONE);
	}

	@Override
	public void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		List<Employee> staff = new ArrayList<>();
		List<Employee> foundEmployees = new ArrayList<>();
		List<Boolean> isSelected = new ArrayList<>();

		Employees workReportStuff = gsp.getWorkReport().getStaff();
		Employees workOrderItemStaff = workOrderItem.getStaff();
		Employees workReportItemStaff = workReportItem.getStaff();

		if (workOrderItemStaff != null)
		{
			staff.addAll(workOrderItemStaff.getEmployees());

			for (Employee employee : staff)
			{
				for (Employee workReportEmployee : workReportStuff.getEmployees())
				{
					if (workReportEmployee.getId().equals(employee.getId()))
					{
						foundEmployees.add(workReportEmployee);
						break;
					}
				}
			}

			if (workReportItemStaff != null)
			{
				for (Employee workOrderItemEmployee : foundEmployees)
				{
					Boolean selected = Boolean.FALSE;

					for (Employee workReportItemEmployee : workReportItemStaff.getEmployees())
					{
						if (workOrderItemEmployee.getId().equals(workReportItemEmployee.getId()))
						{
							selected = Boolean.TRUE;

							break;
						}
					}

					isSelected.add(selected);
				}
			}
			else
			{
				for (int i = 0; i < foundEmployees.size(); i++)
				{
					isSelected.add(Boolean.TRUE);
				}
			}
		}

		adapter.setEmployees(foundEmployees, isSelected);
		adapter.setWorkOrderItem(workOrderItem);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
		Employees staff = workReportItem.getStaff();

		List<Boolean> isSelected = adapter.getIsChecked();
		List<Employee> employeeList = adapter.getEmployees();
		List<Employee> employeesToAdd = new ArrayList<>();

		for (int i = 0; i < isSelected.size(); i++)
		{
			if (isSelected.get(i))
			{
				employeesToAdd.add(employeeList.get(i));
			}
		}

		if (staff != null)
		{
			for (Employee employee : staff.getEmployees())
			{
				workReportItem.setStaff(null);
				databaseAdapter.deleteEmployee(employee);
				databaseAdapter.deleteEmployees(staff);
				databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
			}
		}

		Employees employees = new Employees();
		employees.setEmployees(employeesToAdd);
		workReportItem.setStaff(employees);

		for (Employee employeeToAdd : employeesToAdd)
		{
			Employee employee = GSPUtilities.copyEmployee(employeeToAdd);
			employee.setEmployees(employees);
			databaseAdapter.createEmployee(employee);
		}

		databaseAdapter.createOrUpdateEmployees(employees);
		databaseAdapter.createOrUpdateWorkReportItem(workReportItem);

		return true;
	}
}
