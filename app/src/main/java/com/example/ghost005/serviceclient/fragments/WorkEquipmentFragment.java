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
import com.example.ghost005.serviceclient.dialogs.TimePickerDialogFragment;
import com.example.ghost005.serviceclient.events.DatePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.DatePickerDialogSetEvent;
import com.example.ghost005.serviceclient.events.LongEditTextDialogEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogSetEvent;
import com.example.ghost005.serviceclient.model.types.WorkEquipment;
import com.example.ghost005.serviceclient.model.types.WorkEquipments;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkEquipmentFragment extends Fragment
		implements View.OnClickListener, DatePickerDialogFragment.DatePickerDialogEventListener,
		TimePickerDialogFragment.TimePickerDialogEventListener,
		LongEditTextDialogFragment.LongEditTextDialogEventListener
{
	private static final String BUNDLE_WORK_EQUIPMENTS_DB_ID = "bundle_work_equipments_db_id";
	private static final String BUNDLE_WORK_EQUIPMENT_DB_ID = "bundle_work_equipment_db_id";
	private static final String BUNDLE_NEW_WORK_EQUIPMENT = "bundle_new_work_equipment";
	private static final String DIALOG_NAME = "dialog_name";
	private static final String DIALOG_CATEGORY = "dialog_category";
	private static final String DIALOG_TYPE = "dialog_type";
	private static final String DIALOG_SERIES = "dialog_series";
	private static final String DIALOG_ITEM_NUMBER = "dialog_item_number";
	private static final String DIALOG_GTIN = "dialog_gtin";
	private static final String DIALOG_SERIAL_NUMBER = "dialog_serial_number";
	private static final String DIALOG_DATE_OF_MANUFACTURE = "dialog_date_of_manufacture";
	private static final String DIALOG_START_OF_WARRANTY = "dialog_start_of_warranty";
	private static final String DIALOG_END_OF_WARRANTY = "dialog_end_of_warranty";
	private static final String DIALOG_INVENTORY_NUMBER = "dialog_inventory_number";
	private static final String DIALOG_USAGE_START = "dialog_usage_start";
	private static final String DIALOG_USAGE_END = "dialog_usage_end";
	private static final String FRAGMENT_DATE_PICKER = "fragment_date_picker";
	private static final String FRAGMENT_LONG_EDIT_TEXT = "fragment_long_edit_text";
	private static final String FRAGMENT_TIME_PICKER = "fragment_time_picker";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String STATE_NAME = "state_name";
	private static final String STATE_CATEGORY = "state_category";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_SERIES = "state_series";
	private static final String STATE_ITEM_NUMBER = "state_item_number";
	private static final String STATE_GTIN = "state_gtin";
	private static final String STATE_SERIAL_NUMBER = "state_serial_number";
	private static final String STATE_DATE_OF_MANUFACTURE = "state_date_of_manufacture";
	private static final String STATE_START_OF_WARRANTY = "state_start_of_warranty";
	private static final String STATE_END_OF_WARRANTY = "state_end_of_warranty";
	private static final String STATE_INVENTORY_NUMBER = "state_inventory_number";
	private static final String STATE_USAGE_START = "state_usage_start";
	private static final String STATE_USAGE_END = "state_usage_end";
	private static final String STATE_DURATION = "state_duration";

	private TextView tvName;
	private TextView tvType;
	private TextView tvCategory;
	private TextView tvSeries;
	private TextView tvItemNumber;
	private TextView tvGtin;
	private TextView tvSerialNumber;
	private TextView tvDateOfManufacture;
	private TextView tvStartOfWarranty;
	private TextView tvEndOfWarranty;
	private TextView tvInventoryNumber;
	private TextView tvUsageStart;
	private TextView tvUsageEnd;
	private TextView tvDuration;
	private CardView cvName;
	private CardView cvCategory;
	private CardView cvType;
	private CardView cvSeries;
	private CardView cvItemNumber;
	private CardView cvGtin;
	private CardView cvSerialNumber;
	private CardView cvDateOfManufacture;
	private CardView cvStartOfWarranty;
	private CardView cvEndOfWarranty;
	private CardView cvInventoryNumber;
	private CardView cvUsageStart;
	private CardView cvUsageEnd;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	private String dateOfManufacture;
	private String startOfWarranty;
	private String endOfWarranty;
	private String usageStart;
	private String usageEnd;
	private String duration;
	private String dialog;

	private int workEquipmentsDBID;
	private int workEquipmentDBID;
	private boolean newWorkEquipment;

	public static WorkEquipmentFragment newInstance(int workEquipmentsDBID, int workEquipmentDBID, boolean newWorkEquipment)
	{
		WorkEquipmentFragment fragment = new WorkEquipmentFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_EQUIPMENTS_DB_ID, workEquipmentsDBID);
		args.putInt(BUNDLE_WORK_EQUIPMENT_DB_ID, workEquipmentDBID);
		args.putBoolean(BUNDLE_NEW_WORK_EQUIPMENT, newWorkEquipment);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		workEquipmentsDBID = getArguments().getInt(BUNDLE_WORK_EQUIPMENTS_DB_ID);
		workEquipmentDBID = getArguments().getInt(BUNDLE_WORK_EQUIPMENT_DB_ID);
		newWorkEquipment = getArguments().getBoolean(BUNDLE_NEW_WORK_EQUIPMENT);
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
		View view = inflater.inflate(R.layout.fragment_work_equipment, container, false);

		tvName = (TextView) view.findViewById(R.id.text_view_work_equipment_name);
		tvCategory = (TextView) view.findViewById(R.id.text_view_work_equipment_category);
		tvType = (TextView) view.findViewById(R.id.text_view_equipment_information_type);
		tvSeries = (TextView) view.findViewById(R.id.text_view_equipment_information_series);
		tvItemNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_item_number);
		tvGtin = (TextView) view.findViewById(R.id.text_view_equipment_information_gtin);
		tvSerialNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_serial_number);
		tvDateOfManufacture = (TextView) view.findViewById(R.id.text_view_equipment_information_date_of_manufacture);
		tvStartOfWarranty = (TextView) view.findViewById(R.id.text_view_equipment_information_start_of_warranty);
		tvEndOfWarranty = (TextView) view.findViewById(R.id.text_view_equipment_information_end_of_warranty);
		tvInventoryNumber = (TextView) view.findViewById(R.id.text_view_equipment_information_inventory_number);
		tvUsageStart = (TextView) view.findViewById(R.id.text_view_work_equipment_usage_start);
		tvUsageEnd = (TextView) view.findViewById(R.id.text_view_work_equipment_usage_end);
		tvDuration = (TextView) view.findViewById(R.id.text_view_work_equipment_usage_duration);

		cvName = (CardView) view.findViewById(R.id.card_view_work_equipment_name);
		cvCategory = (CardView) view.findViewById(R.id.card_view_work_equipment_category);
		cvType = (CardView) view.findViewById(R.id.card_view_equipment_information_type);
		cvSeries = (CardView) view.findViewById(R.id.card_view_equipment_information_series);
		cvItemNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_item_number);
		cvGtin = (CardView) view.findViewById(R.id.card_view_equipment_information_gtin);
		cvSerialNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_serial_number);
		cvDateOfManufacture = (CardView) view.findViewById(R.id.card_view_equipment_information_date_of_manufacture);
		cvStartOfWarranty = (CardView) view.findViewById(R.id.card_view_equipment_information_start_of_warranty);
		cvEndOfWarranty = (CardView) view.findViewById(R.id.card_view_equipment_information_end_of_warranty);
		cvInventoryNumber = (CardView) view.findViewById(R.id.card_view_equipment_information_inventory_number);
		cvUsageStart = (CardView) view.findViewById(R.id.card_view_work_equipment_usage_start);
		cvUsageEnd = (CardView) view.findViewById(R.id.card_view_work_equipment_usage_end);

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
		cvCategory.setOnClickListener(this);
		cvType.setOnClickListener(this);
		cvType.setOnClickListener(this);
		cvSeries.setOnClickListener(this);
		cvItemNumber.setOnClickListener(this);
		cvGtin.setOnClickListener(this);
		cvSerialNumber.setOnClickListener(this);
		cvDateOfManufacture.setOnClickListener(this);
		cvStartOfWarranty.setOnClickListener(this);
		cvEndOfWarranty.setOnClickListener(this);
		cvInventoryNumber.setOnClickListener(this);
		cvUsageStart.setOnClickListener(this);
		cvUsageEnd.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			dialog = savedInstanceState.getString(STATE_DIALOG);
			tvName.setText(savedInstanceState.getString(STATE_NAME));
			tvCategory.setText(savedInstanceState.getString(STATE_CATEGORY));
			tvType.setText(savedInstanceState.getString(STATE_TYPE));
			tvSeries.setText(savedInstanceState.getString(STATE_SERIES));
			tvItemNumber.setText(savedInstanceState.getString(STATE_ITEM_NUMBER));
			tvGtin.setText(savedInstanceState.getString(STATE_GTIN));
			tvSerialNumber.setText(savedInstanceState.getString(STATE_SERIAL_NUMBER));
			tvInventoryNumber.setText(savedInstanceState.getString(STATE_INVENTORY_NUMBER));

			dateOfManufacture = savedInstanceState.getString(STATE_DATE_OF_MANUFACTURE);
			startOfWarranty = savedInstanceState.getString(STATE_START_OF_WARRANTY);
			endOfWarranty = savedInstanceState.getString(STATE_END_OF_WARRANTY);
			usageStart = savedInstanceState.getString(STATE_USAGE_START);
			usageEnd = savedInstanceState.getString(STATE_USAGE_END);
			duration = savedInstanceState.getString(STATE_DURATION);

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

			date = Utilities.getDateFromString(usageStart);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvUsageStart.setText(time);
			}

			date = Utilities.getDateFromString(usageEnd);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvUsageEnd.setText(time);
			}

			String durationString = Utilities.getDurationString(duration);
			tvDuration.setText(durationString);
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
		outState.putString(STATE_CATEGORY, tvCategory.getText().toString());
		outState.putString(STATE_TYPE, tvType.getText().toString());
		outState.putString(STATE_SERIES, tvSeries.getText().toString());
		outState.putString(STATE_ITEM_NUMBER, tvItemNumber.getText().toString());
		outState.putString(STATE_GTIN, tvGtin.getText().toString());
		outState.putString(STATE_SERIAL_NUMBER, tvSerialNumber.getText().toString());
		outState.putString(STATE_DATE_OF_MANUFACTURE, dateOfManufacture);
		outState.putString(STATE_START_OF_WARRANTY, startOfWarranty);
		outState.putString(STATE_END_OF_WARRANTY, endOfWarranty);
		outState.putString(STATE_INVENTORY_NUMBER, tvInventoryNumber.getText().toString());
		outState.putString(STATE_USAGE_START, usageStart);
		outState.putString(STATE_USAGE_END, usageEnd);
		outState.putString(STATE_DURATION, duration);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newWorkEquipment)
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
					WorkEquipment workEquipment = databaseAdapter.getWorkEquipment(workEquipmentDBID);
					WorkEquipments workEquipments = databaseAdapter.getWorkEquipments(workEquipmentsDBID);

					databaseAdapter.deleteWorkEquipment(workEquipment);

					if (workEquipments != null && workEquipments.getWorkEquipments().size() == 0)
					{
						databaseAdapter.deleteWorkEquipments(workEquipments);
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
			case R.id.card_view_work_equipment_name:
			{
				dialog = DIALOG_NAME;
				String title = getResources().getString(R.string.material_name);
				String text = tvName.getText().toString();
				startLongEditText(title, null, text, false);
				break;
			}
			case R.id.card_view_work_equipment_category:
			{
				dialog = DIALOG_CATEGORY;
				String title = getResources().getString(R.string.work_equipment_category);
				String text = tvCategory.getText().toString();
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
			case R.id.card_view_work_equipment_usage_start:
			{
				dialog = DIALOG_USAGE_START;
				startTimePicker();
				break;
			}
			case R.id.card_view_work_equipment_usage_end:
			{
				dialog = DIALOG_USAGE_END;
				startTimePicker();
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

	public void setDate(int year, int monthOfYear, int dayOfMonth)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, monthOfYear);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		Date date = calendar.getTime();
		String dateString = Utilities.getDate(date);

		switch (dialog)
		{
			case DIALOG_DATE_OF_MANUFACTURE:
			{
				dateOfManufacture = Utilities.exportDate(date);
				tvDateOfManufacture.setText(dateString);
				break;
			}
			case DIALOG_START_OF_WARRANTY:
			{
				startOfWarranty = Utilities.exportDate(date);
				tvStartOfWarranty.setText(dateString);
				break;
			}
			case DIALOG_END_OF_WARRANTY:
			{
				endOfWarranty = Utilities.exportDate(date);
				tvEndOfWarranty.setText(dateString);
				break;
			}
		}
	}

	public void resetDate()
	{
		switch (dialog)
		{
			case DIALOG_DATE_OF_MANUFACTURE:
			{
				dateOfManufacture = null;
				tvDateOfManufacture.setText("");
				break;
			}
			case DIALOG_START_OF_WARRANTY:
			{
				startOfWarranty = null;
				tvStartOfWarranty.setText("");
				break;
			}
			case DIALOG_END_OF_WARRANTY:
			{
				endOfWarranty = null;
				tvEndOfWarranty.setText("");
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
		else if (dialog.equals(DIALOG_CATEGORY))
		{
			tvCategory.setText(text);
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

	private void loadData() throws SQLException
	{
		if (!newWorkEquipment)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			WorkEquipment workEquipment = databaseAdapter.getWorkEquipment(workEquipmentDBID);

			tvName.setText(workEquipment.getName());

			if (workEquipment.getType() != null)
			{
				tvType.setText(workEquipment.getType());
			}

			if (workEquipment.getEquipmentCategory() != null)
			{
				tvCategory.setText(workEquipment.getEquipmentCategory());
			}

			if (workEquipment.getSeries() != null)
			{
				tvSeries.setText(workEquipment.getSeries());
			}

			if (workEquipment.getItemNumber() != null)
			{
				tvItemNumber.setText(workEquipment.getItemNumber());
			}

			if (workEquipment.getGtin() != null)
			{
				tvGtin.setText(workEquipment.getGtin());
			}

			if (workEquipment.getSerialNumber() != null)
			{
				tvSerialNumber.setText(workEquipment.getSerialNumber());
			}

			if (workEquipment.getInventoryNumber() != null)
			{
				tvInventoryNumber.setText(workEquipment.getInventoryNumber());
			}

			if (workEquipment.getDateOfManufacture() != null)
			{
				dateOfManufacture = workEquipment.getDateOfManufacture();
				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvDateOfManufacture.setText(Utilities.getDate(date));
				}
			}

			if (workEquipment.getStartOfWarranty() != null)
			{
				startOfWarranty = workEquipment.getStartOfWarranty();
				Date date = Utilities.getDateFromString(startOfWarranty);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvStartOfWarranty.setText(Utilities.getDate(date));
				}
			}

			if (workEquipment.getEndOfWarranty() != null)
			{
				endOfWarranty = workEquipment.getEndOfWarranty();

				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvEndOfWarranty.setText(Utilities.getDate(date));
				}
			}

			if (workEquipment.getUsageStart() != null)
			{
				usageStart = workEquipment.getUsageStart();
				Date date = Utilities.getDateFromString(usageStart);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvUsageStart.setText(time);
				}
			}

			if (workEquipment.getUsageEnd() != null)
			{
				usageEnd = workEquipment.getUsageEnd();
				Date date = Utilities.getDateFromString(usageEnd);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvUsageEnd.setText(time);
				}
			}

			if (workEquipment.getUsageDuration() != null)
			{
				duration = workEquipment.getUsageDuration();
				String durationString = Utilities.getDurationString(duration);
				tvDuration.setText(durationString);
			}
		}
	}

	private boolean save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		WorkEquipment workEquipment;

		if (newWorkEquipment)
		{
			workEquipment = new WorkEquipment();
		}
		else
		{
			workEquipment = databaseAdapter.getWorkEquipment(workEquipmentDBID);
		}

		workEquipment.setName(tvName.getText().toString().trim());

		if (tvCategory.getText().toString().trim().length() > 0)
		{
			workEquipment.setEquipmentCategory(tvCategory.getText().toString().trim());
		}
		else
		{
			workEquipment.setType(null);
		}

		if (tvType.getText().toString().trim().length() > 0)
		{
			workEquipment.setType(tvType.getText().toString().trim());
		}
		else
		{
			workEquipment.setType(null);
		}

		if (tvSeries.getText().toString().trim().length() > 0)
		{
			workEquipment.setSeries(tvSeries.getText().toString().trim());
		}
		else
		{
			workEquipment.setSeries(null);
		}

		if (tvItemNumber.getText().toString().trim().length() > 0)
		{
			workEquipment.setItemNumber(tvItemNumber.getText().toString().trim());
		}
		else
		{
			workEquipment.setItemNumber(null);
		}

		if (tvGtin.getText().toString().trim().length() > 0)
		{
			workEquipment.setGtin(tvGtin.getText().toString().trim());
		}
		else
		{
			workEquipment.setGtin(null);
		}

		if (tvSerialNumber.getText().toString().trim().length() > 0)
		{
			workEquipment.setSerialNumber(tvSerialNumber.getText().toString().trim());
		}
		else
		{
			workEquipment.setSerialNumber(null);
		}

		if (tvInventoryNumber.getText().toString().trim().length() > 0)
		{
			workEquipment.setInventoryNumber(tvInventoryNumber.getText().toString().trim());
		}
		else
		{
			workEquipment.setInventoryNumber(null);
		}

		if (dateOfManufacture != null)
		{
			workEquipment.setDateOfManufacture(dateOfManufacture);
		}
		else
		{
			workEquipment.setDateOfManufacture(null);
		}

		if (startOfWarranty != null)
		{
			workEquipment.setStartOfWarranty(startOfWarranty);
		}
		else
		{
			workEquipment.setStartOfWarranty(null);
		}

		if (endOfWarranty != null)
		{
			workEquipment.setEndOfWarranty(endOfWarranty);
		}
		else
		{
			workEquipment.setEndOfWarranty(null);
		}

		if (usageStart != null)
		{
			workEquipment.setUsageStart(usageStart);
		}
		else
		{
			workEquipment.setUsageStart(null);
		}

		if (usageEnd != null)
		{
			workEquipment.setUsageEnd(usageEnd);
		}
		else
		{
			workEquipment.setUsageEnd(null);
		}

		if (duration != null)
		{
			workEquipment.setUsageDuration(duration);
		}

		if (newWorkEquipment)
		{
			WorkEquipments workEquipments = databaseAdapter.getWorkEquipments(workEquipmentsDBID);
			workEquipment.setWorkEquipments(workEquipments);
			
			databaseAdapter.createOrUpdateWorkEquipment(workEquipment);
		}
		else
		{
			databaseAdapter.createOrUpdateWorkEquipment(workEquipment);
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

	private void startTimePicker()
	{
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();
		fragment.show(getChildFragmentManager(), FRAGMENT_TIME_PICKER);
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

	@Override
	public void onEvent(TimePickerDialogSetEvent event)
	{
		int hourOfDay = event.getHourOfDay();
		int minute = event.getMinute();
		setTime(hourOfDay, minute);
	}

	@Override
	public void onEvent(TimePickerDialogResetEvent event)
	{
		resetTime();
	}

	private void setTime(int hourOfDay, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		Date date = calendar.getTime();
		String time = Utilities.getTime(date);

		switch (dialog)
		{
			case DIALOG_USAGE_START:
			{
				usageStart = Utilities.exportDate(date);
				tvUsageStart.setText(time);
				break;
			}
			case DIALOG_USAGE_END:
			{
				usageEnd = Utilities.exportDate(date);
				tvUsageEnd.setText(time);
				break;
			}
		}

		duration = Utilities.getDurationAsString(usageStart, usageEnd);

		if (duration != null)
		{
			String durationString = Utilities.getDurationString(duration);
			tvDuration.setText(durationString);
		}
	}

	private void resetTime()
	{
		switch (dialog)
		{
			case DIALOG_USAGE_START:
			{
				usageStart = null;
				duration = null;
				tvUsageStart.setText("");
				tvDuration.setText("");
				break;
			}
			case DIALOG_USAGE_END:
			{
				usageEnd = null;
				duration = null;
				tvUsageEnd.setText("");
				tvDuration.setText("");
				break;
			}
		}
	}
}
