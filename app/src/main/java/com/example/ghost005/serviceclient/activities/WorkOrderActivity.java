package com.example.ghost005.serviceclient.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.ghost005.serviceclient.dialogs.CloseDialogFragment;
import com.example.ghost005.serviceclient.events.CloseEvent;
import com.example.ghost005.serviceclient.fragments.AttachmentsFragment;
import com.example.ghost005.serviceclient.fragments.work_order.ContactsFragment;
import com.example.ghost005.serviceclient.fragments.work_order.EnergySystemFragment;
import com.example.ghost005.serviceclient.fragments.work_order.PowerPlantDataFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkLogFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrderDetailFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrderTimeReportsFragment;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.services.CameraService;
import com.example.ghost005.serviceclient.utilities.AndroidUtilities;
import com.example.ghost005.serviceclient.utilities.FileUtilities;
import com.example.ghost005.serviceclient.wizard.WorkOrderWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * Activity that displays details for a work order item.
 */
public class WorkOrderActivity extends AppCompatActivity
		implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
		WorkOrderWizardFragment.OnWorkOrderFinishListener
{
	public static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final int NAV_CLOSE_DELAY = 250;
	private static final int REQUEST_AUDIO_CAPTURE = 10;
	private static final int REQUEST_IMAGE_CAPTURE = 11;
    private static final int REQUEST_CAMERA = 12;
	private static final String STATE_SELECTED_POSITION = "state_position";
	private static final String STATE_BUTTON_VISIBLE = "state_button_visible";
	private static final String STATE_WIZARD_STARTED = "state_wizard_started";
	private static final String FRAGMENT_CURRENT = "fragment_current";
	private static final String FRAGMENT_ATTACHMENTS = "fragment_attachments";
	private static final String FRAGMENT_CLOSE = "fragment_close";
	private static final String FRAGMENT_WIZARD = "fragment_wizard";

	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private LinearLayout llButtons;
	private Button btnStart;

	private Handler handler;

	private MenuItem itemFlashOn;
	private MenuItem itemFlashOff;

	private int selectedItem;
	private boolean wizardStarted = false;
	private boolean close = false;
	private String imageFilePath;

	private CameraService cameraService;
	private boolean bound = false;

	private int gspDBID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_order);

		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		navigationView = (NavigationView) findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(this);

		llButtons = (LinearLayout) findViewById(R.id.linear_layout_buttons);

		btnStart = (Button) findViewById(R.id.button_start);
		btnStart.setOnClickListener(this);

		handler = new Handler();

		Menu menu = navigationView.getMenu();

		Bundle bundle = getIntent().getExtras();

		if (bundle != null)
		{
			gspDBID = bundle.getInt(BUNDLE_GSP_DB_ID);
		}

		if (savedInstanceState != null)
		{
			wizardStarted = savedInstanceState.getBoolean(STATE_WIZARD_STARTED);
			menu.setGroupVisible(R.id.section_wizard, wizardStarted);
			int visibility = savedInstanceState.getInt(STATE_BUTTON_VISIBLE);

			if (visibility == View.VISIBLE)
			{
				llButtons.setVisibility(View.VISIBLE);
			}
			else
			{
				llButtons.setVisibility(View.GONE);
			}

			selectedItem = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			menu.getItem(selectedItem).setChecked(true);
			setTitle(menu.getItem(selectedItem).getTitle());
		}
		else
		{
			try
			{
				String progress = getProgress();

				if (progress != null && !progress.equals(WorkOrder.STATUS_OPEN))
				{
					llButtons.setVisibility(View.GONE);
					wizardStarted = true;
					menu.getItem(7).setChecked(true);
					menu.setGroupVisible(R.id.section_wizard, wizardStarted);
					setWizardFragment();
				}
				else
				{
					menu.getItem(0).setChecked(true);
					setTitle(menu.getItem(0).getTitle());
					MenuItem menuItem = menu.findItem(R.id.drawer_order_information);
					onNavigationItemSelected(menuItem);
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
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

		EventBus.getDefault().register(this);

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

		EventBus.getDefault().unregister(this);

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
		outState.putInt(STATE_BUTTON_VISIBLE, llButtons.getVisibility());
		outState.putBoolean(STATE_WIZARD_STARTED, wizardStarted);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_work_order, menu);
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
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }
                else
                {
                    if (AndroidUtilities.isFlashLightAvailable(this))
                    {
                        cameraService.enableFlashlight();
                        item.setVisible(false);
                        itemFlashOff.setVisible(true);
                    }
				}

				return true;
			}
			case R.id.action_flashlight_off:
			{
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }
				else
				{
                    if (AndroidUtilities.isFlashLightAvailable(this))
                    {
                        cameraService.disableFlashlight();
                        item.setVisible(false);
                        itemFlashOn.setVisible(true);
                    }

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
					WorkReport workReport = gsp.getWorkReport();
					GSPDocument gspDocument = databaseAdapter.getGSPDocument(gsp.getId());
					String mediaPath = gspDocument.getMediaPath();
					createAttachments(workReport);

					AttachmentDialogFragment fragment = AttachmentDialogFragment.newInstance(workReport.getAttachments(), mediaPath, audioFileUri);
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
					WorkReport workReport = gsp.getWorkReport();
					GSPDocument gspDocument = databaseAdapter.getGSPDocument(gsp.getId());
					String mediaPath = gspDocument.getMediaPath();
					createAttachments(workReport);

					AttachmentDialogFragment fragment = AttachmentDialogFragment.newInstance(workReport.getAttachments(), mediaPath, imageFilePath);
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
			FragmentManager manager = getFragmentManager();

			if (manager.getBackStackEntryCount() > 0)
			{
				manager.popBackStack();
				Menu menu = navigationView.getMenu();
				menu.getItem(7).setChecked(true);
			}
			else
			{
				if (close)
				{
					setResult(RESULT_CANCELED);
					super.onBackPressed();
				}
				else
				{
					String title = getResources().getString(R.string.title_dialog_close_work_order);
					String message = getResources().getString(R.string.dialog_close_work_order);
					CloseDialogFragment fragment = CloseDialogFragment.newInstance(title, message);
					fragment.show(getFragmentManager(), FRAGMENT_CLOSE);
				}
			}
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem)
	{
		drawerLayout.closeDrawers();

		Fragment fragment = getCurrentFragment();

		switch (menuItem.getItemId())
		{
			case R.id.drawer_order_information:
			{
				if (fragment != null && fragment instanceof WorkOrderDetailFragment)
				{
					return true;
				}

				selectedItem = 0;
				fragment = WorkOrderDetailFragment.newInstance(gspDBID);
				break;
			}
			case R.id.drawer_order_power_plant:
			{
				if (fragment != null && fragment instanceof PowerPlantDataFragment)
				{
					return true;
				}

				selectedItem = 1;
				fragment = PowerPlantDataFragment.newInstance(gspDBID);
				break;
			}
			case R.id.drawer_order_energy_system:
			{
				if (fragment != null && fragment instanceof EnergySystemFragment)
				{
					return true;
				}

				selectedItem = 2;
				fragment = EnergySystemFragment.newInstance(gspDBID);
				break;
			}
			case R.id.drawer_contact:
			{
				if (fragment != null && fragment instanceof ContactsFragment)
				{
					return true;
				}

				selectedItem = 3;
				fragment = ContactsFragment.newInstance(gspDBID);
				break;
			}
			case R.id.drawer_history:
			{
				if (fragment != null && fragment instanceof WorkLogFragment)
				{
					return true;
				}

				selectedItem = 4;
				fragment = WorkLogFragment.newInstance(gspDBID);
				break;
			}

			case R.id.drawer_times:
			{
				if (fragment != null && fragment instanceof WorkOrderTimeReportsFragment)
				{
					return true;
				}

				selectedItem = 5;
				fragment = WorkOrderTimeReportsFragment.newInstance(gspDBID);
				break;
			}
			case R.id.drawer_attachments:
			{
				if (fragment != null && fragment instanceof AttachmentsFragment)
				{
					return true;
				}

				selectedItem = 6;

				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
					GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
					WorkReport workReport = globalServiceProtocol.getWorkReport();

					int attachmentDBID;

					if (workReport.getAttachments() != null)
					{
						attachmentDBID = workReport.getAttachments().getId();
					}
					else
					{
						Attachments attachments = new Attachments();
						ArrayList<Attachment> attachmentList = new ArrayList<>();
						attachments.setAttachments(attachmentList);
						workReport.setAttachments(attachments);

						databaseAdapter.createOrUpdateAttachments(attachments);
						databaseAdapter.createOrUpdateWorkReport(workReport);

						attachmentDBID = attachments.getId();
					}

					fragment = AttachmentsFragment.newInstance(attachmentDBID);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				break;
			}
			case R.id.drawer_wizard:
			{
				fragment = getWizardFragment();

				if (fragment != null && fragment.isVisible() && fragment.isAdded())
				{
					return true;
				}

				selectedItem = 7;
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
						if (finalFragment instanceof WorkOrderWizardFragment)
						{
							FragmentManager manager = getFragmentManager();
							manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
							setWizardFragment();
						}
						else
						{
							addFragment(finalFragment);
						}
					}
				}, NAV_CLOSE_DELAY);

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

	private void addFragment(Fragment fragment)
	{
		FragmentManager manager = getFragmentManager();
		manager.popBackStackImmediate();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.content_frame, fragment, FRAGMENT_CURRENT);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		if (wizardStarted)
		{
			transaction.addToBackStack(null);
		}

		transaction.commit();
	}

	private Fragment getCurrentFragment()
	{
		FragmentManager manager = getFragmentManager();
		return manager.findFragmentByTag(FRAGMENT_CURRENT);
	}

	private Fragment getWizardFragment()
	{
		FragmentManager manager = getFragmentManager();
		return manager.findFragmentByTag(FRAGMENT_WIZARD);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_start:
			{
				llButtons.setVisibility(View.GONE);
				wizardStarted = true;
				Menu menu = navigationView.getMenu();
				menu.setGroupVisible(R.id.section_wizard, wizardStarted);
				menu.getItem(7).setChecked(true);
				setWizardFragment();
			}
		}
	}

	@Override
	public void onFinish()
	{
		setResult(RESULT_CANCELED);

		super.finish();
	}

	private void createAttachments(WorkReport workReport) throws SQLException
	{
		Attachments attachments = workReport.getAttachments();

		if (attachments == null)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();

			attachments = new Attachments();
			attachments.setAttachments(new ArrayList<Attachment>());
			workReport.setAttachments(attachments);

			databaseAdapter.createOrUpdateAttachments(attachments);
			databaseAdapter.createOrUpdateWorkReport(workReport);
		}
	}

	private String getProgress() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = gsp.getWorkOrder();

		return workOrder.getProgress();
	}

	private void setWizardFragment()
	{
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentByTag(FRAGMENT_WIZARD);

		if (fragment == null)
		{
			fragment = WorkOrderWizardFragment.newInstance(gspDBID);
		}

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.content_frame, fragment, FRAGMENT_WIZARD);
		transaction.commit();
	}

	public void onEvent(CloseEvent event)
	{
		close = event.isClose();
		onBackPressed();
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
