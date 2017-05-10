package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.EnvironmentalCondition;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for environmental conditions RecyclerView.
 */
public class EnvironmentalConditionsAdapter extends
		RecyclerView.Adapter<EnvironmentalConditionsAdapter.EnvironmentalConditionViewHolder>
{
	private Context context;
	private List<EnvironmentalCondition> environmentalConditionsHistory;
	private List<EnvironmentalCondition> environmentalConditionsNew;
	private OnItemClickListener onItemClickListener;

	public EnvironmentalConditionsAdapter(
			Context context, List<EnvironmentalCondition> environmentalConditionsHistory,
			List<EnvironmentalCondition> environmentalConditionsNew)
	{
		this.context = context;
		this.environmentalConditionsHistory = environmentalConditionsHistory;
		this.environmentalConditionsNew = environmentalConditionsNew;
	}

	@Override
	public EnvironmentalConditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_environmental_condition, parent, false);

		return new EnvironmentalConditionViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(EnvironmentalConditionViewHolder holder, int position)
	{
		EnvironmentalCondition environmentalCondition = null;

		if (position < environmentalConditionsHistory.size())
		{
			environmentalCondition = environmentalConditionsHistory.get(position);
		}
		else if (position >= environmentalConditionsHistory.size() && environmentalConditionsNew.size() > 0)
		{
			int historySize = environmentalConditionsHistory.size();

			environmentalCondition = environmentalConditionsNew.get(position - historySize);
		}

		if (environmentalCondition != null)
		{
			holder.llLeft.removeAllViews();
			holder.llRight.removeAllViews();

			String processingDate = Utilities.importDate(environmentalCondition.getTimestamp());
			String envDescription = environmentalCondition.getDescription();
			GeneralValue windSpeed = environmentalCondition.getWindSpeed();
			GeneralValue windDirection = environmentalCondition.getWindDirection();
			GeneralValue probabilityOfRain = environmentalCondition.getProbabilityOfRain();
			GeneralValue amountOfPrecipitation = environmentalCondition.getAmountOfPrecipitation();
			GeneralValue humidity = environmentalCondition.getHumidity();
			String cloudCover = environmentalCondition.getCloudCover();
			GeneralValue atmosphericPressure = environmentalCondition.getAtmosphericPressure();
			GeneralValue meanTemperature = environmentalCondition.getMeanTemperature();
			GeneralValue minTemperature = environmentalCondition.getMinTemperature();
			GeneralValue maxTemperature = environmentalCondition.getMaxTemperature();
			GeneralValue windChill = environmentalCondition.getWindChill();
			GeneralValue depthOfSnow = environmentalCondition.getDepthOfSnow();
			GeneralValue waterCurrent = environmentalCondition.getWaterCurrent();
			GeneralValue waveHeight = environmentalCondition.getWaveHeight();
			GeneralValue wavePeriod = environmentalCondition.getWavePeriod();
			String sunrise = environmentalCondition.getSunrise();
			String sunset = environmentalCondition.getSunset();
			String lowTide = environmentalCondition.getLowTide();
			String highTide = environmentalCondition.getHighTide();

			holder.tvEnvironmentDate.setText(processingDate);

			boolean left = true;

			if (envDescription != null)
			{
				holder.tvEnvironmentDescription.setVisibility(View.VISIBLE);
				holder.tvEnvironmentDescription.setText(envDescription);
			}
			else
			{
				holder.tvEnvironmentDescription.setVisibility(View.GONE);
			}

			if (windSpeed != null)
			{
				String description = context.getResources().getString(R.string.environment_wind_speed);
				String value = windSpeed.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_wind_speed_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (windDirection != null)
			{
				String description = context.getResources().getString(R.string.environment_wind_direction);
				String value = windDirection.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_wind_direction_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (probabilityOfRain != null)
			{
				String description = context.getResources().getString(R.string.environment_probability_of_rain);
				String value = probabilityOfRain.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_probability_of_rain_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (amountOfPrecipitation != null)
			{
				String description = context.getResources().getString(R.string.environment_amount_of_precipitation);
				String value = amountOfPrecipitation.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_amount_of_precipitation_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (humidity != null)
			{
				String description = context.getResources().getString(R.string.environment_humidity);
				String value = humidity.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_humidity_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (cloudCover != null)
			{
				String description = context.getResources().getString(R.string.environment_cloud_cover);
				Pair<String, Integer> cloudCoverPair = GSPUtilities.getCloudCover(context, cloudCover);
				String value = cloudCoverPair.first;
				String unit = "";

				if (value != null)
				{
					addItem(holder, left, description, value, unit);

					left = !left;
				}
			}

			if (atmosphericPressure != null)
			{
				String description = context.getResources().getString(R.string.environment_atmospheric_pressure);
				String value = atmosphericPressure.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_atmospheric_pressure_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (meanTemperature != null)
			{
				String description = context.getResources().getString(R.string.environment_mean_temperature);
				String value = meanTemperature.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_mean_temperature_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (minTemperature != null)
			{
				String description = context.getResources().getString(R.string.environment_min_temperature);
				String value = minTemperature.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_min_temperature_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (maxTemperature != null)
			{
				String description = context.getResources().getString(R.string.environment_max_temperature);
				String value = maxTemperature.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_max_temperature_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (windChill != null)
			{
				String description = context.getResources().getString(R.string.environment_wind_chill);
				String value = windChill.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_wind_chill_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (depthOfSnow != null)
			{
				String description = context.getResources().getString(R.string.environment_depth_of_snow);
				String value = depthOfSnow.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_depth_of_snow_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (waterCurrent != null)
			{
				String description = context.getResources().getString(R.string.environment_water_current);
				String value = waterCurrent.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_water_current_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (waveHeight != null)
			{
				String description = context.getResources().getString(R.string.environment_wave_height);
				String value = waveHeight.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_wave_height_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (wavePeriod != null)
			{
				String description = context.getResources().getString(R.string.environment_wave_period);
				String value = wavePeriod.getValue().toString();
				String unit = context.getResources().getString(R.string.environment_wave_period_unit);

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (sunrise != null)
			{
				Date date = Utilities.getDateFromString(sunrise);

				String description = context.getResources().getString(R.string.environment_sunrise);
				String value = Utilities.getTime(date);
				String unit = "";

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (sunset != null)
			{
				Date date = Utilities.getDateFromString(sunset);

				String description = context.getResources().getString(R.string.environment_sunset);
				String value = Utilities.getTime(date);
				String unit = "";

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (lowTide != null)
			{
				Date date = Utilities.getDateFromString(lowTide);

				String description = context.getResources().getString(R.string.environment_low_tide);
				String value = Utilities.getTime(date);
				String unit = "";

				addItem(holder, left, description, value, unit);

				left = !left;
			}

			if (highTide != null)
			{
				Date date = Utilities.getDateFromString(highTide);
				
				String description = context.getResources().getString(R.string.environment_high_tide);
				String value = Utilities.getTime(date);
				String unit = "";

				addItem(holder, left, description, value, unit);

				left = !left;
			}
		}
	}

	@Override
	public int getItemCount()
	{
		return environmentalConditionsHistory.size() + environmentalConditionsNew.size();
	}

	/**
	 * Adds views dynamically to two parent LinearLayouts, alternating between both.
	 *
	 * @param holder      ViewHolder which will contain the views.
	 * @param left        left or right LinearLayout
	 * @param description description for the value
	 * @param value       value to display
	 * @param unit        unit for the value
	 */
	private void addItem(EnvironmentalConditionViewHolder holder, boolean left, String description, String value, String unit)
	{
		int horizontalPaddingSmall = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				context.getResources().getDimension(R.dimen.card_view_horizontal_padding_small),
				context.getResources().getDisplayMetrics());

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams layoutParamsLinearLayout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout.setLayoutParams(layoutParamsLinearLayout);

		LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsTextView.setMargins(horizontalPaddingSmall, 0, horizontalPaddingSmall, 0);

		TextView tvDescription = new TextView(context);
		tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getInteger(R.integer.text_size_medium_value));
		tvDescription.setTextColor(ContextCompat.getColor(context,R.color.environment_small_text));
		tvDescription.setText(description);

		TextView tvValue = new TextView(context);
		tvValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getInteger(R.integer.text_size_medium_value));
		tvValue.setTextColor(ContextCompat.getColor(context,R.color.environment_small_text));
		tvValue.setText(value);

		TextView tvUnit = new TextView(context);
		tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getInteger(R.integer.text_size_medium_value));
		tvUnit.setTextColor(ContextCompat.getColor(context,R.color.environment_small_text));
		tvUnit.setText(unit);

		linearLayout.addView(tvDescription);
		linearLayout.addView(tvValue, layoutParamsTextView);
		linearLayout.addView(tvUnit);

		if (left)
		{
			holder.llLeft.addView(linearLayout);
		}
		else
		{
			holder.llRight.addView(linearLayout);
		}
	}

	public int getHistoryCount()
	{
		return environmentalConditionsHistory.size();
	}

	public int getNewCount()
	{
		return environmentalConditionsNew.size();
	}

	public List<EnvironmentalCondition> getEnvironmentalConditionsHistory()
	{
		return environmentalConditionsHistory;
	}

	public void setEnvironmentalConditionsHistory(List<EnvironmentalCondition> environmentalConditionsHistory)
	{
		this.environmentalConditionsHistory = environmentalConditionsHistory;
	}

	public List<EnvironmentalCondition> getEnvironmentalConditionsNew()
	{
		return environmentalConditionsNew;
	}

	public void setEnvironmentalConditionsNew(List<EnvironmentalCondition> environmentalConditionsNew)
	{
		this.environmentalConditionsNew = environmentalConditionsNew;
	}

	public EnvironmentalCondition getItem(int position)
	{
		EnvironmentalCondition environmentalCondition = null;

		if (position < environmentalConditionsHistory.size())
		{
			environmentalCondition = environmentalConditionsHistory.get(position);
		}
		else if (position >= environmentalConditionsHistory.size() && environmentalConditionsNew.size() > 0)
		{
			int historySize = environmentalConditionsHistory.size();

			environmentalCondition = environmentalConditionsNew.get(position - historySize);
		}

		return environmentalCondition;
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

	public class EnvironmentalConditionViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{
		protected TextView tvEnvironmentDate;
		protected TextView tvEnvironmentDescription;

		protected LinearLayout llLeft;
		protected LinearLayout llRight;

		public EnvironmentalConditionViewHolder(View itemView)
		{
			super(itemView);

			tvEnvironmentDate = (TextView) itemView.findViewById(R.id.text_view_environment_date);
			tvEnvironmentDescription = (TextView) itemView.findViewById(R.id.text_view_environment_description);

			llLeft = (LinearLayout) itemView.findViewById(R.id.linear_layout_left);
			llRight = (LinearLayout) itemView.findViewById(R.id.linear_layout_right);

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
