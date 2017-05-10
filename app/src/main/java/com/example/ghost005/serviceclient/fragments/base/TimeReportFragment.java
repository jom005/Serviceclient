package com.example.ghost005.serviceclient.fragments.base;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.EmployeeSpinnerDialogFragment;
import com.example.ghost005.serviceclient.dialogs.SpinnerDialogFragment;
import com.example.ghost005.serviceclient.dialogs.TimePickerDialogFragment;
import com.example.ghost005.serviceclient.events.EmployeeSpinnerDialogEvent;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogSetEvent;
import com.example.ghost005.serviceclient.model.enums.TimePaymentType;
import com.example.ghost005.serviceclient.model.enums.TimeType;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TimeReportFragment extends Fragment implements View.OnClickListener,
		SpinnerDialogFragment.SpinnerDialogEventListener,
		TimePickerDialogFragment.TimePickerDialogEventListener,
		EmployeeSpinnerDialogFragment.EmployeeSpinnerDialogEventListener
{
	protected static final String BUNDLE_TIME_REPORT_DB_ID = "bundle_time_report_db_id";
	protected static final String BUNDLE_NEW = "bundle_new";
	private static final int DIALOG_EMPLOYEE = 1;
	private static final int DIALOG_TIME_TYPE = 2;
	private static final int DIALOG_PAYMENT_TYPE = 3;
	private static final int DIALOG_START_TIME = 4;
	private static final int DIALOG_END_TIME = 5;
	private static final String FRAGMENT_EMPLOYEE_SPINNER = "fragment_employee_spinner";
	private static final String FRAGMENT_SPINNER = "fragment_spinner";
	private static final String FRAGMENT_TIME_PICKER = "fragment_time_picker";
	private static final String STATE_EMPLOYEE = "state_employee";
	private static final String STATE_EMPLOYEE_POSITION = "state_employee_position";
	private static final String STATE_TIME_TYPE = "state_time_type";
	private static final String STATE_TIME_TYPE_POSITION = "state_time_type_position";
	private static final String STATE_PAYMENT_TYPE = "state_payment_type";
	private static final String STATE_PAYMENT_TYPE_POSITION = "state_payment_type_position";
	private static final String STATE_START_TIME = "state_start_time";
	private static final String STATE_END_TIME = "state_end_time";
	private static final String STATE_DURATION = "state_duration";
	private static final String STATE_DIALOG = "state_dialog";

	protected TextView tvEmployee;
	protected TextView tvTimeType;
	protected TextView tvPaymentType;
	protected TextView tvStartTime;
	protected TextView tvEndTime;
	protected TextView tvDuration;
	private CardView cvEmployee;
	private CardView cvTimeType;
	private CardView cvPaymentType;
	private CardView cvStartTime;
	private CardView cvEndTime;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	protected int employeeId;
	protected int timeTypePosition;
	protected int paymentTypePosition;
	protected String startTime;
	protected String endTime;
	protected String duration;
	private int dialog;

	protected boolean newReport;
	protected int timeReportDBID;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_time_report, container, false);

		cvEmployee = (CardView) view.findViewById(R.id.card_view_time_report_employee);
		cvTimeType = (CardView) view.findViewById(R.id.card_view_time_report_time_type);
		cvPaymentType = (CardView) view.findViewById(R.id.card_view_time_report_payment_type);
		cvStartTime = (CardView) view.findViewById(R.id.card_view_time_report_start_time);
		cvEndTime = (CardView) view.findViewById(R.id.card_view_time_report_end_time);
		tvEmployee = (TextView) view.findViewById(R.id.text_view_time_report_employee);
		tvTimeType = (TextView) view.findViewById(R.id.text_view_time_report_time_type);
		tvPaymentType = (TextView) view.findViewById(R.id.text_view_time_report_payment_type);
		tvStartTime = (TextView) view.findViewById(R.id.text_view_time_report_start_time);
		tvEndTime = (TextView) view.findViewById(R.id.text_view_time_report_end_time);
		tvDuration = (TextView) view.findViewById(R.id.text_view_time_report_duration);
		llButtons = (LinearLayout) view.findViewById(R.id.buttons);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvEmployee.setOnClickListener(this);
		cvTimeType.setOnClickListener(this);
		cvPaymentType.setOnClickListener(this);
		cvStartTime.setOnClickListener(this);
		cvEndTime.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState == null)
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
		else
		{
			employeeId = savedInstanceState.getInt(STATE_EMPLOYEE_POSITION);
			timeTypePosition = savedInstanceState.getInt(STATE_TIME_TYPE_POSITION);
			paymentTypePosition = savedInstanceState.getInt(STATE_PAYMENT_TYPE_POSITION);
			startTime = savedInstanceState.getString(STATE_START_TIME);
			endTime = savedInstanceState.getString(STATE_END_TIME);
			duration = savedInstanceState.getString(STATE_DURATION);

			dialog = savedInstanceState.getInt(STATE_DIALOG);
			tvEmployee.setText(savedInstanceState.getString(STATE_EMPLOYEE));
			tvTimeType.setText(savedInstanceState.getString(STATE_TIME_TYPE));
			tvPaymentType.setText(savedInstanceState.getString(STATE_PAYMENT_TYPE));

			Date startDate = Utilities.getDateFromString(startTime);
			String startTime = Utilities.getTime(startDate);
			tvStartTime.setText(startTime);

			Date endDate = Utilities.getDateFromString(endTime);
			String endTime = Utilities.getTime(endDate);
			tvEndTime.setText(endTime);

			String durationString = Utilities.getDurationString(duration);
			tvDuration.setText(durationString);
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
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

		outState.putString(STATE_EMPLOYEE, tvEmployee.getText().toString());
		outState.putString(STATE_TIME_TYPE, tvTimeType.getText().toString());
		outState.putString(STATE_PAYMENT_TYPE, tvPaymentType.getText().toString());
		outState.putString(STATE_START_TIME, startTime);
		outState.putString(STATE_END_TIME, endTime);
		outState.putString(STATE_DURATION, duration);
		outState.putInt(STATE_EMPLOYEE_POSITION, employeeId);
		outState.putInt(STATE_TIME_TYPE_POSITION, timeTypePosition);
		outState.putInt(STATE_PAYMENT_TYPE_POSITION, paymentTypePosition);
		outState.putInt(STATE_DIALOG, dialog);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newReport)
		{
			itemDelete.setVisible(false);
		}
		else
		{
			itemDelete.setVisible(true);
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.card_view_time_report_employee:
			{
				dialog = DIALOG_EMPLOYEE;
				startEmployeeSpinner();
				break;
			}
			case R.id.card_view_time_report_time_type:
			{
				dialog = DIALOG_TIME_TYPE;
				String title = getResources().getString(R.string.time_report_time_type);
				int resource = R.array.timetype;
				startSpinner(title, null, resource, timeTypePosition, false);
				break;
			}
			case R.id.card_view_time_report_payment_type:
			{
				dialog = DIALOG_PAYMENT_TYPE;
				String title = getResources().getString(R.string.time_report_payment_type);
				int resource = R.array.timepaymenttype;
				startSpinner(title, null, resource, paymentTypePosition, true);
				break;
			}
			case R.id.card_view_time_report_start_time:
			{
				dialog = DIALOG_START_TIME;
				startTimePicker();
				break;
			}
			case R.id.card_view_time_report_end_time:
			{
				dialog = DIALOG_END_TIME;
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
					getActivity().setResult(Activity.RESULT_OK);
					getActivity().finish();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}
	}

	private void startTimePicker()
	{
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();
		fragment.show(getChildFragmentManager(), FRAGMENT_TIME_PICKER);
	}

	@Override
	public void onEvent(TimePickerDialogSetEvent event)
	{
		int hourOfDay = event.getHourOfDay();
		int minute = event.getMinute();
		setTime(hourOfDay, minute);
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
			case DIALOG_START_TIME:
			{
				startTime = Utilities.exportDate(date);
				tvStartTime.setText(time);
				break;
			}
			case DIALOG_END_TIME:
			{
				endTime = Utilities.exportDate(date);
				tvEndTime.setText(time);
				break;
			}
		}

		duration = Utilities.getDurationAsString(startTime, endTime);

		if (duration != null)
		{
			String durationString = Utilities.getDurationString(duration);
			tvDuration.setText(durationString);
		}
	}

	@Override
	public void onEvent(TimePickerDialogResetEvent event)
	{
		resetTime();
	}

	private void resetTime()
	{
		switch (dialog)
		{
			case DIALOG_START_TIME:
			{
				startTime = null;
				duration = null;
				tvStartTime.setText("");
				tvDuration.setText("");
				break;
			}
			case DIALOG_END_TIME:
			{
				endTime = null;
				duration = null;
				tvEndTime.setText("");
				tvDuration.setText("");
				break;
			}
		}
	}

	private void startSpinner(String title, String subTitle, int resource, int position, boolean emptyItem)
	{
		SpinnerDialogFragment fragment = SpinnerDialogFragment.newInstance(title, subTitle, resource, position, emptyItem);
		fragment.show(getChildFragmentManager(), FRAGMENT_SPINNER);
	}

	private void startEmployeeSpinner()
	{
		try
		{
			ArrayList<Employee> employees = loadEmployees();
			EmployeeSpinnerDialogFragment fragment = EmployeeSpinnerDialogFragment.newInstance("123", employees);
			fragment.show(getChildFragmentManager(), FRAGMENT_EMPLOYEE_SPINNER);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onEvent(SpinnerDialogEvent event)
	{
		switch (dialog)
		{
			case DIALOG_TIME_TYPE:
			{
				String text = event.getText();
				timeTypePosition = event.getPosition();
				tvTimeType.setText(text);
				break;
			}
			case DIALOG_PAYMENT_TYPE:
			{
				String text = event.getText();
				paymentTypePosition = event.getPosition();
				tvPaymentType.setText(text);
				break;
			}
		}
	}

	@Override
	public void onEvent(EmployeeSpinnerDialogEvent event)
	{
		switch (dialog)
		{
			case DIALOG_EMPLOYEE:
			{
				String employee = event.getName() + " (" + event.getPersonnelNumber() + ")";
				tvEmployee.setText(employee);
				employeeId = event.getDbID();
				break;
			}
		}
	}

	/**
	 * Loads a TimeReport if its not a new TimeReport.
	 *
	 * @throws SQLException
	 */
	public void loadData() throws SQLException
	{
		if (newReport)
		{
			timeTypePosition = 0;
			paymentTypePosition = -1;

			String[] timeTypeStrings = getResources().getStringArray(R.array.timetype);
			tvTimeType.setText(timeTypeStrings[timeTypePosition]);
		}
		else
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			TimeReport timeReport = databaseAdapter.getTimeReport(timeReportDBID);

			if (timeReport.getEmployee() != null)
			{
				Employee employee = timeReport.getEmployee();
				StringBuilder stringBuilder = new StringBuilder();

				if (employee.getLastName() != null)
				{
					if (employee.getFirstName() != null)
					{
						stringBuilder.append(employee.getFirstName());
						stringBuilder.append(" ");
					}

					stringBuilder.append(employee.getLastName());
				}

				stringBuilder.append(" (");
				stringBuilder.append(employee.getId());
				stringBuilder.append(")");
				tvEmployee.setText(stringBuilder.toString());
			}

			Pair<String, Integer> timeTypePair = GSPUtilities.getTimeType(getActivity(), timeReport.getTimeType());

			if (timeTypePair != null)
			{
				tvTimeType.setText(timeTypePair.first);
				timeTypePosition = timeTypePair.second;
			}

			if (timeReport.getTimePaymentType() != null)
			{
				Pair<String, Integer> timePaymentTypePair = GSPUtilities.getTimePaymentType(getActivity(), timeReport.getTimePaymentType());

				if (timePaymentTypePair != null)
				{
					tvPaymentType.setText(timePaymentTypePair.first);
					paymentTypePosition = timePaymentTypePair.second;
				}
			}
			else
			{
				tvPaymentType.setText("");
			}

			if (timeReport.getStartTime() != null)
			{
				startTime = timeReport.getStartTime();
				Date date = Utilities.getDateFromString(startTime);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvStartTime.setText(time);
				}
			}
			else
			{
				tvStartTime.setText("");
			}

			if (timeReport.getEndTime() != null)
			{
				endTime = timeReport.getEndTime();
				Date date = Utilities.getDateFromString(endTime);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvEndTime.setText(time);
				}
			}
			else
			{
				tvEndTime.setText("");
			}

			if (timeReport.getDuration() != null)
			{
				duration = timeReport.getDuration();
				String durationString = Utilities.getDurationString(duration);
				tvDuration.setText(durationString);
			}
		}
	}

	/**
	 * Generates a TimeReport from the entered data.
	 *
	 * @return TimeReport
	 * @throws SQLException
	 */
	protected TimeReport generateTimeReport() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		TimeReport timeReport;

		if (newReport)
		{
			timeReport = new TimeReport();
		}
		else
		{
			timeReport = databaseAdapter.getTimeReport(timeReportDBID);
		}

		if (employeeId > 0)
		{
			Employee employee = databaseAdapter.getEmployee(employeeId);
			Employee employeeCopy = GSPUtilities.copyEmployee(employee);
			employeeCopy.setEmployees(null);
			timeReport.setEmployee(employeeCopy);
		}

		TimeType[] timeTypes = TimeType.values();
		timeReport.setTimeType(timeTypes[timeTypePosition].getValue());

		if (paymentTypePosition > -1)
		{
			TimePaymentType[] timePaymentTypes = TimePaymentType.values();
			timeReport.setTimePaymentType(timePaymentTypes[paymentTypePosition].getValue());
		}

		if (startTime != null)
		{
			timeReport.setStartTime(startTime);
		}

		if (endTime != null)
		{
			timeReport.setEndTime(endTime);
		}

		if (duration != null)
		{
			timeReport.setDuration(duration);
		}

		return timeReport;
	}

	@Override
	public abstract boolean onOptionsItemSelected(MenuItem item);

	public abstract void save() throws SQLException;

	public abstract ArrayList<Employee> loadEmployees() throws SQLException;
}
