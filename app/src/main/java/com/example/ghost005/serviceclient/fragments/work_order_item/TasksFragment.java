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
import com.example.ghost005.serviceclient.activities.TaskActivity;
import com.example.ghost005.serviceclient.adapter.TasksAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.model.types.Task;
import com.example.ghost005.serviceclient.model.types.Tasks;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.wizard.WorkOrderItemWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment
		implements TasksAdapter.OnItemClickListener, WorkOrderItemWizardFragment.OnStatusSaveListener
{
	public static final int REQUEST_UPDATE = 1001;
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String BUNDLE_WORK_ORDER_ITEM_DATABASE_ID = "bundle_work_order_item_database_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "bundle_work_report_item_database_id";

	private RecyclerView rvTasks;
	private TasksAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	private int gspDBID;
	private int workOrderItemDBID;
	private int workReportItemDBID;
	private int position;

	public static TasksFragment newInstance(int gspDBID, int workOrderItemDBID, int workReportItemDBID, int position)
	{
		TasksFragment fragment = new TasksFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_POSITION, position);
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID, workOrderItemDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID, workReportItemDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		workOrderItemDBID = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);
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
		View view = inflater.inflate(R.layout.fragment_tasks, container, false);

		rvTasks = (RecyclerView) view.findViewById(R.id.recycler_view_tasks);
		rvTasks.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvTasks.setLayoutManager(layoutManager);
		adapter = new TasksAdapter(getActivity(), new ArrayList<Task>());
		adapter.setOnItemClickListener(this);
		rvTasks.setAdapter(adapter);

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
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onItemClick(View view, int position)
	{
		Task task = adapter.getItem(position);

		Intent intent = new Intent(getActivity(), TaskActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(TaskActivity.BUNDLE_GSP_DB_ID, gspDBID);
		bundle.putInt(TaskActivity.BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBID);
		bundle.putInt(TaskActivity.BUNDLE_TASK_DB_ID, task.get_id());
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_UPDATE);
	}

	@Override
	public boolean save()
	{
		return true;
	}

	private void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);

		Tasks tasks = workOrderItem.getTasks();

		if (tasks != null)
		{
			adapter.setTasks(new ArrayList<>(tasks.getTasks()));
		}
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

