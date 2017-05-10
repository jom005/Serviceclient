package com.example.ghost005.serviceclient.fragments.work_order;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.LongEditTextDialogFragment;
import com.example.ghost005.serviceclient.dialogs.ShortEditTextDialogFragment;
import com.example.ghost005.serviceclient.dialogs.SpinnerDialogFragment;
import com.example.ghost005.serviceclient.dialogs.TimePickerDialogFragment;
import com.example.ghost005.serviceclient.events.LongEditTextDialogEvent;
import com.example.ghost005.serviceclient.events.ShortEditTextDialogEvent;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogResetEvent;
import com.example.ghost005.serviceclient.events.TimePickerDialogSetEvent;
import com.example.ghost005.serviceclient.model.enums.CloudCover;
import com.example.ghost005.serviceclient.model.enums.UnitMultiplier;
import com.example.ghost005.serviceclient.model.enums.UnitSymbol;
import com.example.ghost005.serviceclient.model.types.EnvironmentalCondition;
import com.example.ghost005.serviceclient.model.types.EnvironmentalConditions;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * Fragment for adding environmental conditions.
 */
public class EnvironmentalConditionFragment extends Fragment
		implements View.OnClickListener, TimePickerDialogFragment.TimePickerDialogEventListener,
		LongEditTextDialogFragment.LongEditTextDialogEventListener,
		ShortEditTextDialogFragment.ShortEditTextDialogEventListener,
		SpinnerDialogFragment.SpinnerDialogEventListener
{
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String BUNDLE_ENV_COND_DB_ID = "bundle_env_cond_db_id";
	private static final String BUNDLE_NEW = "bundle_new";
	private static final String DIALOG_AMOUNT_OF_PRECIPITATION = "dialog_amount_of_precipitation";
	private static final String DIALOG_ATMOSPHERIC_PRESSURE = "dialog_atmospheric_pressure";
	private static final String DIALOG_CLOUD_COVER = "dialog_cloud_cover";
	private static final String DIALOG_DEPTH_OF_SNOW = "dialog_depth_of_snow";
	private static final String DIALOG_DESCRIPTION = "dialog_description";
	private static final String DIALOG_HIGH_TIDE = "dialog_high_tide";
	private static final String DIALOG_HUMIDITY = "dialog_humidity";
	private static final String DIALOG_LOW_TIDE = "dialog_low_tide";
	private static final String DIALOG_MAX_TEMPERATURE = "dialog_max_temperature";
	private static final String DIALOG_MEAN_TEMPERATURE = "dialog_mean_temperature";
	private static final String DIALOG_MIN_TEMPERATURE = "dialog_min_temperature";
	private static final String DIALOG_PROBABILITY_OF_RAIN = "dialog_probability_of_rain";
	private static final String DIALOG_SUNRISE = "dialog_sunrise";
	private static final String DIALOG_SUNSET = "dialog_sunset";
	private static final String DIALOG_WATER_CURRENT = "dialog_water_current";
	private static final String DIALOG_WIND_CHILL = "dialog_wind_chill";
	private static final String DIALOG_WIND_DIRECTION = "dialog_wind_direction";
	private static final String DIALOG_WIND_SPEED = "dialog_wind_speed";
	private static final String DIALOG_WAVE_HEIGHT = "dialog_wave_height";
	private static final String DIALOG_WAVE_PERIOD = "dialog_wave_period";
	private static final String FRAGMENT_LONG_EDIT_TEXT = "fragment_long_edit_text";
	private static final String FRAGMENT_SHORT_EDIT_TEXT = "fragment_short_edit_text";
	private static final String FRAGMENT_SPINNER = "fragment_spinner";
	private static final String FRAGMENT_TIME_PICKER = "fragment_time_picker";
	private static final String STATE_AMOUNT_OF_PRECIPITATION = "state_amount_of_precipitation";
	private static final String STATE_ATMOSPHERIC_PRESSURE = "state_atmospheric_pressure";
	private static final String STATE_CLOUD_COVER = "state_cloud_cover";
	private static final String STATE_DEPTH_OF_SNOW = "state_depth_of_snow";
	private static final String STATE_DESCRIPTION = "state_description";
	private static final String STATE_HIGH_TIDE_DATE = "state_high_tide_date";
	private static final String STATE_HUMIDITY = "state_humidity";
	private static final String STATE_LOW_TIDE_DATE = "state_low_tide_date";
	private static final String STATE_MAX_TEMPERATURE = "state_max_temperature";
	private static final String STATE_MEAN_TEMPERATURE = "state_mean_temperature";
	private static final String STATE_MIN_TEMPERATURE = "state_min_temperature";
	private static final String STATE_PROBABILITY_OF_RAIN = "state_probability_of_rain";
	private static final String STATE_SUNRISE_DATE = "state_sunrise_date";
	private static final String STATE_SUNSET_DATE = "state_sunset_date";
	private static final String STATE_WATER_CURRENT = "state_water_current";
	private static final String STATE_WAVE_HEIGHT = "state_wave_height";
	private static final String STATE_WAVE_PERIOD = "state_wave_period";
	private static final String STATE_WIND_CHILL = "state_wind_chill";
	private static final String STATE_WIND_DIRECTION = "state_wind_direction";
	private static final String STATE_WIND_SPEED = "state_wind_speed";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String STATE_AMOUNT_OF_PRECIPITATION_UNIT = "state_amount_of_precipitation_unit";
	private static final String STATE_ATMOSPHERIC_PRESSURE_UNIT = "state_atmospheric_pressure_unit";
	private static final String STATE_DEPTH_OF_SNOW_UNIT = "state_depth_of_snow_unit";
	private static final String STATE_HUMIDITY_UNIT = "state_humidity_unit";
	private static final String STATE_MAX_TEMPERATURE_UNIT = "state_max_temperature_unit";
	private static final String STATE_MEAN_TEMPERATURE_UNIT = "state_mean_temperature_unit";
	private static final String STATE_MIN_TEMPERATURE_UNIT = "state_min_temperature_unit";
	private static final String STATE_PROBABILITY_OF_RAIN_UNIT = "state_probability_of_rain_unit";
	private static final String STATE_WATER_CURRENT_UNIT = "state_water_current_unit";
	private static final String STATE_WAVE_HEIGHT_UNIT = "state_wave_height_unit";
	private static final String STATE_WAVE_PERIOD_UNIT = "state_wave_period_unit";
	private static final String STATE_WIND_CHILL_UNIT = "state_wind_chill_unit";
	private static final String STATE_WIND_DIRECTION_UNIT = "state_wind_direction_unit";
	private static final String STATE_WIND_SPEED_UNIT = "state_wind_speed_unit";

	private CardView cvAmountOfPrecipitation;
	private CardView cvAtmosphericPressure;
	private CardView cvCloudCover;
	private CardView cvDepthOfSnow;
	private CardView cvDescription;
	private CardView cvHighTide;
	private CardView cvHumidity;
	private CardView cvLowTide;
	private CardView cvMaxTemperature;
	private CardView cvMeanTemperature;
	private CardView cvMinTemperature;
	private CardView cvProbabilityOfRain;
	private CardView cvSunrise;
	private CardView cvSunset;
	private CardView cvWaterCurrent;
	private CardView cvWaveHeight;
	private CardView cvWavePeriod;
	private CardView cvWindChill;
	private CardView cvWindDirection;
	private CardView cvWindSpeed;
	private TextView tvAmountOfPrecipitation;
	private TextView tvAmountOfPrecipitationUnit;
	private TextView tvAtmosphericPressure;
	private TextView tvAtmosphericPressureUnit;
	private TextView tvCloudCover;
	private TextView tvDepthOfSnow;
	private TextView tvDepthOfSnowUnit;
	private TextView tvDescription;
	private TextView tvHighTide;
	private TextView tvHumidity;
	private TextView tvHumidityUnit;
	private TextView tvLowTide;
	private TextView tvMaxTemperature;
	private TextView tvMaxTemperatureUnit;
	private TextView tvMeanTemperature;
	private TextView tvMeanTemperatureUnit;
	private TextView tvMinTemperature;
	private TextView tvMinTemperatureUnit;
	private TextView tvProbabilityOfRain;
	private TextView tvProbabilityOfRainUnit;
	private TextView tvSunrise;
	private TextView tvSunset;
	private TextView tvWaterCurrent;
	private TextView tvWaterCurrentUnit;
	private TextView tvWaveHeight;
	private TextView tvWaveHeightUnit;
	private TextView tvWavePeriod;
	private TextView tvWavePeriodUnit;
	private TextView tvWindChill;
	private TextView tvWindChillUnit;
	private TextView tvWindDirection;
	private TextView tvWindDirectionUnit;
	private TextView tvWindSpeed;
	private TextView tvWindSpeedUnit;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	private int cloudCoverPosition;
	private String sunrise;
	private String sunset;
	private String lowTide;
	private String highTide;
	private String dialog;

	private int gspDBID;
	private int envCondDBID;
	private boolean newEnvCond;

	public static EnvironmentalConditionFragment newInstance(int gspDBID, int envCondDBID, boolean newEnvCond)
	{
		EnvironmentalConditionFragment fragment = new EnvironmentalConditionFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_ENV_COND_DB_ID, envCondDBID);
		args.putBoolean(BUNDLE_NEW, newEnvCond);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		envCondDBID = getArguments().getInt(BUNDLE_ENV_COND_DB_ID);
		newEnvCond = getArguments().getBoolean(BUNDLE_NEW);
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
		View view = inflater.inflate(R.layout.fragment_environmental_condition, container, false);

		cvAmountOfPrecipitation = (CardView) view.findViewById(R.id.card_view_amount_of_precipitation);
		cvAtmosphericPressure = (CardView) view.findViewById(R.id.card_view_atmospheric_pressure);
		cvCloudCover = (CardView) view.findViewById(R.id.card_view_cloud_cover);
		cvDepthOfSnow = (CardView) view.findViewById(R.id.card_view_depth_of_snow);
		cvDescription = (CardView) view.findViewById(R.id.card_view_description);
		cvHighTide = (CardView) view.findViewById(R.id.card_view_high_tide);
		cvHumidity = (CardView) view.findViewById(R.id.card_view_humidity);
		cvLowTide = (CardView) view.findViewById(R.id.card_view_low_tide);
		cvMaxTemperature = (CardView) view.findViewById(R.id.card_view_max_temperature);
		cvMeanTemperature = (CardView) view.findViewById(R.id.card_view_mean_temperature);
		cvMinTemperature = (CardView) view.findViewById(R.id.card_view_min_temperature);
		cvProbabilityOfRain = (CardView) view.findViewById(R.id.card_view_probability_of_rain);
		cvSunrise = (CardView) view.findViewById(R.id.card_view_sunrise);
		cvSunset = (CardView) view.findViewById(R.id.card_view_sunset);
		cvWaterCurrent = (CardView) view.findViewById(R.id.card_view_water_current);
		cvWaveHeight = (CardView) view.findViewById(R.id.card_view_wave_height);
		cvWavePeriod = (CardView) view.findViewById(R.id.card_view_wave_period);
		cvWindChill = (CardView) view.findViewById(R.id.card_view_wind_chill);
		cvWindDirection = (CardView) view.findViewById(R.id.card_view_wind_direction);
		cvWindSpeed = (CardView) view.findViewById(R.id.card_view_wind_speed);

		tvAmountOfPrecipitation = (TextView) view.findViewById(R.id.text_view_amount_of_precipitation);
		tvAmountOfPrecipitationUnit = (TextView) view.findViewById(R.id.text_view_amount_of_precipitation_unit);
		tvAtmosphericPressure = (TextView) view.findViewById(R.id.text_view_atmospheric_pressure);
		tvAtmosphericPressureUnit = (TextView) view.findViewById(R.id.text_view_atmospheric_pressure_unit);
		tvCloudCover = (TextView) view.findViewById(R.id.text_view_cloud_cover);
		tvDepthOfSnow = (TextView) view.findViewById(R.id.text_view_depth_of_snow);
		tvDepthOfSnowUnit = (TextView) view.findViewById(R.id.text_view_depth_of_snow_unit);
		tvDescription = (TextView) view.findViewById(R.id.text_view_description);
		tvHighTide = (TextView) view.findViewById(R.id.text_view_high_tide);
		tvHumidity = (TextView) view.findViewById(R.id.text_view_humidity);
		tvHumidityUnit = (TextView) view.findViewById(R.id.text_view_humidity_unit);
		tvLowTide = (TextView) view.findViewById(R.id.text_view_low_tide);
		tvMaxTemperature = (TextView) view.findViewById(R.id.text_view_max_temperature);
		tvMaxTemperatureUnit = (TextView) view.findViewById(R.id.text_view_max_temperature_unit);
		tvMeanTemperature = (TextView) view.findViewById(R.id.text_view_mean_temperature);
		tvMeanTemperatureUnit = (TextView) view.findViewById(R.id.text_view_mean_temperature_unit);
		tvMinTemperature = (TextView) view.findViewById(R.id.text_view_min_temperature);
		tvMinTemperatureUnit = (TextView) view.findViewById(R.id.text_view_min_temperature_unit);
		tvProbabilityOfRain = (TextView) view.findViewById(R.id.text_view_probability_of_rain);
		tvProbabilityOfRainUnit = (TextView) view.findViewById(R.id.text_view_probability_of_rain_unit);
		tvSunrise = (TextView) view.findViewById(R.id.text_view_sunrise);
		tvSunset = (TextView) view.findViewById(R.id.text_view_sunset);
		tvWaterCurrent = (TextView) view.findViewById(R.id.text_view_water_current);
		tvWaterCurrentUnit = (TextView) view.findViewById(R.id.text_view_water_current_unit);
		tvWaveHeight = (TextView) view.findViewById(R.id.text_view_wave_height);
		tvWaveHeightUnit = (TextView) view.findViewById(R.id.text_view_wave_height_unit);
		tvWavePeriod = (TextView) view.findViewById(R.id.text_view_wave_period);
		tvWavePeriodUnit = (TextView) view.findViewById(R.id.text_view_wave_period_unit);
		tvWindChill = (TextView) view.findViewById(R.id.text_view_wind_chill);
		tvWindChillUnit = (TextView) view.findViewById(R.id.text_view_wind_chill_unit);
		tvWindDirection = (TextView) view.findViewById(R.id.text_view_wind_direction);
		tvWindDirectionUnit = (TextView) view.findViewById(R.id.text_view_wind_direction_unit);
		tvWindSpeed = (TextView) view.findViewById(R.id.text_view_wind_speed);
		tvWindSpeedUnit = (TextView) view.findViewById(R.id.text_view_wind_speed_unit);

		llButtons = (LinearLayout) view.findViewById(R.id.buttons);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);

		return view;
	}

	@Override
	@SuppressWarnings("ResourceType")
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvAmountOfPrecipitation.setOnClickListener(this);
		cvAtmosphericPressure.setOnClickListener(this);
		cvCloudCover.setOnClickListener(this);
		cvDepthOfSnow.setOnClickListener(this);
		cvDescription.setOnClickListener(this);
		cvHighTide.setOnClickListener(this);
		cvHumidity.setOnClickListener(this);
		cvLowTide.setOnClickListener(this);
		cvMaxTemperature.setOnClickListener(this);
		cvMeanTemperature.setOnClickListener(this);
		cvMinTemperature.setOnClickListener(this);
		cvProbabilityOfRain.setOnClickListener(this);
		cvSunrise.setOnClickListener(this);
		cvSunset.setOnClickListener(this);
		cvWaterCurrent.setOnClickListener(this);
		cvWaveHeight.setOnClickListener(this);
		cvWavePeriod.setOnClickListener(this);
		cvWindChill.setOnClickListener(this);
		cvWindDirection.setOnClickListener(this);
		cvWindSpeed.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState == null)
		{
			try
			{
				loadData();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			tvAmountOfPrecipitation.setText(savedInstanceState.getString(STATE_AMOUNT_OF_PRECIPITATION));
			tvAtmosphericPressure.setText(savedInstanceState.getString(STATE_ATMOSPHERIC_PRESSURE));
			tvDepthOfSnow.setText(savedInstanceState.getString(STATE_DEPTH_OF_SNOW));
			tvDescription.setText(savedInstanceState.getString(STATE_DESCRIPTION));
			tvHumidity.setText(savedInstanceState.getString(STATE_HUMIDITY));
			tvMaxTemperature.setText(savedInstanceState.getString(STATE_MAX_TEMPERATURE));
			tvMeanTemperature.setText(savedInstanceState.getString(STATE_MEAN_TEMPERATURE));
			tvMinTemperature.setText(savedInstanceState.getString(STATE_MIN_TEMPERATURE));
			tvProbabilityOfRain.setText(savedInstanceState.getString(STATE_PROBABILITY_OF_RAIN));
			tvWaterCurrent.setText(savedInstanceState.getString(STATE_WATER_CURRENT));
			tvWaveHeight.setText(savedInstanceState.getString(STATE_WAVE_HEIGHT));
			tvWavePeriod.setText(savedInstanceState.getString(STATE_WAVE_PERIOD));
			tvWindChill.setText(savedInstanceState.getString(STATE_WIND_CHILL));
			tvWindDirection.setText(savedInstanceState.getString(STATE_WIND_DIRECTION));
			tvWindSpeed.setText(savedInstanceState.getString(STATE_WIND_SPEED));


			tvAmountOfPrecipitationUnit.setVisibility(savedInstanceState.getInt(STATE_AMOUNT_OF_PRECIPITATION_UNIT));
			tvAtmosphericPressureUnit.setVisibility(savedInstanceState.getInt(STATE_ATMOSPHERIC_PRESSURE_UNIT));
			tvDepthOfSnowUnit.setVisibility(savedInstanceState.getInt(STATE_DEPTH_OF_SNOW_UNIT));
			tvHumidityUnit.setVisibility(savedInstanceState.getInt(STATE_HUMIDITY_UNIT));
			tvMaxTemperatureUnit.setVisibility(savedInstanceState.getInt(STATE_MAX_TEMPERATURE_UNIT));
			tvMeanTemperatureUnit.setVisibility(savedInstanceState.getInt(STATE_MEAN_TEMPERATURE_UNIT));
			tvMinTemperatureUnit.setVisibility(savedInstanceState.getInt(STATE_MIN_TEMPERATURE_UNIT));
			tvProbabilityOfRainUnit.setVisibility(savedInstanceState.getInt(STATE_PROBABILITY_OF_RAIN_UNIT));
			tvWaterCurrentUnit.setVisibility(savedInstanceState.getInt(STATE_WATER_CURRENT_UNIT));
			tvWaveHeightUnit.setVisibility(savedInstanceState.getInt(STATE_WAVE_HEIGHT_UNIT));
			tvWavePeriodUnit.setVisibility(savedInstanceState.getInt(STATE_WAVE_PERIOD_UNIT));
			tvWindChillUnit.setVisibility(savedInstanceState.getInt(STATE_WIND_CHILL_UNIT));
			tvWindDirectionUnit.setVisibility(savedInstanceState.getInt(STATE_WIND_DIRECTION_UNIT));
			tvWindSpeedUnit.setVisibility(savedInstanceState.getInt(STATE_WIND_SPEED_UNIT));

			cloudCoverPosition = savedInstanceState.getInt(STATE_CLOUD_COVER);
			highTide = savedInstanceState.getString(STATE_HIGH_TIDE_DATE);
			lowTide = savedInstanceState.getString(STATE_LOW_TIDE_DATE);
			sunrise = savedInstanceState.getString(STATE_SUNRISE_DATE);
			sunset = savedInstanceState.getString(STATE_SUNSET_DATE);
			dialog = savedInstanceState.getString(STATE_DIALOG);

			if (cloudCoverPosition > -1)
			{
				String[] cloudCovers = getResources().getStringArray(R.array.cloudcover);
				tvCloudCover.setText(cloudCovers[cloudCoverPosition]);
			}

			Date date = Utilities.getDateFromString(highTide);
			String time = Utilities.getTime(date);

			if (time != null)
			{
				tvHighTide.setText(time);
			}

			date = Utilities.getDateFromString(lowTide);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvLowTide.setText(time);
			}

			date = Utilities.getDateFromString(sunrise);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvSunrise.setText(time);
			}

			date = Utilities.getDateFromString(sunset);
			time = Utilities.getTime(date);

			if (time != null)
			{
				tvSunset.setText(time);
			}
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

		outState.putInt(STATE_CLOUD_COVER, cloudCoverPosition);
		outState.putString(STATE_AMOUNT_OF_PRECIPITATION, tvAmountOfPrecipitation.getText().toString());
		outState.putString(STATE_ATMOSPHERIC_PRESSURE, tvAtmosphericPressure.getText().toString());
		outState.putString(STATE_DEPTH_OF_SNOW, tvDepthOfSnow.getText().toString());
		outState.putString(STATE_DESCRIPTION, tvDescription.getText().toString());
		outState.putString(STATE_HIGH_TIDE_DATE, highTide);
		outState.putString(STATE_HUMIDITY, tvHumidity.getText().toString());
		outState.putString(STATE_LOW_TIDE_DATE, lowTide);
		outState.putString(STATE_MAX_TEMPERATURE, tvMaxTemperature.getText().toString());
		outState.putString(STATE_MEAN_TEMPERATURE, tvMeanTemperature.getText().toString());
		outState.putString(STATE_MIN_TEMPERATURE, tvMinTemperature.getText().toString());
		outState.putString(STATE_PROBABILITY_OF_RAIN, tvProbabilityOfRain.getText().toString());
		outState.putString(STATE_SUNRISE_DATE, sunrise);
		outState.putString(STATE_SUNSET_DATE, sunrise);
		outState.putString(STATE_WATER_CURRENT, tvWaterCurrent.getText().toString());
		outState.putString(STATE_WAVE_HEIGHT, tvWaveHeight.getText().toString());
		outState.putString(STATE_WAVE_PERIOD, tvWavePeriod.getText().toString());
		outState.putString(STATE_WIND_CHILL, tvWindChill.getText().toString());
		outState.putString(STATE_WIND_DIRECTION, tvWindDirection.getText().toString());
		outState.putString(STATE_WIND_SPEED, tvWindSpeed.getText().toString());
		outState.putString(STATE_DIALOG, dialog);
		outState.putInt(STATE_AMOUNT_OF_PRECIPITATION_UNIT, tvAmountOfPrecipitationUnit.getVisibility());
		outState.putInt(STATE_ATMOSPHERIC_PRESSURE_UNIT, tvAtmosphericPressureUnit.getVisibility());
		outState.putInt(STATE_DEPTH_OF_SNOW_UNIT, tvDepthOfSnowUnit.getVisibility());
		outState.putInt(STATE_HUMIDITY_UNIT, tvHumidityUnit.getVisibility());
		outState.putInt(STATE_MAX_TEMPERATURE_UNIT, tvMaxTemperatureUnit.getVisibility());
		outState.putInt(STATE_MEAN_TEMPERATURE_UNIT, tvMeanTemperatureUnit.getVisibility());
		outState.putInt(STATE_MIN_TEMPERATURE_UNIT, tvMinTemperatureUnit.getVisibility());
		outState.putInt(STATE_PROBABILITY_OF_RAIN_UNIT, tvProbabilityOfRainUnit.getVisibility());
		outState.putInt(STATE_WATER_CURRENT_UNIT, tvWaterCurrentUnit.getVisibility());
		outState.putInt(STATE_WAVE_HEIGHT_UNIT, tvWaveHeightUnit.getVisibility());
		outState.putInt(STATE_WAVE_PERIOD_UNIT, tvWavePeriodUnit.getVisibility());
		outState.putInt(STATE_WIND_CHILL_UNIT, tvWindChillUnit.getVisibility());
		outState.putInt(STATE_WIND_DIRECTION_UNIT, tvWindDirectionUnit.getVisibility());
		outState.putInt(STATE_WIND_SPEED_UNIT, tvWindSpeedUnit.getVisibility());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_delete, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newEnvCond)
		{
			itemDelete.setVisible(false);
		}
		else
		{
			itemDelete.setVisible(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_delete:
			{
				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
					GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
					WorkReport workReport = gsp.getWorkReport();
					EnvironmentalCondition envCond = databaseAdapter.getEnvironmentalCondition(envCondDBID);
					databaseAdapter.deleteEnvironmentalCondition(envCond);

					if (workReport.getEnvironmentalConditions().getEnvironmentalConditions().size() == 0)
					{
						EnvironmentalConditions envCons = workReport.getEnvironmentalConditions();
						databaseAdapter.deleteEnvironmentalConditions(envCons);
					}

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void loadData() throws SQLException
	{
		if (newEnvCond)
		{
			cloudCoverPosition = -1;
			tvAmountOfPrecipitationUnit.setVisibility(View.GONE);
			tvAtmosphericPressureUnit.setVisibility(View.GONE);
			tvDepthOfSnowUnit.setVisibility(View.GONE);
			tvHumidityUnit.setVisibility(View.GONE);
			tvMaxTemperatureUnit.setVisibility(View.GONE);
			tvMeanTemperatureUnit.setVisibility(View.GONE);
			tvMinTemperatureUnit.setVisibility(View.GONE);
			tvProbabilityOfRainUnit.setVisibility(View.GONE);
			tvWaterCurrentUnit.setVisibility(View.GONE);
			tvWaveHeightUnit.setVisibility(View.GONE);
			tvWavePeriodUnit.setVisibility(View.GONE);
			tvWindChillUnit.setVisibility(View.GONE);
			tvWindDirectionUnit.setVisibility(View.GONE);
			tvWindSpeedUnit.setVisibility(View.GONE);
		}
		else
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			EnvironmentalCondition envCond = databaseAdapter.getEnvironmentalCondition(envCondDBID);

			if (envCond.getAmountOfPrecipitation() != null)
			{
				tvAmountOfPrecipitation.setText(envCond.getAmountOfPrecipitation().getValue().toString());
				tvAmountOfPrecipitationUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvAmountOfPrecipitation.setText("");
				tvAmountOfPrecipitationUnit.setVisibility(View.GONE);
			}

			if (envCond.getAtmosphericPressure() != null)
			{
				tvAtmosphericPressure.setText(envCond.getAtmosphericPressure().getValue().toString());
				tvAtmosphericPressureUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvAtmosphericPressure.setText("");
				tvAtmosphericPressureUnit.setVisibility(View.GONE);
			}

			if (envCond.getCloudCover() != null)
			{
				Pair<String, Integer> cloudCoverPair = GSPUtilities.getCloudCover(getActivity(), envCond.getCloudCover());

				if (cloudCoverPair != null)
				{
					tvCloudCover.setText(cloudCoverPair.first);
					cloudCoverPosition = cloudCoverPair.second;
				}
			}
			else
			{
				tvCloudCover.setText("");
				cloudCoverPosition = -1;
			}

			if (envCond.getDepthOfSnow() != null)
			{
				tvDepthOfSnow.setText(envCond.getDepthOfSnow().getValue().toString());
				tvDepthOfSnowUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvDepthOfSnow.setText("");
				tvDepthOfSnowUnit.setVisibility(View.GONE);
			}

			if (envCond.getDescription() != null)
			{
				tvDescription.setText(envCond.getDescription());
			}
			else
			{
				tvDescription.setText("");
			}

			if (envCond.getHighTide() != null)
			{
				highTide = envCond.getHighTide();

				Date date = Utilities.getDateFromString(highTide);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvHighTide.setText(time);
				}
			}
			else
			{
				tvHighTide.setText("");
			}

			if (envCond.getHumidity() != null)
			{
				tvHumidity.setText(envCond.getHumidity().getValue().toString());
				tvHumidityUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvHumidity.setText("");
				tvHumidityUnit.setVisibility(View.GONE);
			}

			if (envCond.getLowTide() != null)
			{
				lowTide = envCond.getLowTide();

				Date date = Utilities.getDateFromString(lowTide);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvLowTide.setText(time);
				}
			}
			else
			{
				tvLowTide.setText("");
			}

			if (envCond.getMaxTemperature() != null)
			{
				tvMaxTemperature.setText(envCond.getMaxTemperature().getValue().toString());
				tvMaxTemperatureUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvMaxTemperature.setText("");
				tvMaxTemperatureUnit.setVisibility(View.GONE);
			}

			if (envCond.getMeanTemperature() != null)
			{
				tvMeanTemperature.setText(envCond.getMeanTemperature().getValue().toString());
				tvMeanTemperatureUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvMeanTemperature.setText("");
				tvMeanTemperatureUnit.setVisibility(View.GONE);
			}

			if (envCond.getMinTemperature() != null)
			{
				tvMinTemperature.setText(envCond.getMinTemperature().getValue().toString());
				tvMinTemperatureUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvMinTemperature.setText("");
				tvMinTemperatureUnit.setVisibility(View.GONE);
			}

			if (envCond.getProbabilityOfRain() != null)
			{
				tvProbabilityOfRain.setText(envCond.getProbabilityOfRain().getValue().toString());
				tvProbabilityOfRainUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvProbabilityOfRain.setText("");
				tvProbabilityOfRainUnit.setVisibility(View.GONE);
			}

			if (envCond.getSunrise() != null)
			{
				sunrise = envCond.getSunrise();

				Date date = Utilities.getDateFromString(sunrise);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvSunrise.setText(time);
				}
			}
			else
			{
				tvSunrise.setText("");
			}

			if (envCond.getSunset() != null)
			{
				sunset = envCond.getSunset();

				Date date = Utilities.getDateFromString(sunset);
				String time = Utilities.getTime(date);

				if (time != null)
				{
					tvSunset.setText(time);
				}
			}
			else
			{
				tvSunset.setText("");
			}

			if (envCond.getWaterCurrent() != null)
			{
				tvWaterCurrent.setText(envCond.getWaterCurrent().getValue().toString());
				tvWaterCurrentUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWaterCurrent.setText("");
				tvWaterCurrentUnit.setVisibility(View.GONE);
			}

			if (envCond.getWaveHeight() != null)
			{
				tvWaveHeight.setText(envCond.getWaveHeight().getValue().toString());
				tvWaveHeightUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWaveHeight.setText("");
				tvWaveHeightUnit.setVisibility(View.GONE);
			}

			if (envCond.getWavePeriod() != null)
			{
				tvWavePeriod.setText(envCond.getWavePeriod().getValue().toString());
				tvWavePeriodUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWavePeriod.setText("");
				tvWavePeriodUnit.setVisibility(View.GONE);
			}

			if (envCond.getWindChill() != null)
			{
				tvWindChill.setText(envCond.getWindChill().getValue().toString());
				tvWindChillUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWindChill.setText("");
				tvWindChillUnit.setVisibility(View.GONE);
			}

			if (envCond.getWindDirection() != null)
			{
				tvWindDirection.setText(envCond.getWindDirection().getValue().toString());
				tvWindDirectionUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWindDirection.setText("");
				tvWindDirectionUnit.setVisibility(View.GONE);
			}

			if (envCond.getWindSpeed() != null)
			{
				tvWindSpeed.setText(envCond.getWindSpeed().getValue().toString());
				tvWindSpeedUnit.setVisibility(View.VISIBLE);
			}
			else
			{
				tvWindSpeed.setText("");
				tvWindSpeedUnit.setVisibility(View.GONE);
			}
		}
	}

	public void save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		EnvironmentalCondition envCond;

		if (newEnvCond)
		{
			envCond = new EnvironmentalCondition();
		}
		else
		{
			envCond = databaseAdapter.getEnvironmentalCondition(envCondDBID);
		}

		String description = tvDescription.getText().toString().trim();
		String windSpeed = tvWindSpeed.getText().toString().trim();
		String windDirection = tvWindDirection.getText().toString().trim();
		String probabilityOfRain = tvProbabilityOfRain.getText().toString().trim();
		String amountOfPrecipitation = tvAmountOfPrecipitation.getText().toString().trim();
		String humidity = tvHumidity.getText().toString().trim();
		String atmosphericPressure = tvAtmosphericPressure.getText().toString().trim();
		String meanTemperature = tvMeanTemperature.getText().toString().trim();
		String minTemperature = tvMinTemperature.getText().toString().trim();
		String maxTemperature = tvMaxTemperature.getText().toString().trim();
		String windChill = tvWindChill.getText().toString().trim();
		String depthOfSnow = tvDepthOfSnow.getText().toString().trim();
		String waterCurrent = tvWaterCurrent.getText().toString().trim();
		String waveHeight = tvWaveHeight.getText().toString().trim();
		String wavePeriod = tvWavePeriod.getText().toString().trim();

		envCond.setTimestamp(Utilities.exportDate(new Date()));

		if (description.length() > 0)
		{

			envCond.setDescription(description);
		}
		else
		{
			envCond.setDescription(null);
		}

		if (windSpeed.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(windSpeed));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.NONE.getValue());
			// TODO: no m/s in UnitSymbol

			envCond.setWindSpeed(generalValue);
		}
		else
		{
			envCond.setWindSpeed(null);
		}

		if (windDirection.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(windDirection));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.DEG.getValue());

			envCond.setWindDirection(generalValue);
		}
		else
		{
			envCond.setWindDirection(null);
		}

		if (probabilityOfRain.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(probabilityOfRain));
			generalValue.setMultiplier(getResources().getString(R.string.none_unitmultiplier));
			generalValue.setSymbol(UnitSymbol.NONE.getValue());
			// TODO: no % in UnitSymbol

			envCond.setProbabilityOfRain(generalValue);
		}
		else
		{
			envCond.setProbabilityOfRain(null);
		}

		if (amountOfPrecipitation.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(amountOfPrecipitation));
			generalValue.setMultiplier(UnitMultiplier.M.getValue());
			generalValue.setSymbol(UnitSymbol.M.getValue());

			envCond.setAmountOfPrecipitation(generalValue);
		}
		else
		{
			envCond.setAmountOfPrecipitation(null);
		}

		if (humidity.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(humidity));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.NONE.getValue());
			// TODO: no % in UnitSymbol

			envCond.setHumidity(generalValue);
		}
		else
		{
			envCond.setHumidity(null);
		}

		String cloudCoverValue = null;

		if (cloudCoverPosition > -1)
		{
			CloudCover[] cloudCovers = CloudCover.values();

			cloudCoverValue = cloudCovers[cloudCoverPosition].getValue();
		}

		envCond.setCloudCover(cloudCoverValue);

		if (atmosphericPressure.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(atmosphericPressure));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			// TODO: no Multiplier for h
			generalValue.setSymbol(UnitSymbol.PA.getValue());

			envCond.setAtmosphericPressure(generalValue);
		}
		else
		{
			envCond.setAtmosphericPressure(null);
		}

		if (meanTemperature.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(meanTemperature));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.DEGC.getValue());

			envCond.setMeanTemperature(generalValue);
		}
		else
		{
			envCond.setMeanTemperature(null);
		}

		if (minTemperature.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(minTemperature));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.DEGC.getValue());

			envCond.setMinTemperature(generalValue);
		}
		else
		{
			envCond.setMinTemperature(null);
		}

		if (maxTemperature.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(maxTemperature));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.DEGC.getValue());

			envCond.setMaxTemperature(generalValue);
		}
		else
		{
			envCond.setMaxTemperature(null);
		}

		if (windChill.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(windChill));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.DEGC.getValue());

			envCond.setWindChill(generalValue);
		}
		else
		{
			envCond.setWindChill(null);
		}

		if (depthOfSnow.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(depthOfSnow));
			generalValue.setMultiplier(UnitMultiplier.C.getValue());
			generalValue.setSymbol(UnitSymbol.M.getValue());

			envCond.setDepthOfSnow(generalValue);
		}
		else
		{
			envCond.setDepthOfSnow(null);
		}

		if (waterCurrent.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(waterCurrent));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.NONE.getValue());
			// TODO: no m/s in UnitSymbol

			envCond.setWaterCurrent(generalValue);
		}
		else
		{
			envCond.setWaterCurrent(null);
		}

		if (waveHeight.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(waveHeight));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.M.getValue());

			envCond.setWaveHeight(generalValue);
		}
		else
		{
			envCond.setWaveHeight(null);
		}

		if (wavePeriod.length() > 0)
		{
			GeneralValue generalValue = new GeneralValue();
			generalValue.setValue(new BigDecimal(wavePeriod));
			generalValue.setMultiplier(UnitMultiplier.NONE.getValue());
			generalValue.setSymbol(UnitSymbol.S.getValue());

			envCond.setWavePeriod(generalValue);
		}
		else
		{
			envCond.setWavePeriod(null);
		}

		if (sunrise != null)
		{
			envCond.setSunrise(sunrise);
		}
		else
		{
			envCond.setSunrise(null);
		}

		if (sunset != null)
		{
			envCond.setSunset(sunset);
		}
		else
		{
			envCond.setSunset(null);
		}

		if (lowTide != null)
		{
			envCond.setLowTide(lowTide);
		}
		else
		{
			envCond.setLowTide(null);
		}

		if (highTide != null)
		{
			envCond.setHighTide(highTide);
		}
		else
		{
			envCond.setHighTide(null);
		}

		if (newEnvCond)
		{
			GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
			WorkReport workReport = gsp.getWorkReport();

			if (workReport.getEnvironmentalConditions() != null)
			{
				EnvironmentalConditions envConds = workReport.getEnvironmentalConditions();
				envCond.setEnvironmentalConditions(envConds);
				databaseAdapter.createOrUpdateEnvironmentalCondition(envCond);
			}
			else
			{
				EnvironmentalConditions envConds = new EnvironmentalConditions();
				ArrayList<EnvironmentalCondition> envCondList = new ArrayList<>();

				envCond.setEnvironmentalConditions(envConds);
				envCondList.add(envCond);
				envConds.setEnvironmentalConditions(envCondList);
				workReport.setEnvironmentalConditions(envConds);

				databaseAdapter.createOrUpdateEnvironmentalCondition(envCond);
				databaseAdapter.createOrUpdateEnvironmentalConditions(envConds);
				databaseAdapter.createOrUpdateWorkReport(workReport);
			}
		}
		else
		{
			databaseAdapter.createOrUpdateGeneralValue(envCond.getAmountOfPrecipitation());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getAtmosphericPressure());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getDepthOfSnow());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getHumidity());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getMaxTemperature());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getMeanTemperature());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getMinTemperature());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getProbabilityOfRain());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWaterCurrent());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWaveHeight());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWavePeriod());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWindChill());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWindDirection());
			databaseAdapter.createOrUpdateGeneralValue(envCond.getWindSpeed());
			databaseAdapter.createOrUpdateEnvironmentalCondition(envCond);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.card_view_amount_of_precipitation:
			{
				dialog = DIALOG_AMOUNT_OF_PRECIPITATION;
				String title = getResources().getString(R.string.environment_amount_of_precipitation);
				String text = tvAmountOfPrecipitation.getText().toString();
				String unit = getResources().getString(R.string.environment_amount_of_precipitation_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_atmospheric_pressure:
			{
				dialog = DIALOG_ATMOSPHERIC_PRESSURE;
				String title = getResources().getString(R.string.environment_atmospheric_pressure);
				String text = tvAtmosphericPressure.getText().toString();
				String unit = getResources().getString(R.string.environment_atmospheric_pressure_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_cloud_cover:
			{
				dialog = DIALOG_CLOUD_COVER;
				String title = getResources().getString(R.string.environment_cloud_cover);
				int resource = R.array.cloudcover;
				startSpinner(title, null, resource, cloudCoverPosition, true);
				break;
			}
			case R.id.card_view_depth_of_snow:
			{
				dialog = DIALOG_DEPTH_OF_SNOW;
				String title = getResources().getString(R.string.environment_depth_of_snow);
				String text = tvDepthOfSnow.getText().toString();
				String unit = getResources().getString(R.string.environment_depth_of_snow_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_description:
			{
				dialog = DIALOG_DESCRIPTION;
				String title = getResources().getString(R.string.environment_description);
				String text = tvDescription.getText().toString();
				startLongEditText(title, null, text, false);
				break;
			}
			case R.id.card_view_high_tide:
			{
				dialog = DIALOG_HIGH_TIDE;
				startTimePicker();
				break;
			}
			case R.id.card_view_humidity:
			{
				dialog = DIALOG_HUMIDITY;
				String title = getResources().getString(R.string.environment_humidity);
				String text = tvHumidity.getText().toString();
				String unit = getResources().getString(R.string.environment_humidity_unit);
				int minValue = 0;
				int maxValue = 100;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_low_tide:
			{
				dialog = DIALOG_LOW_TIDE;
				startTimePicker();
				break;
			}
			case R.id.card_view_max_temperature:
			{
				dialog = DIALOG_MAX_TEMPERATURE;
				String title = getResources().getString(R.string.environment_max_temperature);
				String text = tvMaxTemperature.getText().toString();
				String unit = getResources().getString(R.string.environment_max_temperature_unit);
				int minValue = -273;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_mean_temperature:
			{
				dialog = DIALOG_MEAN_TEMPERATURE;
				String title = getResources().getString(R.string.environment_mean_temperature);
				String text = tvMeanTemperature.getText().toString();
				String unit = getResources().getString(R.string.environment_mean_temperature_unit);
				int minValue = -273;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_min_temperature:
			{
				dialog = DIALOG_MIN_TEMPERATURE;
				String title = getResources().getString(R.string.environment_min_temperature);
				String text = tvMinTemperature.getText().toString();
				String unit = getResources().getString(R.string.environment_min_temperature_unit);
				int minValue = -273;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_probability_of_rain:
			{
				dialog = DIALOG_PROBABILITY_OF_RAIN;
				String title = getResources().getString(R.string.environment_probability_of_rain);
				String text = tvProbabilityOfRain.getText().toString();
				String unit = getResources().getString(R.string.environment_probability_of_rain_unit);
				int minValue = 0;
				int maxValue = 100;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_sunrise:
			{
				dialog = DIALOG_SUNRISE;
				startTimePicker();
				break;
			}
			case R.id.card_view_sunset:
			{
				dialog = DIALOG_SUNSET;
				startTimePicker();
				break;
			}
			case R.id.card_view_water_current:
			{
				dialog = DIALOG_WATER_CURRENT;
				String title = getResources().getString(R.string.environment_water_current);
				String text = tvWaterCurrent.getText().toString();
				String unit = getResources().getString(R.string.environment_water_current_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_wind_chill:
			{
				dialog = DIALOG_WIND_CHILL;
				String title = getResources().getString(R.string.environment_wind_chill);
				String text = tvWindChill.getText().toString();
				String unit = getResources().getString(R.string.environment_wind_chill_unit);
				int minValue = -273;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_wind_direction:
			{
				dialog = DIALOG_WIND_DIRECTION;
				String title = getResources().getString(R.string.environment_wind_direction);
				String text = tvWindDirection.getText().toString();
				String unit = getResources().getString(R.string.environment_wind_direction_unit);
				int minValue = 1;
				int maxValue = 360;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_wind_speed:
			{
				dialog = DIALOG_WIND_SPEED;
				String title = getResources().getString(R.string.environment_wind_speed);
				String text = tvWindSpeed.getText().toString();
				String unit = getResources().getString(R.string.environment_wind_speed_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_wave_height:
			{
				dialog = DIALOG_WAVE_HEIGHT;
				String title = getResources().getString(R.string.environment_wave_height);
				String text = tvWaveHeight.getText().toString();
				String unit = getResources().getString(R.string.environment_wave_height_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_wave_period:
			{
				dialog = DIALOG_WAVE_PERIOD;
				String title = getResources().getString(R.string.environment_wave_period);
				String text = tvWavePeriod.getText().toString();
				String unit = getResources().getString(R.string.environment_wave_period_unit);
				int minValue = 0;
				int maxValue = Integer.MAX_VALUE;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}

			case R.id.button_cancel:
			{
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
				break;
			}
			case R.id.button_ok:
			{
				try
				{
					save();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				break;
			}
		}
	}

	public void setTime(int hourOfDay, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);

		Date date = calendar.getTime();
		String time = Utilities.getTime(date);

		switch (dialog)
		{
			case DIALOG_SUNRISE:
			{
				sunrise = Utilities.exportDate(date);
				tvSunrise.setText(time);
				break;
			}
			case DIALOG_SUNSET:
			{
				sunset = Utilities.exportDate(date);
				tvSunset.setText(time);
				break;
			}
			case DIALOG_LOW_TIDE:
			{
				lowTide = Utilities.exportDate(date);
				tvLowTide.setText(time);
				break;
			}
			case DIALOG_HIGH_TIDE:
			{
				highTide = Utilities.exportDate(date);
				tvHighTide.setText(time);
				break;
			}
		}
	}

	public void resetTime()
	{
		switch (dialog)
		{
			case DIALOG_SUNRISE:
			{
				sunrise = null;
				tvSunrise.setText("");
				break;
			}
			case DIALOG_SUNSET:
			{
				sunset = null;
				tvSunset.setText("");
				break;
			}
			case DIALOG_LOW_TIDE:
			{
				lowTide = null;
				tvLowTide.setText("");
				break;
			}
			case DIALOG_HIGH_TIDE:
			{
				highTide = null;
				tvHighTide.setText("");
				break;
			}
		}
	}

	public void setText(String text)
	{
		switch (dialog)
		{
			case DIALOG_AMOUNT_OF_PRECIPITATION:
			{
				tvAmountOfPrecipitation.setText(text);

				if (text.length() > 0)
				{
					tvAmountOfPrecipitationUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvAmountOfPrecipitationUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_ATMOSPHERIC_PRESSURE:
			{
				tvAtmosphericPressure.setText(text);

				if (text.length() > 0)
				{
					tvAtmosphericPressureUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvAtmosphericPressureUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_DEPTH_OF_SNOW:
			{
				tvDepthOfSnow.setText(text);

				if (text.length() > 0)
				{
					tvDepthOfSnowUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvDepthOfSnowUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_DESCRIPTION:
			{
				tvDescription.setText(text);
				break;
			}
			case DIALOG_HUMIDITY:
			{
				tvHumidity.setText(text);

				if (text.length() > 0)
				{
					tvHumidityUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvHumidityUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_MAX_TEMPERATURE:
			{
				tvMaxTemperature.setText(text);

				if (text.length() > 0)
				{
					tvMaxTemperatureUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvMaxTemperatureUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_MEAN_TEMPERATURE:
			{
				tvMeanTemperature.setText(text);

				if (text.length() > 0)
				{
					tvMeanTemperatureUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvMeanTemperatureUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_MIN_TEMPERATURE:
			{
				tvMinTemperature.setText(text);

				if (text.length() > 0)
				{
					tvMinTemperatureUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvMinTemperatureUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_PROBABILITY_OF_RAIN:
			{
				tvProbabilityOfRain.setText(text);

				if (text.length() > 0)
				{
					tvProbabilityOfRainUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvProbabilityOfRainUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WIND_CHILL:
			{
				tvWindChill.setText(text);

				if (text.length() > 0)
				{
					tvWindChillUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWindChillUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WIND_DIRECTION:
			{
				tvWindDirection.setText(text);

				if (text.length() > 0)
				{
					tvWindDirectionUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWindDirectionUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WATER_CURRENT:
			{
				tvWaterCurrent.setText(text);

				if (text.length() > 0)
				{
					tvWaterCurrentUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWaterCurrentUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WAVE_HEIGHT:
			{
				tvWaveHeight.setText(text);

				if (text.length() > 0)
				{
					tvWaveHeightUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWaveHeightUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WAVE_PERIOD:
			{
				tvWavePeriod.setText(text);

				if (text.length() > 0)
				{
					tvWavePeriodUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWavePeriodUnit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_WIND_SPEED:
			{
				tvWindSpeed.setText(text);

				if (text.length() > 0)
				{
					tvWindSpeedUnit.setVisibility(View.VISIBLE);
				}
				else
				{
					tvWindSpeedUnit.setVisibility(View.GONE);
				}

				break;
			}
		}
	}

	public void setSpinner(String text, int position)
	{
		switch (dialog)
		{
			case DIALOG_CLOUD_COVER:
			{
				tvCloudCover.setText(text);
				cloudCoverPosition = position;
				break;
			}
		}
	}

	private void startLongEditText(String title, String subTitle, String text, boolean scan)
	{
		LongEditTextDialogFragment fragment = LongEditTextDialogFragment.newInstance(title, subTitle, text, scan);
		fragment.show(getChildFragmentManager(), FRAGMENT_LONG_EDIT_TEXT);
	}

	private void startShortEditText(String title, String text, String unit, int minValue, int maxValue)
	{
		ShortEditTextDialogFragment fragment = ShortEditTextDialogFragment.newInstance(title, text, unit, minValue, maxValue);
		fragment.show(getChildFragmentManager(), FRAGMENT_SHORT_EDIT_TEXT);
	}

	private void startSpinner(String title, String subTitle, int resource, int position, boolean emptyItem)
	{
		SpinnerDialogFragment fragment = SpinnerDialogFragment.newInstance(title, subTitle, resource, position, emptyItem);
		fragment.show(getChildFragmentManager(), FRAGMENT_SPINNER);
	}

	private void startTimePicker()
	{
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();
		fragment.show(getChildFragmentManager(), FRAGMENT_TIME_PICKER);
	}

	@Override
	public void onEvent(LongEditTextDialogEvent event)
	{
		String text = event.getText();
		setText(text);
	}

	@Override
	public void onEvent(ShortEditTextDialogEvent event)
	{
		String text = event.getText();
		setText(text);
	}

	@Override
	public void onEvent(SpinnerDialogEvent event)
	{
		String text = event.getText();
		int position = event.getPosition();
		setSpinner(text, position);
	}

	@Override
	public void onEvent(TimePickerDialogSetEvent event)
	{
		int hourOfDay = event.getHourOfDay();
		int minute = event.getMinute();
		setTime(hourOfDay, minute);
	}

	@Override
	public void onEvent(TimePickerDialogResetEvent event)
	{
		resetTime();
	}
}
