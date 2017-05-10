package com.example.ghost005.serviceclient.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.database.GSPDocument;
import com.example.ghost005.serviceclient.utilities.GSPExporter;

/**
 * IntentService for gsp file export.
 */
public class ExportService extends IntentService
{
	public static final String BUNLDE_FILENAME = "bundle_filename";
	public static final String BUNDLE_FOLDER = "bundle_folder";
	public static final String BUNDLE_GSP_DOCUMENTS = "bundle_gsp_documents";

	private static final int NOTIFY_ID = 2;
	private static final String TAG = ImportService.class.getSimpleName();

	public ExportService()
	{
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Bundle bundle = intent.getExtras();

		if (bundle != null)
		{
			String fileName = bundle.getString(BUNLDE_FILENAME);
			String folder = bundle.getString(BUNDLE_FOLDER);
			java.io.File exportDir = new java.io.File(folder);
			ArrayList<Integer> gspIDs = bundle.getIntegerArrayList(BUNDLE_GSP_DOCUMENTS);
			ArrayList<GSPDocument> gspDocuments = new ArrayList<>();

			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher);
			builder.setContentTitle(getString(getApplicationInfo().labelRes));
			builder.setContentText(getString(R.string.export_running));
			builder.setTicker(getString(R.string.export_running));
			builder.setPriority(Notification.PRIORITY_HIGH);
			builder.setVibrate(new long[0]);

			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(NOTIFY_ID, builder.build());

			try
			{
				DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();

				for (Integer integer : gspIDs)
				{
					GSPDocument gspDocument = databaseAdapter.getGSPDocument(integer);
					gspDocuments.add(gspDocument);
				}

				if (gspDocuments.size() > 0)
				{
					GSPExporter gspExporter = new GSPExporter(this);
					gspExporter.exportGSP(gspDocuments, fileName, exportDir);
				}


				builder.setContentText(getString(R.string.export_finished));
				builder.setTicker(getString(R.string.export_finished));
				manager.notify(NOTIFY_ID, builder.build());
			}
			catch (SQLException e)
			{
				builder.setContentText(getString(R.string.export_failed));
				builder.setTicker(getString(R.string.export_failed));
				manager.notify(NOTIFY_ID, builder.build());

				e.printStackTrace();
			}
			catch (Exception e)
			{
				builder.setContentText(getString(R.string.export_failed));
				builder.setTicker(getString(R.string.export_failed));
				manager.notify(NOTIFY_ID, builder.build());

				e.printStackTrace();
			}
		}
	}
}
