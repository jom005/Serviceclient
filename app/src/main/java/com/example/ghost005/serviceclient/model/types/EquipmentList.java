package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType EquipmentList.
 */
@DatabaseTable
@Root
public class EquipmentList implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "equipmentListEntries", entry = "equipmentListEntry", inline = true)
	private Collection<EquipmentListEntry> equipmentListEntries;

	/**
	 * Required constructor.
	 */
	public EquipmentList()
	{
		super();
	}

	public EquipmentList(Collection<EquipmentListEntry> equipmentListEntries)
	{
		this.equipmentListEntries = equipmentListEntries;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<EquipmentListEntry> getEquipmentListEntries()
	{
		return equipmentListEntries;
	}

	/* ---- Setter ----*/

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEquipmentListEntries(Collection<EquipmentListEntry> equipmentListEntries)
	{
		this.equipmentListEntries = equipmentListEntries;
	}
}
