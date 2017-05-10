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
import com.example.ghost005.serviceclient.model.types.WorkEquipment;

/**
 * Adapter for the work equipment RecyclerView.
 */
public class WorkEquipmentsAdapter extends RecyclerView.Adapter<WorkEquipmentsAdapter.WorkEquipmentViewHolder>
{
	private Context context;
	private List<WorkEquipment> workEquipments;
	private OnItemClickListener onItemClickListener;

	public WorkEquipmentsAdapter(Context context, List<WorkEquipment> workEquipments)
	{
		this.context = context;
		this.workEquipments = workEquipments;
	}

	@Override
	public WorkEquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_work_equipment, parent, false);

		return new WorkEquipmentViewHolder(itemView);	}

	@Override
	public void onBindViewHolder(WorkEquipmentViewHolder holder, int position)
	{
		WorkEquipment workEquipment = workEquipments.get(position);

		holder.tvName.setText(workEquipment.getName());

		if (workEquipment.getSerialNumber() != null)
		{
			holder.tvSerialNumber.setVisibility(View.VISIBLE);
			holder.tvSerialNumber.setText(workEquipment.getSerialNumber());
		}
		else
		{
			holder.tvSerialNumber.setVisibility(View.GONE);
		}

		if (workEquipment.getType() != null)
		{
			holder.llType.setVisibility(View.VISIBLE);
			holder.tvType.setText(workEquipment.getType());
		}
		else
		{
			holder.llType.setVisibility(View.GONE);
		}

		if (workEquipment.getSeries() != null)
		{
			holder.llSeries.setVisibility(View.VISIBLE);
			holder.tvSeries.setText(workEquipment.getSeries());
		}
		else
		{
			holder.llSeries.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return workEquipments.size();
	}

	public WorkEquipment getItem(int position)
	{
		return workEquipments.get(position);
	}

	public List<WorkEquipment> getWorkEquipments()
	{
		return workEquipments;
	}

	public void setWorkEquipments(List<WorkEquipment> workEquipments)
	{
		this.workEquipments = workEquipments;
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

	public class WorkEquipmentViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
	{
		protected LinearLayout llType;
		protected LinearLayout llSeries;
		protected TextView tvName;
		protected TextView tvSerialNumber;
		protected TextView tvType;
		protected TextView tvSeries;

		public WorkEquipmentViewHolder(View itemView)
		{
			super(itemView);

			llType = (LinearLayout) itemView.findViewById(R.id.linear_layout_type);
			llSeries = (LinearLayout) itemView.findViewById(R.id.linear_layout_series);
			tvName = (TextView) itemView.findViewById(R.id.text_view_work_equipment_name);
			tvSerialNumber = (TextView) itemView.findViewById(R.id.text_view_work_equipment_serial_number);
			tvType = (TextView) itemView.findViewById(R.id.text_view_work_equipment_type);
			tvSeries = (TextView) itemView.findViewById(R.id.text_view_work_equipment_series);

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
