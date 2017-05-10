package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType WorkStatusLogs.
 */
@DatabaseTable
@Root
public class WorkStatusLogs implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "workStatusLogs", entry = "status", inline = true)
	private Collection<WorkStatusLog> workStatusLogs;

	/**
	 * Required constructor.
	 */
	public WorkStatusLogs()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param workStatusLogs workStatusLogs element
	 */
	public WorkStatusLogs(Collection<WorkStatusLog> workStatusLogs)
	{
		this.workStatusLogs = workStatusLogs;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<WorkStatusLog> getWorkStatusLogs()
	{
		return workStatusLogs;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setWorkStatusLogs(Collection<WorkStatusLog> workStatusLogs)
	{
		this.workStatusLogs = workStatusLogs;
	}
}
