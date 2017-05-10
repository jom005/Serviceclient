package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType ApplierElement.
 */
@DatabaseTable
@Root
public class ApplierElement implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "applierElementDesignation")
	private String applierElementDesignation;
	@DatabaseField
	@Element(name = "rdsPPReference")
	private String rdsPPReference;
	@DatabaseField
	@Element(name = "elementName")
	private String elementName;
	@DatabaseField
	@Element(name = "pOIDesignation", required = false)
	private String pOIDesignation;
	@DatabaseField
	@Element(name = "pOIName", required = false)
	private String pOIName;
	@DatabaseField
	@Element(name = "locationDesignation", required = false)
	private String locationDesignation;
	@DatabaseField
	@Element(name = "locationName", required = false)
	private String locationName;

	/**
	 * Required constructor.
	 */
	public ApplierElement()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param applierElementDesignation applierElementDesignation element
	 * @param rdsPPReference            rdsPPReference element
	 * @param elementName               elementName element
	 * @param pOIDesignation            pOIDesignation element
	 * @param pOIName                   pOIName element
	 * @param locationDesignation       locationDesignation element
	 * @param locationName              locationName element
	 */
	public ApplierElement(String applierElementDesignation, String rdsPPReference,
						  String elementName, String pOIDesignation, String pOIName,
						  String locationDesignation, String locationName)
	{
		this.applierElementDesignation = applierElementDesignation;
		this.rdsPPReference = rdsPPReference;
		this.elementName = elementName;
		this.pOIDesignation = pOIDesignation;
		this.pOIName = pOIName;
		this.locationDesignation = locationDesignation;
		this.locationName = locationName;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getApplierElementDesignation()
	{
		return applierElementDesignation;
	}

	public String getRdsPPReference()
	{
		return rdsPPReference;
	}

	public String getElementName()
	{
		return elementName;
	}

	public String getpOIDesignation()
	{
		return pOIDesignation;
	}

	public String getpOIName()
	{
		return pOIName;
	}

	public String getLocationDesignation()
	{
		return locationDesignation;
	}

	public String getLocationName()
	{
		return locationName;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setApplierElementDesignation(String applierElementDesignation)
	{
		this.applierElementDesignation = applierElementDesignation;
	}

	public void setRdsPPReference(String rdsPPReference)
	{
		this.rdsPPReference = rdsPPReference;
	}

	public void setElementName(String elementName)
	{
		this.elementName = elementName;
	}

	public void setpOIDesignation(String pOIDesignation)
	{
		this.pOIDesignation = pOIDesignation;
	}

	public void setpOIName(String pOIName)
	{
		this.pOIName = pOIName;
	}

	public void setLocationDesignation(String locationDesignation)
	{
		this.locationDesignation = locationDesignation;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}
}
