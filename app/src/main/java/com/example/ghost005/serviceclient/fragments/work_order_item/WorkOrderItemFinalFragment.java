package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.SpinnerDialogFragment;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.enums.Gender;
import com.example.ghost005.serviceclient.model.enums.WorkStatus;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import com.example.ghost005.serviceclient.wizard.WorkOrderItemWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkOrderItemFinalFragment extends Fragment
		implements View.OnClickListener, WorkOrderItemWizardFragment.OnStatusSaveListener,
		SpinnerDialogFragment.SpinnerDialogEventListener
{
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_TITLES = "bundle_titles";
	private static final String BUNDLE_WORK_ORDER_ITEM_DB_ID = "work_order_item_db_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "work_report_item_db_id";
	private static final String STATE_STATUS = "state_status";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String DIALOG_STATUS = "dialog_status";
	private static final String FRAGMENT_SPINNER = "fragment_spinner";

	private CardView cvFinalStatus;
	private LinearLayout llButtons;
	private TextView tvFinalStatus;

	private int statusPosition;
	private String dialog;

	private int workOrderItemDBID;
	private int workReportItemDBIB;
	private ArrayList<String> titles;
	private int position;

	public static WorkOrderItemFinalFragment newInstance(int workOrderItemDBID, int workReportItemDBIB, ArrayList<String> titles, int position)
	{
		WorkOrderItemFinalFragment fragment = new WorkOrderItemFinalFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DB_ID, workOrderItemDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBIB);
		args.putStringArrayList(BUNDLE_TITLES, titles);
		args.putInt(BUNDLE_POSITION, position);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		workOrderItemDBID = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DB_ID);
		workReportItemDBIB = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);
		titles = getArguments().getStringArrayList(BUNDLE_TITLES);
		position = getArguments().getInt(BUNDLE_POSITION);

		if (savedInstanceState != null)
		{
			dialog = savedInstanceState.getString(STATE_DIALOG);
			statusPosition = savedInstanceState.getInt(STATE_STATUS);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_work_order_item_final, container, false);

		cvFinalStatus = (CardView) view.findViewById(R.id.card_view_final_status);
		llButtons = (LinearLayout) view.findViewById(R.id.linear_layout_buttons);
		tvFinalStatus = (TextView) view.findViewById(R.id.text_view_final_status);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvFinalStatus.setOnClickListener(this);

		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				getResources().getDimension(R.dimen.button_padding_final),
				getResources().getDisplayMetrics());

		List<Button> buttons = new ArrayList<>();

		for (int i = 0; i < titles.size(); i++)
		{
			Button button = new Button(getActivity());
			button.setPadding(padding, padding, padding, padding);
			button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getResources().getInteger(R.integer.text_size_large_value));
			button.setTransformationMethod(null);
			button.setText(titles.get(i));
			button.setId(i);
			button.setOnClickListener(this);

			buttons.add(button);

			llButtons.addView(button);
		}

		String[] workStatus = getResources().getStringArray(R.array.workstatus);
		tvFinalStatus.setText(workStatus[statusPosition]);
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

		outState.putString(STATE_DIALOG, dialog);
		outState.putInt(STATE_STATUS, statusPosition);
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.card_view_final_status)
		{
			dialog = DIALOG_STATUS;
			String title = getResources().getString(R.string.final_status);
			int resource = R.array.workstatus;

			startSpinner(title, null, resource, statusPosition, false);
		}
		else
		{
			Fragment fragment = getParentFragment();

			if (fragment != null && fragment instanceof WorkOrderItemWizardFragment)
			{
				((WorkOrderItemWizardFragment) fragment).onPageSelected(v.getId());
				((WorkOrderItemWizardFragment) fragment).setPagerItem(v.getId());
			}
		}
	}

	@Override
	public boolean save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBIB);

		WorkStatus[] workStatuses = WorkStatus.values();
		Gender[] genders = Gender.values();
		WorkStatusLog workStatusLog = new WorkStatusLog();

		workStatusLog.setTimestamp(Utilities.exportDate(new Date()));
		workStatusLog.setStatus(workStatuses[statusPosition].getValue());

		Employee employee = new Employee();
		employee.setId("123"); // TODO: Temp employee
		employee.setGender(genders[0].getValue());
		workStatusLog.setResponsibleEmployee(employee);

		if (workReportItem.getStatuses() != null)
		{
			WorkStatusLogs workStatusLogs = workReportItem.getStatuses();
			workStatusLog.setWorkStatusLogs(workStatusLogs);

			databaseAdapter.createOrUpdateEmployee(employee);
			databaseAdapter.createOrUpdateWorkStatusLog(workStatusLog);
		}
		else
		{
			WorkStatusLogs workStatusLogs = new WorkStatusLogs();
			ArrayList<WorkStatusLog> workStatusLogList = new ArrayList<>();
			workStatusLogs.setWorkStatusLogs(workStatusLogList);
			workStatusLog.setWorkStatusLogs(workStatusLogs);
			workReportItem.setStatuses(workStatusLogs);

			databaseAdapter.createOrUpdateEmployee(employee);
			databaseAdapter.createOrUpdateWorkStatusLog(workStatusLog);
			databaseAdapter.createOrUpdateWorkStatusLogs(workStatusLogs);
			databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
		}

		return true;
	}

	public void setSpinner(String text, int position)
	{
		if (dialog.equals(DIALOG_STATUS))
		{
			statusPosition = position;
			tvFinalStatus.setText(text);
		}
	}

	private void startSpinner(String title, String subTitle, int resource, int position, boolean emptyItem)
	{
		SpinnerDialogFragment fragment = SpinnerDialogFragment.newInstance(title, subTitle, resource, position, emptyItem);
		fragment.show(getChildFragmentManager(), FRAGMENT_SPINNER);
	}

	private void loadData() throws SQLException
	{

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBIB);

		WorkStatusLogs workStatusLogs = workReportItem.getStatuses();

		if (workStatusLogs == null)
		{
			workStatusLogs = workOrderItem.getStatuses();
		}

		WorkStatusLog workStatusLog = null;

		if (workStatusLogs != null)
		{
			List<WorkStatusLog> workStatusLogList = new ArrayList<>(workStatusLogs.getWorkStatusLogs());

			if (workStatusLogList.size() > 0)
			{
				if (workStatusLogList.size() == 1)
				{
					workStatusLog = workStatusLogList.get(0);
				}
				else if (workStatusLogList.size() > 1)
				{
					workStatusLog = Collections.max(workStatusLogList, new WorkStatusLogComparator());
				}
			}
		}

		if (workStatusLog != null)
		{
			Pair<String, Integer> workStatusPair = GSPUtilities.getWorkStatus(getActivity(), workStatusLog.getStatus());

			if (workStatusPair != null)
			{
				statusPosition = workStatusPair.second;
			}
		}
		else
		{
			statusPosition = 0;
		}
	}

	public void onEvent(SaveEvent event)
	{
		if (event.getPosition() == position)
		{
			try
			{
				if (save())
				{
					EventBus.getDefault().post(new NextEvent(true));
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onEvent(SpinnerDialogEvent event)
	{
		String text = event.getText();
		int position = event.getPosition();

		setSpinner(text, position);
	}
}
