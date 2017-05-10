package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Measurement.
 */
@DatabaseTable
@Root
public class Measurement implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "measurementSeries", required = false)
	private MeasurementSeries measurementSeries;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "nominalValue", required = false)
	private GeneralValue nominalValue;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "measuringEquipmentInfo", required = false)
	private MeasuringEquipment measuringEquipmentInfo;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "ln", required = false)
	private LogicalNodeInformation ln;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Measurements measurements;

	/**
	 * Required constructor.
	 */
	public Measurement()
	{
		super();
	}

	public Measurement(String name, MeasurementSeries measurementSeries, String longDescription, GeneralValue nominalValue, Employee responsibleEmployee, MeasuringEquipment measuringEquipmentInfo, LogicalNodeInformation ln, UserSpecificContents userSpecificContents, Comments comments)
	{
		this.name = name;
		this.measurementSeries = measurementSeries;
		this.longDescription = longDescription;
		this.nominalValue = nominalValue;
		this.responsibleEmployee = responsibleEmployee;
		this.measuringEquipmentInfo = measuringEquipmentInfo;
		this.ln = ln;
		this.userSpecificContents = userSpecificContents;
		this.comments = comments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public MeasurementSeries getMeasurementSeries()
	{
		return measurementSeries;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public GeneralValue getNominalValue()
	{
		return nominalValue;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public MeasuringEquipment getMeasuringEquipmentInfo()
	{
		return measuringEquipmentInfo;
	}

	public LogicalNodeInformation getLn()
	{
		return ln;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public Comments getComments()
	{
		return comments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setMeasurementSeries(MeasurementSeries measurementSeries)
	{
		this.measurementSeries = measurementSeries;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setNominalValue(GeneralValue nominalValue)
	{
		this.nominalValue = nominalValue;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setMeasuringEquipmentInfo(MeasuringEquipment measuringEquipmentInfo)
	{
		this.measuringEquipmentInfo = measuringEquipmentInfo;
	}

	public void setLn(LogicalNodeInformation ln)
	{
		this.ln = ln;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}
}
