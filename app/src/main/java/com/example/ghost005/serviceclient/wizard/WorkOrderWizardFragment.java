package com.example.ghost005.serviceclient.wizard;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.fragments.work_order.EnvironmentalConditionsFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrderFinalFragment;
import com.example.ghost005.serviceclient.fragments.work_order.WorkOrderStaffFragment;
import com.example.ghost005.serviceclient.fragments.work_order.ZeusPart1AssessmentFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.WorkOrderItemsFragment;
import com.example.ghost005.serviceclient.model.enums.ActivityType;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import de.greenrobot.event.EventBus;

/**
 * Fragment hat displays the work order wizard.
 */
public class WorkOrderWizardFragment extends Fragment
		implements View.OnClickListener, ViewPager.OnPageChangeListener
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String STATE_PROGRESS = "progress";
	private static final String STATE_LAST_FRAGMENT = "state_last_fragment";

	private Button btnPrev;
	private Button btnNext;
	private WizardViewPager pager;
	private WorkOrderWizardFragmentPagerAdapter pagerAdapter;

	private String progress;
	private boolean lastFragment = false;

	private int gspDBID;

	public static WorkOrderWizardFragment newInstance(int gspDBID)
	{
		WorkOrderWizardFragment fragment = new WorkOrderWizardFragment();

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

		if (savedInstanceState == null)
		{
			try
			{
				setProgress(WorkOrder.STATUS_IN_PROGRESS);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			progress = savedInstanceState.getString(STATE_PROGRESS);
			lastFragment = savedInstanceState.getBoolean(STATE_LAST_FRAGMENT);
		}
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
		View view = inflater.inflate(R.layout.fragment_work_order_wizard, container, false);

		pager = (WizardViewPager) view.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(1);

		btnPrev = (Button) view.findViewById(R.id.button_prev);
		btnPrev.setOnClickListener(this);

		btnNext = (Button) view.findViewById(R.id.button_next);
		btnNext.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			Pair<List<Fragment>, List<String>> pagerFragments = setPagerFragments();

			pagerAdapter = new WorkOrderWizardFragmentPagerAdapter(getChildFragmentManager(), pagerFragments.first, pagerFragments.second);
			pager.setAdapter(pagerAdapter);
			pager.addOnPageChangeListener(this);

			if (savedInstanceState == null)
			{
				DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
				GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
				WorkOrder workOrder = globalServiceProtocol.getWorkOrder();

				if (workOrder.getPage() != null)
				{
					int page = getPage(workOrder.getPage().intValue());

					pager.setCurrentItem(page);
					onPageSelected(page);
				}
				else
				{
					onPageSelected(0);
				}
			}
			else
			{
				int page = pager.getCurrentItem();

				onPageSelected(page);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
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

		outState.putBoolean(STATE_LAST_FRAGMENT, lastFragment);
		outState.putString(STATE_PROGRESS, progress);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.button_prev:
			{
				int page = pager.getCurrentItem();

				if (page > 0)
				{
					pager.setCurrentItem(page - 1, false);
				}

				break;
			}
			case R.id.button_next:
			{
				int page = pager.getCurrentItem();

				EventBus.getDefault().post(new SaveEvent(page));

				if (page == pagerAdapter.getCount() - 1)
				{
					try
					{
						setProgress(WorkOrder.STATUS_CLOSED);
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}

					Activity activity = getActivity();

					if (activity instanceof OnWorkOrderFinishListener)
					{
						((OnWorkOrderFinishListener) activity).onFinish();
					}
					else
					{
						throw new UnsupportedOperationException(activity.getClass() +
								" must implement WorkOrderWizardFragment.OnWorkOrderFinishListener");
					}
				}

				break;
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		// Not used
	}

	@Override
	public void onPageSelected(int position)
	{
		getActivity().setTitle(pagerAdapter.getPageTitle(position));

		if (position == pagerAdapter.getCount() - 1)
		{
			btnNext.setText(getResources().getString(R.string.button_finish));
			btnPrev.setVisibility(View.INVISIBLE);
			lastFragment = true;
		}
		else
		{
			if (progress.equals(WorkOrder.STATUS_CLOSED) || lastFragment)
			{
				btnNext.setText(getResources().getString(R.string.button_ok));
				btnPrev.setVisibility(View.INVISIBLE);
			}
			else
			{
				btnNext.setText(getResources().getString(R.string.button_next));

				if (position > 0 && position != pagerAdapter.getCount() - 1)
				{
					if (btnPrev.getVisibility() == View.INVISIBLE)
					{
						btnPrev.setVisibility(View.VISIBLE);
					}
				}
			}
		}

		try
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);

			WorkOrder workOrder = globalServiceProtocol.getWorkOrder();

			if (workOrder.getPage() == null || workOrder.getPage().intValue() < position)
			{
				workOrder.setPage(BigInteger.valueOf(position));
				databaseAdapter.updateWorkOrder(workOrder);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// Not used
	}

	public void setPagerItem(int item)
	{
		pager.setCurrentItem(item, false);
	}

	private void setProgress(String status) throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = globalServiceProtocol.getWorkOrder();

		if (status.equals(WorkOrder.STATUS_CLOSED))
		{
			if (workOrder.getProgress() != null && !workOrder.getProgress().equals(WorkOrder.STATUS_CLOSED))
			{
				workOrder.setProgress(WorkOrder.STATUS_CLOSED);
				databaseAdapter.updateWorkOrder(workOrder);
			}
		}
		else if (status.equals(WorkOrder.STATUS_IN_PROGRESS))
		{
			if (workOrder.getProgress() == null
					|| (!workOrder.getProgress().equals(WorkOrder.STATUS_CLOSED)
					&& !workOrder.getProgress().equals(WorkOrder.STATUS_IN_PROGRESS)))
			{
				workOrder.setProgress(WorkOrder.STATUS_IN_PROGRESS);
				databaseAdapter.updateWorkOrder(workOrder);
			}
		}

		progress = workOrder.getProgress();
	}

	private Pair<List<Fragment>, List<String>> setPagerFragments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol globalServiceProtocol = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkOrder workOrder = globalServiceProtocol.getWorkOrder();
		String activityType = workOrder.getActivityType();

		List<Fragment> fragments = new ArrayList<>();
		List<String> titles = new ArrayList<>();

		// Employees
		fragments.add(WorkOrderStaffFragment.newInstance(gspDBID, fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_employees));

		// First Zeus Part 1 Assessment
		fragments.add(ZeusPart1AssessmentFragment.newInstance(ZeusPart1AssessmentFragment.FIRST_ASSESSMENT, gspDBID, fragments.size()));

		if (!activityType.equals(ActivityType.GSP_ACT_001.getValue()) && !activityType.equals(ActivityType.GSP_ACT_002))
		{
			titles.add(getResources().getString(R.string.title_fragment_zeus_part_1_preliminary));
		}
		else
		{
			titles.add(getResources().getString(R.string.title_fragment_zeus_part_1));
		}

		// Environmental Conditions
		if (!activityType.equals(ActivityType.GSP_ACT_001.getValue()))
		{
			fragments.add(EnvironmentalConditionsFragment.newInstance(gspDBID, fragments.size()));
			titles.add(getResources().getString(R.string.title_fragment_environment));
		}

		//WorkOrderItems
		fragments.add(WorkOrderItemsFragment.newInstance(gspDBID, fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_work_order_items));

		// Second Zeus Part 1 Assessment
		if (!activityType.equals(ActivityType.GSP_ACT_001.getValue()) && !activityType.equals(ActivityType.GSP_ACT_002))
		{
			fragments.add(ZeusPart1AssessmentFragment.newInstance(ZeusPart1AssessmentFragment.SECOND_ASSESSMENT, gspDBID, fragments.size()));
			titles.add(getResources().getString(R.string.title_fragment_zeus_part_1_final));
		}

		// Final Fragment
		fragments.add(WorkOrderFinalFragment.newInstance(gspDBID, new ArrayList<>(titles), fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_finish));

		return new Pair<>(fragments, titles);
	}

	private int getPage(int page)
	{
		if (page >= pagerAdapter.getCount())
		{
			return pagerAdapter.getCount() - 1;
		}

		return page;
	}

	public void onEvent(NextEvent event)
	{
		if (event.isNext())
		{
			int page = pager.getCurrentItem();

			if (page < pagerAdapter.getCount() - 1)
			{
				if (progress.equals(WorkOrder.STATUS_CLOSED) || lastFragment)
				{
					pager.setCurrentItem(pagerAdapter.getCount() - 1, false);
				}
				else
				{
					pager.setCurrentItem(page + 1, false);
				}
			}
		}
	}

	/**
	 * Interface which every Fragment used with the adapter must implement, so the data can be
	 * saved if the next button is pressed.
	 */
	public interface OnStatusSaveListener
	{
		boolean save() throws SQLException;
	}

	/**
	 * Interface which parent activity must implement to finish the wizard.
	 */
	public interface OnWorkOrderFinishListener
	{
		void onFinish();
	}
}
