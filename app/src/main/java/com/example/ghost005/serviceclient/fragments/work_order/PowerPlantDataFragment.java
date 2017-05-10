package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Location;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.StreetDetail;
import com.example.ghost005.serviceclient.model.types.TownDetail;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class PowerPlantDataFragment extends Fragment
{
	private static final String BUNDLE_GSP_DATABASE_ID = "bundle_gsp_database_id";

	private TableRow trPowerPlantAddress;
	private TableRow trPowerPlantDescription;
	private TableRow trPowerPlantGeneratingUnits;
	private TableRow trPowerPlantOperationalSince;
	private TableRow trPowerPlantRatedPower;
	private TextView tvPowerPlantAddress;
	private TextView tvPowerPlantDescription;
	private TextView tvPowerPlantId;
	private TextView tvPowerPlantName;
	private TextView tvPowerPlantGeneratingUnits;
	private TextView tvPowerPlantOperationalSince;
	private TextView tvPowerPlantOperator;
	private TextView tvPowerPlantOwner;
	private TextView tvPowerPlantRatedPower;
	private TextView tvPowerPlantRDSPPConjoint;

	public static PowerPlantDataFragment newInstance(int gspDatabaseId)
	{
		PowerPlantDataFragment fragment = new PowerPlantDataFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DATABASE_ID, gspDatabaseId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_power_plant_data, container, false);

		trPowerPlantAddress = (TableRow) view.findViewById(R.id.table_row_power_plant_address);
		trPowerPlantDescription = (TableRow) view.findViewById(R.id.table_row_power_plant_description);
		trPowerPlantGeneratingUnits = (TableRow) view.findViewById(R.id.table_row_power_plant_generating_units);
		trPowerPlantOperationalSince = (TableRow) view.findViewById(R.id.table_row_power_plant_operational_since);
		trPowerPlantRatedPower = (TableRow) view.findViewById(R.id.table_row_power_plant_rated_power);

		tvPowerPlantAddress = (TextView) view.findViewById(R.id.text_view_power_plant_address);
		tvPowerPlantDescription = (TextView) view.findViewById(R.id.text_view_power_plant_description);
		tvPowerPlantId = (TextView) view.findViewById(R.id.text_view_power_plant_id);
		tvPowerPlantName = (TextView) view.findViewById(R.id.text_view_power_plant_name);
		tvPowerPlantGeneratingUnits = (TextView) view.findViewById(R.id.text_view_power_plant_generating_units);
		tvPowerPlantOperationalSince = (TextView) view.findViewById(R.id.text_view_power_plant_operational_since);
		tvPowerPlantOperator = (TextView) view.findViewById(R.id.text_view_power_plant_operator);
		tvPowerPlantOwner = (TextView) view.findViewById(R.id.text_view_power_plant_owner);
		tvPowerPlantRatedPower = (TextView) view.findViewById(R.id.text_view_power_plant_rated_power);
		tvPowerPlantRDSPPConjoint = (TextView) view.findViewById(R.id.text_view_power_plant_rds_pp_conjoint);

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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void loadData() throws SQLException
	{
		int gspDatabaseId = getArguments().getInt(BUNDLE_GSP_DATABASE_ID);

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDatabaseId);
		PowerPlant powerPlant = globalServiceProtocol.getPowerPlant();

		tvPowerPlantId.setText(powerPlant.getId());
		tvPowerPlantName.setText(powerPlant.getName());
		tvPowerPlantRDSPPConjoint.setText(powerPlant.getRdsPPConjoint());
		tvPowerPlantOperator.setText(powerPlant.getOperator().getName());
		tvPowerPlantOwner.setText(powerPlant.getOwner().getName());

		if (powerPlant.getDescription() != null)
		{
			tvPowerPlantDescription.setText(powerPlant.getDescription());
		}
		else
		{
			trPowerPlantDescription.setVisibility(View.GONE);
		}

		if (powerPlant.getNumberOfGeneratingUnits() != null)
		{
			tvPowerPlantGeneratingUnits.setText(powerPlant.getNumberOfGeneratingUnits().toString());
		}
		else
		{
			trPowerPlantGeneratingUnits.setVisibility(View.GONE);
		}

		if (powerPlant.getRatedPower() != null)
		{
			GeneralValue generalValue = powerPlant.getRatedPower();

			tvPowerPlantRatedPower.setText(generalValue.getValue().toString());

			//TODO: set unit and multiplier
		}
		else
		{
			trPowerPlantRatedPower.setVisibility(View.GONE);
		}

		if (powerPlant.getOperationalSince() != null)
		{
			Date date = Utilities.getDateFromString(powerPlant.getOperationalSince());

			tvPowerPlantOperationalSince.setText(Utilities.getDate(date));
		}
		else
		{
			trPowerPlantOperationalSince.setVisibility(View.GONE);
		}

		StringBuilder stringBuilder = new StringBuilder();

		if (powerPlant.getAddress() != null)
		{
			Location location = powerPlant.getAddress();

			if (location.getStreetAddress() != null)
			{
				StreetAddress streetAddress = location.getStreetAddress();
				streetAddress = databaseAdapter.getStreetAddress(streetAddress.getId());

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
			tvPowerPlantAddress.setText(stringBuilder.toString());
		}
		else
		{
			trPowerPlantAddress.setVisibility(View.GONE);
		}
	}
}
