package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType WorkReportItem.
 */
@DatabaseTable
@Root
public class WorkReportItem implements Serializable
{
	public static final String DATABASE_ID = "_id";
	public static final String WORK_ORDER_ID = "orderItemId";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField(columnName = WORK_ORDER_ID)
	@Element(name = "orderItemId")
	private String orderItemId;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "statuses")
	private WorkStatusLogs statuses;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "assignedElement")
	private AssignedElement assignedElement;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "zeusPart2Assessment")
	private ZEUSPart2Assessment zeusPart2Assessment;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "equipments", required = false)
	private Equipments equipments;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "tasks", required = false)
	private Tasks tasks;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "materials", required = false)
	private Materials materials;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "staff", required = false)
	private Employees staff;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "timeReport", required = false)
	private TimeReports timeReport;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "measurements", required = false)
	private Measurements measurements;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "workEquipments", required = false)
	private WorkEquipments workEquipments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "attachments", required = false)
	private Attachments attachments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private WorkReportItems workReportItems;

	/**
	 * Required constructor.
	 */
	public WorkReportItem()
	{
		super();
	}

	public WorkReportItem(String id, String orderItemId, String name, WorkStatusLogs statuses,
						  AssignedElement assignedElement, ZEUSPart2Assessment zeusPart2Assessment,
						  Equipments equipments, String longDescription, Tasks tasks,
						  Materials materials, Employees staff, TimeReports timeReport,
						  Measurements measurements, WorkEquipments workEquipments,
						  Attachments attachments, Comments comments,
						  UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.orderItemId = orderItemId;
		this.name = name;
		this.statuses = statuses;
		this.assignedElement = assignedElement;
		this.zeusPart2Assessment = zeusPart2Assessment;
		this.equipments = equipments;
		this.longDescription = longDescription;
		this.tasks = tasks;
		this.materials = materials;
		this.staff = staff;
		this.timeReport = timeReport;
		this.measurements = measurements;
		this.workEquipments = workEquipments;
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

	public String getOrderItemId()
	{
		return orderItemId;
	}

	public String getName()
	{
		return name;
	}

	public WorkStatusLogs getStatuses()
	{
		return statuses;
	}

	public AssignedElement getAssignedElement()
	{
		return assignedElement;
	}

	public ZEUSPart2Assessment getZeusPart2Assessment()
	{
		return zeusPart2Assessment;
	}

	public Equipments getEquipments()
	{
		return equipments;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Tasks getTasks()
	{
		return tasks;
	}

	public Materials getMaterials()
	{
		return materials;
	}

	public Employees getStaff()
	{
		return staff;
	}

	public TimeReports getTimeReport()
	{
		return timeReport;
	}

	public Measurements getMeasurements()
	{
		return measurements;
	}

	public WorkEquipments getWorkEquipments()
	{
		return workEquipments;
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

	public WorkReportItems getWorkReportItems()
	{
		return workReportItems;
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

	public void setOrderItemId(String orderItemId)
	{
		this.orderItemId = orderItemId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setStatuses(WorkStatusLogs statuses)
	{
		this.statuses = statuses;
	}

	public void setAssignedElement(AssignedElement assignedElement)
	{
		this.assignedElement = assignedElement;
	}

	public void setZeusPart2Assessment(ZEUSPart2Assessment zeusPart2Assessment)
	{
		this.zeusPart2Assessment = zeusPart2Assessment;
	}

	public void setEquipments(Equipments equipments)
	{
		this.equipments = equipments;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setTasks(Tasks tasks)
	{
		this.tasks = tasks;
	}

	public void setMaterials(Materials materials)
	{
		this.materials = materials;
	}

	public void setStaff(Employees staff)
	{
		this.staff = staff;
	}

	public void setTimeReport(TimeReports timeReport)
	{
		this.timeReport = timeReport;
	}

	public void setMeasurements(Measurements measurements)
	{
		this.measurements = measurements;
	}

	public void setWorkEquipments(WorkEquipments workEquipments)
	{
		this.workEquipments = workEquipments;
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

	public void setWorkReportItems(WorkReportItems workReportItems)
	{
		this.workReportItems = workReportItems;
	}
}
