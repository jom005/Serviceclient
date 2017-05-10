package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType TownDetail.
 */
@DatabaseTable
@Root
public class TownDetail implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "code", required = false)
	private String code;
	@DatabaseField(canBeNull = true)
	@Element(name = "country", required = false)
	private String country; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "name" , required = false)
	private String name;
	@DatabaseField(canBeNull = true)
	@Element(name = "section", required = false)
	private String section;
	@DatabaseField(canBeNull = true)
	@Element(name = "stateOrProvince", required = false)
	private String stateOrProvince;

	/**
	 * Required constructor.
	 */
	public TownDetail()
	{
		super();
	}

	public TownDetail(String code, String country, String name, String section,
					  String stateOrProvince)
	{
		this.code = code;
		this.country = country;
		this.name = name;
		this.section = section;
		this.stateOrProvince = stateOrProvince;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getCode()
	{
		return code;
	}

	public String getCountry()
	{
		return country;
	}

	public String getName()
	{
		return name;
	}

	public String getSection()
	{
		return section;
	}

	public String getStateOrProvince()
	{
		return stateOrProvince;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSection(String section)
	{
		this.section = section;
	}

	public void setStateOrProvince(String stateOrProvince)
	{
		this.stateOrProvince = stateOrProvince;
	}
}
