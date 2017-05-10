package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Assessment.
 */
@DatabaseTable
@Root
public class Assessment implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; //XMLGregorianCalendar
	@DatabaseField
	@Element(name = "statusInfo")
	private String statusInfo; // restrictions
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "company", required = false)
	private Organisation company;
	@DatabaseField(canBeNull = true)
	@Element(name = "stateDescription", required = false)
	private String stateDescription;
	@DatabaseField(canBeNull = true)
	@Element(name = "failureCode", required = false)
	private String failureCode;

	/**
	 * Required constructor.
	 */
	public Assessment()
	{
		super();
	}

	/**
	 * Constructor sets Elements
	 * @param timestamp timestamp element
	 * @param statusInfo statusInfo element
	 * @param responsibleEmployee responsibleEmployee element
	 * @param company company element
	 * @param stateDescription stateDescription element
	 * @param failureCode failureCode element
	 */
	public Assessment(String timestamp, String statusInfo,
					  Employee responsibleEmployee, Organisation company, String stateDescription,
					  String failureCode)
	{
		this.timestamp = timestamp;
		this.statusInfo = statusInfo;
		this.responsibleEmployee = responsibleEmployee;
		this.company = company;
		this.stateDescription = stateDescription;
		this.failureCode = failureCode;
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

	public String getStatusInfo()
	{
		return statusInfo;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public Organisation getCompany()
	{
		return company;
	}

	public String getStateDescription()
	{
		return stateDescription;
	}

	public String getFailureCode()
	{
		return failureCode;
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

	public void setStatusInfo(String statusInfo)
	{
		this.statusInfo = statusInfo;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setCompany(Organisation company)
	{
		this.company = company;
	}

	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	public void setFailureCode(String failureCode)
	{
		this.failureCode = failureCode;
	}
}
