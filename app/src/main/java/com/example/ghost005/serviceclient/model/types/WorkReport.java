package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType WorkReport.
 */
@DatabaseTable
@Root
public class WorkReport implements Serializable
{
	public static final String DATABASE_ID = "_id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "statuses")
	private WorkStatusLogs statuses;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "items")
	private WorkReportItems items;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "zeusPart1Assessment")
	private ZEUSPart1Assessment zeusPart1Assessment;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription; //false
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "staff", required = false)
	private Employees staff;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "transportProcesses", required = false)
	private TransportProcesses transportProcesses;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "environmentalConditions", required = false)
	private EnvironmentalConditions environmentalConditions;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "timeReport", required = false)
	private TimeReports timeReport;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "attachments", required = false)
	private Attachments attachments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	/**
	 * Required constructor.
	 */
	public WorkReport()
	{
		super();
	}

	public WorkReport(String id, String name, WorkStatusLogs statuses, WorkReportItems items,
					  ZEUSPart1Assessment zeusPart1Assessment, String longDescription,
					  Employee responsibleEmployee, Employees staff,
					  TransportProcesses transportProcesses,
					  EnvironmentalConditions environmentalConditions, TimeReports timeReport,
					  Attachments attachments, Comments comments,
					  UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.name = name;
		this.statuses = statuses;
		this.items = items;
		this.zeusPart1Assessment = zeusPart1Assessment;
		this.longDescription = longDescription;
		this.responsibleEmployee = responsibleEmployee;
		this.staff = staff;
		this.transportProcesses = transportProcesses;
		this.environmentalConditions = environmentalConditions;
		this.timeReport = timeReport;
		this.attachments = attachments;
		this.comments = comments;
		this.userSpecificContents = userSpecificContents;
	}

	/* ---- Getter ---- */

	public int get_id()
	{
		return _id;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public WorkStatusLogs getStatuses()
	{
		return statuses;
	}

	public WorkReportItems getItems()
	{
		return items;
	}

	public ZEUSPart1Assessment getZeusPart1Assessment()
	{
		return zeusPart1Assessment;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public Employees getStaff()
	{
		return staff;
	}

	public TransportProcesses getTransportProcesses()
	{
		return transportProcesses;
	}

	public EnvironmentalConditions getEnvironmentalConditions()
	{
		return environmentalConditions;
	}

	public TimeReports getTimeReport()
	{
		return timeReport;
	}

	public Attachments getAttachments()
	{
		return attachments;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	/* ---- Setter ---- */

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setStatuses(WorkStatusLogs statuses)
	{
		this.statuses = statuses;
	}

	public void setItems(WorkReportItems items)
	{
		this.items = items;
	}

	public void setZeusPart1Assessment(ZEUSPart1Assessment zeusPart1Assessment)
	{
		this.zeusPart1Assessment = zeusPart1Assessment;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setStaff(Employees staff)
	{
		this.staff = staff;
	}

	public void setTransportProcesses(TransportProcesses transportProcesses)
	{
		this.transportProcesses = transportProcesses;
	}

	public void setEnvironmentalConditions(EnvironmentalConditions environmentalConditions)
	{
		this.environmentalConditions = environmentalConditions;
	}

	public void setTimeReport(TimeReports timeReport)
	{
		this.timeReport = timeReport;
	}

	public void setAttachments(Attachments attachments)
	{
		this.attachments = attachments;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}
}
