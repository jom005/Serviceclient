package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType WorkOrderItem.
 */
@DatabaseTable
@Root
public class WorkOrderItem implements Serializable
{
	public static final String STATUS_OPEN = "open";
	public static final String STATUS_IN_PROGRESS = "in_progress";
	public static final String STATUS_CLOSED = "closed";
	public static final String DATABASE_ID = "_id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "statuses")
	private WorkStatusLogs statuses;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "assignedElement")
	private AssignedElement assignedElement;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "zeusPart2History")
	private ZEUSPart2Assessment zeusPart2History;
	@DatabaseField(canBeNull = true)
	@Element(name = "maintenanceLevel", required = false)
	private String maintenanceLevel; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "equipments", required = false)
	private Equipments equipments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "tasks", required = false)
	private Tasks tasks;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "timeReport", required = false)
	private TimeReports timeReport;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "materials", required = false)
	private Materials materials;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "staff", required = false)
	private Employees staff;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "workEquipments", required = false)
	private WorkEquipments workEquipments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "attachments", required = false)
	private Attachments attachments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "skills", required = false)
	private Skills skills;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "measurements", required = false)
	private Measurements measurements;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private WorkOrderItems workOrderItems;

	@DatabaseField(canBeNull = true)
	private String progress;
	@DatabaseField(canBeNull = true)
	private BigInteger page;

	/**
	 * Required constructor.
	 */
	public WorkOrderItem()
	{
		super();
	}

	public WorkOrderItem(String id, String name, WorkStatusLogs statuses,
						 AssignedElement assignedElement, ZEUSPart2Assessment zeusPart2History,
						 String maintenanceLevel, String longDescription, Equipments equipments,
						 Tasks tasks, TimeReports timeReport, Materials materials, Employees staff,
						 WorkEquipments workEquipments, Attachments attachments, Skills skills,
						 Measurements measurements, Comments comments,
						 UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.name = name;
		this.statuses = statuses;
		this.assignedElement = assignedElement;
		this.zeusPart2History = zeusPart2History;
		this.maintenanceLevel = maintenanceLevel;
		this.longDescription = longDescription;
		this.equipments = equipments;
		this.tasks = tasks;
		this.timeReport = timeReport;
		this.materials = materials;
		this.staff = staff;
		this.workEquipments = workEquipments;
		this.attachments = attachments;
		this.skills = skills;
		this.measurements = measurements;
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

	public AssignedElement getAssignedElement()
	{
		return assignedElement;
	}

	public ZEUSPart2Assessment getZeusPart2History()
	{
		return zeusPart2History;
	}

	public String getMaintenanceLevel()
	{
		return maintenanceLevel;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Equipments getEquipments()
	{
		return equipments;
	}

	public Tasks getTasks()
	{
		return tasks;
	}

	public TimeReports getTimeReport()
	{
		return timeReport;
	}

	public Materials getMaterials()
	{
		return materials;
	}

	public Employees getStaff()
	{
		return staff;
	}

	public WorkEquipments getWorkEquipments()
	{
		return workEquipments;
	}

	public Attachments getAttachments()
	{
		return attachments;
	}

	public Skills getSkills()
	{
		return skills;
	}

	public Measurements getMeasurements()
	{
		return measurements;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public WorkOrderItems getWorkOrderItems()
	{
		return workOrderItems;
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

	public void setStatuses(WorkStatusLogs statuses)
	{
		this.statuses = statuses;
	}

	public void setAssignedElement(AssignedElement assignedElement)
	{
		this.assignedElement = assignedElement;
	}

	public void setZeusPart2History(ZEUSPart2Assessment zeusPart2History)
	{
		this.zeusPart2History = zeusPart2History;
	}

	public void setMaintenanceLevel(String maintenanceLevel)
	{
		this.maintenanceLevel = maintenanceLevel;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setEquipments(Equipments equipments)
	{
		this.equipments = equipments;
	}

	public void setTasks(Tasks tasks)
	{
		this.tasks = tasks;
	}

	public void setTimeReport(TimeReports timeReport)
	{
		this.timeReport = timeReport;
	}

	public void setMaterials(Materials materials)
	{
		this.materials = materials;
	}

	public void setStaff(Employees staff)
	{
		this.staff = staff;
	}

	public void setWorkEquipments(WorkEquipments workEquipments)
	{
		this.workEquipments = workEquipments;
	}

	public void setAttachments(Attachments attachments)
	{
		this.attachments = attachments;
	}

	public void setSkills(Skills skills)
	{
		this.skills = skills;
	}

	public void setMeasurements(Measurements measurements)
	{
		this.measurements = measurements;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setWorkOrderItems(WorkOrderItems workOrderItems)
	{
		this.workOrderItems = workOrderItems;
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
