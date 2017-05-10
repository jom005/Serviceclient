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
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.sql.SQLException;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.InfoDialogFragment;
import com.example.ghost005.serviceclient.fragments.KnowledgeBasementFragment;
import com.example.ghost005.serviceclient.fragments.SchedulingFragment;
import com.example.ghost005.serviceclient.fragments.VehicleInventoryFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrdersFragment;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.Organisation;
import com.example.ghost005.serviceclient.services.CameraService;
import com.example.ghost005.serviceclient.services.ImportService;
import com.example.ghost005.serviceclient.utilities.AndroidUtilities;
import com.example.ghost005.serviceclient.activities.LoginActivity;

/**
 * MainActivity displays the Work order overview.
 */
public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener
{
	private static final int REQUEST_FILE = 0;
	private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
	private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_CAMERA = 3;
	private static final int NAV_CLOSE_DELAY = 250;
	private static final String FRAGMENT_INFO = "fragment_info";
	private static final String STATE_SELECTED_POSITION = "position";
	private static final String FRAGMENT_CURRENT = "fragment_current";

	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;

	private MenuItem itemFlashOn;
	private MenuItem itemFlashOff;

	private Handler handler;

	private int selectedItem;

	private CameraService cameraService;
	private boolean bound = false;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		navigationView = (NavigationView) findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(this);

		handler = new Handler();

		Menu menu = navigationView.getMenu();

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

			MenuItem menuItem = menu.findItem(R.id.drawer_overview);
			onNavigationItemSelected(menuItem);
		}

		// Temp Employee
		try
		{
			Organisation organisation = new Organisation();
			organisation.setName("Windradfix GmbH");
			Employee employee = new Employee();
			employee.setId("-1");
			employee.setFirstName("Max");
			employee.setLastName("Mustermann");
			employee.setOrganisation(organisation);

			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getApplication()).getDatabaseAdapter();
			Employee employeeTest = databaseAdapter.getEmployee(employee.getId());

			if (employeeTest == null)
			{
				databaseAdapter.createOrUpdateOrganisation(organisation);
				databaseAdapter.createEmployee(employee);
			}

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
	protected void onDestroy()
	{
		super.onDestroy();

		if (isFinishing())
		{
			Intent intent = new Intent(this, CameraService.class);
			stopService(intent);
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
				drawerLayout.openDrawer(GravityCompat.START);

				return true;
			}
			case R.id.action_flashlight_on:
		    {
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
				{
					cameraService.enableFlashlight();
					item.setVisible(false);
					itemFlashOff.setVisible(true);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_FILE && resultCode == RESULT_OK)
		{
			final Uri uri = data.getData();
			final String id = DocumentsContract.getDocumentId(uri);
			final String path = Environment.getExternalStorageDirectory() + "/" + id.split(":")[1];
			final File file = new File(path);

			Intent intent = new Intent(this, ImportService.class);
			intent.putExtra("file", file);
			startService(intent);
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem)
	{
		drawerLayout.closeDrawers();

		Fragment fragment = getCurrentFragment();

		switch (menuItem.getItemId())
		{
			case R.id.drawer_overview:
			{
				if (fragment != null && fragment instanceof WorkOrdersFragment)
				{
					return true;

				}

				selectedItem = 0;
				fragment = WorkOrdersFragment.newInstance();
				break;
			}
			case R.id.drawer_inventory:
			{
				if (fragment != null && fragment instanceof VehicleInventoryFragment)
				{
					return true;

				}

				selectedItem = 1;
				fragment = VehicleInventoryFragment.newInstance();
				break;
			}
			case R.id.drawer_knowledge:
			{
				if (fragment != null && fragment instanceof KnowledgeBasementFragment)
				{
					return true;

				}

				selectedItem = 2;
				fragment = KnowledgeBasementFragment.newInstance();
				break;
			}
			case R.id.drawer_scheduling:
			{
				if (fragment != null && fragment instanceof SchedulingFragment)
				{
					return true;

				}

				selectedItem = 3;
				fragment = SchedulingFragment.newInstance();
				break;
			}
			case R.id.drawer_import:
			{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }
                else
                {
                runImportIntent();
                }

				return true;
			}
			case R.id.drawer_export:
			{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                else
                {
                runExportIntent();
                }

				return true;
			}
			case R.id.drawer_settings:
			{
				return true;
			}
			case R.id.drawer_info:
			{
				fragment = new InfoDialogFragment();
				break;
			}
			default:
			{
				return false;
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
						if (finalFragment instanceof InfoDialogFragment)
						{
							((InfoDialogFragment) finalFragment).show(getFragmentManager(), FRAGMENT_INFO);
						}
						else
						{
							addFragment(finalFragment);
						}
					}
				}, NAV_CLOSE_DELAY);

				if (!(finalFragment instanceof InfoDialogFragment))
				{
					setTitle(menuItem.getTitle());
				}

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
    @Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
{
    switch (requestCode)
    {
        case REQUEST_READ_EXTERNAL_STORAGE:
        {
                runImportIntent();
        break;
    }
    case REQUEST_WRITE_EXTERNAL_STORAGE:
        {
        runExportIntent();
        break;
    }
    }
    }

private void runImportIntent()
{
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("application/*");
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    startActivityForResult(Intent.createChooser(intent, "Import gsp file"), REQUEST_FILE);
    }
private void runExportIntent()
{
    Intent intent = new Intent(this, ExportActivity.class);
    startActivity(intent);
   }
}
