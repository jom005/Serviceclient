package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType PriorityLog.
 */
@DatabaseTable
@Root
public class PriorityLog implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "priorityEntries", entry = "priorityEntry", inline = true)
	private Collection<PriorityEntry> priorityEntries;

	/**
	 * Required constructor.
	 */
	public PriorityLog()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param priorityEntries priorityEntries element
	 */
	public PriorityLog(Collection<PriorityEntry> priorityEntries)
	{
		this.priorityEntries = priorityEntries;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<PriorityEntry> getPriorityEntries()
	{
		return priorityEntries;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setPriorityEntries(Collection<PriorityEntry> priorityEntries)
	{
		this.priorityEntries = priorityEntries;
	}
}
