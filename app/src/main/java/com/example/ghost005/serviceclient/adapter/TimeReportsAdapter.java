package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for the time reports RecyclerView.
 */
public class TimeReportsAdapter extends RecyclerView.Adapter<TimeReportsAdapter.TimeReportViewHolder>
{
	private Context context;
	private List<TimeReport> timeReports;
	private OnItemClickListener onItemClickListener;

	public TimeReportsAdapter(Context context, List<TimeReport> timeReports)
	{
		this.context = context;
		this.timeReports = timeReports;
	}

	@Override
	public TimeReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.card_view_time_report, parent, false);

		return new TimeReportViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(TimeReportViewHolder holder, int position)
	{
		TimeReport timeReport = timeReports.get(position);

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

			holder.tvEmployee.setVisibility(View.VISIBLE);
			holder.tvEmployee.setText(stringBuilder.toString());
		}
		else
		{
			holder.tvEmployee.setVisibility(View.GONE);
		}

		Pair<String, Integer> timeTypePair = GSPUtilities.getTimeType(context, timeReport.getTimeType());

		if (timeTypePair != null)
		{
			holder.tvTimeType.setText(timeTypePair.first);
		}
		else
		{
			holder.tvTimeType.setText("");
		}

		if (timeReport.getTimePaymentType() != null)
		{
			Pair<String, Integer> timePaymentPair = GSPUtilities.getTimePaymentType(context, timeReport.getTimePaymentType());

			if (timePaymentPair != null)
			{
				holder.tvPaymentType.setVisibility(View.VISIBLE);
				holder.tvPaymentType.setText(timePaymentPair.first);
			}
		}
		else
		{
			holder.tvPaymentType.setVisibility(View.GONE);
		}

		if (timeReport.getStartTime() != null)
		{
			Date date = Utilities.getDateFromString(timeReport.getStartTime());
			String start = Utilities.getTime(date);
			holder.tvStart.setText(start);
		}
		else
		{
			holder.tvStart.setText(context.getResources().getString(R.string.placeholder_not_set));
		}

		if (timeReport.getEndTime() != null)
		{
			Date date = Utilities.getDateFromString(timeReport.getEndTime());
			String end = Utilities.getTime(date);
			holder.tvEnd.setText(end);
		}
		else
		{
			holder.tvEnd.setText(context.getResources().getString(R.string.placeholder_not_set));
		}

		if (timeReport.getDuration() != null)
		{
			String durationString = Utilities.getDurationString(timeReport.getDuration());
			holder.tvDuration.setText(durationString);
		}
		else
		{
			holder.tvDuration.setText("");
		}
	}

	@Override
	public int getItemCount()
	{
		return timeReports.size();
	}

	public TimeReport getItem(int position)
	{
		return timeReports.get(position);
	}

	public void setTimeReports(List<TimeReport> timeReports)
	{
		this.timeReports = timeReports;
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

	public class TimeReportViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{
		protected TextView tvEmployee;
		protected TextView tvTimeType;
		protected TextView tvPaymentType;
		protected TextView tvDuration;
		protected TextView tvStart;
		protected TextView tvEnd;

		public TimeReportViewHolder(View itemView)
		{
			super(itemView);

			tvEmployee = (TextView) itemView.findViewById(R.id.text_view_employee);
			tvTimeType = (TextView) itemView.findViewById(R.id.text_view_time_type);
			tvPaymentType = (TextView) itemView.findViewById(R.id.text_view_time_payment_type);
			tvDuration = (TextView) itemView.findViewById(R.id.text_view_time_duration);
			tvStart = (TextView) itemView.findViewById(R.id.text_view_time_start_time);
			tvEnd = (TextView) itemView.findViewById(R.id.text_view_time_end_time);

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
