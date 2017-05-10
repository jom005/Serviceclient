package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType TaskStatusLog.
 */
@DatabaseTable
@Root
public class TaskStatusLog implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; // XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "responsibleEmployee")
	private Employee responsibleEmployee;
	@DatabaseField
	@Element(name = "status")
	private String status; // restrictions

	/**
	 * Required constructor.
	 */
	public TaskStatusLog()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param timestamp timestamp element
	 * @param responsibleEmployee responsibleEmployee element
	 * @param status status element
	 */
	public TaskStatusLog(String timestamp, Employee responsibleEmployee,
						 String status)
	{
		this.timestamp = timestamp;
		this.responsibleEmployee = responsibleEmployee;
		this.status = status;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public String getStatus()
	{
		return status;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
