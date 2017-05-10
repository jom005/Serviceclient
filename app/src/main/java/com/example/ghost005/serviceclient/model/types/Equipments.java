package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Equipments.
 */
@DatabaseTable
@Root
public class Equipments implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "equipments", entry = "equipment", inline = true)
	private Collection<Equipment> equipments;

	/**
	 * Required constructor.
	 */
	public Equipments()
	{
		super();
	}

	public Equipments(Collection<Equipment> equipments)
	{
		this.equipments = equipments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Equipment> getEquipments()
	{
		return equipments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEquipments(Collection<Equipment> equipments)
	{
		this.equipments = equipments;
	}
}
