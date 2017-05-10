package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * Adapter for the RecyclerView in the ExportFragment.
 */
public class ExportAdapter extends RecyclerView.Adapter<ExportAdapter.ExportViewHolder>
{
	private Context context;
	private ArrayList<GlobalServiceProtocol> gsps;
	private ArrayList<Boolean> isChecked;
	private OnItemClickListener onItemClickListener;

	public ExportAdapter(Context context, ArrayList<GlobalServiceProtocol> gsps)
	{
		this.context = context;
		this.gsps = gsps;

		isChecked = new ArrayList<>();

		for (int i = 0; i < gsps.size(); i++)
		{
			isChecked.add(Boolean.FALSE);
		}
	}

	@Override
	public ExportViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_export, parent, false);

		return new ExportViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ExportViewHolder holder, int position)
	{

		GlobalServiceProtocol gsp = gsps.get(position);
		WorkOrder workOrder = gsp.getWorkOrder();
		WorkReport workReport = gsp.getWorkReport();

		holder.tvOrderId.setText(workOrder.getId());
		holder.tvOrderName.setText(workOrder.getName());
		holder.tvOrderType.setText(GSPUtilities.getActivityTypeString(context, gsp.getWorkOrder().getActivityType()));
		holder.chbIsSelected.setChecked(isChecked.get(position));

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
		return gsps.size();
	}

	public void setGsps(ArrayList<GlobalServiceProtocol> gsps)
	{
		this.gsps = gsps;

		isChecked = new ArrayList<>();

		for (int i = 0; i < gsps.size(); i++)
		{
			isChecked.add(Boolean.FALSE);
		}
	}

	public void setGsps(ArrayList<GlobalServiceProtocol> gsps, ArrayList<Boolean> isChecked)
	{
		this.gsps = gsps;
		this.isChecked = isChecked;
	}

	public ArrayList<GlobalServiceProtocol> getGsps()
	{
		return gsps;
	}

	public ArrayList<Boolean> getIsChecked()
	{
		return isChecked;
	}

	public void setChecked(int position, CheckBox checkBox)
	{
		checkBox.setChecked(!checkBox.isChecked());
		isChecked.set(position, checkBox.isChecked());
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

	public class ExportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected CheckBox chbIsSelected;
		protected TextView tvOrderId;
		protected TextView tvOrderName;
		protected TextView tvOrderType;
		protected TextView tvOrderStatus;
		protected View vStatusIndicator;

		public ExportViewHolder(View itemView)
		{
			super(itemView);

			chbIsSelected = (CheckBox) itemView.findViewById(R.id.check_box_export);
			tvOrderId = (TextView) itemView.findViewById(R.id.text_view_order_id);
			tvOrderName = (TextView) itemView.findViewById(R.id.text_view_order_name);
			tvOrderType = (TextView) itemView.findViewById(R.id.text_view_order_type);
			tvOrderStatus = (TextView) itemView.findViewById(R.id.text_view_order_status);
			vStatusIndicator = itemView.findViewById(R.id.view_status_indicator);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view)
		{
			if (onItemClickListener != null)
			{
				onItemClickListener.onItemClick(view, getAdapterPosition());
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
