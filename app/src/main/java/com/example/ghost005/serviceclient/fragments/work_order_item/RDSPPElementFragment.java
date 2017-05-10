package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.AssignedElement;
import com.example.ghost005.serviceclient.model.types.RDSPPElement;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class RDSPPElementFragment extends Fragment
{
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";
	private static final String BUNDLE_WORK_ORDER_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";

	private Activity activity;
	private TableRow trElementParent;
	private TableRow trPOIDesignation;
	private TableRow trPOIName;
	private TableRow trPOIParent;
	private TableRow trLocationDesignation;
	private TableRow trLocationName;
	private TableRow trLocationParent;
	private TextView tvRDSPPDesignation;
	private TextView tvName;
	private TextView tvElementParent;
	private TextView tvPOIDesignation;
	private TextView tvPOIName;
	private TextView tvPOIParent;
	private TextView tvLocationDesignation;
	private TextView tvLocationName;
	private TextView tvLocationParent;

	public static RDSPPElementFragment newInstance(int workOrderItemDatabaseId, int workReportItemDatabaseId)
//			final nach static gelöscht, gucken ob es funktioniert
	{
		RDSPPElementFragment fragment = new RDSPPElementFragment();

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
		View view = inflater.inflate(R.layout.fragment_rdsppelement, container, false);

		trElementParent = (TableRow) view.findViewById(R.id.table_row_rdspp_element_element_parent);
		trPOIDesignation = (TableRow) view.findViewById(R.id.table_row_rdspp_element_poi_designation);
		trPOIName = (TableRow) view.findViewById(R.id.table_row_rdspp_element_poi_name);
		trPOIParent = (TableRow) view.findViewById(R.id.table_row_rdspp_element_poi_parent);
		trLocationDesignation = (TableRow) view.findViewById(R.id.table_row_rdspp_element_location_designation);
		trLocationName = (TableRow) view.findViewById(R.id.table_row_rdspp_element_location_name);
		trLocationParent = (TableRow) view.findViewById(R.id.table_row_rdspp_element_location_parent);
		tvRDSPPDesignation = (TextView) view.findViewById(R.id.text_view_rdspp_element_rdspp_designation);
		tvName = (TextView) view.findViewById(R.id.text_view_rdspp_element_description);
		tvElementParent = (TextView) view.findViewById(R.id.text_view_rdspp_element_element_parent);
		tvPOIDesignation = (TextView) view.findViewById(R.id.text_view_rdspp_element_poi_designation);
		tvPOIName = (TextView) view.findViewById(R.id.text_view_rdspp_element_poi_name);
		tvPOIParent = (TextView) view.findViewById(R.id.text_view_rdspp_element_poi_parent);
		tvLocationDesignation = (TextView) view.findViewById(R.id.text_view_rdspp_element_location_designation);
		tvLocationName = (TextView) view.findViewById(R.id.text_view_rdspp_element_location_name);
		tvLocationParent = (TextView) view.findViewById(R.id.text_view_rdspp_element_location_parent);

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

		this.activity = activity;
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
		int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) activity.getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDatabaseId);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDatabaseId);

		RDSPPElement rdsppElement;

		if (workReportItem != null)
		{
			AssignedElement assignedElement = workReportItem.getAssignedElement();
			rdsppElement = assignedElement.getRdsPPDesignation();
		}
		else
		{
			AssignedElement assignedElement = workOrderItem.getAssignedElement();
			rdsppElement = assignedElement.getRdsPPDesignation();
		}

		tvRDSPPDesignation.setText(rdsppElement.getRdsppElementDesignation());
		tvName.setText(rdsppElement.getElementName());

		if (rdsppElement.getElementParent() != null)
		{
			tvElementParent.setText(rdsppElement.getElementParent());
		}
		else
		{
			trElementParent.setVisibility(View.GONE);
		}

		if (rdsppElement.getpOIDesignation() != null)
		{
			tvPOIDesignation.setText(rdsppElement.getpOIDesignation());
		}
		else
		{
			trPOIDesignation.setVisibility(View.GONE);
		}

		if (rdsppElement.getpOIName() != null)
		{
			tvPOIName.setText(rdsppElement.getpOIName());
		}
		else
		{
			trPOIName.setVisibility(View.GONE);
		}

		if (rdsppElement.getpOIParent() != null)
		{
			tvPOIParent.setText(rdsppElement.getpOIParent());
		}
		else
		{
			trPOIParent.setVisibility(View.GONE);
		}

		if (rdsppElement.getLocationDesignation() != null)
		{
			tvLocationDesignation.setText(rdsppElement.getLocationDesignation());
		}
		else
		{
			trLocationDesignation.setVisibility(View.GONE);
		}

		if (rdsppElement.getLocationName() != null)
		{
			tvLocationName.setText(rdsppElement.getLocationName());
		}
		else
		{
			trLocationName.setVisibility(View.GONE);
		}

		if (rdsppElement.getLocationParent() != null)
		{
			tvLocationParent.setText(rdsppElement.getLocationParent());
		}
		else
		{
			trLocationParent.setVisibility(View.GONE);
		}
	}
}
