package com.example.ghost005.serviceclient.fragments.base;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.StaffAdapter;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.wizard.WorkOrderItemWizardFragment;
import com.example.ghost005.serviceclient.wizard.WorkOrderWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * Fragment for displaying the responsible employee and the staff.
 */
public abstract class StaffFragment extends Fragment
		implements WorkOrderWizardFragment.OnStatusSaveListener, StaffAdapter.OnItemClickListener,
		WorkOrderItemWizardFragment.OnStatusSaveListener
{
	protected static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	protected static final String BUNDLE_POSITION = "bundle_position";

	protected CardView cvResponsibleEmployee;
	protected CardView cvResponsibleEmployeeHeader;
	protected LinearLayout llMissingSkills;
	protected TextView tvName;
	protected TextView tvId;

	private RecyclerView rvStaff;
	private RecyclerView.LayoutManager layoutManager;
	protected StaffAdapter adapter;

	protected int gspDBID;
	protected int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_staff, container, false);

		cvResponsibleEmployee = (CardView) view.findViewById(R.id.card_view_responsible_employee);
		cvResponsibleEmployeeHeader = (CardView) view.findViewById(R.id.card_view_responsible_employee_header);
		llMissingSkills = (LinearLayout) view.findViewById(R.id.linear_layout_missing_skills);
		tvName = (TextView) cvResponsibleEmployee.findViewById(R.id.text_view_name);
		tvId = (TextView) cvResponsibleEmployee.findViewById(R.id.text_view_item);
		CheckBox cvEmployee = (CheckBox) cvResponsibleEmployee.findViewById(R.id.check_box_employee);
		cvEmployee.setVisibility(View.GONE);

		rvStaff = (RecyclerView) view.findViewById(R.id.recycler_view_staff);
		rvStaff.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvStaff.setLayoutManager(layoutManager);
		adapter = new StaffAdapter(getActivity(), new ArrayList<Employee>(), null);
		adapter.setOnItemClickListener(this);
		rvStaff.setAdapter(adapter);

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
	public void onItemClick(View view, int position)
	{
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box_employee);
		adapter.setChecked(position, checkBox);
	}

	public void onEvent(SaveEvent event)
	{
		int position = getArguments().getInt(BUNDLE_POSITION);

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

	public abstract void loadData() throws SQLException;

	@Override
	public abstract boolean save() throws SQLException;
}
