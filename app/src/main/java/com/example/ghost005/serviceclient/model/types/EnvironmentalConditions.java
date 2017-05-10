package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType EnvironmentalConditions.
 */
@DatabaseTable
@Root
public class EnvironmentalConditions implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "environmentalConditions", entry = "environmentalCondition", inline = true)
	private Collection<EnvironmentalCondition> environmentalConditions;

	/**
	 * Required constructor.
	 */
	public EnvironmentalConditions()
	{
		super();
	}

	public EnvironmentalConditions(Collection<EnvironmentalCondition> environmentalConditions)
	{
		this.environmentalConditions = environmentalConditions;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<EnvironmentalCondition> getEnvironmentalConditions()
	{
		return environmentalConditions;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEnvironmentalConditions(Collection<EnvironmentalCondition> environmentalConditions)
	{
		this.environmentalConditions = environmentalConditions;
	}
}
