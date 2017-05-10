package com.example.ghost005.serviceclient.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.FilesFragment;
import com.example.ghost005.serviceclient.utilities.Constants;

/**
 * Activity for the FilesFragment.
 */
public class FilesActivity extends AppCompatActivity
{
	private static final String FRAGMENT_FILES = "fragment_files";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_files);

		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int attachmentDbId = bundle.getInt(Constants.BUNDLE_ATTACHMENT_DB_ID);

				FilesFragment fragment = FilesFragment.newInstance(attachmentDbId);

				FragmentManager manager = getFragmentManager();
				manager.beginTransaction().add(R.id.content_frame_files, fragment, FRAGMENT_FILES).commit();
			}
		}
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
