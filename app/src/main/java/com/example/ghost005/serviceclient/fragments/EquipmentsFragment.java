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
import com.example.ghost005.serviceclient.activities.EquipmentActivity;
import com.example.ghost005.serviceclient.adapter.EquipmentsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Equipment;
import com.example.ghost005.serviceclient.model.types.Equipments;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentsFragment extends Fragment
		implements View.OnClickListener, EquipmentsAdapter.OnItemClickListener
{
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";
	private static final int REQUEST_UPDATE = 0x0011;

	private RecyclerView rvEquipments;
	private EquipmentsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int workReportItemDBID;

	public static EquipmentsFragment newInstance(int workReportItemDatabaseId)
	{
		EquipmentsFragment fragment = new EquipmentsFragment();

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
		View view = inflater.inflate(R.layout.fragment_equipments, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);

		rvEquipments = (RecyclerView) view.findViewById(R.id.recycler_view_equipments);
		rvEquipments.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvEquipments.setLayoutManager(layoutManager);
		adapter = new EquipmentsAdapter(getActivity(), new ArrayList<Equipment>());
		adapter.setOnItemClickListener(this);
		rvEquipments.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fbtnAdd.setOnClickListener(this);

		try
		{
			loadEquipments();
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
				adapter.setEquipments(new ArrayList<Equipment>());
				adapter.notifyDataSetChanged();
				loadEquipments();
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
					Equipments equipments = workReportItem.getEquipments();

					if (equipments == null)
					{
						equipments = new Equipments();
						ArrayList<Equipment> equipmentList = new ArrayList<>();
						equipments.setEquipments(equipmentList);
						workReportItem.setEquipments(equipments);
						databaseAdapter.createOrUpdateEquipments(equipments);
						databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
					}

					Intent intent = new Intent(getActivity(), EquipmentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt(EquipmentActivity.BUNDLE_EQUIPMENTS_DB_ID, equipments.getId());
					bundle.putInt(EquipmentActivity.BUNDLE_EQUIPMENT_DB_ID, -1);
					bundle.putBoolean(EquipmentActivity.BUNDLE_NEW_EQUIPMENT, true);
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
			Equipments equipments = workReportItem.getEquipments();

			Equipment equipment = adapter.getItem(position);

			Intent intent = new Intent(getActivity(), EquipmentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt(EquipmentActivity.BUNDLE_EQUIPMENTS_DB_ID, equipments.getId());
			bundle.putInt(EquipmentActivity.BUNDLE_EQUIPMENT_DB_ID, equipment.getId());
			bundle.putBoolean(EquipmentActivity.BUNDLE_NEW_EQUIPMENT, false);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_UPDATE);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void loadEquipments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		if (workReportItem.getEquipments() != null)
		{
			Equipments equipments = workReportItem.getEquipments();

			List<Equipment> equipmentList = new ArrayList<>(equipments.getEquipments());

			adapter.setEquipments(equipmentList);
			adapter.notifyDataSetChanged();
		}
	}
}
