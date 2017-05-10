package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Parameter.
 */
@DatabaseTable
@Root
public class Parameter implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "value")
	private ParameterValue value;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@DatabaseField(canBeNull = true)
	@Element(name = "category", required = false)
	private String category;
	@DatabaseField(canBeNull = true)
	@Element(name = "timestamp", required = false)
	private String timestamp; //XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private UserSpecificParameterSet userSpecificParameterSet;

	/**
	 * Required constructor.
	 */
	public Parameter()
	{
		super();
	}

	public Parameter(String name, ParameterValue value, String longDescription, String category, String timestamp, Employee responsibleEmployee, Comments comments)
	{
		this.name = name;
		this.value = value;
		this.longDescription = longDescription;
		this.category = category;
		this.timestamp = timestamp;
		this.responsibleEmployee = responsibleEmployee;
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

	public ParameterValue getValue()
	{
		return value;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public String getCategory()
	{
		return category;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
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

	public void setValue(ParameterValue value)
	{
		this.value = value;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}
}
