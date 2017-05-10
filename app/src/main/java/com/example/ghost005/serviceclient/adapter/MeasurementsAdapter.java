package com.example.ghost005.serviceclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.Measurement;
import com.example.ghost005.serviceclient.model.types.MeasurementSeries;

/**
 * Adapter for the measurements RecyclerView.
 */
public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MeasurementViewHolder>
{
	private List<Measurement> measurements;
	private OnItemClickListener onItemClickListener;

	public MeasurementsAdapter(List<Measurement> measurements)
	{
		this.measurements = measurements;
	}

	@Override
	public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_measurement, parent, false);

		return new MeasurementViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MeasurementViewHolder holder, int position)
	{
		Measurement measurement = measurements.get(position);

		holder.tvName.setText(measurement.getName());

		if (measurement.getLongDescription() != null)
		{
			holder.tvLongDescription.setVisibility(View.VISIBLE);
			holder.tvLongDescription.setText(measurement.getLongDescription());
		}
		else
		{
			holder.tvLongDescription.setVisibility(View.GONE);
		}

		if (measurement.getMeasurementSeries() != null)
		{
			MeasurementSeries measurementSeries = measurement.getMeasurementSeries();

			holder.llMeasurments.setVisibility(View.VISIBLE);
			holder.tvMeasurements.setText(String.valueOf(measurementSeries.getMeasurementValues().size()));
		}
		else
		{
			holder.llMeasurments.setVisibility(View.GONE);
		}

		if (measurement.getNominalValue() != null)
		{
			GeneralValue generalValue = measurement.getNominalValue();

			holder.llNominalValue.setVisibility(View.VISIBLE);
			holder.tvNominalValue.setText(String.valueOf(generalValue.getValue()));
		}
		else
		{
			holder.llNominalValue.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount()
	{
		return measurements.size();
	}

	public Measurement getItem(int position)
	{
		return measurements.get(position);
	}

	public List<Measurement> getMeasurements()
	{
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements)
	{
		this.measurements = measurements;
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

	public class MeasurementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected LinearLayout llMeasurments;
		protected LinearLayout llNominalValue;
		protected TextView tvName;
		protected TextView tvLongDescription;
		protected TextView tvMeasurements;
		protected TextView tvNominalValue;

		public MeasurementViewHolder(View itemView)
		{
			super(itemView);

			llMeasurments = (LinearLayout) itemView.findViewById(R.id.linear_layout_measurements);
			llNominalValue = (LinearLayout) itemView.findViewById(R.id.linear_layout_nominal_value);
			tvName = (TextView) itemView.findViewById(R.id.text_view_measurement_name);
			tvLongDescription = (TextView) itemView.findViewById(R.id.text_view_measurement_long_description);
			tvMeasurements = (TextView) itemView.findViewById(R.id.text_view_measurement_measurements);
			tvNominalValue = (TextView) itemView.findViewById(R.id.text_view_measurement_nominal_value);

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
