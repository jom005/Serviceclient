package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType ZEUSPart1Assessment.
 */
@DatabaseTable
@Root
public class ZEUSPart1Assessment implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "zeusPart1", entry = "zeusPart1", inline = true)
	private Collection<ZEUSPart1> zeusPart1;

	/**
	 * Required constructor.
	 */
	public ZEUSPart1Assessment()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param zeusPart1 zeusPart1 element
	 */
	public ZEUSPart1Assessment(Collection<ZEUSPart1> zeusPart1)
	{
		this.zeusPart1 = zeusPart1;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<ZEUSPart1> getZeusPart1()
	{
		return zeusPart1;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setZeusPart1(Collection<ZEUSPart1> zeusPart1)
	{
		this.zeusPart1 = zeusPart1;
	}
}
