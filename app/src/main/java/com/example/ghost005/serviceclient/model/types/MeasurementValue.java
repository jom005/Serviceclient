package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType MeasurementValue.
 */
@DatabaseTable
@Root
public class MeasurementValue extends GeneralValue implements Serializable
{
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; // XMLGregorianCalendar

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private MeasurementSeries measurementSeries;

	/**
	 * Required constructor.
	 */
	public MeasurementValue()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param timestamp timestamp element
	 */
	public MeasurementValue(String timestamp)
	{
		this.timestamp = timestamp;
	}

	/* ---- Getter ---- */

	public String getTimestamp()
	{
		return timestamp;
	}

	/* ---- Setter ---- */

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
}
