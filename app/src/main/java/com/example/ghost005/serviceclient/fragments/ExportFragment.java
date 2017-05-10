package com.example.ghost005.serviceclient.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.ExportAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.services.ExportService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportFragment extends Fragment
		implements View.OnClickListener, ExportAdapter.OnItemClickListener
{
	private RecyclerView rvExport;
	private ExportAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	public static ExportFragment newInstance()
	{
		ExportFragment fragment = new ExportFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_export, container, false);
		rvExport = (RecyclerView) view.findViewById(R.id.recycler_view_export);
		rvExport.setHasFixedSize(true);

		llButtons = (LinearLayout) view.findViewById(R.id.buttons);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		layoutManager = new LinearLayoutManager(getActivity());
		rvExport.setLayoutManager(layoutManager);
		adapter = new ExportAdapter(getActivity(), new ArrayList<GlobalServiceProtocol>());
		adapter.setOnItemClickListener(this);
		rvExport.setAdapter(adapter);

		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

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
	public void onItemClick(View view, int position)
	{
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box_export);
		adapter.setChecked(position, checkBox);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_cancel:
			{
				getActivity().finish();
				break;
			}
			case R.id.button_ok:
			{
				export();
				getActivity().finish();
				break;
			}
		}
	}

	private void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		ArrayList<GlobalServiceProtocol> gsps = new ArrayList<>(databaseAdapter.getGlobalServiceProtocols());

		adapter.setGsps(gsps);
		adapter.notifyDataSetChanged();
	}

	public void export()
	{
		ArrayList<GlobalServiceProtocol> gsps = adapter.getGsps();
		ArrayList<Boolean> isChecked = adapter.getIsChecked();
		ArrayList<Integer> gspIDs = new ArrayList<>();

		for (int i = 0; i < isChecked.size(); i++)
		{
			if (isChecked.get(i))
			{
				gspIDs.add(gsps.get(i).getId());
			}
		}

		String filename = "Test";
		String folder = Environment.getExternalStorageDirectory().getAbsolutePath();

		Intent intent = new Intent(getActivity(), ExportService.class);

		intent.putExtra(ExportService.BUNLDE_FILENAME, filename);
		intent.putExtra(ExportService.BUNDLE_FOLDER, folder);
		intent.putExtra(ExportService.BUNDLE_GSP_DOCUMENTS, gspIDs);
		getActivity().startService(intent);

	}
}
