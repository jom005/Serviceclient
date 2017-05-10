package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType UserSpecificParameterSet.
 */
@DatabaseTable
@Root
public class UserSpecificParameterSet implements Serializable
{
	@DatabaseField(generatedId = true)
	private int _id;
	@Element(name = "id")
	private String id;
	@ElementList(name = "userSpecificParameters", entry = "userSpecificParameters", inline = true)
	private Collection<Parameter> userSpecificParameters;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "longDescription", required = false)
	private String longDescription;
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private UserSpecificContents userSpecificContents;

	/**
	 * Required constructor.
	 */
	public UserSpecificParameterSet()
	{
		super();
	}

	public UserSpecificParameterSet(String id, Collection<Parameter> userSpecificParameters,
									String name, String longDescription, Comments comments)
	{
		this.id = id;
		this.userSpecificParameters = userSpecificParameters;
		this.name = name;
		this.longDescription = longDescription;
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

	public Collection<Parameter> getUserSpecificParameters()
	{
		return userSpecificParameters;
	}

	public String getName()
	{
		return name;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Comments getComments()
	{
		return comments;
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

	public void setUserSpecificParameters(Collection<Parameter> userSpecificParameters)
	{
		this.userSpecificParameters = userSpecificParameters;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}
}
