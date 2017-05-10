package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.activities.WorkOrderActivity;
import com.example.ghost005.serviceclient.adapter.WorkOrdersAdapter;
import com.example.ghost005.serviceclient.async_tasks.GlobalServiceProtocolTask;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.utilities.Constants;

/**
 * Fragment displays a RecyclerView as an overview for all work orders.
 */
public class WorkOrdersFragment extends Fragment implements WorkOrdersAdapter.OnItemClickListener
{
	public static final int REQUEST_UPDATE = 0x001;
	private static final String STATE_LIST = "state_list";

	private RecyclerView rvWorkOrders;
	private WorkOrdersAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	private ImportFinishedReceiver receiver;

	public static WorkOrdersFragment newInstance()
	{
		WorkOrdersFragment fragment = new WorkOrdersFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		receiver = new ImportFinishedReceiver();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_IMPORT_FINISHED);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		getActivity().registerReceiver(receiver, intentFilter);
	}

	@Override
	public void onPause()
	{
		super.onPause();

		getActivity().unregisterReceiver(receiver);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_orders, container, false);
		setHasOptionsMenu(true);

		rvWorkOrders = (RecyclerView) view.findViewById(R.id.recycler_view_work_orders);
		rvWorkOrders.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvWorkOrders.setLayoutManager(layoutManager);
		adapter = new WorkOrdersAdapter(getActivity(), new ArrayList<GlobalServiceProtocol>());
		adapter.setOnItemClickListener(this);
		rvWorkOrders.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			new GlobalServiceProtocolTask(getActivity(), adapter).execute();
		}
		else
		{
			adapter.setList((List<GlobalServiceProtocol>) savedInstanceState
					.getSerializable(STATE_LIST));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putSerializable(STATE_LIST, new ArrayList<>(adapter.getItems()));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_work_orders, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_sort_date:
			{
				// TODO: Sort list by date

				return true;
			}
			case R.id.action_sort_id:
			{
				// TODO: Sort by id

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		new GlobalServiceProtocolTask(getActivity(), adapter).execute();
	}

	@Override
	public void onItemClick(View view, int position)
	{
		GlobalServiceProtocol gsp = adapter.getItem(position);

		Intent intent = new Intent(getActivity(), WorkOrderActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(WorkOrderActivity.BUNDLE_GSP_DB_ID, gsp.getId());
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_UPDATE);
	}

	public class ImportFinishedReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			new GlobalServiceProtocolTask(getActivity(), adapter).execute();
		}
	}
}
