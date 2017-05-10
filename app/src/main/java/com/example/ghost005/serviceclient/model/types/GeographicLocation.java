package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class for ComplexType GeographicLocation.
 */
@DatabaseTable
@Root
public class GeographicLocation implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "longitude", required = false)
	private BigDecimal longitude;
	@DatabaseField(canBeNull = true)
	@Element(name = "latitude", required = false)
	private BigDecimal latitude;

	/**
	 * Required constructor.
	 */
	public GeographicLocation()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param longitude longitude element
	 * @param latitude latitude element
	 */
	public GeographicLocation(BigDecimal longitude, BigDecimal latitude)
	{
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public BigDecimal getLongitude()
	{
		return longitude;
	}

	public BigDecimal getLatitude()
	{
		return latitude;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setLongitude(BigDecimal longitude)
	{
		this.longitude = longitude;
	}

	public void setLatitude(BigDecimal latitude)
	{
		this.latitude = latitude;
	}
}
