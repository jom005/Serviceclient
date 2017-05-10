package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.types.EnergySystem;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Location;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.TownDetail;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for work orders RecyclerView.
 */
public class WorkOrdersAdapter extends RecyclerView.Adapter<WorkOrdersAdapter.WorkOrderViewHolder>
{
	private Context context;
	private OnItemClickListener onItemClickListener;
	private List<GlobalServiceProtocol> gspList;

	public WorkOrdersAdapter(Context context, List<GlobalServiceProtocol> gspList)
	{
		this.context = context;
		this.gspList = gspList;
	}

	@Override
	public WorkOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_work_order, parent, false);

		return new WorkOrderViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(WorkOrderViewHolder holder, int position)
	{
		GlobalServiceProtocol gsp = gspList.get(position);
		PowerPlant powerPlant = gsp.getPowerPlant();
		EnergySystem energySystem = gsp.getEnergySystem();
		WorkOrder workOrder = gsp.getWorkOrder();
		WorkReport workReport = gsp.getWorkReport();

		String dateStart = Utilities.importDate(workOrder.getScheduledWorkStart());
		String dateEnd = Utilities.importDate(workOrder.getScheduledWorkEnd());

		holder.tvOrderId.setText(workOrder.getId());
		holder.tvOrderName.setText(workOrder.getName());
		holder.tvOrderStart.setText(dateStart);
		holder.tvOrderEnd.setText(dateEnd);

		String town = null;
		Location address = powerPlant.getAddress();

		if (address != null)
		{
			StreetAddress streetAddress = address.getStreetAddress();

			if (streetAddress != null)
			{
				TownDetail townDetail = streetAddress.getTownDetail();

				if (townDetail != null)
				{
					town = townDetail.getName();
				}
			}
		}

		if (town != null)
		{
			holder.llTown.setVisibility(View.VISIBLE);
			holder.tvTown.setText(town);
		}
		else
		{
			holder.llTown.setVisibility(View.GONE);
		}

		holder.tvOrderType.setText(GSPUtilities.getActivityTypeString(context, gsp.getWorkOrder().getActivityType()));

		String weaType = energySystem.getType();

		if (weaType != null)
		{
			holder.llWeaType.setVisibility(View.VISIBLE);
			holder.tvWeaType.setText(weaType);
		}
		else
		{
			holder.llWeaType.setVisibility(View.GONE);
		}

		WorkStatusLogs workStatusLogs = null;
		WorkStatusLog workStatusLog = null;

		if (workReport != null)
		{
			workStatusLogs = workReport.getStatuses();
		}

		if (workStatusLogs == null)
		{
			workStatusLog = workOrder.getStatus();
		}
		else
		{
			if (workStatusLogs.getWorkStatusLogs().size() == 1)
			{
				ArrayList<WorkStatusLog> workStatusLogList = new ArrayList<>(workStatusLogs.getWorkStatusLogs());
				workStatusLog = workStatusLogList.get(0);
			}
			else if (workStatusLogs.getWorkStatusLogs().size() > 1)
			{
				workStatusLog = Collections.max(workStatusLogs.getWorkStatusLogs(), new WorkStatusLogComparator());
			}
		}

		if (workStatusLog != null)
		{
			String status = workStatusLog.getStatus();

			Pair<String, Integer> statusPair = GSPUtilities.getWorkStatus(context, status);

			if (statusPair != null)
			{
				status = statusPair.first;
			}

			holder.tvOrderStatus.setText(status);
		}

		String progress = gsp.getWorkOrder().getProgress();

		if (progress == null || progress.equals(WorkOrder.STATUS_OPEN))
		{
			GradientDrawable gdIndicatorBackground = (GradientDrawable) holder.vStatusIndicator.getBackground();
			gdIndicatorBackground.setColor(ContextCompat.getColor(context,R.color.status_open));
		}
		else if (progress.equals(WorkOrder.STATUS_IN_PROGRESS))
		{
			GradientDrawable gdIndicatorBackground = (GradientDrawable) holder.vStatusIndicator.getBackground();
			gdIndicatorBackground.setColor(ContextCompat.getColor(context,R.color.status_in_progress));
//					(context.getResources().getColor(R.color.status_in_progress));
		}
		else if (progress.equals(WorkOrder.STATUS_CLOSED))
		{
			GradientDrawable gdIndicatorBackground = (GradientDrawable) holder.vStatusIndicator.getBackground();
			gdIndicatorBackground.setColor(ContextCompat.getColor(context,R.color.status_finished));
		}
		else
		{
			throw new IllegalArgumentException("Wrong value for progress.");
		}
	}

	@Override
	public int getItemCount()
	{
		return gspList.size();
	}

	public GlobalServiceProtocol getItem(int position)
	{
		return gspList.get(position);
	}

	public List<GlobalServiceProtocol> getItems()
	{
		return gspList;
	}

	public void setList(List<GlobalServiceProtocol> gspList)
	{
		this.gspList = gspList;
	}

	/**
	 * Sets the custom on ItemClickListener for clicks on items.
	 *
	 * @param onItemClickListener onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.onItemClickListener = onItemClickListener;
	}

	public class WorkOrderViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{
		protected LinearLayout llTown;
		protected LinearLayout llWeaType;
		protected TextView tvOrderId;
		protected TextView tvOrderName;
		protected TextView tvOrderType;
		protected TextView tvOrderStatus;
		protected TextView tvOrderStart;
		protected TextView tvOrderEnd;
		protected TextView tvTown;
		protected TextView tvWeaType;
		protected View vStatusIndicator;

		public WorkOrderViewHolder(View itemView)
		{
			super(itemView);

			llTown = (LinearLayout) itemView.findViewById(R.id.linear_layout_town);
			llWeaType = (LinearLayout) itemView.findViewById(R.id.linear_layout_wea_type);
			tvOrderId = (TextView) itemView.findViewById(R.id.text_view_order_id);
			tvOrderName = (TextView) itemView.findViewById(R.id.text_view_order_name);
			tvOrderType = (TextView) itemView.findViewById(R.id.text_view_order_type);
			tvOrderStatus = (TextView) itemView.findViewById(R.id.text_view_order_status);
			tvOrderStart = (TextView) itemView.findViewById(R.id.text_view_order_scheduled_start);
			tvOrderEnd = (TextView) itemView.findViewById(R.id.text_view_order_scheduled_end);
			tvTown = (TextView) itemView.findViewById(R.id.text_view_order_town);
			tvWeaType = (TextView) itemView.findViewById(R.id.text_view_order_wea_type);
			vStatusIndicator = itemView.findViewById(R.id.view_status_indicator);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v)
		{
			if (onItemClickListener != null)
			{
				onItemClickListener.onItemClick(v, getAdapterPosition());
			}
		}
	}

	/**
	 * Interface for the OnItemClickListener.
	 */
	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
	}
}
