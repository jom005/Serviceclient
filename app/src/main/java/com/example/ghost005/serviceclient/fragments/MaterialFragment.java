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

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.DatePickerDialogFragment;
import com.example.ghost005.serviceclient.dialogs.LongEditTextDialogFragment;
import com.example.ghost005.serviceclient.dialogs.ShortEditTextDialogFragment;
import com.example.ghost005.serviceclient.events.DatePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.DatePickerDialogSetEvent;
import com.example.ghost005.serviceclient.events.LongEditTextDialogEvent;
import com.example.ghost005.serviceclient.events.ShortEditTextDialogEvent;
import com.example.ghost005.serviceclient.model.types.Material;
import com.example.ghost005.serviceclient.model.types.Materials;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment
		implements View.OnClickListener, DatePickerDialogFragment.DatePickerDialogEventListener,
		LongEditTextDialogFragment.LongEditTextDialogEventListener,
		ShortEditTextDialogFragment.ShortEditTextDialogEventListener
{
	private static final String BUNDLE_MATERIALS_DB_ID = "bundle_materials_db_id";
	private static final String BUNDLE_MATERIAL_DB_ID = "bundle_material_db_id";
	private static final String BUNDLE_NEW_MATERIAL = "bundle_new_material";
	private static final String DIALOG_NAME_OF_MATERIAL = "dialog_name_of_material";
	private static final String DIALOG_QUANTITY = "dialog_quantity";
	private static final String DIALOG_MATERIAL_CODE = "dialog_material_code";
	private static final String DIALOG_TYPE = "dialog_type";
	private static final String DIALOG_SERIES = "dialog_series";
	private static final String DIALOG_ITEM_NUMBER = "dialog_item_number";
	private static final String DIALOG_GTIN = "dialog_gtin";
	private static final String DIALOG_SERIAL_NUMBER = "dialog_serial_number";
	private static final String DIALOG_DATE_OF_MANUFACTURE = "dialog_date_of_manufacture";
	private static final String DIALOG_START_OF_WARRANTY = "dialog_start_of_warranty";
	private static final String DIALOG_END_OF_WARRANTY = "dialog_end_of_warranty";
	private static final String DIALOG_INVENTORY_NUMBER = "dialog_inventory_number";
	private static final String FRAGMENT_DATE_PICKER = "fragment_date_picker";
	private static final String FRAGMENT_LONG_EDIT_TEXT = "fragment_long_edit_text";
	private static final String FRAGMENT_SHORT_EDIT_TEXT = "fragment_shor_edit_text";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String STATE_NAME = "state_name";
	private static final String STATE_QUANTITY = "state_quantity";
	private static final String STATE_MATERIAL_CODE = "state_material_code";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_SERIES = "state_series";
	private static final String STATE_ITEM_NUMBER = "state_item_number";
	private static final String STATE_GTIN = "state_gtin";
	private static final String STATE_SERIAL_NUMBER = "state_serial_number";
	private static final String STATE_DATE_OF_MANUFACTURE = "state_date_of_manufacture";
	private static final String STATE_START_OF_WARRANTY = "state_start_of_warranty";
	private static final String STATE_END_OF_WARRANTY = "state_end_of_warranty";
	private static final String STATE_INVENTORY_NUMBER = "state_inventory_number";

	private TextView tvName;
	private TextView tvQuantity;
	private TextView tvMaterialCode;
	private TextView tvType;
	private TextView tvSeries;
	private TextView tvItemNumber;
	private TextView tvGtin;
	private TextView tvSerialNumber;
	private TextView tvDateOfManufacture;
	private TextView tvStartOfWarranty;
	private TextView tvEndOfWarranty;
	private TextView tvInventoryNumber;
	private CardView cvName;
	private CardView cvQuantity;
	private CardView cvMaterialCode;
	private CardView cvType;
	private CardView cvSeries;
	private CardView cvItemNumber;
	private CardView cvGtin;
	private CardView cvSerialNumber;
	private CardView cvDateOfManufacture;
	private CardView cvStartOfWarranty;
	private CardView cvEndOfWarranty;
	private CardView cvInventoryNumber;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	private String dateOfManufacture;
	private String startOfWarranty;
	private String endOfWarranty;
	private String dialog;

	private int materialsDBID;
	private int materialDBID;
	private boolean newMaterial;

	public static MaterialFragment newInstance(int materialsDBID, int materialDBID, boolean newMaterial)
	{
		MaterialFragment fragment = new MaterialFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_MATERIALS_DB_ID, materialsDBID);
		args.putInt(BUNDLE_MATERIAL_DB_ID, materialDBID);
		args.putBoolean(BUNDLE_NEW_MATERIAL, newMaterial);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		materialsDBID = getArguments().getInt(BUNDLE_MATERIALS_DB_ID);
		materialDBID = getArguments().getInt(BUNDLE_MATERIAL_DB_ID);
		newMaterial = getArguments().getBoolean(BUNDLE_NEW_MATERIAL);
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
		View view = inflater.inflate(R.layout.fragment_material, container, false);

		tvName = (TextView) view.findViewById(R.id.text_view_material_name);
		tvQuantity = (TextView) view.findViewById(R.id.text_view_material_quantity);
		tvMaterialCode = (TextView) view.findViewById(R.id.text_view_material_code);
		tvType = (TextView) view.findViewById(R.id.text_view_material_type);
		tvSeries = (TextView) view.findViewById(R.id.text_view_material_series);
		tvItemNumber = (TextView) view.findViewById(R.id.text_view_material_item_number);
		tvGtin = (TextView) view.findViewById(R.id.text_view_material_gtin);
		tvSerialNumber = (TextView) view.findViewById(R.id.text_view_material_serial_number);
		tvDateOfManufacture = (TextView) view.findViewById(R.id.text_view_material_date_of_manufacture);
		tvStartOfWarranty = (TextView) view.findViewById(R.id.text_view_material_start_of_warranty);
		tvEndOfWarranty = (TextView) view.findViewById(R.id.text_view_material_end_of_warranty);
		tvInventoryNumber = (TextView) view.findViewById(R.id.text_view_material_inventory_number);

		cvName = (CardView) view.findViewById(R.id.card_view_name_of_material);
		cvQuantity = (CardView) view.findViewById(R.id.card_view_quantity);
		cvMaterialCode = (CardView) view.findViewById(R.id.card_view_material_code);
		cvType = (CardView) view.findViewById(R.id.card_view_type);
		cvSeries = (CardView) view.findViewById(R.id.card_view_series);
		cvItemNumber = (CardView) view.findViewById(R.id.card_view_item_number);
		cvGtin = (CardView) view.findViewById(R.id.card_view_gtin);
		cvSerialNumber = (CardView) view.findViewById(R.id.card_view_serial_number);
		cvDateOfManufacture = (CardView) view.findViewById(R.id.card_view_date_of_manufacture);
		cvStartOfWarranty = (CardView) view.findViewById(R.id.card_view_start_of_warranty);
		cvEndOfWarranty = (CardView) view.findViewById(R.id.card_view_end_of_warranty);
		cvInventoryNumber = (CardView) view.findViewById(R.id.card_view_inventory_number);

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
		cvQuantity.setOnClickListener(this);
		cvMaterialCode.setOnClickListener(this);
		cvType.setOnClickListener(this);
		cvSeries.setOnClickListener(this);
		cvItemNumber.setOnClickListener(this);
		cvGtin.setOnClickListener(this);
		cvSerialNumber.setOnClickListener(this);
		cvDateOfManufacture.setOnClickListener(this);
		cvStartOfWarranty.setOnClickListener(this);
		cvEndOfWarranty.setOnClickListener(this);
		cvInventoryNumber.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			dialog = savedInstanceState.getString(STATE_DIALOG);
			tvName.setText(savedInstanceState.getString(STATE_NAME));
			tvQuantity.setText(savedInstanceState.getString(STATE_QUANTITY));
			tvMaterialCode.setText(savedInstanceState.getString(STATE_MATERIAL_CODE));
			tvType.setText(savedInstanceState.getString(STATE_TYPE));
			tvSeries.setText(savedInstanceState.getString(STATE_SERIES));
			tvItemNumber.setText(savedInstanceState.getString(STATE_ITEM_NUMBER));
			tvGtin.setText(savedInstanceState.getString(STATE_GTIN));
			tvSerialNumber.setText(savedInstanceState.getString(STATE_SERIAL_NUMBER));
			tvInventoryNumber.setText(savedInstanceState.getString(STATE_INVENTORY_NUMBER));

			dateOfManufacture = savedInstanceState.getString(STATE_DATE_OF_MANUFACTURE);
			startOfWarranty = savedInstanceState.getString(STATE_START_OF_WARRANTY);
			endOfWarranty = savedInstanceState.getString(STATE_END_OF_WARRANTY);

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newMaterial)
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
					Material material = databaseAdapter.getMaterial(materialDBID);
					Materials materials = databaseAdapter.getMaterials(materialsDBID);

					databaseAdapter.deleteMaterial(material);

					if (materials != null && materials.getMaterials().size() == 0)
					{
						databaseAdapter.deleteMaterials(materials);
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
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(STATE_DIALOG, dialog);
		outState.putString(STATE_NAME, tvName.getText().toString());
		outState.putString(STATE_QUANTITY, tvQuantity.getText().toString());
		outState.putString(STATE_MATERIAL_CODE, tvMaterialCode.getText().toString());
		outState.putString(STATE_TYPE, tvType.getText().toString());
		outState.putString(STATE_SERIES, tvSeries.getText().toString());
		outState.putString(STATE_ITEM_NUMBER, tvItemNumber.getText().toString());
		outState.putString(STATE_GTIN, tvGtin.getText().toString());
		outState.putString(STATE_SERIAL_NUMBER, tvSerialNumber.getText().toString());
		outState.putString(STATE_DATE_OF_MANUFACTURE, dateOfManufacture);
		outState.putString(STATE_START_OF_WARRANTY, startOfWarranty);
		outState.putString(STATE_END_OF_WARRANTY, endOfWarranty);
		outState.putString(STATE_INVENTORY_NUMBER, tvInventoryNumber.getText().toString());
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.card_view_name_of_material:
			{
				dialog = DIALOG_NAME_OF_MATERIAL;
				String title = getResources().getString(R.string.material_name);
				String text = tvName.getText().toString();
				startLongEditText(title, null, text, false);
				break;
			}
			case R.id.card_view_quantity:
			{
				dialog = DIALOG_QUANTITY;
				String title = getResources().getString(R.string.material_quantity);
				String text = tvQuantity.getText().toString();
				startShortEditText(title, text, null, 0, Integer.MAX_VALUE);
				break;
			}
			case R.id.card_view_material_code:
			{
				dialog = DIALOG_MATERIAL_CODE;
				String title = getResources().getString(R.string.material_material_code);
				String text = tvMaterialCode.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_type:
			{
				dialog = DIALOG_TYPE;
				String title = getResources().getString(R.string.equipment_information_type);
				String text = tvType.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_series:
			{
				dialog = DIALOG_SERIES;
				String title = getResources().getString(R.string.equipment_information_series);
				String text = tvSeries.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_item_number:
			{
				dialog = DIALOG_ITEM_NUMBER;
				String title = getResources().getString(R.string.equipment_information_item_number);
				String text = tvItemNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_gtin:
			{
				dialog = DIALOG_GTIN;
				String title = getResources().getString(R.string.equipment_information_gtin);
				String text = tvGtin.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_serial_number:
			{
				dialog = DIALOG_SERIAL_NUMBER;
				String title = getResources().getString(R.string.equipment_information_serial_number);
				String text = tvSerialNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_inventory_number:
			{
				dialog = DIALOG_INVENTORY_NUMBER;
				String title = getResources().getString(R.string.equipment_information_inventory_number);
				String text = tvInventoryNumber.getText().toString();
				startLongEditText(title, null, text, true);
				break;
			}
			case R.id.card_view_date_of_manufacture:
			{
				dialog = DIALOG_DATE_OF_MANUFACTURE;
				startDatePicker();
				break;
			}
			case R.id.card_view_start_of_warranty:
			{
				dialog = DIALOG_START_OF_WARRANTY;
				startDatePicker();
				break;
			}
			case R.id.card_view_end_of_warranty:
			{
				dialog = DIALOG_END_OF_WARRANTY;
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

	private void startLongEditText(String title, String subTitle, String text, boolean scan)
	{
		LongEditTextDialogFragment fragment = LongEditTextDialogFragment.newInstance(title, subTitle, text, scan);
		fragment.show(getChildFragmentManager(), FRAGMENT_LONG_EDIT_TEXT);
	}

	private void startShortEditText(String title, String text, String unit, double minValue, double maxValue)
	{
		ShortEditTextDialogFragment fragment = ShortEditTextDialogFragment.newInstance(title, text, unit, minValue, maxValue);
		fragment.show(getChildFragmentManager(), FRAGMENT_SHORT_EDIT_TEXT);
	}

	private void startDatePicker()
	{
		DatePickerDialogFragment fragment = new DatePickerDialogFragment();
		fragment.show(getChildFragmentManager(), FRAGMENT_DATE_PICKER);
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
	}

	public void setText(String text)
	{
		if (dialog.equals(DIALOG_NAME_OF_MATERIAL))
		{
			tvName.setText(text);
		}
		else if (dialog.equals(DIALOG_QUANTITY))
		{
			tvQuantity.setText(text);
		}
		else if (dialog.equals(DIALOG_MATERIAL_CODE))
		{
			tvMaterialCode.setText(text);
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

		if (!newMaterial)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			Material material = databaseAdapter.getMaterial(materialDBID);

			tvName.setText(material.getName());

			if (material.getQuantity() != null)
			{
				tvQuantity.setText(String.valueOf(material.getQuantity()));
			}

			if (material.getMaterialCode() != null)
			{
				tvMaterialCode.setText(material.getMaterialCode());
			}

			if (material.getType() != null)
			{
				tvType.setText(material.getType());
			}

			if (material.getSeries() != null)
			{
				tvSeries.setText(material.getSeries());
			}

			if (material.getItemNumber() != null)
			{
				tvItemNumber.setText(material.getItemNumber());
			}

			if (material.getGtin() != null)
			{
				tvGtin.setText(material.getGtin());
			}

			if (material.getSerialNumber() != null)
			{
				tvSerialNumber.setText(material.getSerialNumber());
			}

			if (material.getInventoryNumber() != null)
			{
				tvInventoryNumber.setText(material.getInventoryNumber());
			}

			if (material.getDateOfManufacture() != null)
			{
				dateOfManufacture = material.getDateOfManufacture();

				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvDateOfManufacture.setText(Utilities.getDate(date));
				}
			}

			if (material.getStartOfWarranty() != null)
			{
				startOfWarranty = material.getStartOfWarranty();

				Date date = Utilities.getDateFromString(startOfWarranty);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvStartOfWarranty.setText(Utilities.getDate(date));
				}
			}

			if (material.getEndOfWarranty() != null)
			{
				endOfWarranty = material.getEndOfWarranty();

				Date date = Utilities.getDateFromString(dateOfManufacture);
				String dateString = Utilities.getDate(date);

				if (dateString != null)
				{
					tvEndOfWarranty.setText(Utilities.getDate(date));
				}
			}
		}
	}

	public void save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		Material material;

		if (newMaterial)
		{
			material = new Material();
		}
		else
		{
			material = databaseAdapter.getMaterial(materialDBID);
		}

		material.setName(tvName.getText().toString().trim());

		if (tvQuantity.getText().toString().trim().length() > 0)
		{
			material.setQuantity(new BigInteger(tvQuantity.getText().toString().trim()));
		}
		else
		{
			material.setQuantity(null);
		}

		if (tvMaterialCode.getText().toString().trim().length() > 0)
		{
			material.setMaterialCode(tvMaterialCode.getText().toString().trim());
		}
		else
		{
			material.setMaterialCode(null);
		}

		if (tvType.getText().toString().trim().length() > 0)
		{
			material.setType(tvType.getText().toString().trim());
		}
		else
		{
			material.setType(null);
		}

		if (tvSeries.getText().toString().trim().length() > 0)
		{
			material.setSeries(tvSeries.getText().toString().trim());
		}
		else
		{
			material.setSeries(null);
		}

		if (tvItemNumber.getText().toString().trim().length() > 0)
		{
			material.setItemNumber(tvItemNumber.getText().toString().trim());
		}
		else
		{
			material.setItemNumber(null);
		}

		if (tvGtin.getText().toString().trim().length() > 0)
		{
			material.setGtin(tvGtin.getText().toString().trim());
		}
		else
		{
			material.setGtin(null);
		}

		if (tvSerialNumber.getText().toString().trim().length() > 0)
		{
			material.setSerialNumber(tvSerialNumber.getText().toString().trim());
		}
		else
		{
			material.setSerialNumber(null);
		}

		if (tvInventoryNumber.getText().toString().trim().length() > 0)
		{
			material.setInventoryNumber(tvInventoryNumber.getText().toString().trim());
		}
		else
		{
			material.setInventoryNumber(null);
		}

		if (dateOfManufacture != null)
		{
			material.setDateOfManufacture(dateOfManufacture);
		}
		else
		{
			material.setDateOfManufacture(null);
		}

		if (startOfWarranty != null)
		{
			material.setStartOfWarranty(startOfWarranty);
		}
		else
		{
			material.setStartOfWarranty(null);
		}

		if (endOfWarranty != null)
		{
			material.setEndOfWarranty(endOfWarranty);
		}
		else
		{
			material.setEndOfWarranty(null);
		}

		if (newMaterial)
		{
			Materials materials = databaseAdapter.getMaterials(materialsDBID);
			material.setMaterials(materials);

			databaseAdapter.createOrUpdateMaterial(material);
		}
		else
		{
			databaseAdapter.createOrUpdateMaterial(material);
		}
	}

	@Override
	public void onEvent(ShortEditTextDialogEvent event)
	{
		String text = event.getText();

		setText(text);
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
