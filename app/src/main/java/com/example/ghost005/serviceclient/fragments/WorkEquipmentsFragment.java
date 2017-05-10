package com.example.ghost005.serviceclient.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.activities.WorkEquipmentActivity;
import com.example.ghost005.serviceclient.adapter.WorkEquipmentsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.WorkEquipment;
import com.example.ghost005.serviceclient.model.types.WorkEquipments;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkEquipmentsFragment extends Fragment
		implements View.OnClickListener, WorkEquipmentsAdapter.OnItemClickListener
{
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";
	private static final int REQUEST_UPDATE = 0x0012;

	private RecyclerView rvWorkEquipments;
	private WorkEquipmentsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int workReportItemDBID;

	public static WorkEquipmentsFragment newInstance(int workReportItemDatabaseId)
	{
		WorkEquipmentsFragment fragment = new WorkEquipmentsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_equipments, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);

		rvWorkEquipments = (RecyclerView) view.findViewById(R.id.recycler_view_work_equipments);
		rvWorkEquipments.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvWorkEquipments.setLayoutManager(layoutManager);
		adapter = new WorkEquipmentsAdapter(getActivity(), new ArrayList<WorkEquipment>());
		adapter.setOnItemClickListener(this);
		rvWorkEquipments.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fbtnAdd.setOnClickListener(this);

		try
		{
			loadWorkEquipments();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_UPDATE && resultCode == Activity.RESULT_OK)
		{
			try
			{
				adapter.setWorkEquipments(new ArrayList<WorkEquipment>());
				adapter.notifyDataSetChanged();
				loadWorkEquipments();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
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
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
					WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
					WorkEquipments workEquipments = workReportItem.getWorkEquipments();

					if (workEquipments == null)
					{
						workEquipments = new WorkEquipments();
						ArrayList<WorkEquipment> workEquipmentList = new ArrayList<>();
						workEquipments.setWorkEquipments(workEquipmentList);
						workReportItem.setWorkEquipments(workEquipments);
						databaseAdapter.createOrUpdateWorkEquipments(workEquipments);
						databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
					}

					Intent intent = new Intent(getActivity(), WorkEquipmentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt(WorkEquipmentActivity.BUNDLE_WORK_EQUIPMENTS_DB_ID, workEquipments.getId());
					bundle.putInt(WorkEquipmentActivity.BUNDLE_WORK_EQUIPMENT_DB_ID, -1);
					bundle.putBoolean(WorkEquipmentActivity.BUNDLE_NEW_WORK_EQUIPMENT, true);
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUEST_UPDATE);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onItemClick(View view, int position)
	{
		try
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
			WorkEquipments workEquipments = workReportItem.getWorkEquipments();

			WorkEquipment workEquipment = adapter.getItem(position);

			Intent intent = new Intent(getActivity(), WorkEquipmentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt(WorkEquipmentActivity.BUNDLE_WORK_EQUIPMENTS_DB_ID, workEquipments.getId());
			bundle.putInt(WorkEquipmentActivity.BUNDLE_WORK_EQUIPMENT_DB_ID, workEquipment.getId());
			bundle.putBoolean(WorkEquipmentActivity.BUNDLE_NEW_WORK_EQUIPMENT, false);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_UPDATE);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void loadWorkEquipments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		if (workReportItem.getWorkEquipments() != null)
		{
			WorkEquipments workEquipments = workReportItem.getWorkEquipments();

			List<WorkEquipment> equipmentList = new ArrayList<>(workEquipments.getWorkEquipments());

			adapter.setWorkEquipments(equipmentList);
			adapter.notifyDataSetChanged();
		}
	}
}
