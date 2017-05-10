package com.example.ghost005.serviceclient.fragments.work_order;


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

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.activities.EnvironmentalConditionActivity;
import com.example.ghost005.serviceclient.adapter.EnvironmentalConditionsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.model.types.EnvironmentalCondition;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.wizard.WorkOrderWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * Fragment displays a RecyclerView with environmental conditions..
 */
public class EnvironmentalConditionsFragment extends Fragment
		implements View.OnClickListener, EnvironmentalConditionsAdapter.OnItemClickListener,
		WorkOrderWizardFragment.OnStatusSaveListener
{
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final int REQUEST_UPDATE_ENV_COND = 0x002;

	private RecyclerView rvEnvironmentalConditions;
	private EnvironmentalConditionsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int gspDBID;
	private int position;

	public static EnvironmentalConditionsFragment newInstance(int gspDBID, int position)
	{
		EnvironmentalConditionsFragment fragment = new EnvironmentalConditionsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
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

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_environmental_conditions, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);
		fbtnAdd.setOnClickListener(this);

		rvEnvironmentalConditions = (RecyclerView) view.findViewById(
				R.id.recycler_view_environmental_conditions);
		rvEnvironmentalConditions.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvEnvironmentalConditions.setLayoutManager(layoutManager);
		adapter = new EnvironmentalConditionsAdapter(getActivity(),
				new ArrayList<EnvironmentalCondition>(), new ArrayList<EnvironmentalCondition>());
		adapter.setOnItemClickListener(this);
		rvEnvironmentalConditions.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			loadEnvironmentalConditions();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_UPDATE_ENV_COND && resultCode == Activity.RESULT_OK)
		{
			try
			{
				loadEnvironmentalConditions();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.floating_button_add:
			{
				Intent intent = new Intent(getActivity(), EnvironmentalConditionActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(EnvironmentalConditionActivity.BUNDLE_GSP_DB_ID, gspDBID);
				bundle.putInt(EnvironmentalConditionActivity.BUNDLE_ENV_COND_DB_ID, -1);
				bundle.putBoolean(EnvironmentalConditionActivity.BUNDLE_NEW, true);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_UPDATE_ENV_COND);
			}
		}
	}

	@Override
	public void onItemClick(View view, int position)
	{
		EnvironmentalCondition envCond = adapter.getItem(position);

		Intent intent = new Intent(getActivity(), EnvironmentalConditionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(EnvironmentalConditionActivity.BUNDLE_GSP_DB_ID, gspDBID);
		bundle.putInt(EnvironmentalConditionActivity.BUNDLE_ENV_COND_DB_ID, envCond.get_Id());
		bundle.putBoolean(EnvironmentalConditionActivity.BUNDLE_NEW, false);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_UPDATE_ENV_COND);
	}

	private void loadEnvironmentalConditions() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = globalServiceProtocol.getWorkOrder();
		WorkReport workReport = globalServiceProtocol.getWorkReport();

		ArrayList<EnvironmentalCondition> environmentalConditionsHistory = new ArrayList<>();
		ArrayList<EnvironmentalCondition> environmentalConditionsNew = new ArrayList<>();

		if (workOrder.getEnvironmentalConditions() != null)
		{
			environmentalConditionsHistory = new ArrayList<>(workOrder.getEnvironmentalConditions().getEnvironmentalConditions());
		}

		if (workReport != null)
		{
			if (workReport.getEnvironmentalConditions() != null)
			{
				environmentalConditionsNew = new ArrayList<>(workReport.getEnvironmentalConditions().getEnvironmentalConditions());
			}
		}

		adapter.setEnvironmentalConditionsHistory(environmentalConditionsHistory);
		adapter.setEnvironmentalConditionsNew(environmentalConditionsNew);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean save()
	{
		return true;
	}

	public void onEvent(SaveEvent event)
	{
		if (event.getPosition() == position)
		{
			if (save())
			{
				EventBus.getDefault().post(new NextEvent(true));
			}
		}
	}
}
