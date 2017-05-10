package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType PowerPlant.
 */
@DatabaseTable
@Root
public class PowerPlant implements Serializable
{
	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField
	@Element(name = "rdsPPConjoint")
	private String rdsPPConjoint;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "operator")
	private Organisation operator;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "owner")
	private Organisation owner;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "address")
	private Location address;
	@DatabaseField(canBeNull = true)
	@Element(name = "description", required = false)
	private String description;
	@DatabaseField(canBeNull = true)
	@Element(name = "numberOfGeneratingUnits", required = false)
	private BigInteger numberOfGeneratingUnits;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "ratedPower", required = false)
	private GeneralValue ratedPower;
	@DatabaseField(canBeNull = true)
	@Element(name = "operationalSince", required = false)
	private String operationalSince; //XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	/**
	 * Required constructor.
	 */
	public PowerPlant()
	{
		super();
	}

	public PowerPlant(String id, String name, String rdsPPConjoint, Organisation operator, Organisation owner, Location address, String description, BigInteger numberOfGeneratingUnits, GeneralValue ratedPower, String operationalSince, Comments comments, UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.name = name;
		this.rdsPPConjoint = rdsPPConjoint;
		this.operator = operator;
		this.owner = owner;
		this.address = address;
		this.description = description;
		this.numberOfGeneratingUnits = numberOfGeneratingUnits;
		this.ratedPower = ratedPower;
		this.operationalSince = operationalSince;
		this.comments = comments;
		this.userSpecificContents = userSpecificContents;
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

	public String getName()
	{
		return name;
	}

	public String getRdsPPConjoint()
	{
		return rdsPPConjoint;
	}

	public Organisation getOperator()
	{
		return operator;
	}

	public Organisation getOwner()
	{
		return owner;
	}

	public Location getAddress()
	{
		return address;
	}

	public String getDescription()
	{
		return description;
	}

	public BigInteger getNumberOfGeneratingUnits()
	{
		return numberOfGeneratingUnits;
	}

	public GeneralValue getRatedPower()
	{
		return ratedPower;
	}

	public String getOperationalSince()
	{
		return operationalSince;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	/* ---- Setter ---- */

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRdsPPConjoint(String rdsPPConjoint)
	{
		this.rdsPPConjoint = rdsPPConjoint;
	}

	public void setOperator(Organisation operator)
	{
		this.operator = operator;
	}

	public void setOwner(Organisation owner)
	{
		this.owner = owner;
	}

	public void setAddress(Location address)
	{
		this.address = address;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setNumberOfGeneratingUnits(BigInteger numberOfGeneratingUnits)
	{
		this.numberOfGeneratingUnits = numberOfGeneratingUnits;
	}

	public void setRatedPower(GeneralValue ratedPower)
	{
		this.ratedPower = ratedPower;
	}

	public void setOperationalSince(String operationalSince)
	{
		this.operationalSince = operationalSince;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}
}
