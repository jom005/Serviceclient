package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Employee.
 */
@DatabaseTable
@Root
public class Employee extends Person implements Serializable
{
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "organisation", required = false)
	private Organisation organisation;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "skills", required = false)
	private Skills skills;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Employees employees;

	/**
	 * Required constructor.
	 */
	public Employee()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param organisation organisation element
	 * @param skills skills element
	 */
	public Employee(Organisation organisation, Skills skills)
	{
		this.organisation = organisation;
		this.skills = skills;
	}

	/* ---- Getter ---- */

	public Organisation getOrganisation()
	{
		return organisation;
	}

	public Skills getSkills()
	{
		return skills;
	}

	public Employees getEmployees()
	{
		return employees;
	}

	/* ---- Setter ---- */

	public void setOrganisation(Organisation organisation)
	{
		this.organisation = organisation;
	}

	public void setSkills(Skills skills)
	{
		this.skills = skills;
	}

	public void setEmployees(Employees employees)
	{
		this.employees = employees;
	}
}
