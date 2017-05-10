package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.activities.WorkOrderItemActivity;
import com.example.ghost005.serviceclient.adapter.WorkOrderItemsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkOrderItems;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItems;
import com.example.ghost005.serviceclient.utilities.Constants;
import com.example.ghost005.serviceclient.wizard.WorkOrderWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderItemsFragment extends Fragment
		implements WorkOrderItemsAdapter.OnItemClickListener, WorkOrderWizardFragment.OnStatusSaveListener
{
	private static final String BUNDLE_GSP_DB_ID = "gsp_db_id";
	private static final String BUNDLE_POSITION = "bundle_position";

	private RecyclerView rvWorkOrders;
	private WorkOrderItemsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	private int gspDBID;
	private int position;

	public static WorkOrderItemsFragment newInstance(int gspDBID, int position)
	{
		WorkOrderItemsFragment fragment = new WorkOrderItemsFragment();

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_items, container, false);

		rvWorkOrders = (RecyclerView) view.findViewById(R.id.recycler_view_work_order_items);
		rvWorkOrders.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvWorkOrders.setLayoutManager(layoutManager);
		adapter = new WorkOrderItemsAdapter(getActivity(), new ArrayList<WorkOrderItem>(), new ArrayList<WorkReportItem>());
		adapter.setOnItemClickListener(this);
		rvWorkOrders.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

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
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
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
		WorkOrderItem workOrderItem = adapter.getItem(position);

		Intent intent = new Intent(getActivity(), WorkOrderItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(WorkOrderItemActivity.BUNDLE_GSP_DB_ID, gspDBID);
		bundle.putInt(WorkOrderItemActivity.BUNDLE_WORK_ORDER_ITEM_DB_ID, workOrderItem.get_id());
		intent.putExtras(bundle);
		startActivityForResult(intent, Constants.REQUEST_UPDATE);
	}

	@Override
	public boolean save()
	{
		return true;
	}

	private void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrderItems workOrderItems = globalServiceProtocol.getWorkOrder().getItems();
		WorkReportItems workReportItems = globalServiceProtocol.getWorkReport().getItems();

		adapter.setWorkOrderItems(new ArrayList<>(workOrderItems.getWorkOrderItems()));
		adapter.setWorkReportItems(new ArrayList<>(workReportItems.getWorkReportItems()));
		adapter.notifyDataSetChanged();
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
