package com.example.ghost005.serviceclient.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import com.example.ghost005.serviceclient.adapter.MeasurementsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Measurement;
import com.example.ghost005.serviceclient.model.types.Measurements;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementsFragment extends Fragment
		implements View.OnClickListener, MeasurementsAdapter.OnItemClickListener
{
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";
	private static final int REQUEST_UPDATE = 0x0014;

	private Activity activity;
	private RecyclerView rvMeasurements;
	private MeasurementsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	public static MeasurementsFragment newInstance(int workReportItemDatabaseId)
	{
		MeasurementsFragment fragment = new MeasurementsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID, workReportItemDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_measurements, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);

		rvMeasurements = (RecyclerView) view.findViewById(R.id.recycler_view_measurements);
		rvMeasurements.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity);
		rvMeasurements.setLayoutManager(layoutManager);
		adapter = new MeasurementsAdapter(new ArrayList<Measurement>());
		rvMeasurements.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fbtnAdd.setOnClickListener(this);
		adapter.setOnItemClickListener(this);

		try
		{
			loadMeasurements();
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

		this.activity = null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_UPDATE && resultCode == Activity.RESULT_OK)
		{
			adapter.setMeasurements(new ArrayList<Measurement>());
			adapter.notifyDataSetChanged();

			try
			{
				loadMeasurements();
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
				// TODO: start activity

				break;
			}
		}
	}

	@Override
	public void onItemClick(View view, int position)
	{
		Measurement measurement = adapter.getItem(position);

		// TODO: start activity
	}

	private void loadMeasurements() throws SQLException
	{
		int workReportItemDatabaseId = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) activity.getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDatabaseId);

		if (workReportItem.getMeasurements() != null)
		{
			Measurements measurements = workReportItem.getMeasurements();

			if (measurements.getMeasurements() != null)
			{
				List<Measurement> measurementList = new ArrayList<>(measurements.getMeasurements());

				adapter.setMeasurements(measurementList);
				adapter.notifyDataSetChanged();
			}
		}
	}
}
