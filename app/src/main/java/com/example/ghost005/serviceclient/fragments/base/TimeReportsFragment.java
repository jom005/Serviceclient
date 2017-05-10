package com.example.ghost005.serviceclient.fragments.base;


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
import com.example.ghost005.serviceclient.adapter.TimeReportsAdapter;
import com.example.ghost005.serviceclient.model.types.TimeReport;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TimeReportsFragment extends Fragment
		implements View.OnClickListener, TimeReportsAdapter.OnItemClickListener
{
	public static final int REQUEST_REFRESH = 0x10001;

	protected TimeReportsAdapter adapter;
	private RecyclerView rvHistory;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fabAdd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_time_reports, container, false);

		rvHistory = (RecyclerView) view.findViewById(R.id.recycler_view_time_reports);
		rvHistory.setHasFixedSize(true);

		fabAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fabAdd.setOnClickListener(this);

		layoutManager = new LinearLayoutManager(getActivity());
		rvHistory.setLayoutManager(layoutManager);
		adapter = new TimeReportsAdapter(getActivity(), new ArrayList<TimeReport>());
		adapter.setOnItemClickListener(this);
		rvHistory.setAdapter(adapter);

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
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_REFRESH && resultCode == Activity.RESULT_OK)
		{
			try
			{
				loadData();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public abstract void loadData() throws SQLException;
}
