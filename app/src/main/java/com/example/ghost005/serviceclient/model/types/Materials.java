package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Materials.
 */
@DatabaseTable
@Root
public class Materials implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "materials", entry = "material", inline = true)
	private Collection<Material> materials;

	/**
	 * Required constructor.
	 */
	public Materials()
	{
		super();
	}

	/**
	 * Cnstructor sets elements
	 *
	 * @param materials element
	 */
	public Materials(Collection<Material> materials)
	{
		this.materials = materials;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Material> getMaterials()
	{
		return materials;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMaterials(Collection<Material> materials)
	{
		this.materials = materials;
	}
}
