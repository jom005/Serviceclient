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
import com.example.ghost005.serviceclient.activities.MaterialActivity;
import com.example.ghost005.serviceclient.adapter.MaterialsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Material;
import com.example.ghost005.serviceclient.model.types.Materials;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.utilities.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialsFragment extends Fragment implements View.OnClickListener, MaterialsAdapter.OnItemClickListener
{
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";
	private static final int REQUEST_UPDATE = 0x0013;

	private RecyclerView rvMaterials;
	private MaterialsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int workReportItemDBID;

	public static MaterialsFragment newInstance(int workReportItemDBID)
	{
		MaterialsFragment fragment = new MaterialsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBID);
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
		View view = inflater.inflate(R.layout.fragment_materials, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);
		fbtnAdd.setOnClickListener(this);

		rvMaterials = (RecyclerView) view.findViewById(R.id.recycler_view_materials);
		rvMaterials.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvMaterials.setLayoutManager(layoutManager);
		adapter = new MaterialsAdapter(getActivity(), new ArrayList<Material>());
		adapter.setOnItemClickListener(this);
		rvMaterials.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			loadMaterials();
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
				adapter.setMaterials(new ArrayList<Material>());
				adapter.notifyDataSetChanged();
				loadMaterials();
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
					Materials materials = workReportItem.getMaterials();

					if (materials == null)
					{
						materials = new Materials();
						ArrayList<Material> materialList = new ArrayList<>();
						materials.setMaterials(materialList);
						workReportItem.setMaterials(materials);
						databaseAdapter.createOrUpdateMaterials(materials);
						databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
					}

					Intent intent = new Intent(getActivity(), MaterialActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt(MaterialActivity.BUNDLE_MATERIALS_DB_ID, materials.getId());
					bundle.putInt(MaterialActivity.BUNDLE_MATERIAL_DB_ID, -1);
					bundle.putBoolean(MaterialActivity.BUNDLE_NEW_MATERIAL, true);
					bundle.putString(Constants.BUNDLE_TYPE, Constants.TYPE_NEW);
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
			Materials materials = workReportItem.getMaterials();

			Material material = adapter.getItem(position);

			Intent intent = new Intent(getActivity(), MaterialActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt(MaterialActivity.BUNDLE_MATERIALS_DB_ID, materials.getId());
			bundle.putInt(MaterialActivity.BUNDLE_MATERIAL_DB_ID, material.getId());
			bundle.putBoolean(MaterialActivity.BUNDLE_NEW_MATERIAL, false);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_UPDATE);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void loadMaterials() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		if (workReportItem.getMaterials() != null)
		{
			Materials materials = workReportItem.getMaterials();

			List<Material> materialsList = new ArrayList<>(materials.getMaterials());

			adapter.setMaterials(materialsList);
			adapter.notifyDataSetChanged();
		}
	}
}
