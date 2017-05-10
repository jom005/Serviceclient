package com.example.ghost005.serviceclient.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import java.io.File;
import java.io.IOException;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.utilities.Constants;
import com.example.ghost005.serviceclient.utilities.GSPImporter;

/**
 * IntentService for gsp file import.
 */
public class ImportService extends IntentService
{
	private static final int NOTIFY_ID = 1;
	private static final String TAG = ImportService.class.getSimpleName();

	public ImportService()
	{
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Bundle bundle = intent.getExtras();

		if (bundle != null)
		{
			File file = (File) bundle.get("file");
			GSPImporter importer = new GSPImporter(this);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher);
			builder.setContentTitle(getString(getApplicationInfo().labelRes));
			builder.setContentText(getString(R.string.import_running));
			builder.setTicker(getString(R.string.import_running));
			builder.setPriority(Notification.PRIORITY_HIGH);
			builder.setVibrate(new long[0]);

			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(NOTIFY_ID, builder.build());

			try
			{
				importer.importGSP(file);

				builder.setContentText(getString(R.string.import_finished));
				builder.setTicker(getString(R.string.import_finished));
				manager.notify(NOTIFY_ID, builder.build());
			}
			catch (Exception e)
			{
				builder.setContentText(getString(R.string.import_failed));
				builder.setTicker(getString(R.string.import_failed));
				manager.notify(NOTIFY_ID, builder.build());

				e.printStackTrace();
			}
			finally
			{
				try
				{
					importer.clearTemp();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

			Intent intentFinished = new Intent();
			intentFinished.setAction(Constants.BROADCAST_IMPORT_FINISHED);
			intentFinished.addCategory(Intent.CATEGORY_DEFAULT);
			sendBroadcast(intentFinished);
		}
	}
}
