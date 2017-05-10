package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Collections;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.types.AssignedElement;
import com.example.ghost005.serviceclient.model.types.RDSPPElement;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderItemInfoFragment extends Fragment
{
	private static final String BUNDLE_WORK_ORDER_ITEM_DATABASE_ID = "bundle_work_order_item_database_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";

	private Activity activity;
	private TableRow trOrderItemDescription;
	private TableRow trOrderItemMaintenanceLevel;
	private TableRow trOrderITemSystem;
	private TextView tvOrderItemDescription;
	private TextView tvOrderItemElement;
	private TextView tvOrderItemId;
	private TextView tvOrderItemMaintenanceLevel;
	private TextView tvOrderItemName;
	private TextView tvOrderITemStatus;
	private TextView tvOrderITemSystem;

	public static WorkOrderItemInfoFragment newInstance(int workOrderItemDatabaseId, int workReportItemDatabaseId)
	{
		WorkOrderItemInfoFragment fragment = new WorkOrderItemInfoFragment();

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
		View view = inflater.inflate(R.layout.fragment_work_order_item_info, container, false);

		trOrderItemDescription = (TableRow) view.findViewById(R.id.table_row_order_item_long_description);
		trOrderItemMaintenanceLevel = (TableRow) view.findViewById(R.id.table_row_order_item_maintenance_level);
		trOrderITemSystem = (TableRow) view.findViewById(R.id.table_row_order_item_system);
		tvOrderItemDescription = (TextView) view.findViewById(R.id.text_view_order_item_long_description);
		tvOrderItemElement = (TextView) view.findViewById(R.id.text_view_order_item_element);
		tvOrderItemId = (TextView) view.findViewById(R.id.text_view_order_item_id);
		tvOrderItemMaintenanceLevel = (TextView) view.findViewById(R.id.text_view_order_item_maintenance_level);
		tvOrderItemName = (TextView) view.findViewById(R.id.text_view_order_item_name);
		tvOrderITemStatus = (TextView) view.findViewById(R.id.text_view_order_item_status);
		tvOrderITemSystem = (TextView) view.findViewById(R.id.text_view_order_item_system);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			loadData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		Activity a;

		if (context instanceof Activity){
			a=(Activity) context;
		}

	}

	@Override
	public void onDetach()
	{
		super.onDetach();

		activity = null;
	}

	private void loadData() throws SQLException
	{
		int workOrderItemDatabaseId = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
		//int workReportDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) activity.getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDatabaseId);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workOrderItemDatabaseId);

		// Set name & id
		tvOrderItemId.setText(workOrderItem.getId());
		tvOrderItemName.setText(workOrderItem.getName());

		// Set long description
		if (workOrderItem.getLongDescription() != null)
		{
			tvOrderItemDescription.setText(workOrderItem.getLongDescription());
		}
		else
		{
			trOrderItemDescription.setVisibility(View.GONE);
		}

		// Set Maintenance level
		if (workOrderItem.getMaintenanceLevel() != null)
		{
			String maintenanceLevel = GSPUtilities.getMaintenanceLevel(activity, workOrderItem.getMaintenanceLevel());

			if (maintenanceLevel != null)
			{
				tvOrderItemMaintenanceLevel.setText(maintenanceLevel);
			}
		}
		else
		{
			trOrderItemMaintenanceLevel.setVisibility(View.GONE);
		}

		// System & Element
		AssignedElement assignedElement = workOrderItem.getAssignedElement();
		RDSPPElement rdsppElement = assignedElement.getRdsPPDesignation();

		tvOrderItemElement.setText(rdsppElement.getElementName());

		if (rdsppElement.getElementParent() != null)
		{
			tvOrderITemSystem.setText(rdsppElement.getElementParent());
		}
		else
		{
			trOrderITemSystem.setVisibility(View.GONE);
		}

		//Set work status
		WorkStatusLog workStatusLog = null;

		if (workReportItem != null)
		{
			WorkStatusLogs workStatusLogs = workReportItem.getStatuses();

			if (workStatusLogs != null && workStatusLogs.getWorkStatusLogs().size() > 0)
			{
				workStatusLog = Collections.max(workStatusLogs.getWorkStatusLogs(), new WorkStatusLogComparator());
			}
		}

		if (workStatusLog == null)
		{
			WorkStatusLogs workStatusLogs = workOrderItem.getStatuses();

			if (workStatusLogs != null && workStatusLogs.getWorkStatusLogs().size() > 0)
			{
				workStatusLog = Collections.max(workStatusLogs.getWorkStatusLogs(), new WorkStatusLogComparator());
			}
		}

		if (workStatusLog != null)
		{
			Pair<String, Integer> statusPair = GSPUtilities.getWorkStatus(activity, workStatusLog.getStatus());

			if (statusPair != null)
			{
				tvOrderITemStatus.setText(statusPair.first);
			}
		}
	}
}
