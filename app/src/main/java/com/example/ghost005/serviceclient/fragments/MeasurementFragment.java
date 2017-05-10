package com.example.ghost005.serviceclient.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.utilities.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementFragment extends Fragment
{
	private static final String BUNDLE_DATABASE_ID = "bundle_database_id";
	private static final String STATE_DIALOG = "state_dialog";

	private String dialog;

	public static MeasurementFragment newInstance(int databaseId, String type)
	{
		MeasurementFragment fragment = new MeasurementFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_DATABASE_ID, databaseId);
		args.putString(Constants.BUNDLE_TYPE, type);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_measurement, container, false);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public boolean save()
	{
		return true;
	}
}
