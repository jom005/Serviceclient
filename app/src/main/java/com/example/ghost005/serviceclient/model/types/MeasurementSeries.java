package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType MeasurementSeries.
 */
@DatabaseTable
@Root
public class MeasurementSeries implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "measurementValues", entry = "measurementValue", inline = true)
	private Collection<MeasurementValue> measurementValues;

	/**
	 * Required constructor.
	 */
	public MeasurementSeries()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param measurementValues measurementValues element
	 */
	public MeasurementSeries(Collection<MeasurementValue> measurementValues)
	{
		this.measurementValues = measurementValues;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<MeasurementValue> getMeasurementValues()
	{
		return measurementValues;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMeasurementValues(Collection<MeasurementValue> measurementValues)
	{
		this.measurementValues = measurementValues;
	}
}
