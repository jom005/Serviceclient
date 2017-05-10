package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType LogicalNodeInformation.
 */
@DatabaseTable
@Root
public class LogicalNodeInformation implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "lnIdentifier")
	private String lnIdentifier;
	@DatabaseField(canBeNull = true)
	@Element(name = "description", required = false)
	private String description;

	/**
	 * Required constructor.
	 */
	public LogicalNodeInformation()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param lnIdentifier lnIdentifier element
	 * @param description description element
	 */
	public LogicalNodeInformation(String lnIdentifier, String description)
	{
		this.lnIdentifier = lnIdentifier;
		this.description = description;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getLnIdentifier()
	{
		return lnIdentifier;
	}

	public String getDescription()
	{
		return description;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setLnIdentifier(String lnIdentifier)
	{
		this.lnIdentifier = lnIdentifier;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
