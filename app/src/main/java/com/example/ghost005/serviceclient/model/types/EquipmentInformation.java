package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType EquipmentInformation
 */
@DatabaseTable
@Root
public class EquipmentInformation implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "manufacturer", required = false)
	private Organisation manufacturer;
	@DatabaseField(canBeNull = true)
	@Element(name = "type", required = false)
	private String type;
	@DatabaseField(canBeNull = true)
	@Element(name = "series",required = false)
	private String series;
	@DatabaseField(canBeNull = true)
	@Element(name = "itemNumber", required = false)
	private String itemNumber;
	@DatabaseField(canBeNull = true)
	@Element(name = "gtin", required = false)
	private String gtin;
	@DatabaseField(canBeNull = true)
	@Element(name = "serialNumber", required = false)
	private String serialNumber;
	@DatabaseField(canBeNull = true)
	@Element(name = "dateOfManufacture", required = false)
	private String dateOfManufacture; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "startOfWarranty", required = false)
	private String startOfWarranty; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "endOfWarranty", required = false)
	private String endOfWarranty; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "inventoryNumber", required = false)
	private String inventoryNumber;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	/**
	 * Required constructor.
	 */
	public EquipmentInformation()
	{
		super();
	}

	public EquipmentInformation(String name, Organisation manufacturer, String type, String series,
								String itemNumber, String gtin, String serialNumber,
								String dateOfManufacture,
								String startOfWarranty,
								String endOfWarranty,
								String inventoryNumber, UserSpecificContents userSpecificContents,
								Comments comments)
	{
		this.name = name;
		this.manufacturer = manufacturer;
		this.type = type;
		this.series = series;
		this.itemNumber = itemNumber;
		this.gtin = gtin;
		this.serialNumber = serialNumber;
		this.dateOfManufacture = dateOfManufacture;
		this.startOfWarranty = startOfWarranty;
		this.endOfWarranty = endOfWarranty;
		this.inventoryNumber = inventoryNumber;
		this.userSpecificContents = userSpecificContents;
		this.comments = comments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Organisation getManufacturer()
	{
		return manufacturer;
	}

	public String getType()
	{
		return type;
	}

	public String getSeries()
	{
		return series;
	}

	public String getItemNumber()
	{
		return itemNumber;
	}

	public String getGtin()
	{
		return gtin;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public String getDateOfManufacture()
	{
		return dateOfManufacture;
	}

	public String getStartOfWarranty()
	{
		return startOfWarranty;
	}

	public String getEndOfWarranty()
	{
		return endOfWarranty;
	}

	public String getInventoryNumber()
	{
		return inventoryNumber;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public Comments getComments()
	{
		return comments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setManufacturer(Organisation manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public void setItemNumber(String itemNumber)
	{
		this.itemNumber = itemNumber;
	}

	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}

	public void setDateOfManufacture(String dateOfManufacture)
	{
		this.dateOfManufacture = dateOfManufacture;
	}

	public void setStartOfWarranty(String startOfWarranty)
	{
		this.startOfWarranty = startOfWarranty;
	}

	public void setEndOfWarranty(String endOfWarranty)
	{
		this.endOfWarranty = endOfWarranty;
	}

	public void setInventoryNumber(String inventoryNumber)
	{
		this.inventoryNumber = inventoryNumber;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}
}
