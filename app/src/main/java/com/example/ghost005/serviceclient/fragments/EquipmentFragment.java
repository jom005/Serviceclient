package com.example.ghost005.serviceclient.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.DatePickerDialogFragment;
import com.example.ghost005.serviceclient.dialogs.LongEditTextDialogFragment;
import com.example.ghost005.serviceclient.events.DatePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.DatePickerDialogSetEvent;
import com.example.ghost005.serviceclient.events.LongEditTextDialogEvent;
import com.example.ghost005.serviceclient.model.types.Equipment;
import com.example.ghost005.serviceclient.model.types.Equipments;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment implements View.OnClickListener,
		DatePickerDialogFragment.DatePickerDialogEventListener,
		LongEditTextDialogFragment.LongEditTextDialogEventListener
{
	private static final String BUNDLE_EQUIPMENTS_DB_ID = "bundle_equipments_db_id";
	private static final String BUNDLE_EQUIPMENT_DB_ID = "bundle_equipment_db_id";
	private static final String BUNDLE_NEW_EQUIPMENT = "bundle_new_equipment";
	private static final String DIALOG_NAME = "dialog_name";
	private static final String DIALOG_TYPE = "dialog_type";
	private static final String DIALOG_SERIES = "dialog_series";
	private static final String DIALOG_ITEM_NUMBER = "dialog_item_number";
	private static final String DIALOG_GTIN = "dialog_gtin";
	private static final String DIALOG_SERIAL_NUMBER = "dialog_serial_number";
	private static final String DIALOG_DATE_OF_MANUFACTURE = "dialog_date_of_manufacture";
	private static final String DIALOG_START_OF_WARRANTY = "dialog_start_of_warranty";
	private static final String DIALOG_END_OF_WARRANTY = "dialog_end_of_warranty";
	private static final String DIALOG_INVENTORY_NUMBER = "dialog_inventory_number";
	private static final String DIALOG_DATE_OF_INSTALLATION = "dialog_date_of_installation";
	private static final String DIALOG_DATE_OF_REMOVAL = "dialog_date_of_removal";
	private static final String FRAGMENT_DATE_PICKER = "fragment_date_picker";
	private static final String FRAGMENT_LONG_EDIT_TEXT = "fragment_long_edit_text";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String STATE_NAME = "state_name";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_SERIES = "state_series";
	private static final String STATE_ITEM_NUMBER = "state_item_number";
	private static final String STATE_GTIN = "state_gtin";
	private static final String STATE_SERIAL_NUMBER = "state_serial_number";
	private static final String STATE_DATE_OF_MANUFACTURE = "state_date_of_manufacture";
	private static final String STATE_START_OF_WARRANTY = "state_start_of_warranty";
	private static final String STATE_END_OF_WARRANTY = "state_end_of_warranty";
	private static final String STATE_INVENTORY_NUMBER = "state_inventory_number";
	private static final String STATE_DATE_OF_INSTALLATION = "state_date_of_installation";
	private static final String STATE_DATE_OF_REMOVAL = "state_date_of_removal";

	private TextView tvName;
	private TextView tvType;
	private TextView tvSeries;
	private TextView tvItemNumber;
	private TextView tvGtin;
	private TextView tvSerialNumber;
	private TextView tvDateOfManufacture;
	private TextView tvStartOfWarranty;
	private TextView tvEndOfWarranty;
	private TextView tvInventoryNumber;
	private TextView tvDateOfInstallation;
	private TextView tvDateOfRemoval;
	private CardView cvName;
	private CardView cvType;
	private CardView cvSeries;
	private CardView cvItemNumber;
	private CardView cvGtin;
	private CardView cvSerialNumber;
	private CardView cvDateOfManufacture;
	private CardView cvStartOfWarranty;
	private CardView cvEndOfWarranty;
	private CardView cvInventoryNumber;
	private CardView cvDateOfInstallation;
	private CardView cvDateOfRemoval;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	private String dateOfManufacture;
	private String startOfWarranty;
	private String endOfWarranty;
	private String dateOfInstallation;
	private String dateOfRemoval;
	private String dialog;

	private boolean newEqipment;
	private int equipmentsDatabaseId;
	private int equipmentDatabaseId;

	public static EquipmentFragment newInstance(int equipmentsDBID, int equipmentDBID, boolean newEqipment)
	{
		EquipmentFragment fragment = new EquipmentFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_EQUIPMENTS_DB_ID, equipmentsDBID);
		args.putInt(BUNDLE_EQUIPMENT_DB_ID, equipmentDBID);
		args.putBoolean(BUNDLE_NEW_EQUIPMENT, newEqipment);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		equipmentsDatabaseId = getArguments().getInt(BUNDLE_EQUIPMENTS_DB_ID);
		equipmentDatabaseId = getArguments().getInt(BUNDLE_EQUIPMENT_DB_ID);
		newEqipment = getArguments().getBoolean(BUNDLE_NEW_EQUIPMENT);
	}

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_equipment, container, false);

		tvName = (TextView) view.findViewById(R.id.text_view_equipment_information_name);
		tvType = (TextView) view.findViewById(R.id.text_view_equipment_information_type);
		tvSeries = (TextView) view.findViewById(R.id.text_view_equipment_information_series);
		tvItemNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_item_number);
		tvGtin = (TextView) view.findViewById(R.id.text_view_equipment_information_gtin);
		tvSerialNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_serial_number);
		tvDateOfManufacture = (TextView) view.findViewById(R.id.text_view_equipment_information_date_of_manufacture);
		tvStartOfWarranty = (TextView) view.findViewById(R.id.text_view_equipment_information_start_of_warranty);
		tvEndOfWarranty = (TextView) view.findViewById(R.id.text_view_equipment_information_end_of_warranty);
		tvInventoryNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_inventory_number);
		tvDateOfInstallation = (TextView) view.findViewById(R.id.text_view_equipment_date_of_installation);
		tvDateOfRemoval = (TextView) view.findViewById(R.id.text_view_equipment_Date_of_removal);

		cvName = (CardView) view.findViewById(R.id.card_view_equipment_information_name);
		cvType = (CardView) view.findViewById(R.id.card_view_equipment_information_type);
		cvSeries = (CardView) view.findViewById(R.id.card_view_equipment_information_series);
		cvItemNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_item_number);
		cvGtin = (CardView) view.findViewById(R.id.card_view_equipment_information_gtin);
		cvSerialNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_serial_number);
		cvDateOfManufacture = (CardView) view.findViewById(R.id.card_view_equipment_information_date_of_manufacture);
		cvStartOfWarranty = (CardView) view.findViewById(R.id.card_view_equipment_information_start_of_warranty);
		cvEndOfWarranty = (CardView) view.findViewById(R.id.card_view_equipment_information_end_of_warranty);
		cvInventoryNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_inventory_number);
		cvDateOfInstallation = (CardView) view.findViewById(R.id.card_view_equipment_date_of_installation);
		cvDateOfRemoval = (CardView) view.findViewById(R.id.card_view_equipment_Date_of_removal);

		llButtons = (LinearLayout) view.findViewById(R.id.buttons);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvName.setOnClickListener(this);
		cvType.setOnClickListener(this);
		cvSeries.setOnClickListener(this);
		cvItemNumber.setOnClickListener(this);
		cvGtin.setOnClickListener(this);
		cvSerialNumber.setOnClickListener(this);
		cvDateOfManufacture.setOnClickListener(this);
		cvStartOfWarranty.setOnClickListener(this);
		cvEndOfWarranty.setOnClickListener(this);
		cvInventoryNumber.setOnClickListener(this);
		cvDateOfInstallation.setOnClickListener(this);
		cvDateOfRemoval.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			dialog = savedInstanceState.getString(STATE_DIALOG);
			tvName.setText(savedInstanceState.getString(STATE_NAME));
			tvType.setText(savedInstanceState.getString(STATE_TYPE));
			tvSeries.setText(savedInstanceState.getString(STATE_SERIES));
			tvItemNumber.setText(savedInstanceState.getString(STATE_ITEM_NUMBER));
			tvGtin.setText(savedInstanceState.getString(STATE_GTIN));
			tvSerialNumber.setText(savedInstanceState.getString(STATE_SERIAL_NUMBER));
			tvInventoryNumber.setText(savedInstanceState.getString(STATE_INVENTORY_NUMBER));

			dateOfManufacture = savedInstanceState.getString(STATE_DATE_OF_MANUFACTURE);
			startOfWarranty = savedInstanceState.getString(STATE_START_OF_WARRANTY);
			endOfWarranty = savedInstanceState.getString(STATE_END_OF_WARRANTY);
			dateOfInstallation = savedInstanceState.getString(STATE_DATE_OF_INSTALLATION);
			dateOfRemoval = savedInstanceState.getString(STATE_DATE_OF_REMOVAL);

			Date date = Utilities.getDateFromString(dateOfManufacture);
			String time = Utilities.getTime(date);

			if (time != null)
			{
				tvDateOfManufacture.setText(time);
			}

			date = Utilities.getDateFromString(startOfWarranty);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvStartOfWarranty.setText(time);
			}

			date = Utilities.getDateFromString(endOfWarranty);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvEndOfWarranty.setText(time);
			}

			date = Utilities.getDateFromString(dateOfInstallation);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvDateOfInstallation.setText(time);
			}

			date = Utilities.getDateFromString(dateOfRemoval);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvDateOfRemoval.setText(time);
			}
		}
		else
		{
			try
			{
				loadData();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(STATE_DIALOG, dialog);
		outState.putString(STATE_NAME, tvName.getText().toString());
		outState.putString(STATE_TYPE, tvType.getText().toString());
		outState.putString(STATE_SERIES, tvSeries.getText().toString());
		outState.putString(STATE_ITEM_NUMBER, tvItemNumber.getText().toString());
		outState.putString(STATE_GTIN, tvGtin.getText().toString());
		outState.putString(STATE_SERIAL_NUMBER, tvSerialNumber.getText().toString());
		outState.putString(STATE_DATE_OF_MANUFACTURE, dateOfManufacture);
		outState.putString(STATE_START_OF_WARRANTY, startOfWarranty);
		outState.putString(STATE_END_OF_WARRANTY, endOfWarranty);
		outState.putString(STATE_INVENTORY_NUMBER, tvInventoryNumber.getText().toString());
		outState.putString(STATE_DATE_OF_INSTALLATION, dateOfInstallation);
		outState.putString(STATE_DATE_OF_REMOVAL, dateOfRemoval);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newEqipment)
		{
			itemDelete.setVisible(false);
		}
		else
		{
			itemDelete.setVisible(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_delete:
			{
				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
					Equipment equipment = databaseAdapter.getEquipment(equipmentDatabaseId);
					Equipments equipments = databaseAdapter.getEquipments(equipmentsDatabaseId);

					databaseAdapter.deleteEquipment(equipment);

					if (equipments != null && equipments.getEquipments().size() == 0)
					{
						databaseAdapter.deleteEquipments(equipments);
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.card_view_equipment_information_name:
			{
				dialog = DIALOG_NAME;
				String title = getResources().getString(R.string.material_name);
				String text = tvName.getText().toString();
				startLongEditText(title, null, text, false);
				break;
			}
			case R.id.card_view_equipment_information_type:
			{
				dialog = DIALOG_TYPE;
				String title = getResources().getString(R.string.equipment_information_type);
				String text = tvType.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_series:
			{
				dialog = DIALOG_SERIES;
				String title = getResources().getString(R.string.equipment_information_series);
				String text = tvSeries.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_item_number:
			{
				dialog = DIALOG_ITEM_NUMBER;
				String title = getResources().getString(R.string.equipment_information_item_number);
				String text = tvItemNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_gtin:
			{
				dialog = DIALOG_GTIN;
				String title = getResources().getString(R.string.equipment_information_gtin);
				String text = tvGtin.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_serial_number:
			{
				dialog = DIALOG_SERIAL_NUMBER;
				String title = getResources().getString(R.string.equipment_information_serial_number);
				String text = tvSerialNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_inventory_number:
			{
				dialog = DIALOG_INVENTORY_NUMBER;
				String title = getResources().getString(R.string.equipment_information_inventory_number);
				String text = tvInventoryNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_equipment_information_date_of_manufacture:
			{
				dialog = DIALOG_DATE_OF_MANUFACTURE;
				startDatePicker();
				break;
			}
			case R.id.card_view_equipment_information_start_of_warranty:
			{
				dialog = DIALOG_START_OF_WARRANTY;
				startDatePicker();
				break;
			}
			case R.id.card_view_equipment_information_end_of_warranty:
			{
				dialog = DIALOG_END_OF_WARRANTY;
				startDatePicker();
				break;
			}
			case R.id.card_view_equipment_date_of_installation:
			{
				dialog = DIALOG_DATE_OF_INSTALLATION;
				startDatePicker();
				break;
			}
			case R.id.card_view_equipment_Date_of_removal:
			{
				dialog = DIALOG_DATE_OF_REMOVAL;
				startDatePicker();
				break;
			}
			case R.id.button_cancel:
			{
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
				break;
			}
			case R.id.button_ok:
			{
				try
				{
					save();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				break;
			}
		}
	}

	public void setText(String text)
	{
		if (dialog.equals(DIALOG_NAME))
		{
			tvName.setText(text);
		}
		else if (dialog.equals(DIALOG_TYPE))
		{
			tvType.setText(text);
		}
		else if (dialog.equals(DIALOG_SERIES))
		{
			tvSeries.setText(text);
		}
		else if (dialog.equals(DIALOG_ITEM_NUMBER))
		{
			tvItemNumber.setText(text);
		}
		else if (dialog.equals(DIALOG_GTIN))
		{
			tvGtin.setText(text);
		}
		else if (dialog.equals(DIALOG_SERIAL_NUMBER))
		{
			tvSerialNumber.setText(text);
		}
		else if (dialog.equals(DIALOG_INVENTORY_NUMBER))
		{
			tvInventoryNumber.setText(text);
		}
	}

	public void setDate(int year, int monthOfYear, int dayOfMonth)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, monthOfYear);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		Date date = calendar.getTime();
		String dateString = Utilities.getDate(date);

		if (dialog.equals(DIALOG_DATE_OF_MANUFACTURE))
		{
			dateOfManufacture = Utilities.exportDate(date);
			tvDateOfManufacture.setText(dateString);
		}
		else if (dialog.equals(DIALOG_START_OF_WARRANTY))
		{
			startOfWarranty = Utilities.exportDate(date);
			tvStartOfWarranty.setText(dateString);
		}
		else if (dialog.equals(DIALOG_END_OF_WARRANTY))
		{
			endOfWarranty = Utilities.exportDate(date);
			tvEndOfWarranty.setText(dateString);
		}
		else if (dialog.equals(DIALOG_DATE_OF_INSTALLATION))
		{
			dateOfInstallation = Utilities.exportDate(date);
			tvDateOfInstallation.setText(dateString);
		}
		else if (dialog.equals(DIALOG_DATE_OF_REMOVAL))
		{
			dateOfRemoval = Utilities.exportDate(date);
			tvDateOfRemoval.setText(dateString);
		}
	}

	public void resetDate()
	{
		if (dialog.equals(DIALOG_DATE_OF_MANUFACTURE))
		{
			dateOfManufacture = null;
			tvDateOfManufacture.setText("");
		}
		else if (dialog.equals(DIALOG_START_OF_WARRANTY))
		{
			startOfWarranty = null;
			tvStartOfWarranty.setText("");
		}
		else if (dialog.equals(DIALOG_END_OF_WARRANTY))
		{
			endOfWarranty = null;
			tvEndOfWarranty.setText("");
		}
		else if (dialog.equals(DIALOG_DATE_OF_INSTALLATION))
		{
			dateOfInstallation = null;
			tvDateOfInstallation.setText("");
		}
		else if (dialog.equals(DIALOG_DATE_OF_REMOVAL))
		{
			dateOfRemoval = null;
			tvDateOfRemoval.setText("");
		}
	}

	private void loadData() throws SQLException
	{
		if (!newEqipment)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			Equipment equipment = databaseAdapter.getEquipment(equipmentDatabaseId);

			tvName.setText(equipment.getName());

			if (equipment.getType() != null)
			{
				tvType.setText(equipment.getType());
			}

			if (equipment.getSeries() != null)
			{
				tvSeries.setText(equipment.getSeries());
			}

			if (equipment.getItemNumber() != null)
			{
				tvItemNumber.setText(equipment.getItemNumber());
			}

			if (equipment.getGtin() != null)
			{
				tvGtin.setText(equipment.getGtin());
			}

			if (equipment.getSerialNumber() != null)
			{
				tvSerialNumber.setText(equipment.getSerialNumber());
			}

			if (equipment.getInventoryNumber() != null)
			{
				tvInventoryNumber.setText(equipment.getInventoryNumber());
			}

			if (equipment.getDateOfManufacture() != null)
			{
				dateOfManufacture = equipment.getDateOfManufacture();

				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvDateOfManufacture.setText(Utilities.getDate(date));
				}
			}

			if (equipment.getStartOfWarranty() != null)
			{
				startOfWarranty = equipment.getStartOfWarranty();

				Date date = Utilities.getDateFromString(startOfWarranty);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvStartOfWarranty.setText(Utilities.getDate(date));
				}
			}

			if (equipment.getEndOfWarranty() != null)
			{
				endOfWarranty = equipment.getEndOfWarranty();

				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvEndOfWarranty.setText(Utilities.getDate(date));
				}
			}

			if (equipment.getDateOfInstallation() != null)
			{
				dateOfInstallation = equipment.getDateOfInstallation();

				Date date = Utilities.getDateFromString(dateOfInstallation);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvDateOfInstallation.setText(Utilities.getDate(date));
				}
			}

			if (equipment.getDateOfRemoval() != null)
			{
				dateOfRemoval = equipment.getDateOfRemoval();

				Date date = Utilities.getDateFromString(dateOfRemoval);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvDateOfRemoval.setText(Utilities.getDate(date));
				}
			}
		}
	}

	private boolean save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		Equipment equipment;

		if (newEqipment)
		{
			equipment = new Equipment();
		}
		else
		{
			equipment = databaseAdapter.getEquipment(equipmentDatabaseId);
		}

		equipment.setName(tvName.getText().toString().trim());

		if (tvType.getText().toString().trim().length() > 0)
		{
			equipment.setType(tvType.getText().toString().trim());
		}
		else
		{
			equipment.setType(null);
		}

		if (tvSeries.getText().toString().trim().length() > 0)
		{
			equipment.setSeries(tvSeries.getText().toString().trim());
		}
		else
		{
			equipment.setSeries(null);
		}

		if (tvItemNumber.getText().toString().trim().length() > 0)
		{
			equipment.setItemNumber(tvItemNumber.getText().toString().trim());
		}
		else
		{
			equipment.setItemNumber(null);
		}

		if (tvGtin.getText().toString().trim().length() > 0)
		{
			equipment.setGtin(tvGtin.getText().toString().trim());
		}
		else
		{
			equipment.setGtin(null);
		}

		if (tvSerialNumber.getText().toString().trim().length() > 0)
		{
			equipment.setSerialNumber(tvSerialNumber.getText().toString().trim());
		}
		else
		{
			equipment.setSerialNumber(null);
		}

		if (tvInventoryNumber.getText().toString().trim().length() > 0)
		{
			equipment.setInventoryNumber(tvInventoryNumber.getText().toString().trim());
		}
		else
		{
			equipment.setInventoryNumber(null);
		}

		if (dateOfManufacture != null)
		{
			equipment.setDateOfManufacture(dateOfManufacture);
		}
		else
		{
			equipment.setDateOfManufacture(null);
		}

		if (startOfWarranty != null)
		{
			equipment.setStartOfWarranty(startOfWarranty);
		}
		else
		{
			equipment.setStartOfWarranty(null);
		}

		if (endOfWarranty != null)
		{
			equipment.setEndOfWarranty(endOfWarranty);
		}
		else
		{
			equipment.setEndOfWarranty(null);
		}

		if (dateOfInstallation != null)
		{
			equipment.setDateOfInstallation(dateOfInstallation);
		}
		else
		{
			equipment.setDateOfInstallation(null);
		}

		if (dateOfRemoval != null)
		{
			equipment.setDateOfRemoval(dateOfRemoval);
		}
		else
		{
			equipment.setDateOfRemoval(null);
		}

		if (newEqipment)
		{
			Equipments equipments = databaseAdapter.getEquipments(equipmentsDatabaseId);
			equipment.setEquipments(equipments);

			databaseAdapter.createOrUpdateEquipment(equipment);
		}
		else
		{
			databaseAdapter.createOrUpdateEquipment(equipment);
		}

		return true;
	}

	private void startLongEditText(String title, String subTitle, String text, boolean scan)
	{
		LongEditTextDialogFragment fragment = LongEditTextDialogFragment.newInstance(title, subTitle, text, scan);
		fragment.show(getChildFragmentManager(), FRAGMENT_LONG_EDIT_TEXT);
	}

	private void startDatePicker()
	{
		DatePickerDialogFragment fragment = new DatePickerDialogFragment();
		fragment.show(getChildFragmentManager(), FRAGMENT_DATE_PICKER);
	}

	@Override
	public void onEvent(LongEditTextDialogEvent event)
	{
		String text = event.getText();

		setText(text);
	}

	@Override
	public void onEvent(DatePickerDialogSetEvent event)
	{
		int year = event.getYear();
		int monthOfYear = event.getMonthOfYear();
		int dayOfMonth = event.getDayOfMonth();

		setDate(year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onEvent(DatePickerDialogResetEvent event)
	{
		resetDate();
	}
}
