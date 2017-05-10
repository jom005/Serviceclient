package com.example.ghost005.serviceclient.fragments.work_order;

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
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * Fragment that extends StaffFragment to display the staff from in the work order.
 * Created by me on 30.09.15.
 */
public class WorkOrderStaffFragment extends StaffFragment
{
	public static WorkOrderStaffFragment newInstance(int id, int position)
	{
		WorkOrderStaffFragment fragment = new WorkOrderStaffFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, id);
		args.putInt(BUNDLE_POSITION, position);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		position = getArguments().getInt(BUNDLE_POSITION);
	}

	/**
	 * Loads responsible employee and staff from database. For Staff members which are present in
	 * the work report, the checkbox get checked.
	 *
	 * @throws SQLException
	 */
	public void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = gsp.getWorkOrder();
		WorkReport workReport = gsp.getWorkReport();
		Employee responsibleEmployee = workReport.getResponsibleEmployee();
		Employees workOrderStaff = workOrder.getStaff();
		Employees workReportStaff = workReport.getStaff();

		List<Employee> staff = new ArrayList<>();
		List<Boolean> isSelected = new ArrayList<>();

		if (workOrderStaff != null)
		{
			staff.addAll(workOrderStaff.getEmployees());

			if (workReportStaff != null)
			{
				for (Employee workOrderEmployee : staff)
				{
					Boolean selected = Boolean.FALSE;

					for (Employee workReportEmployee : workReportStaff.getEmployees())
					{
						if (workOrderEmployee.getId().equals(workReportEmployee.getId()))
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
				for (int i = 0; i < staff.size(); i++)
				{
					isSelected.add(Boolean.TRUE);
				}
			}
		}

		if (responsibleEmployee != null)
		{
			if (responsibleEmployee.getLastName() != null)
			{
				StringBuilder stringBuilder = new StringBuilder();

				if (responsibleEmployee.getFirstName() != null)
				{
					stringBuilder.append(responsibleEmployee.getFirstName() + " ");
				}

				stringBuilder.append(responsibleEmployee.getLastName());

				tvName.setText(stringBuilder.toString());
			}

			tvId.setText(responsibleEmployee.getId());
			llMissingSkills.setVisibility(View.GONE);
		}

		adapter.setEmployees(staff, isSelected);
		adapter.notifyDataSetChanged();
	}

	public boolean save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkReport workReport = gsp.getWorkReport();
		Employees staff = workReport.getStaff();

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
				workReport.setStaff(null);
				databaseAdapter.deleteEmployee(employee);
				databaseAdapter.deleteEmployees(staff);
				databaseAdapter.createOrUpdateWorkReport(workReport);
			}
		}

		Employees employees = new Employees();
		employees.setEmployees(employeesToAdd);
		workReport.setStaff(employees);

		for (Employee employeeToAdd : employeesToAdd)
		{
			Employee employee = GSPUtilities.copyEmployee(employeeToAdd);
			employee.setEmployees(employees);
			databaseAdapter.createEmployee(employee);
		}

		databaseAdapter.createOrUpdateEmployees(employees);
		databaseAdapter.createOrUpdateWorkReport(workReport);

		return true;
	}
}
