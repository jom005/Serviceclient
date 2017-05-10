package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType TimeReport.
 */
@DatabaseTable
@Root
public class TimeReport implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField
	@Element(name = "timeType") // TODO: add 'default="GSP-WTT-001"'
	private String timeType; // restrictions
	@DatabaseField
	@Element(name = "duration")
	private String duration; // Duration
	@DatabaseField(canBeNull = true)
	@Element(name = "timePaymentType", required = false) // TODO: add 'default="GSP-PAT-001"'
	private String timePaymentType; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "startTime", required = false)
	private String startTime; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "endTime", required = false)
	private String endTime; // XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "employee", required = false)
	private Employee employee;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private TimeReports timeReports;

	/**
	 * Required constructor.
	 */
	public TimeReport()
	{
		super();
	}

	public TimeReport(String timeType, String duration, String timePaymentType,
					  String startTime, String endTime,
					  Employee employee)
	{
		this.timeType = timeType;
		this.duration = duration;
		this.timePaymentType = timePaymentType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.employee = employee;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getTimeType()
	{
		return timeType;
	}

	public String getDuration()
	{
		return duration;
	}

	public String getTimePaymentType()
	{
		return timePaymentType;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public TimeReports getTimeReports()
	{
		return timeReports;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTimeType(String timeType)
	{
		this.timeType = timeType;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public void setTimePaymentType(String timePaymentType)
	{
		this.timePaymentType = timePaymentType;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	public void setTimeReports(TimeReports timeReports)
	{
		this.timeReports = timeReports;
	}
}
