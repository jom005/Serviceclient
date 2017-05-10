package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType AssignedElement.
 */
@DatabaseTable
@Root
public class AssignedElement implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "rdsPPDesignation")
	private RDSPPElement rdsPPDesignation;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "applierDesignation", required = false)
	private ApplierElement applierDesignation;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(canBeNull = true)
	@Element(name = "objectPart", required = false)
	private String objectPart;
	@DatabaseField(canBeNull = true)
	@Element(name = "startOfWarranty", required = false)
	private String startOfWarranty; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "endOfWarranty", required = false)
	private String endOfWarranty; // XMLGregorianCalendar

	/**
	 * Required constructor.
	 */
	public AssignedElement()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param name name element
	 * @param rdsPPDesignation rdsPPDesignation element
	 * @param applierDesignation applierDesignation element
	 * @param userSpecificContents userSpecificContents element
	 * @param comments comments element
	 * @param objectPart objectPart element
	 * @param startOfWarranty startOfWarranty element
	 * @param endOfWarranty endOfWarranty element
	 */
	public AssignedElement(String name, RDSPPElement rdsPPDesignation,
						   ApplierElement applierDesignation,
						   UserSpecificContents userSpecificContents, Comments comments,
						   String objectPart, String startOfWarranty,
						   String endOfWarranty)
	{
		this.name = name;
		this.rdsPPDesignation = rdsPPDesignation;
		this.applierDesignation = applierDesignation;
		this.userSpecificContents = userSpecificContents;
		this.comments = comments;
		this.objectPart = objectPart;
		this.startOfWarranty = startOfWarranty;
		this.endOfWarranty = endOfWarranty;
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

	public RDSPPElement getRdsPPDesignation()
	{
		return rdsPPDesignation;
	}

	public ApplierElement getApplierDesignation()
	{
		return applierDesignation;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public Comments getComments()
	{
		return comments;
	}

	public String getObjectPart()
	{
		return objectPart;
	}

	public String getStartOfWarranty()
	{
		return startOfWarranty;
	}

	public String getEndOfWarranty()
	{
		return endOfWarranty;
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

	public void setRdsPPDesignation(RDSPPElement rdsPPDesignation)
	{
		this.rdsPPDesignation = rdsPPDesignation;
	}

	public void setApplierDesignation(ApplierElement applierDesignation)
	{
		this.applierDesignation = applierDesignation;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setObjectPart(String objectPart)
	{
		this.objectPart = objectPart;
	}

	public void setStartOfWarranty(String startOfWarranty)
	{
		this.startOfWarranty = startOfWarranty;
	}

	public void setEndOfWarranty(String endOfWarranty)
	{
		this.endOfWarranty = endOfWarranty;
	}
}
