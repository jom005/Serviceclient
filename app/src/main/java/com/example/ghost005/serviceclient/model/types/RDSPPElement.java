package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType RDSPPElement.
 */
@DatabaseTable
@Root
public class RDSPPElement implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "rdsppElementDesignation")
	private String rdsppElementDesignation;
	@DatabaseField
	@Element(name = "elementName")
	private String elementName;
	@DatabaseField(canBeNull = true)
	@Element(name = "elementParent", required = false)
	private String elementParent;
	@DatabaseField(canBeNull = true)
	@Element(name = "pOIDesignation", required = false)
	private String pOIDesignation;
	@DatabaseField(canBeNull = true)
	@Element(name = "pOIName", required = false)
	private String pOIName;
	@DatabaseField(canBeNull = true)
	@Element(name = "pOIParent", required = false)
	private String pOIParent;
	@DatabaseField(canBeNull = true)
	@Element(name = "locationDesignation", required = false)
	private String locationDesignation;
	@DatabaseField(canBeNull = true)
	@Element(name = "locationName", required = false)
	private String locationName;
	@DatabaseField(canBeNull = true)
	@Element(name = "locationParent", required = false)
	private String locationParent;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private RDSPPStructure rdsppStructure;

	/**
	 * Required constructor.
	 */
	public RDSPPElement()
	{
		super();
	}

	public RDSPPElement(String rdsppElementDesignation, String elementName, String elementParent, String pOIDesignation, String pOIName, String pOIParent, String locationDesignation, String locationName, String locationParent)
	{
		this.rdsppElementDesignation = rdsppElementDesignation;
		this.elementName = elementName;
		this.elementParent = elementParent;
		this.pOIDesignation = pOIDesignation;
		this.pOIName = pOIName;
		this.pOIParent = pOIParent;
		this.locationDesignation = locationDesignation;
		this.locationName = locationName;
		this.locationParent = locationParent;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getRdsppElementDesignation()
	{
		return rdsppElementDesignation;
	}

	public String getElementName()
	{
		return elementName;
	}

	public String getElementParent()
	{
		return elementParent;
	}

	public String getpOIDesignation()
	{
		return pOIDesignation;
	}

	public String getpOIName()
	{
		return pOIName;
	}

	public String getpOIParent()
	{
		return pOIParent;
	}

	public String getLocationDesignation()
	{
		return locationDesignation;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public String getLocationParent()
	{
		return locationParent;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setRdsppElementDesignation(String rdsppElementDesignation)
	{
		this.rdsppElementDesignation = rdsppElementDesignation;
	}

	public void setElementName(String elementName)
	{
		this.elementName = elementName;
	}

	public void setElementParent(String elementParent)
	{
		this.elementParent = elementParent;
	}

	public void setpOIDesignation(String pOIDesignation)
	{
		this.pOIDesignation = pOIDesignation;
	}

	public void setpOIName(String pOIName)
	{
		this.pOIName = pOIName;
	}

	public void setpOIParent(String pOIParent)
	{
		this.pOIParent = pOIParent;
	}

	public void setLocationDesignation(String locationDesignation)
	{
		this.locationDesignation = locationDesignation;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public void setLocationParent(String locationParent)
	{
		this.locationParent = locationParent;
	}
}
