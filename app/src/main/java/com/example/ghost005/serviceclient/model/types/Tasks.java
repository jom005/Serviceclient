package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Tasks.
 */
@DatabaseTable
@Root
public class Tasks implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "tasks", entry = "task", inline = true)
	private Collection<Task> tasks;

	/**
	 * Required constructor.
	 */
	public Tasks()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param tasks tasks element
	 */
	public Tasks(Collection<Task> tasks)
	{
		this.tasks = tasks;
	}

	/* ---- Getter ---- */

	public Collection<Task> getTasks()
	{
		return tasks;
	}

	/* ---- Setter ---- */
	public void setTasks(Collection<Task> tasks)
	{
		this.tasks = tasks;
	}
}
