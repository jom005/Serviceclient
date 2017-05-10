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
import com.example.ghost005.serviceclient.model.types.Equipment;

/**
 * Adapter for the equipment RecyclerView.
 */
public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.EquipmentViewHolder>
{
	private Context context;
	private List<Equipment> equipments;
	private OnItemClickListener onItemClickListener;

	public EquipmentsAdapter(Context context, List<Equipment> equipments)
	{
		this.context = context;
		this.equipments = equipments;
	}

	@Override
	public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_equipment, parent, false);

		return new EquipmentViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(EquipmentViewHolder holder, int position)
	{
		Equipment equipment = equipments.get(position);

		holder.tvName.setText(equipment.getName());

		if (equipment.getSerialNumber() != null)
		{
			holder.tvSerialNumber.setVisibility(View.VISIBLE);
			holder.tvSerialNumber.setText(equipment.getSerialNumber());
		}
		else
		{
			holder.tvSerialNumber.setVisibility(View.GONE);
		}

		if (equipment.getType() != null)
		{
			holder.llType.setVisibility(View.VISIBLE);
			holder.tvType.setText(equipment.getType());
		}
		else
		{
			holder.llType.setVisibility(View.GONE);
		}

		if (equipment.getSeries() != null)
		{
			holder.llSeries.setVisibility(View.VISIBLE);
			holder.tvSeries.setText(equipment.getSeries());
		}
		else
		{
			holder.llSeries.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return equipments.size();
	}

	public Equipment getItem(int position)
	{
		return equipments.get(position);
	}

	public List<Equipment> getEquipments()
	{
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments)
	{
		this.equipments = equipments;
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

	public class EquipmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected LinearLayout llType;
		protected LinearLayout llSeries;
		protected TextView tvName;
		protected TextView tvSerialNumber;
		protected TextView tvType;
		protected TextView tvSeries;

		public EquipmentViewHolder(View itemView)
		{
			super(itemView);

			llType = (LinearLayout) itemView.findViewById(R.id.linear_layout_type);
			llSeries = (LinearLayout) itemView.findViewById(R.id.linear_layout_series);
			tvName = (TextView) itemView.findViewById(R.id.text_view_equipment_name);
			tvSerialNumber = (TextView) itemView.findViewById(R.id.text_view_equipment_serial_number);
			tvType = (TextView) itemView.findViewById(R.id.text_view_equipment_type);
			tvSeries = (TextView) itemView.findViewById(R.id.text_view_equipment_series);

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
