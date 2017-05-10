package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType WorkEquipments.
 */
@DatabaseTable
@Root
public class WorkEquipments implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "workEquipments", entry = "workEquipment", inline = true)
	private Collection<WorkEquipment> workEquipments;

	/**
	 * Required constructor.
	 */
	public WorkEquipments()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param workEquipments workEquipments element
	 */
	public WorkEquipments(Collection<WorkEquipment> workEquipments)
	{
		this.workEquipments = workEquipments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<WorkEquipment> getWorkEquipments()
	{
		return workEquipments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setWorkEquipments(Collection<WorkEquipment> workEquipments)
	{
		this.workEquipments = workEquipments;
	}
}
