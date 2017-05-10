package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.types.RDSPPElement;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * Adapter for work order items RecyclerView.
 */
public class WorkOrderItemsAdapter
		extends RecyclerView.Adapter<WorkOrderItemsAdapter.WorkOrderItemsItemViewHolder>
{
	private Context context;
	private OnItemClickListener onItemClickListener;
	private List<WorkOrderItem> workOrderItems;
	private List<WorkReportItem> workReportItems;

	public WorkOrderItemsAdapter(Context context, List<WorkOrderItem> workOrderItems, List<WorkReportItem> workReportItems)
	{
		this.context = context;
		this.workOrderItems = workOrderItems;
		this.workReportItems = workReportItems;
	}

	@Override
	public WorkOrderItemsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_work_order_item, parent, false);

		return new WorkOrderItemsItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(WorkOrderItemsItemViewHolder holder, int position)
	{
		WorkOrderItem workOrderItem = workOrderItems.get(position);
		RDSPPElement rdsppElement = workOrderItem.getAssignedElement().getRdsPPDesignation();

		holder.tvOrderItemId.setText(workOrderItem.getId());
		holder.tvOrderItemSystem.setText(workOrderItem.getAssignedElement().getName());
		holder.tvOrderItemDescription.setText(workOrderItem.getName());
		holder.tvOrderItemElementName.setText(rdsppElement.getElementName());
		holder.tvOrderItemElementRDSPP.setText(rdsppElement.getRdsppElementDesignation());

		WorkReportItem workReportItem = null;
		String status = null;

		for (int i = 0; i < workReportItems.size(); i++)
		{
			if (workOrderItem.getId().equals(workReportItems.get(i).getOrderItemId()))
			{
				workReportItem = workReportItems.get(i);
				break;
			}
		}

		WorkStatusLogs workStatusLogs;

		if (workReportItem != null)
		{
			workStatusLogs = workReportItem.getStatuses();
		}
		else
		{
			workStatusLogs = workOrderItem.getStatuses();
		}

		if (workStatusLogs.getWorkStatusLogs().size() == 1)
		{
			ArrayList<WorkStatusLog> workStatusLogList = new ArrayList<>(workStatusLogs.getWorkStatusLogs());
			status = workStatusLogList.get(0).getStatus();
		}
		else if (workStatusLogs.getWorkStatusLogs().size() > 1)
		{
			WorkStatusLog workStatusLog = Collections.max(workStatusLogs.getWorkStatusLogs(), new WorkStatusLogComparator());
			status = workStatusLog.getStatus();
		}

		Pair<String, Integer> statusPair = GSPUtilities.getWorkStatus(context, status);

		if (statusPair != null)
		{
			status = statusPair.first;
		}

		if (status != null)
		{
			holder.tvOrderItemStatus.setText(status);
		}

		String progress = workOrderItem.getProgress();

		if (progress == null || progress.equals(WorkOrderItem.STATUS_OPEN))
		{
			GradientDrawable gdIndicatorBackground = (GradientDrawable) holder.vStatusIndicator.getBackground();
			gdIndicatorBackground.setColor(ContextCompat.getColor(context,R.color.status_open));
		}
		else if (progress.equals(WorkOrderItem.STATUS_IN_PROGRESS))
		{
			GradientDrawable gdIndicatorBackground = (GradientDrawable) holder.vStatusIndicator.getBackground();
			gdIndicatorBackground.setColor(ContextCompat.getColor(context,R.color.status_in_progress));
		}
		else if (progress.equals(WorkOrderItem.STATUS_CLOSED))
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
		return workOrderItems.size();
	}

	public WorkOrderItem getItem(int position)
	{
		return workOrderItems.get(position);
	}

	public List<WorkOrderItem> getWorkOrderItems()
	{
		return workOrderItems;
	}

	public void setWorkOrderItems(List<WorkOrderItem> workOrderItems)
	{
		this.workOrderItems = workOrderItems;
	}

	public List<WorkReportItem> getWorkReportItems()
	{
		return workReportItems;
	}

	public void setWorkReportItems(List<WorkReportItem> workReportItems)
	{
		this.workReportItems = workReportItems;
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

	public class WorkOrderItemsItemViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{
		protected TextView tvOrderItemId;
		protected TextView tvOrderItemSystem;
		protected TextView tvOrderItemDescription;
		protected TextView tvOrderItemStatus;
		protected TextView tvOrderItemElementName;
		protected TextView tvOrderItemElementRDSPP;
		protected View vStatusIndicator;

		public WorkOrderItemsItemViewHolder(View itemView)
		{
			super(itemView);

			tvOrderItemId = (TextView) itemView.findViewById(R.id.text_view_order_item_id);
			tvOrderItemSystem = (TextView) itemView.findViewById(R.id.text_view_order_item_system);
			tvOrderItemDescription = (TextView) itemView.findViewById(
					R.id.text_view_order_item_name);
			tvOrderItemStatus = (TextView) itemView.findViewById(R.id.text_view_order_item_status);
			tvOrderItemElementName = (TextView) itemView.findViewById(
					R.id.text_view_order_item_element_name);
			tvOrderItemElementRDSPP = (TextView) itemView.findViewById(
					R.id.text_view_order_item_element_rdspp);
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
