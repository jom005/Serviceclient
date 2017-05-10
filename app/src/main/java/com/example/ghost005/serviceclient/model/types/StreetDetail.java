package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType StreetDetail.
 */
@DatabaseTable
@Root
public class StreetDetail implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "addressGeneral", required = false)
	private String addressGeneral;
	@DatabaseField(canBeNull = true)
	@Element(name = "buildingName", required = false)
	private String buildingName;
	@DatabaseField(canBeNull = true)
	@Element(name = "code", required = false)
	private String code;
	@DatabaseField(canBeNull = true)
	@Element(name = "name", required = false)
	private String name;
	@DatabaseField(canBeNull = true)
	@Element(name = "number", required = false)
	private String number;
	@DatabaseField(canBeNull = true)
	@Element(name = "prefix", required = false)
	private String prefix;
	@DatabaseField(canBeNull = true)
	@Element(name = "suffix", required = false)
	private String suffix;
	@DatabaseField(canBeNull = true)
	@Element(name = "suiteNumber", required = false)
	private String suiteNumber;
	@DatabaseField(canBeNull = true)
	@Element(name = "type", required = false)
	private String type;
	@DatabaseField(canBeNull = true)
	@Element(name = "withinTownLimits", required = false)
	private boolean withinTownLimits;

	/**
	 * Required constructor.
	 */
	public StreetDetail()
	{
		super();
	}

	public StreetDetail(String addressGeneral, String buildingName, String code, String name,
						String number, String prefix, String suffix, String suiteNumber,
						String type, boolean withinTownLimits)
	{
		this.addressGeneral = addressGeneral;
		this.buildingName = buildingName;
		this.code = code;
		this.name = name;
		this.number = number;
		this.prefix = prefix;
		this.suffix = suffix;
		this.suiteNumber = suiteNumber;
		this.type = type;
		this.withinTownLimits = withinTownLimits;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getAddressGeneral()
	{
		return addressGeneral;
	}

	public String getBuildingName()
	{
		return buildingName;
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public String getNumber()
	{
		return number;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public String getSuiteNumber()
	{
		return suiteNumber;
	}

	public String getType()
	{
		return type;
	}

	public boolean isWithinTownLimits()
	{
		return withinTownLimits;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setAddressGeneral(String addressGeneral)
	{
		this.addressGeneral = addressGeneral;
	}

	public void setBuildingName(String buildingName)
	{
		this.buildingName = buildingName;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	public void setSuiteNumber(String suiteNumber)
	{
		this.suiteNumber = suiteNumber;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setWithinTownLimits(boolean withinTownLimits)
	{
		this.withinTownLimits = withinTownLimits;
	}
}
