package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType UserSpecificContents.
 */
@DatabaseTable
@Root
public class UserSpecificContents implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "userSpecificContents", entry = "userSpecificContent", inline = true)
	private Collection<UserSpecificParameterSet> userSpecificContents;

	/**
	 * Required constructor.
	 */
	public UserSpecificContents()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param userSpecificContents userSpecificContents element
	 */
	public UserSpecificContents(Collection<UserSpecificParameterSet> userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<UserSpecificParameterSet> getUserSpecificContents()
	{
		return userSpecificContents;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setUserSpecificContents(Collection<UserSpecificParameterSet> userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}
}
