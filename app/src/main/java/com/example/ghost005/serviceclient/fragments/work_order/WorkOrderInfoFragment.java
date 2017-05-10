package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Collections;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.types.GeographicLocation;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Location;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.TownDetail;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderInfoFragment extends Fragment implements View.OnClickListener
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";

	private ImageView ivMaps;
	private TableRow trOrderDescription;
	private TableRow trOrderTown;
	private TableRow trOrderScheduledStart;
	private TableRow trOrderScheduledEnd;
	private TextView tvOrderId;
	private TextView tvOrderName;
	private TextView tvOrderDescription;
	private TextView tvOrderType;
	private TextView tvOrderStatus;
	private TextView tvOrderTown;
	private TextView tvOrderScheduledStart;
	private TextView tvOrderScheduledEnd;

	private float latitude;
	private float longitude;
	private int gspDBID;

	public static WorkOrderInfoFragment newInstance(int gspDBID)
	{
		WorkOrderInfoFragment fragment = new WorkOrderInfoFragment();

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_info, container, false);

		ivMaps = (ImageView) view.findViewById(R.id.image_view_maps);

		trOrderDescription = (TableRow) view.findViewById(R.id.table_row_order_description);
		trOrderScheduledEnd = (TableRow) view.findViewById(R.id.table_row_order_scheduled_end);
		trOrderScheduledStart = (TableRow) view.findViewById(R.id.table_row_order_scheduled_start);
		trOrderTown = (TableRow) view.findViewById(R.id.table_row_order_town);

		tvOrderDescription = (TextView) view.findViewById(R.id.text_view_order_description);
		tvOrderId = (TextView) view.findViewById(R.id.text_view_order_id);
		tvOrderName = (TextView) view.findViewById(R.id.text_view_order_name);
		tvOrderTown = (TextView) view.findViewById(R.id.text_view_order_town);
		tvOrderType = (TextView) view.findViewById(R.id.text_view_order_type);
		tvOrderScheduledStart = (TextView) view.findViewById(R.id.text_view_order_scheduled_start);
		tvOrderScheduledEnd = (TextView) view.findViewById(R.id.text_view_order_scheduled_end);
		tvOrderStatus = (TextView) view.findViewById(R.id.text_view_order_status);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		ivMaps.setOnClickListener(this);

		try
		{
			loadData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void loadData() throws SQLException
	{

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = gsp.getWorkOrder();
		WorkReport workReport = gsp.getWorkReport();

		// Set name & id
		tvOrderId.setText(workOrder.getId());
		tvOrderName.setText(workOrder.getName());

		// Long description
		if (workOrder.getLongDescription() != null)
		{
			tvOrderDescription.setText(workOrder.getLongDescription());
		}
		else
		{
			trOrderDescription.setVisibility(View.GONE);
		}

		// Set scheduled start & end
		if (workOrder.getScheduledWorkStart() != null && workOrder.getScheduledWorkEnd() != null)
		{
			String workOrderStart = Utilities.importDate(workOrder.getScheduledWorkStart());
			tvOrderScheduledStart.setText(workOrderStart);
		}
		else
		{
			trOrderScheduledStart.setVisibility(View.GONE);
		}

		if (workOrder.getScheduledWorkEnd() != null)
		{
			String workOrderEnd = Utilities.importDate(workOrder.getScheduledWorkEnd());
			tvOrderScheduledEnd.setText(workOrderEnd);
		}
		else
		{
			trOrderScheduledEnd.setVisibility(View.GONE);
		}

		// Set work status
		WorkStatusLog workStatusLog = null;

		if (workReport != null)
		{
			WorkStatusLogs workStatusLogs = workReport.getStatuses();

			if (workStatusLogs != null && workStatusLogs.getWorkStatusLogs().size() > 0)
			{
				workStatusLog = Collections.max(workStatusLogs.getWorkStatusLogs(), new WorkStatusLogComparator());
			}
		}

		if (workStatusLog == null)
		{
			workStatusLog = workOrder.getStatus();
		}

		if (workStatusLog != null)
		{
			Pair<String, Integer> statusPair = GSPUtilities.getWorkStatus(getActivity(), workStatusLog.getStatus());

			if (statusPair != null)
			{
				tvOrderStatus.setText(statusPair.first);
			}
		}

		// Set activity type
		String activityType = GSPUtilities.getActivityTypeString(getActivity(), workOrder.getActivityType());
		tvOrderType.setText(activityType);

		// Set town & coordinates
		PowerPlant powerPlant = gsp.getPowerPlant();

		String town = null;
		boolean showButton = false;

		if (powerPlant.getAddress() != null)
		{
			Location address = powerPlant.getAddress();

			if (address.getStreetAddress() != null)
			{
				StreetAddress streetAddress = address.getStreetAddress();

				if (streetAddress.getTownDetail() != null)
				{
					TownDetail townDetail = streetAddress.getTownDetail();

					if (townDetail.getName() != null)
					{
						town = townDetail.getName();
					}
				}
			}

			if (address.getGeographicPosition() != null)
			{
				GeographicLocation location = address.getGeographicPosition();
				location = databaseAdapter.getGeographicLocation(location.getId());

				if (location.getLatitude() != null && location.getLongitude() != null)
				{
					longitude = location.getLongitude().floatValue();
					latitude = location.getLatitude().floatValue();

					showButton = true;
				}
			}
		}

		if (town != null)
		{
			tvOrderTown.setText(town);
		}
		else
		{
			trOrderTown.setVisibility(View.GONE);
		}

		if (!showButton)
		{
			ivMaps.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.image_view_maps:
			{
				Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");

				if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null)
				{
					startActivity(mapIntent);
				}
			}
		}
	}
}
