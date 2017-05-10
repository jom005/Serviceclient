package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.EquipmentFragment;

/**
 * Activity for the EquipmentFragment.
 */
public class EquipmentActivity extends AppCompatActivity
{
	public static final String BUNDLE_EQUIPMENTS_DB_ID = "bundle_equipments_db_id";
	public static final String BUNDLE_EQUIPMENT_DB_ID = "bundle_equipment_db_id";
	public static final String BUNDLE_NEW_EQUIPMENT = "bundle_new_equipment";
	private static final String FRAGMENT_EQUIPMENT = "fragments_equipment";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);

		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_EQUIPMENT);

		if (fragment == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int equipmentsDBID = bundle.getInt(BUNDLE_EQUIPMENTS_DB_ID);
				int equipmentDBID = bundle.getInt(BUNDLE_EQUIPMENT_DB_ID);
				boolean newEquipment = bundle.getBoolean(BUNDLE_NEW_EQUIPMENT);

				fragment = EquipmentFragment.newInstance(equipmentsDBID, equipmentDBID, newEquipment);
				fragmentTransaction.replace(R.id.content_frame_equipment, fragment, FRAGMENT_EQUIPMENT);
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
