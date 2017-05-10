package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType TelephoneNumber.
 */
@DatabaseTable
@Root
public class TelephoneNumber implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "areaCode", required = false)
	private String areaCode;
	@DatabaseField(canBeNull = true)
	@Element(name = "cityCode", required = false)
	private String cityCode;
	@DatabaseField(canBeNull = true)
	@Element(name = "countryCode", required = false)
	private String countryCode;
	@DatabaseField(canBeNull = true)
	@Element(name = "extension", required = false)
	private String extension;
	@DatabaseField(canBeNull = true)
	@Element(name = "localNumber", required = false)
	private String localNumber;

	/**
	 * Required constructor.
	 */
	public TelephoneNumber()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param areaCode areaCode element
	 * @param cityCode cityCode element
	 * @param countryCode countryCode element
	 * @param extension extension element
	 * @param localNumber localNumber element
	 */
	public TelephoneNumber(String areaCode, String cityCode, String countryCode, String extension,
						   String localNumber)
	{
		this.areaCode = areaCode;
		this.cityCode = cityCode;
		this.countryCode = countryCode;
		this.extension = extension;
		this.localNumber = localNumber;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public String getCityCode()
	{
		return cityCode;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public String getExtension()
	{
		return extension;
	}

	public String getLocalNumber()
	{
		return localNumber;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public void setExtension(String extension)
	{
		this.extension = extension;
	}

	public void setLocalNumber(String localNumber)
	{
		this.localNumber = localNumber;
	}
}
