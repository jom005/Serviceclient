package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType ZEUSPart2Assessment.
 */
@DatabaseTable
@Root
public class ZEUSPart2Assessment implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "zeusPart2", entry = "zeusPart2", inline = true)
	private Collection<ZEUSPart2> zeusPart2;

	/**
	 * Required constructor.
	 */
	public ZEUSPart2Assessment()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param zeusPart2 zeusPart2 element
	 */
	public ZEUSPart2Assessment(Collection<ZEUSPart2> zeusPart2)
	{
		this.zeusPart2 = zeusPart2;
	}

	/* ---- Getter ---- */
	public Collection<ZEUSPart2> getZeusPart2()
	{
		return zeusPart2;
	}

	/* ---- Setter ---- */
	public void setZeusPart2(Collection<ZEUSPart2> zeusPart2)
	{
		this.zeusPart2 = zeusPart2;
	}
}
