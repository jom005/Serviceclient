package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType MeasuringEquipment.
 */
@DatabaseTable
@Root
public class MeasuringEquipment implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ElementUnion({
			@Element(name = "measuringInstallation", type = MeasuringInstallation.class),
			@Element(name = "measuringDevice", type = WorkEquipment.class)
	})
	private Object value;

	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private MeasuringEquipment measuringInstallation;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private WorkEquipment measuringDevice;


	/**
	 * Required constructor.
	 */
	public MeasuringEquipment()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param value value element
	 */
	public MeasuringEquipment(Object value)
	{
		this.value = value;

		if (value instanceof  MeasuringEquipment)
		{
			measuringInstallation = (MeasuringEquipment) value;
		}
		else if (value instanceof  WorkEquipment)
		{
			measuringDevice = (WorkEquipment) value;
		}
		else
		{
			throw new ClassCastException("Parameter must be of " +
					MeasuringEquipment.class.getName() + " or " + WorkEquipment.class.getName());
		}
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Object getValue()
	{
		return value;
	}

	public MeasuringEquipment getMeasuringInstallation()
	{
		return measuringInstallation;
	}

	public WorkEquipment getMeasuringDevice()
	{
		return measuringDevice;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setValue(Object value)
	{
		this.value = value;

		if (value instanceof MeasuringEquipment)
		{
			measuringInstallation = (MeasuringEquipment) value;
		}
		else if (value instanceof WorkEquipment)
		{
			measuringDevice = (WorkEquipment) value;
		}
	}

	public void setMeasuringInstallation(MeasuringEquipment measuringInstallation)
	{
		this.measuringInstallation = measuringInstallation;

		value = measuringInstallation;
	}

	public void setMeasuringDevice(WorkEquipment measuringDevice)
	{
		this.measuringDevice = measuringDevice;

		value = measuringDevice;
	}
}
