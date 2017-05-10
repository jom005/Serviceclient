package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrderTimeReportFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.WorkOrderItemTimeReportFragment;
import com.example.ghost005.serviceclient.model.types.WorkOrder;

/**
 * Activity for the WorkOrderTimeReportFragment or WorkOrderItemTimeReportFragment.
 */
public class TimeReportActivity extends AppCompatActivity
{
	public static final int TYPE_WORK_REPORT = 0;
	public static final int TYPE_WORK_REPORT_ITEM = 1;
	public static final int TYPE_TASK = 2;
	public static final String BUNDLE_TYPE = "bundle_type";
	public static final String BUNDLE_WORK_REPORT_DB_ID = "bundle_db_id";
	public static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";
	public static final String BUNDLE_TIME_REPORT_DB_ID = "bundle_time_report_db_id";
	public static final String BUNDLE_NEW = "bundle_new";
	private static final String FRAGMENT_TIME_REPORT = "fragment_time_report";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_report);

		toolbar = (Toolbar) findViewById(R.id.toolbar_time_report);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		Fragment fragment = manager.findFragmentByTag(FRAGMENT_TIME_REPORT);

		if (fragment == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int type = bundle.getInt(BUNDLE_TYPE);
				int workReportDBID = bundle.getInt(BUNDLE_WORK_REPORT_DB_ID);
				int workReportItemDBID = bundle.getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
				int timeReportDBID = bundle.getInt(BUNDLE_TIME_REPORT_DB_ID);
				boolean newTimeReport = bundle.getBoolean(BUNDLE_NEW);

				if (type == TYPE_WORK_REPORT)
				{
					fragment = WorkOrderTimeReportFragment.newInstance(workReportDBID, timeReportDBID,newTimeReport);
				}
				else if (type == TYPE_WORK_REPORT_ITEM)
				{
					fragment = WorkOrderItemTimeReportFragment.newInstance(workReportDBID, workReportItemDBID, timeReportDBID, newTimeReport);
				}
				else if (type == TYPE_TASK)
				{
					// TODO
				}
				else
				{
					throw new IllegalStateException("Wrong value for type: " + type);
				}

				transaction.replace(R.id.frame_layout_content_frame, fragment, FRAGMENT_TIME_REPORT);
			}
		}

		transaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
			{
				onBackPressed();

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
