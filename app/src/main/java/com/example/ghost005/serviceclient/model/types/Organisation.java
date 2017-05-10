package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Organisation.
 */
@DatabaseTable
@Root
public class Organisation implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField(canBeNull = true)
	@Element(name = "taxNumber", required = false)
	private String taxNumber;
	@DatabaseField(canBeNull = true)
	@Element(name = "gln", required = false)
	private String gln;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "contacts", required = false)
	private Persons contacts;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "electronicAddress", required = false)
	private ElectronicAddress electronicAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "phone1", required = false)
	private TelephoneNumber phone1;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "phone2", required = false)
	private TelephoneNumber phone2;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "fax", required = false)
	private TelephoneNumber fax;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "postalAddress", required = false)
	private PostalAddress postalAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "streetAddress", required = false)
	private StreetAddress streetAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "geoLocation", required = false)
	private GeographicLocation geoLocation;

	/**
	 * Required constructor.
	 */
	public Organisation()
	{
		super();
	}

	public Organisation(String name, String taxNumber, String gln, Persons contacts,
						ElectronicAddress electronicAddress, TelephoneNumber phone1,
						TelephoneNumber phone2, TelephoneNumber fax, PostalAddress postalAddress,
						StreetAddress streetAddress, GeographicLocation geoLocation)
	{
		this.name = name;
		this.taxNumber = taxNumber;
		this.gln = gln;
		this.contacts = contacts;
		this.electronicAddress = electronicAddress;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.postalAddress = postalAddress;
		this.streetAddress = streetAddress;
		this.geoLocation = geoLocation;
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

	public String getTaxNumber()
	{
		return taxNumber;
	}

	public String getGln()
	{
		return gln;
	}

	public Persons getContacts()
	{
		return contacts;
	}

	public ElectronicAddress getElectronicAddress()
	{
		return electronicAddress;
	}

	public TelephoneNumber getPhone1()
	{
		return phone1;
	}

	public TelephoneNumber getPhone2()
	{
		return phone2;
	}

	public TelephoneNumber getFax()
	{
		return fax;
	}

	public PostalAddress getPostalAddress()
	{
		return postalAddress;
	}

	public StreetAddress getStreetAddress()
	{
		return streetAddress;
	}

	public GeographicLocation getGeographicLocation()
	{
		return geoLocation;
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

	public void setTaxNumber(String taxNumber)
	{
		this.taxNumber = taxNumber;
	}

	public void setGln(String gln)
	{
		this.gln = gln;
	}

	public void setContacts(Persons contacts)
	{
		this.contacts = contacts;
	}

	public void setElectronicAddress(ElectronicAddress electronicAddress)
	{
		this.electronicAddress = electronicAddress;
	}

	public void setPhone1(TelephoneNumber phone1)
	{
		this.phone1 = phone1;
	}

	public void setPhone2(TelephoneNumber phone2)
	{
		this.phone2 = phone2;
	}

	public void setFax(TelephoneNumber fax)
	{
		this.fax = fax;
	}

	public void setPostalAddress(PostalAddress postalAddress)
	{
		this.postalAddress = postalAddress;
	}

	public void setStreetAddress(StreetAddress streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	public void setGeoLocation(GeographicLocation geoLocation)
	{
		this.geoLocation = geoLocation;
	}
}
