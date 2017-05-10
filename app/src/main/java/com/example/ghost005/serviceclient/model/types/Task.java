package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Task.
 */
@DatabaseTable
@Root
public class Task implements Serializable
{
	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField
	@Element(name = "description")
	private String description;
	@DatabaseField(canBeNull = true)
	@Element(name = "type", required = false)
	private String type;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "status", required = false)
	private TaskStatusLog status;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "timeReport", required = false)
	private TimeReports timeReport;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "workEquipments", required = false)
	private WorkEquipments workEquipments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Tasks tasks;

	/**
	 * Required constructor.
	 */
	public Task()
	{
		super();
	}

	public Task(String id, String name, String description, String type, TaskStatusLog status,
				TimeReports timeReport, WorkEquipments workEquipments, Comments comments)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.status = status;
		this.timeReport = timeReport;
		this.workEquipments = workEquipments;
		this.comments = comments;
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

	public String getDescription()
	{
		return description;
	}

	public String getType()
	{
		return type;
	}

	public TaskStatusLog getStatus()
	{
		return status;
	}

	public TimeReports getTimeReport()
	{
		return timeReport;
	}

	public WorkEquipments getWorkEquipments()
	{
		return workEquipments;
	}

	public Comments getComments()
	{
		return comments;
	}

	public Tasks getTasks()
	{
		return tasks;
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

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setStatus(TaskStatusLog status)
	{
		this.status = status;
	}

	public void setTimeReport(TimeReports timeReport)
	{
		this.timeReport = timeReport;
	}

	public void setWorkEquipments(WorkEquipments workEquipments)
	{
		this.workEquipments = workEquipments;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setTasks(Tasks tasks)
	{
		this.tasks = tasks;
	}
}
