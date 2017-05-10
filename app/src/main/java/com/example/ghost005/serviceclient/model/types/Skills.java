package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Skills.
 */
@DatabaseTable
@Root
public class Skills implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "skills", entry = "skill", inline = true)
	private Collection<Skill> skills;

	/**
	 * Required constructor.
	 */
	public Skills()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param skills skills element
	 */
	public Skills(Collection<Skill> skills)
	{
		this.skills = skills;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Skill> getSkills()
	{
		return skills;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setSkills(Collection<Skill> skills)
	{
		this.skills = skills;
	}
}
