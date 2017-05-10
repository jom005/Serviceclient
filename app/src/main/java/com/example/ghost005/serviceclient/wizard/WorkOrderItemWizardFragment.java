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
import com.example.ghost005.serviceclient.fragments.work_order_item.TasksFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.WorkOrderItemFinalFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.WorkOrderItemStaffFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.WorkOrderItemWorkFragment;
import com.example.ghost005.serviceclient.fragments.work_order_item.ZeusPart2AssessmentFragment;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import de.greenrobot.event.EventBus;

/**
 * Fragment that displays the work order item wizard.
 */
public class WorkOrderItemWizardFragment extends Fragment
		implements View.OnClickListener, ViewPager.OnPageChangeListener
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String BUNDLE_WORK_ORDER_ITEM_DB_ID = "bundle_work_order_item_db_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DB_ID = "bundle_work_report_item_db_id";
	private static final String STATE_PROGRESS = "state_progress";
	private static final String STATE_LAST_FRAGMENT = "state_last_fragment";

	private Button btnPrev;
	private Button btnNext;
	private WizardViewPager pager;
	private WorkOrderWizardFragmentPagerAdapter pagerAdapter;

	private String progress;
	private boolean lastFragment = false;
	private int gspDBID;
	private int workOrderItemDBID;
	private int workReportItemDBID;

	public static WorkOrderItemWizardFragment newInstance(int gspDBID, int workOrderItemDBID, int workReportItemDBID)
	{
		WorkOrderItemWizardFragment fragment = new WorkOrderItemWizardFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DB_ID, workOrderItemDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DB_ID, workReportItemDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		workOrderItemDBID = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DB_ID);
		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DB_ID);

		if (savedInstanceState == null)
		{
			try
			{
				setProgress(WorkOrderItem.STATUS_IN_PROGRESS);
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
		View view = inflater.inflate(R.layout.fragment_work_order_item_wizard, container, false);

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
			Pair<List<Fragment>, List<String>> fragments = setPagerFragments();

			pagerAdapter = new WorkOrderWizardFragmentPagerAdapter(getChildFragmentManager(), fragments.first, fragments.second);
			pager.setAdapter(pagerAdapter);
			pager.addOnPageChangeListener(this);

			if (savedInstanceState == null)
			{
				DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
				WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);

				if (workOrderItem.getPage() != null)
				{
					int page = getPage(workOrderItem.getPage().intValue());

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
						setProgress(WorkOrderItem.STATUS_CLOSED);
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}

					Activity activity = getActivity();

					if (activity instanceof OnWorkOrderItemFinishListener)
					{
						((OnWorkOrderItemFinishListener) activity).onFinish();
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
			if (progress.equals(WorkOrderItem.STATUS_CLOSED) || lastFragment)
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
			WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);

			if (workOrderItem.getPage() == null || workOrderItem.getPage().intValue() < position)
			{
				workOrderItem.setPage(BigInteger.valueOf(position));
				databaseAdapter.updateWorkOrderItem(workOrderItem);
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
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);

		if (status.equals(WorkOrderItem.STATUS_CLOSED))
		{
			if (workOrderItem.getProgress() != null && !workOrderItem.getProgress().equals(WorkOrderItem.STATUS_CLOSED))
			{
				workOrderItem.setProgress(WorkOrderItem.STATUS_CLOSED);
				databaseAdapter.updateWorkOrderItem(workOrderItem);
			}
		}
		else if (status.equals(WorkOrderItem.STATUS_IN_PROGRESS))
		{
			if (workOrderItem.getProgress() == null
					|| (!workOrderItem.getProgress().equals(WorkOrderItem.STATUS_CLOSED)
					&& !workOrderItem.getProgress().equals(WorkOrderItem.STATUS_IN_PROGRESS)))
			{
				workOrderItem.setProgress(WorkOrderItem.STATUS_IN_PROGRESS);
				databaseAdapter.updateWorkOrderItem(workOrderItem);
			}
		}

		progress = workOrderItem.getProgress();
	}

	private Pair<List<Fragment>, List<String>> setPagerFragments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderItemDBID);

		List<Fragment> fragments = new ArrayList<>();
		List<String> titles = new ArrayList<>();

		// Employees
		fragments.add(WorkOrderItemStaffFragment.newInstance(gspDBID, workOrderItemDBID, workReportItemDBID, fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_employees));

		// Zeus Part 2 Assessment
		fragments.add(ZeusPart2AssessmentFragment.newInstance(workOrderItemDBID, workReportItemDBID, fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_zeus_part_2));

		// Work Fragment (Equipments, WorkEquipments, Materials, Measurements
		fragments.add(WorkOrderItemWorkFragment.newInstance(workReportItemDBID, fragments.size()));
		titles.add(getResources().getString(R.string.title_fragment_work));

		// Tasks
		if (workOrderItem.getTasks() != null)
		{
			fragments.add(TasksFragment.newInstance(gspDBID, workOrderItemDBID, workReportItemDBID, fragments.size()));
			titles.add(getResources().getString(R.string.title_fragment_tasks));
		}

		// Final Fragment
		fragments.add(WorkOrderItemFinalFragment.newInstance(workOrderItemDBID, workReportItemDBID, new ArrayList<>(titles), fragments.size()));
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
	 * Interface, which every Fragment used with the adapter must implement, so the data can be
	 * saved if the next button is pressed.
	 */
	public interface OnStatusSaveListener
	{
		boolean save() throws SQLException;
	}

	/**
	 * Interface which parent activity must implement to finish the wizard.
	 */
	public interface OnWorkOrderItemFinishListener
	{
		void onFinish();
	}
}
