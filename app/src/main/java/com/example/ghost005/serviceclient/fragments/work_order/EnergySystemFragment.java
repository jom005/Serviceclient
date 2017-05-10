package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.EnergySystem;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Location;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.StreetDetail;
import com.example.ghost005.serviceclient.model.types.TownDetail;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnergySystemFragment extends Fragment
{
	public static final String BUNDLE_GSP_DATABASE_ID = "bundle_gsp_database_id";

	private Activity activity;
	private TableRow trEnergySystemAddress;
	private TableRow trEnergySystemDateOfManufacture;
	private TableRow trEnergySystemEndOfWarranty;
	private TableRow trEnergySystemHubHeight;
	private TableRow trEnergySystemOperationalSince;
	private TableRow trEnergySystemRatedPower;
	private TableRow trEnergySystemRotorDiameter;
	private TableRow trEnergySystemStartOfWarranty;
	private TableRow trEnergySystemSource;
	private TableRow trEnergySystemWeaNis;
	private TextView tvEnergySystemAddress;
	private TextView tvEnergySystemDateOfManufacture;
	private TextView tvEnergySystemEndOfWarranty;
	private TextView tvEnergySystemHubHeight;
	private TextView tvEnergySystemId;
	private TextView tvEnergySystemManufacturer;
	private TextView tvEnergySystemOperationalSince;
	private TextView tvEnergySystemOperator;
	private TextView tvEnergySystemOwner;
	private TextView tvEnergySystemRatedPower;
	private TextView tvEnergySystemRotorDiameter;
	private TextView tvEnergySystemSerialNumber;
	private TextView tvEnergySystemSeries;
	private TextView tvEnergySystemSource;
	private TextView tvEnergySystemStartOfWarranty;
	private TextView tvEnergySystemType;
	private TextView tvEnergySystemWeaNis;

	public static EnergySystemFragment newInstance(int gspDatabaseId)
	{
		EnergySystemFragment fragment = new EnergySystemFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DATABASE_ID, gspDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_energy_system, container, false);

		trEnergySystemAddress = (TableRow) view.findViewById(R.id.table_row_energy_system_address);
		trEnergySystemDateOfManufacture = (TableRow) view.findViewById(R.id.table_row_energy_system_date_of_manufacture);
		trEnergySystemEndOfWarranty = (TableRow) view.findViewById(R.id.table_row_energy_system_end_of_warranty);
		trEnergySystemHubHeight = (TableRow) view.findViewById(R.id.table_row_energy_system_hub_height);
		trEnergySystemOperationalSince = (TableRow) view.findViewById(R.id.table_row_energy_system_operational_since);
		trEnergySystemRatedPower = (TableRow) view.findViewById(R.id.table_row_energy_system_rated_power);
		trEnergySystemRotorDiameter = (TableRow) view.findViewById(R.id.table_row_energy_system_rotor_diameter);
		trEnergySystemStartOfWarranty = (TableRow) view.findViewById(R.id.table_row_energy_system_start_of_warranty);
		trEnergySystemSource = (TableRow) view.findViewById(R.id.table_row_energy_system_source);
		trEnergySystemWeaNis = (TableRow) view.findViewById(R.id.table_row_energy_system_wae_nis);

		tvEnergySystemAddress = (TextView) view.findViewById(R.id.text_view_energy_system_address);
		tvEnergySystemDateOfManufacture = (TextView) view.findViewById(R.id.text_view_energy_system_date_of_manufacture);
		tvEnergySystemEndOfWarranty = (TextView) view.findViewById(R.id.text_view_energy_system_end_of_warranty);
		tvEnergySystemHubHeight = (TextView) view.findViewById(R.id.text_view_energy_system_hub_heigt);
		tvEnergySystemId = (TextView) view.findViewById(R.id.text_view_energy_system_id);
		tvEnergySystemManufacturer = (TextView) view.findViewById(R.id.text_view_energy_system_manufacturer);
		tvEnergySystemOperationalSince = (TextView) view.findViewById(R.id.text_view_energy_system_operational_since);
		tvEnergySystemOperator = (TextView) view.findViewById(R.id.text_view_energy_system_operator);
		tvEnergySystemOwner = (TextView) view.findViewById(R.id.text_view_energy_system_owner);
		tvEnergySystemRatedPower = (TextView) view.findViewById(R.id.text_view_energy_system_rated_power);
		tvEnergySystemRotorDiameter = (TextView) view.findViewById(R.id.text_view_energy_system_rotor_diameter);
		tvEnergySystemSerialNumber = (TextView) view.findViewById(R.id.text_view_energy_system_serial_number);
		tvEnergySystemSeries = (TextView) view.findViewById(R.id.text_view_energy_system_series);
		tvEnergySystemSource = (TextView) view.findViewById(R.id.text_view_energy_system_source);
		tvEnergySystemStartOfWarranty = (TextView) view.findViewById(R.id.text_view_energy_system_start_of_warranty);
		tvEnergySystemType = (TextView) view.findViewById(R.id.text_view_energy_system_type);
		tvEnergySystemWeaNis = (TextView) view.findViewById(R.id.text_view_energy_system_wea_nis);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			loadData();
		}
		catch (SQLException | ParseException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		this.activity = activity;
	}

	@Override
	public void onDetach()
	{
		super.onDetach();

		activity = null;
	}

	private void loadData() throws SQLException, ParseException
	{
		int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDatabaseId);
		EnergySystem energySystem = globalServiceProtocol.getEnergySystem();

		tvEnergySystemId.setText(energySystem.getEnergySystemID());
		tvEnergySystemSerialNumber.setText(energySystem.getSerialNumber());
		tvEnergySystemType.setText(energySystem.getType());
		tvEnergySystemSeries.setText(energySystem.getSeries());
		tvEnergySystemManufacturer.setText(energySystem.getManufacturer().getName());
		tvEnergySystemOperator.setText(energySystem.getOperator().getName());
		tvEnergySystemOwner.setText(energySystem.getOwner().getName());

		if (energySystem.getSource() != null)
		{
			String energySource = GSPUtilities.getEnergySource(activity, energySystem.getSource());

			if (energySource != null)
			{
				tvEnergySystemSource.setText(energySource);
			}
			else
			{
				trEnergySystemSource.setVisibility(View.GONE);
			}
		}
		else
		{
			trEnergySystemSource.setVisibility(View.GONE);
		}

		if (energySystem.getRatedPower() != null)
		{
			GeneralValue generalValue = energySystem.getRatedPower();

			tvEnergySystemRatedPower.setText(generalValue.getValue().toString());

			//TODO: set unit and multiplier
		}
		else
		{
			trEnergySystemRatedPower.setVisibility(View.GONE);
		}

		if (energySystem.getHubHeight() != null)
		{
			GeneralValue generalValue = energySystem.getHubHeight();

			tvEnergySystemHubHeight.setText(generalValue.getValue().toString());

			//TODO: set unit and multiplier
		}
		else
		{
			trEnergySystemHubHeight.setVisibility(View.GONE);
		}

		if (energySystem.getRotorDiameter() != null)
		{
			GeneralValue generalValue = energySystem.getRotorDiameter();

			tvEnergySystemRotorDiameter.setText(generalValue.getValue().toString());

			//TODO: set unit and multiplier
		}
		else
		{
			trEnergySystemRotorDiameter.setVisibility(View.GONE);
		}

		if (energySystem.getDateOfManufacture() != null)
		{
			Date date = Utilities.getDateFromString(energySystem.getDateOfManufacture());

			tvEnergySystemDateOfManufacture.setText(Utilities.getDate(date));
		}
		else
		{
			trEnergySystemDateOfManufacture.setVisibility(View.GONE);
		}

		if (energySystem.getOperationalSince() != null)
		{
			Date date = Utilities.getDateFromString(energySystem.getOperationalSince());

			tvEnergySystemOperationalSince.setText(Utilities.getDate(date));
		}
		else
		{
			trEnergySystemOperationalSince.setVisibility(View.GONE);
		}

		if (energySystem.getStartOfWarranty() != null)
		{
			Date date = Utilities.getDateFromString(energySystem.getStartOfWarranty());

			tvEnergySystemStartOfWarranty.setText(Utilities.getDate(date));
		}
		else
		{
			trEnergySystemStartOfWarranty.setVisibility(View.GONE);
		}

		if (energySystem.getEndOfWarranty() != null)
		{
			Date date = Utilities.getDateFromString(energySystem.getEndOfWarranty());

			tvEnergySystemEndOfWarranty.setText(Utilities.getDate(date));
		}
		else
		{
			trEnergySystemEndOfWarranty.setVisibility(View.GONE);
		}

		if (energySystem.getWeaNIS() != null)
		{
			tvEnergySystemWeaNis.setText(energySystem.getWeaNIS());
		}
		else
		{
			trEnergySystemWeaNis.setVisibility(View.GONE);
		}

		StringBuilder stringBuilder = new StringBuilder();

		if (energySystem.getAddress() != null)
		{
			Location location = energySystem.getAddress();

			if (location.getStreetAddress() != null)
			{
				StreetAddress streetAddress = location.getStreetAddress();

				if (streetAddress.getStreetDetail() != null)
				{
					StreetDetail streetDetail = streetAddress.getStreetDetail();

					if (streetDetail.getAddressGeneral() != null)
					{
						stringBuilder.append(streetDetail.getAddressGeneral());
						stringBuilder.append(System.lineSeparator());
					}

					if (streetDetail.getBuildingName() != null)
					{
						stringBuilder.append(streetDetail.getBuildingName());
						stringBuilder.append(System.lineSeparator());
					}

					if (streetDetail.getCode() != null)
					{
						stringBuilder.append(streetDetail.getCode());
						stringBuilder.append(System.lineSeparator());
					}

					if (streetDetail.getName() != null)
					{
						if (streetDetail.getPrefix() != null)
						{
							stringBuilder.append(streetDetail.getPrefix() + " ");
						}

						stringBuilder.append(streetDetail.getName());

						if (streetDetail.getSuffix() != null)
						{
							stringBuilder.append(" " + streetDetail.getSuffix());
						}

						if (streetDetail.getNumber() != null)
						{
							stringBuilder.append(" " + streetDetail.getNumber());
						}
					}
				}

				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(System.lineSeparator());
				}

				if (streetAddress.getTownDetail() != null)
				{
					TownDetail townDetail = streetAddress.getTownDetail();

					if (townDetail.getName() != null)
					{
						if (townDetail.getCode() != null)
						{
							stringBuilder.append(townDetail.getCode() + " ");
						}

						stringBuilder.append(townDetail.getName());
					}

					if (townDetail.getStateOrProvince() != null)
					{
						if (stringBuilder.length() > 0)
						{
							stringBuilder.append(", ");
						}

						stringBuilder.append(townDetail.getStateOrProvince());
					}

					if (townDetail.getCountry() != null)
					{
						if (stringBuilder.length() > 0)
						{
							stringBuilder.append(", ");

						}

						stringBuilder.append(townDetail.getCountry());
					}
				}
			}
		}

		if (stringBuilder.length() > 0)
		{
			tvEnergySystemAddress.setText(stringBuilder.toString());
		}
		else
		{
			trEnergySystemAddress.setVisibility(View.GONE);
		}
	}
}
