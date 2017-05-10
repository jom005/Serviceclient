package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Measurement.
 */
@DatabaseTable
@Root
public class Measurements implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "measurements", entry = "measurement", inline = true)
	private Collection<Measurement> measurements;

	/**
	 * Required constructor.
	 */
	public Measurements()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param measurements measurements element
	 */
	public Measurements(Collection<Measurement> measurements)
	{
		this.measurements = measurements;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Measurement> getMeasurements()
	{
		return measurements;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMeasurements(Collection<Measurement> measurements)
	{
		this.measurements = measurements;
	}
}
