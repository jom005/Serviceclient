package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Skill;
 */
@DatabaseTable
@Root
public class Skill implements Serializable
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
	@Element(name = "longDescription")
	private String longDescription;
	@DatabaseField(canBeNull = true)
	@Element(name = "certificationDate", required = false)
	private String certificationDate; //XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "expirationDate", required = false)
	private String expirationDate; //XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Skills skills;

	/**
	 * Required constructor.
	 */
	public Skill()
	{
		super();
	}

	public Skill(String id, String name, String longDescription, String certificationDate, String expirationDate, Comments comments)
	{
		this.id = id;
		this.name = name;
		this.longDescription = longDescription;
		this.certificationDate = certificationDate;
		this.expirationDate = expirationDate;
		this.comments = comments;
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

	public String getLongDescription()
	{
		return longDescription;
	}

	public String getCertificationDate()
	{
		return certificationDate;
	}

	public String getExpirationDate()
	{
		return expirationDate;
	}

	public Comments getComments()
	{
		return comments;
	}

	public Skills getSkills()
	{
		return skills;
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

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setCertificationDate(String certificationDate)
	{
		this.certificationDate = certificationDate;
	}

	public void setExpirationDate(String expirationDate)
	{
		this.expirationDate = expirationDate;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setSkills(Skills skills)
	{
		this.skills = skills;
	}
}
