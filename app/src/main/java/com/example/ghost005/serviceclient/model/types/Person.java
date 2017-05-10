package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Person
 */
@DatabaseTable
@Root
public class Person implements Serializable
{
	public static final String DATABASE_ID = "_id";
	public static final String ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField(columnName = ID)
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "gender")
	private String gender; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "firstName", required = false)
	private String firstName;
	@DatabaseField(canBeNull = true)
	@Element(name = "mName", required = false)
	private String mName;
	@DatabaseField(canBeNull = true)
	@Element(name = "lastName", required = false)
	private String lastName;
	@DatabaseField(canBeNull = true)
	@Element(name = "jobTitle", required = false)
	private String jobTitle;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "electronicAddress", required = false)
	private ElectronicAddress electronicAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "postalAddress", required = false)
	private PostalAddress postalAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "streetAddress", required = false)
	private StreetAddress streetAddress;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "landlinePhone", required = false)
	private TelephoneNumber landlinePhone;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "mobilePhone", required = false)
	private TelephoneNumber mobilePhone;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "fax", required = false)
	private TelephoneNumber fax;
	@DatabaseField(canBeNull = true)
	@Element(name = "prefix", required = false)
	private String prefix;
	@DatabaseField(canBeNull = true)
	@Element(name = "suffix", required = false)
	private String suffix;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Persons persons;

	/**
	 * Required constructor.
	 */
	public Person()
	{
		super();
	}

	public Person(String id, String gender, String firstName, String mName, String lastName,
				  String jobTitle, ElectronicAddress electronicAddress, PostalAddress postalAddress,
				  StreetAddress streetAddress, TelephoneNumber landlinePhone,
				  TelephoneNumber mobilePhone, TelephoneNumber fax, String prefix, String suffix)
	{
		this.id = id;
		this.gender = gender;
		this.firstName = firstName;
		this.mName = mName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.electronicAddress = electronicAddress;
		this.postalAddress = postalAddress;
		this.streetAddress = streetAddress;
		this.landlinePhone = landlinePhone;
		this.mobilePhone = mobilePhone;
		this.fax = fax;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	/* ---- Getter ---- */

	public int get_id()
	{
		return _id;
	}

	public String getId()
	{
		return id;
	}

	public String getGender()
	{
		return gender;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getmName()
	{
		return mName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public ElectronicAddress getElectronicAddress()
	{
		return electronicAddress;
	}

	public PostalAddress getPostalAddress()
	{
		return postalAddress;
	}

	public StreetAddress getStreetAddress()
	{
		return streetAddress;
	}

	public TelephoneNumber getLandlinePhone()
	{
		return landlinePhone;
	}

	public TelephoneNumber getMobilePhone()
	{
		return mobilePhone;
	}

	public TelephoneNumber getFax()
	{
		return fax;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public Persons getPersons()
	{
		return persons;
	}

	/* ---- Setter ---- */

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public void setId(String personId)
	{
		this.id = personId;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setmName(String mName)
	{
		this.mName = mName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public void setElectronicAddress(ElectronicAddress electronicAddress)
	{
		this.electronicAddress = electronicAddress;
	}

	public void setPostalAddress(PostalAddress postalAddress)
	{
		this.postalAddress = postalAddress;
	}

	public void setStreetAddress(StreetAddress streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	public void setLandlinePhone(TelephoneNumber landlinePhone)
	{
		this.landlinePhone = landlinePhone;
	}

	public void setMobilePhone(TelephoneNumber mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public void setFax(TelephoneNumber fax)
	{
		this.fax = fax;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	public void setPersons(Persons persons)
	{
		this.persons = persons;
	}
}
