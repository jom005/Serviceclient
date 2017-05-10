package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType WorkLogEntry.
 */
@DatabaseTable
@Root
public class WorkLogEntry implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; // XMLGregorianCalendar
	@DatabaseField
	@Element(name = "activityType")
	private String activityType; // restrictions
	@DatabaseField
	@Element(name = "longDescription")
	private String longDescription;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "company", required = false)
	private Organisation company;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private WorkLog workLog;

	/**
	 * Required constructor.
	 */
	public WorkLogEntry()
	{
		super();
	}

	public WorkLogEntry(String timestamp, String activityType,
						String longDescription, Organisation company, Employee responsibleEmployee,
						Comments comments, UserSpecificContents userSpecificContents)
	{
		this.timestamp = timestamp;
		this.activityType = activityType;
		this.longDescription = longDescription;
		this.company = company;
		this.responsibleEmployee = responsibleEmployee;
		this.comments = comments;
		this.userSpecificContents = userSpecificContents;
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

	public String getActivityType()
	{
		return activityType;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Organisation getCompany()
	{
		return company;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public WorkLog getWorkLog()
	{
		return workLog;
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

	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setCompany(Organisation company)
	{
		this.company = company;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setWorkLog(WorkLog workLog)
	{
		this.workLog = workLog;
	}
}
