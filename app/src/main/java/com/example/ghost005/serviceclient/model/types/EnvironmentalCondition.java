package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType EnvironmentalCondition.
 */
@DatabaseTable
@Root
public class EnvironmentalCondition implements Serializable
{
	public static final String DATABASE_ID = "_id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "description", required = false)
	private String description;
	@DatabaseField(canBeNull = true)
	@Element(name = "referenceTimestamp", required = false)
	private String referenceTimestamp; // XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "windSpeed", required = false)
	private GeneralValue windSpeed;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "windDirection", required = false)
	private GeneralValue windDirection;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "probabilityOfRain", required = false)
	private GeneralValue probabilityOfRain;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "amountOfPrecipitation", required = false)
	private GeneralValue amountOfPrecipitation;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "humidity", required = false)
	private GeneralValue humidity;
	@DatabaseField(canBeNull = true)
	@Element(name = "cloudCover", required = false)
	private String cloudCover;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "atmosphericPressure", required = false)
	private GeneralValue atmosphericPressure;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "meanTemperature", required = false)
	private GeneralValue meanTemperature;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "minTemperature", required = false)
	private GeneralValue minTemperature;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "maxTemperature", required = false)
	private GeneralValue maxTemperature;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "windChill", required = false)
	private GeneralValue windChill;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "depthOfSnow", required = false)
	private GeneralValue depthOfSnow;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "waterCurrent", required = false)
	private GeneralValue waterCurrent;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "waveHeight", required = false)
	private GeneralValue waveHeight;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "wavePeriod", required = false)
	private GeneralValue wavePeriod;
	@DatabaseField(canBeNull = true)
	@Element(name = "sunrise", required = false)
	private String sunrise; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "sunset", required = false)
	private String sunset; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "lowTide", required = false)
	private String lowTide; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "highTide", required = false)
	private String highTide; // XMLGregorianCalendar

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private EnvironmentalConditions environmentalConditions;

	/**
	 * Required constructor.
	 */
	public EnvironmentalCondition()
	{
		super();
	}

	public EnvironmentalCondition(String timestamp, String description,
								  String referenceTimestamp, GeneralValue windSpeed,
								  GeneralValue windDirection, GeneralValue probabilityOfRain,
								  GeneralValue amountOfPrecipitation, GeneralValue humidity,
								  String cloudCover, GeneralValue atmosphericPressure,
								  GeneralValue meanTemperature, GeneralValue minTemperature,
								  GeneralValue maxTemperature, GeneralValue windChill,
								  GeneralValue depthOfSnow, GeneralValue waterCurrent,
								  GeneralValue waveHeight, GeneralValue wavePeriod,
								  String sunrise, String sunset,
								  String lowTide, String highTide)
	{
		this.timestamp = timestamp;
		this.description = description;
		this.referenceTimestamp = referenceTimestamp;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.probabilityOfRain = probabilityOfRain;
		this.amountOfPrecipitation = amountOfPrecipitation;
		this.humidity = humidity;
		this.cloudCover = cloudCover;
		this.atmosphericPressure = atmosphericPressure;
		this.meanTemperature = meanTemperature;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.windChill = windChill;
		this.depthOfSnow = depthOfSnow;
		this.waterCurrent = waterCurrent;
		this.waveHeight = waveHeight;
		this.wavePeriod = wavePeriod;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.lowTide = lowTide;
		this.highTide = highTide;
	}

	/* ---- Getter ---- */

	public int get_Id()
	{
		return _id;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public String getDescription()
	{
		return description;
	}

	public String getReferenceTimestamp()
	{
		return referenceTimestamp;
	}

	public GeneralValue getWindSpeed()
	{
		return windSpeed;
	}

	public GeneralValue getWindDirection()
	{
		return windDirection;
	}

	public GeneralValue getProbabilityOfRain()
	{
		return probabilityOfRain;
	}

	public GeneralValue getAmountOfPrecipitation()
	{
		return amountOfPrecipitation;
	}

	public GeneralValue getHumidity()
	{
		return humidity;
	}

	public String getCloudCover()
	{
		return cloudCover;
	}

	public GeneralValue getAtmosphericPressure()
	{
		return atmosphericPressure;
	}

	public GeneralValue getMeanTemperature()
	{
		return meanTemperature;
	}

	public GeneralValue getMinTemperature()
	{
		return minTemperature;
	}

	public GeneralValue getMaxTemperature()
	{
		return maxTemperature;
	}

	public GeneralValue getWindChill()
	{
		return windChill;
	}

	public GeneralValue getDepthOfSnow()
	{
		return depthOfSnow;
	}

	public GeneralValue getWaterCurrent()
	{
		return waterCurrent;
	}

	public GeneralValue getWaveHeight()
	{
		return waveHeight;
	}

	public GeneralValue getWavePeriod()
	{
		return wavePeriod;
	}

	public String getSunrise()
	{
		return sunrise;
	}

	public String getSunset()
	{
		return sunset;
	}

	public String getLowTide()
	{
		return lowTide;
	}

	public String getHighTide()
	{
		return highTide;
	}

	public EnvironmentalConditions getEnvironmentalConditions()
	{
		return environmentalConditions;
	}

	/* ---- Setter ---- */

	public void set_Id(int _id)
	{
		this._id = _id;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setReferenceTimestamp(String referenceTimestamp)
	{
		this.referenceTimestamp = referenceTimestamp;
	}

	public void setWindSpeed(GeneralValue windSpeed)
	{
		this.windSpeed = windSpeed;
	}

	public void setWindDirection(GeneralValue windDirection)
	{
		this.windDirection = windDirection;
	}

	public void setProbabilityOfRain(GeneralValue probabilityOfRain)
	{
		this.probabilityOfRain = probabilityOfRain;
	}

	public void setAmountOfPrecipitation(GeneralValue amountOfPrecipitation)
	{
		this.amountOfPrecipitation = amountOfPrecipitation;
	}

	public void setHumidity(GeneralValue humidity)
	{
		this.humidity = humidity;
	}

	public void setCloudCover(String cloudCover)
	{
		this.cloudCover = cloudCover;
	}

	public void setAtmosphericPressure(GeneralValue atmosphericPressure)
	{
		this.atmosphericPressure = atmosphericPressure;
	}

	public void setMeanTemperature(GeneralValue meanTemperature)
	{
		this.meanTemperature = meanTemperature;
	}

	public void setMinTemperature(GeneralValue minTemperature)
	{
		this.minTemperature = minTemperature;
	}

	public void setMaxTemperature(GeneralValue maxTemperature)
	{
		this.maxTemperature = maxTemperature;
	}

	public void setWindChill(GeneralValue windChill)
	{
		this.windChill = windChill;
	}

	public void setDepthOfSnow(GeneralValue depthOfSnow)
	{
		this.depthOfSnow = depthOfSnow;
	}

	public void setWaterCurrent(GeneralValue waterCurrent)
	{
		this.waterCurrent = waterCurrent;
	}

	public void setWaveHeight(GeneralValue waveHeight)
	{
		this.waveHeight = waveHeight;
	}

	public void setWavePeriod(GeneralValue wavePeriod)
	{
		this.wavePeriod = wavePeriod;
	}

	public void setSunrise(String sunrise)
	{
		this.sunrise = sunrise;
	}

	public void setSunset(String sunset)
	{
		this.sunset = sunset;
	}

	public void setLowTide(String lowTide)
	{
		this.lowTide = lowTide;
	}

	public void setHighTide(String highTide)
	{
		this.highTide = highTide;
	}

	public void setEnvironmentalConditions(EnvironmentalConditions environmentalConditions)
	{
		this.environmentalConditions = environmentalConditions;
	}
}
