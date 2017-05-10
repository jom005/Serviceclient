package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.ElectronicAddress;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Organisation;
import com.example.ghost005.serviceclient.model.types.Person;
import com.example.ghost005.serviceclient.model.types.Persons;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.StreetDetail;
import com.example.ghost005.serviceclient.model.types.TelephoneNumber;
import com.example.ghost005.serviceclient.model.types.TownDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";

	private CardView cvOperator;
	private CardView cvOwner;
	private TextView tvOperatorRole;
	private TextView tvOperatorName;
	private TextView tvOperatorAddress;
	private TextView tvOperatorEmail;
	private TextView tvOperatorPhone;
	private TextView tvOperatorEmployeeName;
	private TextView tvOperatorEmployeeEmail;
	private TextView tvOperatorEmployeePhone;
	private TextView tvOperatorEmployeeMobile;
	private TextView tvOwnerRole;
	private TextView tvOwnerName;
	private TextView tvOwnerAddress;
	private TextView tvOwnerEmail;
	private TextView tvOwnerPhone;
	private TextView tvOwnerEmployeeName;
	private TextView tvOwnerEmployeeEmail;
	private TextView tvOwnerEmployeePhone;
	private TextView tvOwnerEmployeeMobile;

	private int gspDBID;

	public static ContactsFragment newInstance(int gspDBID)
	{
		ContactsFragment fragment = new ContactsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);

		cvOperator = (CardView) view.findViewById(R.id.contact_operator);
		cvOwner = (CardView) view.findViewById(R.id.contact_owner);
		tvOperatorRole = (TextView) cvOperator.findViewById(R.id.text_view_contact_role);
		tvOperatorName = (TextView) cvOperator.findViewById(R.id.text_view_contact_name);
		tvOperatorAddress = (TextView) cvOperator.findViewById(R.id.text_view_contact_address);
		tvOperatorEmail = (TextView) cvOperator.findViewById(R.id.text_view_contact_email);
		tvOperatorPhone = (TextView) cvOperator.findViewById(R.id.text_view_contact_phone);
		tvOperatorEmployeeName = (TextView) cvOperator.findViewById(R.id.text_view_contact_employee_name);
		tvOperatorEmployeeEmail = (TextView) cvOperator.findViewById(R.id.text_view_contact_employee_email);
		tvOperatorEmployeePhone = (TextView) cvOperator.findViewById(R.id.text_view_contact_employee_phone);
		tvOperatorEmployeeMobile = (TextView) cvOperator.findViewById(R.id.text_view_contact_employee_mobile);
		tvOwnerRole = (TextView) cvOwner.findViewById(R.id.text_view_contact_role);
		tvOwnerName = (TextView) cvOwner.findViewById(R.id.text_view_contact_name);
		tvOwnerAddress = (TextView) cvOwner.findViewById(R.id.text_view_contact_address);
		tvOwnerEmail = (TextView) cvOwner.findViewById(R.id.text_view_contact_email);
		tvOwnerPhone = (TextView) cvOwner.findViewById(R.id.text_view_contact_phone);
		tvOwnerEmployeeName = (TextView) cvOwner.findViewById(R.id.text_view_contact_employee_name);
		tvOwnerEmployeeEmail = (TextView) cvOwner.findViewById(R.id.text_view_contact_employee_email);
		tvOwnerEmployeePhone = (TextView) cvOwner.findViewById(R.id.text_view_contact_employee_phone);
		tvOwnerEmployeeMobile = (TextView) cvOwner.findViewById(R.id.text_view_contact_employee_mobile);

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
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		PowerPlant powerPlant = gsp.getPowerPlant();
		//EnergySystem energySystem = gsp.getEnergySystem();

		Organisation powerPlantOperator = powerPlant.getOperator();
		Organisation powerPlantOwner = powerPlant.getOwner();

		if (powerPlantOperator != null)
		{
			tvOperatorRole.setText(getResources().getString(R.string.contact_operator));
			tvOperatorName.setText(powerPlantOperator.getName());

			StreetAddress streetAddress = powerPlantOperator.getStreetAddress();

			if (streetAddress != null)
			{
				streetAddress = databaseAdapter.getStreetAddress(streetAddress.getId());
				StringBuilder stringBuilder = getAddress(streetAddress);
				tvOperatorAddress.setText(stringBuilder.toString());
			}

			TelephoneNumber telephoneNumber = powerPlantOperator.getPhone1();

			if (telephoneNumber != null)
			{
				telephoneNumber = databaseAdapter.getTelephoneNumber(telephoneNumber.getId());
				StringBuilder stringBuilder = getPhone(telephoneNumber);
				tvOperatorPhone.setText(stringBuilder.toString());
			}

			ElectronicAddress electronicAddress = powerPlantOperator.getElectronicAddress();

			if (electronicAddress != null)
			{
				electronicAddress = databaseAdapter.getElectronicAddress(electronicAddress.getId());

				if (electronicAddress.getEmail() != null)
				{
					tvOperatorEmail.setText(electronicAddress.getEmail());
				}
			}

			if (powerPlantOperator.getContacts() != null)
			{
				Persons persons = powerPlantOperator.getContacts();
				persons = databaseAdapter.getPersons(persons.getId());

				if (persons.getPersons() != null && persons.getPersons().size() > 0)
				{
					ArrayList<Person> contacts = new ArrayList<>(persons.getPersons());
					Person contact = contacts.get(0);

					if (contact.getLastName() != null)
					{
						StringBuilder stringBuilder = new StringBuilder();

						if (contact.getPrefix() != null)
						{
							stringBuilder.append(contact.getPrefix());
							stringBuilder.append(" ");
						}

						if (contact.getFirstName() != null)
						{
							stringBuilder.append(contact.getFirstName());
							stringBuilder.append(" ");
						}

						if (contact.getmName() != null)
						{
							stringBuilder.append(contact.getmName());
							stringBuilder.append(" ");
						}

						stringBuilder.append(contact.getLastName());
						tvOperatorEmployeeName.setText(stringBuilder.toString());
					}
					else
					{
						tvOperatorEmployeeName.setVisibility(View.GONE);
					}

					if (contact.getLandlinePhone() != null)
					{
						StringBuilder stringBuilder = getPhone(contact.getLandlinePhone());
						tvOperatorEmployeePhone.setText(stringBuilder.toString());
					}
					else
					{
						tvOperatorEmployeePhone.setVisibility(View.GONE);
					}

					if (contact.getMobilePhone() != null)
					{
						StringBuilder stringBuilder = getPhone(contact.getMobilePhone());
						tvOperatorEmployeeMobile.setText(stringBuilder.toString());
					}
					else
					{
						tvOperatorEmployeeMobile.setVisibility(View.GONE);
					}

					ElectronicAddress electronicAddressPerson = contact.getElectronicAddress();

					if (electronicAddressPerson != null)
					{
						electronicAddressPerson = databaseAdapter.getElectronicAddress(electronicAddressPerson.getId());

						if (electronicAddressPerson.getEmail() != null)
						{
							tvOperatorEmployeeEmail.setText(electronicAddressPerson.getEmail());
						}
						else if (electronicAddressPerson.getEmail2() != null)
						{
							tvOperatorEmployeeEmail.setText(electronicAddressPerson.getEmail2());
						}
						else
						{
							tvOperatorEmployeeEmail.setVisibility(View.GONE);
						}
					}
					else
					{
						tvOperatorEmployeeEmail.setVisibility(View.GONE);
					}
				}
			}
		}
		else
		{
			cvOperator.setVisibility(View.GONE);
		}

		if (powerPlantOwner != null)
		{
			tvOwnerRole.setText(getResources().getString(R.string.contact_owner));
			tvOwnerName.setText(powerPlantOwner.getName());

			StreetAddress streetAddress = powerPlantOwner.getStreetAddress();

			if (streetAddress != null)
			{
				streetAddress = databaseAdapter.getStreetAddress(streetAddress.getId());
				StringBuilder stringBuilder = getAddress(streetAddress);
				tvOwnerAddress.setText(stringBuilder.toString());
			}

			TelephoneNumber telephoneNumber = powerPlantOwner.getPhone1();

			if (telephoneNumber != null)
			{
				telephoneNumber = databaseAdapter.getTelephoneNumber(telephoneNumber.getId());
				StringBuilder stringBuilder = getPhone(telephoneNumber);
				tvOwnerPhone.setText(stringBuilder.toString());
			}

			ElectronicAddress electronicAddress = powerPlantOwner.getElectronicAddress();

			if (electronicAddress != null)
			{
				electronicAddress = databaseAdapter.getElectronicAddress(electronicAddress.getId());

				if (electronicAddress.getEmail() != null)
				{
					tvOwnerEmail.setText(electronicAddress.getEmail());
				}
			}

			if (powerPlantOwner.getContacts() != null)
			{
				Persons persons = powerPlantOwner.getContacts();
				persons = databaseAdapter.getPersons(persons.getId());

				if (persons.getPersons() != null && persons.getPersons().size() > 0)
				{
					ArrayList<Person> contacts = new ArrayList<>(persons.getPersons());
					Person contact = contacts.get(0);

					if (contact.getLastName() != null)
					{
						StringBuilder stringBuilder = new StringBuilder();

						if (contact.getPrefix() != null)
						{
							stringBuilder.append(contact.getPrefix());
							stringBuilder.append(" ");
						}

						if (contact.getFirstName() != null)
						{
							stringBuilder.append(contact.getFirstName());
							stringBuilder.append(" ");
						}

						if (contact.getmName() != null)
						{
							stringBuilder.append(contact.getmName());
							stringBuilder.append(" ");
						}

						stringBuilder.append(contact.getLastName());
						tvOwnerEmployeeName.setText(stringBuilder.toString());
					}
					else
					{
						tvOwnerEmployeeName.setVisibility(View.GONE);
					}

					if (contact.getLandlinePhone() != null)
					{
						StringBuilder stringBuilder = getPhone(contact.getLandlinePhone());
						tvOwnerEmployeePhone.setText(stringBuilder.toString());
					}
					else
					{
						tvOwnerEmployeePhone.setVisibility(View.GONE);
					}

					if (contact.getMobilePhone() != null)
					{
						StringBuilder stringBuilder = getPhone(contact.getMobilePhone());
						tvOwnerEmployeeMobile.setText(stringBuilder.toString());
					}
					else
					{
						tvOwnerEmployeeMobile.setVisibility(View.GONE);
					}

					ElectronicAddress electronicAddressPerson = contact.getElectronicAddress();

					if (electronicAddressPerson != null)
					{
						electronicAddressPerson = databaseAdapter.getElectronicAddress(electronicAddressPerson.getId());

						if (electronicAddressPerson.getEmail() != null)
						{
							tvOwnerEmployeeEmail.setText(electronicAddressPerson.getEmail());
						}
						else if (electronicAddressPerson.getEmail2() != null)
						{
							tvOwnerEmployeeEmail.setText(electronicAddressPerson.getEmail2());
						}
						else
						{
							tvOwnerEmployeeEmail.setVisibility(View.GONE);
						}
					}
					else
					{
						tvOwnerEmployeeEmail.setVisibility(View.GONE);
					}
				}
			}
		}
		else
		{
			cvOwner.setVisibility(View.GONE);
		}
	}

	private StringBuilder getAddress(StreetAddress streetAddress)
	{
		StringBuilder stringBuilder = new StringBuilder();
		StreetDetail streetDetail = streetAddress.getStreetDetail();

		if (streetDetail != null)
		{
			if (streetDetail.getPrefix() != null)
			{
				stringBuilder.append(streetDetail.getPrefix());
			}

			if (streetDetail.getName() != null)
			{
				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(" ");
				}

				stringBuilder.append(streetDetail.getName());
			}

			if (streetDetail.getSuffix() != null)
			{
				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(" ");
				}

				stringBuilder.append(streetDetail.getSuffix());
			}

			if (streetDetail.getNumber() != null)
			{
				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(" ");
				}

				stringBuilder.append(streetDetail.getNumber());
			}

			if (streetDetail.getSuiteNumber() != null)
			{
				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(" ");
				}

				stringBuilder.append(streetDetail.getSuiteNumber());
			}
		}

		TownDetail townDetail = streetAddress.getTownDetail();

		if (townDetail != null)
		{
			if (stringBuilder.length() > 0)
			{
				stringBuilder.append(System.lineSeparator());
			}

			if (townDetail.getCode() != null)
			{
				stringBuilder.append(townDetail.getCode());
			}

			if (townDetail.getName() != null)
			{
				if (stringBuilder.length() > 0)
				{
					stringBuilder.append(" ");
				}

				stringBuilder.append(townDetail.getName());
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

		return stringBuilder;
	}

	private StringBuilder getPhone(TelephoneNumber telephoneNumber)
	{
		StringBuilder stringBuilder = new StringBuilder();

		if (telephoneNumber.getCountryCode() != null)
		{
			stringBuilder.append(telephoneNumber.getCountryCode());
		}

		if (telephoneNumber.getCityCode() != null)
		{
			stringBuilder.append(telephoneNumber.getCityCode());
		}

		if (telephoneNumber.getLocalNumber() != null)
		{
			stringBuilder.append(telephoneNumber.getLocalNumber());
		}

		if (telephoneNumber.getExtension() != null)
		{
			stringBuilder.append(telephoneNumber.getExtension());
		}

		return stringBuilder;
	}
}
