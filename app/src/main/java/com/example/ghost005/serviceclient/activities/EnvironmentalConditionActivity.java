package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.work_order.EnvironmentalConditionFragment;


/**
 * Activity for the EnvironmentalConditionFragment.
 */
public class EnvironmentalConditionActivity extends AppCompatActivity
{
	public static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	public static final String BUNDLE_ENV_COND_DB_ID = "bundle_env_cond_db_id";
	public static final String BUNDLE_NEW = "bundle_new";
	private static final String FRAGMENT_ENV_COND = "fragment_environment";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environmental_condition);

		toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_ENV_COND);

		if (fragment == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int gspDBID = bundle.getInt(BUNDLE_GSP_DB_ID);
				int envCondDBID = bundle.getInt(BUNDLE_ENV_COND_DB_ID);
				boolean newEnvCond = bundle.getBoolean(BUNDLE_NEW);

				fragment = EnvironmentalConditionFragment.newInstance(gspDBID, envCondDBID, newEnvCond);
				fragmentTransaction.replace(R.id.frame_layout_content_frame, fragment, FRAGMENT_ENV_COND);
			}
		}

		fragmentTransaction.commit();
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
