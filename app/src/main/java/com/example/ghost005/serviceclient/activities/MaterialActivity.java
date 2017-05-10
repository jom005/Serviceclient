package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.MaterialFragment;

/**
 * Activity for the MaterialFragment.
 */
public class MaterialActivity extends AppCompatActivity
{
	public static final String BUNDLE_MATERIALS_DB_ID = "bundle_materials_db_id";
	public static final String BUNDLE_MATERIAL_DB_ID = "bundle_material_db_id";
	public static final String BUNDLE_NEW_MATERIAL = "bundle_new_material";
	private static final String FRAGMENT_MATERIAL = "fragments_material";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material);

		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_MATERIAL);

		if (fragment == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int materialsDBID = bundle.getInt(BUNDLE_MATERIALS_DB_ID);
				int materialDBID = bundle.getInt(BUNDLE_MATERIAL_DB_ID);
				boolean newMaterial = bundle.getBoolean(BUNDLE_NEW_MATERIAL);

				fragment = MaterialFragment.newInstance(materialsDBID, materialDBID, newMaterial);
				fragmentTransaction.replace(R.id.content_frame_material, fragment, FRAGMENT_MATERIAL);
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
