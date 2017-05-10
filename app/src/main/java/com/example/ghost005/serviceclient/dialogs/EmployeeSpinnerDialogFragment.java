package com.example.ghost005.serviceclient.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.EmployeeArrayAdapter;
import com.example.ghost005.serviceclient.events.EmployeeSpinnerDialogEvent;
import com.example.ghost005.serviceclient.model.types.Employee;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeSpinnerDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final String BUNDLE_PERSONNEL_NUMBER = "bundle_personnel_number";
	private static final String BUNDLE_EMPLOYEES = "bundle_employees";
	private static final String STATE_SELECTED_POSITION = "state_selected_position";

	private Button btnCancel;
	private Button btnOk;
	private Spinner sSpinner;

	private int position;
	private String personnelNumber;
	private ArrayList<Employee> employees;

	public static EmployeeSpinnerDialogFragment newInstance(String personnelNumber, ArrayList<Employee> employees)
	{
		EmployeeSpinnerDialogFragment fragment = new EmployeeSpinnerDialogFragment();
		Bundle args = new Bundle();
		args.putString(BUNDLE_PERSONNEL_NUMBER, personnelNumber);
		args.putSerializable(BUNDLE_EMPLOYEES, employees);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		personnelNumber = getArguments().getString(BUNDLE_PERSONNEL_NUMBER);
		employees = (ArrayList<Employee>) getArguments().getSerializable(BUNDLE_EMPLOYEES);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(R.string.title_dialog_employee);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_employee_spinner_dialog, container, false);

		btnCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
		btnOk = (Button) view.findViewById(R.id.button_dialog_ok);
		sSpinner = (Spinner) view.findViewById(R.id.spinner_dialog);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);

		sSpinner.setAdapter(new EmployeeArrayAdapter(getActivity(), R.layout.spinner_employee, employees));

		if (savedInstanceState != null)
		{
			position = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			sSpinner.setSelection(position);
		}
		else
		{
			for (int i = 0; i < employees.size(); i++)
			{
				if (personnelNumber.equals(employees.get(i).getId()))
				{
					position = i;
					sSpinner.setSelection(position);
					break;
				}
			}
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;

		getDialog().getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_SELECTED_POSITION, sSpinner.getSelectedItemPosition());
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_dialog_cancel:
			{
				dismiss();

				break;
			}
			case R.id.button_dialog_ok:
			{
				getSpinnerItem();

				dismiss();

				break;
			}
		}
	}

	public void getSpinnerItem()
	{
		Employee employee = (Employee) sSpinner.getSelectedItem();

		String name = null;

		if (employee != null)
		{
			if (employee.getLastName() != null)
			{
				StringBuilder stringBuilder = new StringBuilder();

				if (employee.getFirstName() != null)
				{
					stringBuilder.append(employee.getFirstName() + " ");
				}

				stringBuilder.append(employee.getLastName());

				name = stringBuilder.toString();
			}
		}

		EventBus.getDefault().post(new EmployeeSpinnerDialogEvent(name, employee.getId(), employee.get_id()));
	}

	public interface EmployeeSpinnerDialogEventListener
	{
		void onEvent(EmployeeSpinnerDialogEvent event);
	}
}
