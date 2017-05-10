package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType WorkOrder.
 */
@DatabaseTable
@Root
public class WorkOrder implements Serializable
{
	public static final String DATABASE_ID = "_id";
	public static final String STATUS_OPEN = "open";
	public static final String STATUS_IN_PROGRESS = "in_progress";
	public static final String STATUS_CLOSED = "closed";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField
	@Element(name = "activityType")
	private String activityType; // restrictions
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "priority")
	private PriorityLog priority;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "status")
	private WorkStatusLog status;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "items")
	private WorkOrderItems items;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "zeusPart1History")
	private ZEUSPart1Assessment zeusPart1History;
	@DatabaseField(canBeNull = true)
	@Element(name = "maintenanceContract", required = false)
	private String maintenanceContract; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "tariffRegulationsRelevant", required = false)
	private boolean tariffRegulationsRelevant;
	@DatabaseField(canBeNull = true)
	@Element(name = "type", required = false)
	private String type;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "client", required = false)
	private Organisation client;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "staff", required = false)
	private Employees staff;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "contractor", required = false)
	private Organisation contractor;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "transportProcesses", required = false)
	private TransportProcesses transportProcesses;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "workLogHistory", required = false)
	private WorkLog workLogHistory;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "environmentalConditions", required = false)
	private EnvironmentalConditions environmentalConditions;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "timeReport", required = false)
	private TimeReports timeReport;
	@DatabaseField(canBeNull = true)
	@Element(name = "scheduledWorkStart", required = false)
	private String scheduledWorkStart; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "scheduledWorkEnd", required = false)
	private String scheduledWorkEnd; // XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "attachments", required = false)
	private Attachments attachments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	@DatabaseField(canBeNull = true)
	private String progress;
	@DatabaseField(canBeNull = true)
	private BigInteger page;

	/**
	 * Required constructor.
	 */
	public WorkOrder()
	{
		super();
	}

	public WorkOrder(String id, String name, String activityType, PriorityLog priority,
					 WorkStatusLog status, WorkOrderItems items,
					 ZEUSPart1Assessment zeusPart1History, String maintenanceContract,
					 boolean tariffRegulationsRelevant, String type, String longDescription,
					 Organisation client, Employee responsibleEmployee, Employees staff,
					 Organisation contractor, TransportProcesses transportProcesses,
					 WorkLog workLogHistory, EnvironmentalConditions environmentalConditions,
					 TimeReports timeReport, String scheduledWorkStart, String scheduledWorkEnd,
					 Attachments attachments, Comments comments,
					 UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.name = name;
		this.activityType = activityType;
		this.priority = priority;
		this.status = status;
		this.items = items;
		this.zeusPart1History = zeusPart1History;
		this.maintenanceContract = maintenanceContract;
		this.tariffRegulationsRelevant = tariffRegulationsRelevant;
		this.type = type;
		this.longDescription = longDescription;
		this.client = client;
		this.responsibleEmployee = responsibleEmployee;
		this.staff = staff;
		this.contractor = contractor;
		this.transportProcesses = transportProcesses;
		this.workLogHistory = workLogHistory;
		this.environmentalConditions = environmentalConditions;
		this.timeReport = timeReport;
		this.scheduledWorkStart = scheduledWorkStart;
		this.scheduledWorkEnd = scheduledWorkEnd;
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

	public String getActivityType()
	{
		return activityType;
	}

	public PriorityLog getPriority()
	{
		return priority;
	}

	public WorkStatusLog getStatus()
	{
		return status;
	}

	public WorkOrderItems getItems()
	{
		return items;
	}

	public ZEUSPart1Assessment getZeusPart1History()
	{
		return zeusPart1History;
	}

	public String getMaintenanceContract()
	{
		return maintenanceContract;
	}

	public boolean isTariffRegulationsRelevant()
	{
		return tariffRegulationsRelevant;
	}

	public String getType()
	{
		return type;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Organisation getClient()
	{
		return client;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public Employees getStaff()
	{
		return staff;
	}

	public Organisation getContractor()
	{
		return contractor;
	}

	public TransportProcesses getTransportProcesses()
	{
		return transportProcesses;
	}

	public WorkLog getWorkLogHistory()
	{
		return workLogHistory;
	}

	public EnvironmentalConditions getEnvironmentalConditions()
	{
		return environmentalConditions;
	}

	public TimeReports getTimeReport()
	{
		return timeReport;
	}

	public String getScheduledWorkStart()
	{
		return scheduledWorkStart;
	}

	public String getScheduledWorkEnd()
	{
		return scheduledWorkEnd;
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

	public String getProgress()
	{
		return progress;
	}

	public BigInteger getPage()
	{
		return page;
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

	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

	public void setPriority(PriorityLog priority)
	{
		this.priority = priority;
	}

	public void setStatus(WorkStatusLog status)
	{
		this.status = status;
	}

	public void setItems(WorkOrderItems items)
	{
		this.items = items;
	}

	public void setZeusPart1History(ZEUSPart1Assessment zeusPart1History)
	{
		this.zeusPart1History = zeusPart1History;
	}

	public void setMaintenanceContract(String maintenanceContract)
	{
		this.maintenanceContract = maintenanceContract;
	}

	public void setTariffRegulationsRelevant(boolean tariffRegulationsRelevant)
	{
		this.tariffRegulationsRelevant = tariffRegulationsRelevant;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setClient(Organisation client)
	{
		this.client = client;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setStaff(Employees staff)
	{
		this.staff = staff;
	}

	public void setContractor(Organisation contractor)
	{
		this.contractor = contractor;
	}

	public void setTransportProcesses(TransportProcesses transportProcesses)
	{
		this.transportProcesses = transportProcesses;
	}

	public void setWorkLogHistory(WorkLog workLogHistory)
	{
		this.workLogHistory = workLogHistory;
	}

	public void setEnvironmentalConditions(EnvironmentalConditions environmentalConditions)
	{
		this.environmentalConditions = environmentalConditions;
	}

	public void setTimeReport(TimeReports timeReport)
	{
		this.timeReport = timeReport;
	}

	public void setScheduledWorkStart(String scheduledWorkStart)
	{
		this.scheduledWorkStart = scheduledWorkStart;
	}

	public void setScheduledWorkEnd(String scheduledWorkEnd)
	{
		this.scheduledWorkEnd = scheduledWorkEnd;
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

	public void setProgress(String progress)
	{
		this.progress = progress;
	}

	public void setPage(BigInteger page)
	{
		this.page = page;
	}
}
