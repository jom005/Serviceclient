package com.example.ghost005.serviceclient.database;

import android.app.Application;

/**
 * Application class which provides the database for the whole app.
 */
public class DatabaseApplication extends Application
{
	private volatile DatabaseAdapter databaseAdapter = null;

	@Override
	public void onTerminate()
	{
		if (databaseAdapter != null)
		{
			databaseAdapter.close();
		}

		super.onTerminate();
	}

	public DatabaseAdapter getDatabaseAdapter()
	{
		if (databaseAdapter == null)
		{
			databaseAdapter = new DatabaseAdapter(this);
		}

		return databaseAdapter;
	}
}
