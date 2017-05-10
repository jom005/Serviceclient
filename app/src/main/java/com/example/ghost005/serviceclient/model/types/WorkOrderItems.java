package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType WorkOrderItems.
 */
@DatabaseTable
@Root
public class WorkOrderItems implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "workOrderItems", entry = "item", inline = true)
	private Collection<WorkOrderItem> workOrderItems;

	/**
	 * Required constructor.
	 */
	public WorkOrderItems()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param workOrderItems workOrderItems element
	 */
	public WorkOrderItems(Collection<WorkOrderItem> workOrderItems)
	{
		this.workOrderItems = workOrderItems;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<WorkOrderItem> getWorkOrderItems()
	{
		return workOrderItems;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setWorkOrderItems(Collection<WorkOrderItem> workOrderItems)
	{
		this.workOrderItems = workOrderItems;
	}
}
