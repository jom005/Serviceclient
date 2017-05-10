package com.example.ghost005.serviceclient.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.List;

import com.example.ghost005.serviceclient.adapter.WorkOrdersAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;

/**
 * AsyncTask for loading GlobalServiceProtocols from database.
 */
public class GlobalServiceProtocolTask extends AsyncTask<Void, Void, List<GlobalServiceProtocol>>
{
	private Context context;
	private WorkOrdersAdapter adapter;

	public GlobalServiceProtocolTask(Context context, WorkOrdersAdapter adapter)
	{
		this.context = context;
		this.adapter = adapter;
	}

	@Override
	protected List<GlobalServiceProtocol> doInBackground(Void... params)
	{
		try
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) context.getApplicationContext()).getDatabaseAdapter();

			return databaseAdapter.getGlobalServiceProtocols();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(List<GlobalServiceProtocol> globalServiceProtocols)
	{
		adapter.setList(globalServiceProtocols);
		adapter.notifyDataSetChanged();
	}
}
