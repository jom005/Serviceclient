package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType WorkLog.
 */
@DatabaseTable
@Root
public class WorkLog implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "workLogEntries", entry = "entry", inline = true)
	private Collection<WorkLogEntry> workLogEntries;

	/**
	 * Required constructor.
	 */
	public WorkLog()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param workLogEntries workLogEntries element
	 */
	public WorkLog(Collection<WorkLogEntry> workLogEntries)
	{
		this.workLogEntries = workLogEntries;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<WorkLogEntry> getWorkLogEntries()
	{
		return workLogEntries;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setWorkLogEntries(Collection<WorkLogEntry> workLogEntries)
	{
		this.workLogEntries = workLogEntries;
	}
}
