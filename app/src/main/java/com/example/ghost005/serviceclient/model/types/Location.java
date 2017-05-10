package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Location.
 */
@DatabaseTable
@Root
public class Location implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "streetAddress", required = false)
	private StreetAddress streetAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "geographicPosition", required = false)
	private GeographicLocation geographicPosition;

	/**
	 * Required constructor.
	 */
	public Location()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param streetAddress streetAddress element
	 * @param geographicPosition geographicPosition element
	 */
	public Location(StreetAddress streetAddress, GeographicLocation geographicPosition)
	{
		this.streetAddress = streetAddress;
		this.geographicPosition = geographicPosition;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public StreetAddress getStreetAddress()
	{
		return streetAddress;
	}

	public GeographicLocation getGeographicPosition()
	{
		return geographicPosition;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setStreetAddress(StreetAddress streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	public void setGeographicPosition(GeographicLocation geographicPosition)
	{
		this.geographicPosition = geographicPosition;
	}
}
