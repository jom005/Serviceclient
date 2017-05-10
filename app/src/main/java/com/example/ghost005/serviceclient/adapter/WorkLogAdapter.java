package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.Organisation;
import com.example.ghost005.serviceclient.model.types.WorkLogEntry;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for history RecyclerView.
 */
public class WorkLogAdapter extends RecyclerView.Adapter<WorkLogAdapter.HistoryViewHolder>
{
	private Context context;
	private OnItemClickListener onItemClickListener;
	private List<WorkLogEntry> workLogEntries;

	public WorkLogAdapter(Context context, List<WorkLogEntry> workLogEntries)
	{
		this.context = context;
		this.workLogEntries = workLogEntries;
	}

	@Override
	public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.card_view_work_log_entry, parent, false);

		return new HistoryViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(HistoryViewHolder holder, int position)
	{
		WorkLogEntry workLogEntry = workLogEntries.get(position);

		String activityType = GSPUtilities.getActivityTypeString(context, workLogEntry.getActivityType());
		String date = Utilities.importDate(workLogEntries.get(position).getTimestamp());
		String description = workLogEntry.getLongDescription();

		holder.tvWorkLogEntryActivityType.setText(activityType);
		holder.tvWorkLogEntryDate.setText(date);
		holder.tvWorkLogEntryLongDescription.setText(description);

		StringBuilder stringBuilder = new StringBuilder();

		if (workLogEntry.getResponsibleEmployee() != null)
		{
			Employee employee = workLogEntry.getResponsibleEmployee();

			if (employee.getLastName() != null)
			{
				if (employee.getFirstName() != null)
				{
					stringBuilder.append(employee.getFirstName());
					stringBuilder.append(" ");
				}

				stringBuilder.append(employee.getLastName());
			}
		}

		if (stringBuilder.length() > 0)
		{
			holder.llWorkLogEntryEmployee.setVisibility(View.VISIBLE);
			holder.tvWorkLogEntryEmployee.setText(stringBuilder.toString());
		}
		else
		{
			holder.llWorkLogEntryEmployee.setVisibility(View.GONE);
		}

		if (workLogEntry.getCompany() != null)
		{
			Organisation organisation = workLogEntry.getCompany();

			holder.llWorkLogEntryCompany.setVisibility(View.VISIBLE);
			holder.tvWorkLogEntryCompany.setText(organisation.getName());
		}
		else
		{
			holder.llWorkLogEntryCompany.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return workLogEntries.size();
	}

	public WorkLogEntry getWorkLogEntry(int position)
	{
		return workLogEntries.get(position);
	}

	public List<WorkLogEntry> getWorkLogEntries()
	{
		return workLogEntries;
	}

	public void setWorkLogEntries(List<WorkLogEntry> workLogEntries)
	{
		this.workLogEntries = workLogEntries;
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

	public class HistoryViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{
		protected LinearLayout llWorkLogEntryCompany;
		protected LinearLayout llWorkLogEntryEmployee;
		protected TextView tvWorkLogEntryActivityType;
		protected TextView tvWorkLogEntryCompany;
		protected TextView tvWorkLogEntryDate;
		protected TextView tvWorkLogEntryEmployee;
		protected TextView tvWorkLogEntryLongDescription;

		public HistoryViewHolder(View itemView)
		{
			super(itemView);

			llWorkLogEntryCompany = (LinearLayout) itemView.findViewById(R.id.linear_layout_work_log_entry_company);
			llWorkLogEntryEmployee = (LinearLayout) itemView.findViewById(R.id.linear_layout_work_log_employee);

			tvWorkLogEntryActivityType = (TextView) itemView.findViewById(R.id.text_view_work_log_entry_activity_type);
			tvWorkLogEntryCompany = (TextView) itemView.findViewById(R.id.text_view_work_log_entry_company);
			tvWorkLogEntryDate = (TextView) itemView.findViewById(R.id.text_view_work_log_entry_date);
			tvWorkLogEntryEmployee = (TextView) itemView.findViewById(R.id.text_view_work_log_entry_employee);
			tvWorkLogEntryLongDescription = (TextView) itemView.findViewById(R.id.text_view_work_log_entry_long_description);

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
