package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.fragments.CommentFragment;

/**
 * Activity for the CommentFragment.
 */
public class CommentActivity extends AppCompatActivity
{
	public static final String BUNDLE_COMMENTS_DB_ID = "bundle_comments_db_id";
	public static final String BUNDLE_COMMENT_DB_ID = "bundle_comment_db_id";
	public static final String BUNDLE_NEW_COMMENT = "bundle_new_comment";
	private static final String FRAGMENT_COMMENT = "fragment_comment";

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_COMMENT);

		if (fragment == null)
		{
			Bundle bundle = getIntent().getExtras();

			if (bundle != null)
			{
				int commentsDBID = bundle.getInt(BUNDLE_COMMENTS_DB_ID);
				int commentDBID = bundle.getInt(BUNDLE_COMMENT_DB_ID);
				boolean newComment = bundle.getBoolean(BUNDLE_NEW_COMMENT);

				fragment = CommentFragment.newInstance(commentsDBID, commentDBID, newComment);
				fragmentTransaction.replace(R.id.content_frame_comment, fragment, FRAGMENT_COMMENT);
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
