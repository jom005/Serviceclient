package com.example.ghost005.serviceclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.database.GSPDocument;
import com.example.ghost005.serviceclient.dialogs.AttachmentDialogFragment;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.services.CameraService;
import com.example.ghost005.serviceclient.utilities.AndroidUtilities;
import com.example.ghost005.serviceclient.utilities.FileUtilities;

/**
 * Activity that displays details of a task.
 */
public class TaskActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener
{
	public static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	public static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "work_report_item_db_id";
	public static final String BUNDLE_TASK_DB_ID = "bundle_task_db_id";
	private static final int NAV_CLOSE_DELAY = 250;
	private static final int REQUEST_AUDIO_CAPTURE = 10;
	private static final int REQUEST_IMAGE_CAPTURE = 11;
	private static final String STATE_SELECTED_POSITION = "state_position";
	private static final String FRAGMENT_CURRENT = "fragment_current";
	private static final String FRAGMENT_ATTACHMENTS = "fragment_attachments";

	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;

	private Handler handler;

	private MenuItem itemFlashOn;
	private MenuItem itemFlashOff;

	private CameraService cameraService;
	private boolean bound = false;

	private int selectedItem;
	private String imageFilePath;

	private int gspDBID;
	private int workReportItemDBID;
	private int taskDBID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		navigationView = (NavigationView) findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(this);

		handler = new Handler();

		Menu menu = navigationView.getMenu();

		Bundle bundle = getIntent().getExtras();

		if (bundle != null)
		{
			gspDBID = bundle.getInt(BUNDLE_GSP_DB_ID);
			workReportItemDBID = bundle.getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
			taskDBID = bundle.getInt(BUNDLE_TASK_DB_ID);
		}

		if (savedInstanceState != null)
		{
			selectedItem = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			menu.getItem(selectedItem).setChecked(true);
			setTitle(menu.getItem(selectedItem).getTitle());
		}
		else
		{
			menu.getItem(0).setChecked(true);
			setTitle(menu.getItem(0).getTitle());

			MenuItem menuItem = menu.findItem(R.id.drawer_task_information);
			onNavigationItemSelected(menuItem);
		}

		// Temp Employee
		try
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
			Employee employee = databaseAdapter.getEmployee("-1");

			TextView tvName = (TextView) findViewById(R.id.user_name);
			TextView tvOrganisation = (TextView) findViewById(R.id.user_organisation);
			String name = employee.getFirstName() + " " + employee.getLastName();
			//tvName.setText(name);
			//tvOrganisation.setText(employee.getOrganisation().getName());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		if (AndroidUtilities.isFlashLightAvailable(this))
		{
			Intent intent = new Intent(this, CameraService.class);
			bindService(intent, connection, Context.BIND_AUTO_CREATE);
		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		if (bound)
		{
			unbindService(connection);

			bound = false;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_SELECTED_POSITION, selectedItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_task, menu);
		getMenuInflater().inflate(R.menu.menu_flashlight, menu);

		itemFlashOn = menu.findItem(R.id.action_flashlight_on);
		itemFlashOff = menu.findItem(R.id.action_flashlight_off);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		if (AndroidUtilities.isFlashLightAvailable(this))
		{
			if (cameraService != null)
			{
				if (cameraService.isFlashlightOn())
				{
					itemFlashOff.setVisible(true);
					itemFlashOn.setVisible(false);
				}
				else
				{
					itemFlashOff.setVisible(false);
					itemFlashOn.setVisible(true);
				}
			}
		}
		else
		{
			itemFlashOn.setVisible(false);
			itemFlashOff.setVisible(false);
		}

		return super.onPrepareOptionsMenu(menu);
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
			case R.id.action_record:
			{
				Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

				if (intent.resolveActivity(getPackageManager()) != null)
				{
					startActivityForResult(intent, REQUEST_AUDIO_CAPTURE);
				}

				return true;
			}
			case R.id.action_camera:
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				if (intent.resolveActivity(getPackageManager()) != null)
				{
					try
					{
						File file = FileUtilities.createImageFile(this);
						imageFilePath = file.getAbsolutePath();
						Uri fileUri = Uri.fromFile(file);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
						startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				return true;
			}
			case R.id.action_scan:
			{
				new IntentIntegrator(this).initiateScan();

				return true;
			}
			case R.id.action_flashlight_on:
			{
				if (AndroidUtilities.isFlashLightAvailable(this))
				{
					cameraService.enableFlashlight();
					item.setVisible(false);
					itemFlashOff.setVisible(true);
				}

				return true;
			}
			case R.id.action_flashlight_off:
			{
				if (AndroidUtilities.isFlashLightAvailable(this))
				{
					cameraService.disableFlashlight();
					item.setVisible(false);
					itemFlashOn.setVisible(true);
				}

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
				data);

		if (intentResult != null)
		{
			// Temp Toast
			// TODO: Implement view with copy.
			Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_LONG).show();
		}
		else
		{
			if (requestCode == REQUEST_AUDIO_CAPTURE && resultCode == RESULT_OK)
			{
				Uri audioFileUri = data.getData();

				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
					GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
					WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
					GSPDocument gspDocument = databaseAdapter.getGSPDocument(gsp.getId());
					String mediaPath = gspDocument.getMediaPath();
					createAttachments(workReportItem);

					AttachmentDialogFragment fragment = AttachmentDialogFragment.newInstance(workReportItem.getAttachments(), mediaPath, audioFileUri);
					fragment.show(getFragmentManager(), FRAGMENT_ATTACHMENTS);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
			{
				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
					GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
					WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);
					GSPDocument gspDocument = databaseAdapter.getGSPDocument(gsp.getId());
					String mediaPath = gspDocument.getMediaPath();
					createAttachments(workReportItem);

					AttachmentDialogFragment fragment = AttachmentDialogFragment.newInstance(workReportItem.getAttachments(), mediaPath, imageFilePath);
					fragment.show(getFragmentManager(), FRAGMENT_ATTACHMENTS);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed()
	{
		if (drawerLayout.isDrawerOpen(GravityCompat.START))
		{
			drawerLayout.closeDrawers();
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem)
	{
		drawerLayout.closeDrawers();

		Fragment fragment = getCurrentFragment();

		switch (menuItem.getItemId())
		{
			case R.id.drawer_task_information:
			{
				if (fragment != null && fragment instanceof Fragment)
				{
					return true;
				}

				selectedItem = 0;
				break;
			}
			case R.id.drawer_times:
			{
				if (fragment != null && fragment instanceof Fragment)
				{
					return true;
				}

				selectedItem = 1;
				break;
			}
			case R.id.drawer_work_equipments:
			{
				if (fragment != null && fragment instanceof Fragment)
				{
					return true;
				}

				selectedItem = 2;
				break;
			}
		}

		try
		{
			if (fragment != null)
			{
				final Fragment finalFragment = fragment;

				handler.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						addFragment(finalFragment);
					}
				}, NAV_CLOSE_DELAY);

				//menuItem.setChecked(true);
				setTitle(menuItem.getTitle());

				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void finish()
	{
		setResult(RESULT_CANCELED);

		super.finish();
	}

	private void addFragment(Fragment fragment)
	{
		FragmentManager manager = getFragmentManager();
		manager.popBackStackImmediate();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.content_frame, fragment, FRAGMENT_CURRENT);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

	private Fragment getCurrentFragment()
	{
		FragmentManager manager = getFragmentManager();
		return manager.findFragmentByTag(FRAGMENT_CURRENT);
	}

	private void createAttachments(WorkReportItem workReportItem) throws SQLException
	{
		Attachments attachments = workReportItem.getAttachments();

		if (attachments == null)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();

			attachments = new Attachments();
			attachments.setAttachments(new ArrayList<Attachment>());
			workReportItem.setAttachments(attachments);

			databaseAdapter.createOrUpdateAttachments(attachments);
			databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
		}
	}

	private ServiceConnection connection = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			CameraService.CameraBinder binder = (CameraService.CameraBinder) service;
			cameraService = binder.getService();
			bound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			bound = false;
		}
	};
}
