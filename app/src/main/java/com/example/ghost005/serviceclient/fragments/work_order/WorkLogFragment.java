package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.WorkLogAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkLog;
import com.example.ghost005.serviceclient.model.types.WorkLogEntry;

/**
 * Fragment displays a RecyclerView with the work history.
 */
public class WorkLogFragment extends Fragment implements WorkLogAdapter.OnItemClickListener
{
	private static final String BUNDLE_GSP_DATABASE_ID = "bundle_gsp_database_id";

	private Activity activity;
	private RecyclerView rvHistory;
	private WorkLogAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	public static WorkLogFragment newInstance(int gspDatabaseId)
	{
		WorkLogFragment fragment = new WorkLogFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DATABASE_ID, gspDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_log, container, false);

		rvHistory = (RecyclerView) view.findViewById(R.id.recycler_view_history);
		rvHistory.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvHistory.setLayoutManager(layoutManager);
		adapter = new WorkLogAdapter(getActivity(), new ArrayList<WorkLogEntry>());
		adapter.setOnItemClickListener(this);
		rvHistory.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			WorkLog workLog = loadData();

			if (workLog != null)
			{
				adapter.setWorkLogEntries(new ArrayList<>(workLog.getWorkLogEntries()));
				adapter.notifyDataSetChanged();
			}
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

	@Override
	public void onItemClick(View view, int position)
	{
		// TODO: Handle item click (or remove listener if not necessary)
	}

	private WorkLog loadData() throws SQLException
	{
		int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) activity.getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDatabaseId);
		WorkLog workLog = globalServiceProtocol.getWorkOrder().getWorkLogHistory();

		if (workLog != null)
		{
			return workLog;
		}

		return null;
	}
}
