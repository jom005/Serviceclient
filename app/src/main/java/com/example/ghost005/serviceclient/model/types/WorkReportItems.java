package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType WorkReportItems.
 */
@DatabaseTable
@Root
public class WorkReportItems implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "workReportItems", entry = "item", inline = true)
	private Collection<WorkReportItem> workReportItems;

	/**
	 * Required constructor.
	 */
	public WorkReportItems()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param workReportItems workReportItems element
	 */
	public WorkReportItems(Collection<WorkReportItem> workReportItems)
	{
		this.workReportItems = workReportItems;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<WorkReportItem> getWorkReportItems()
	{
		return workReportItems;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setWorkReportItems(Collection<WorkReportItem> workReportItems)
	{
		this.workReportItems = workReportItems;
	}
}
