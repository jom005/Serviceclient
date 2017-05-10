package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Employees.
 */
@DatabaseTable
@Root
public class Employees implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "employees", entry = "employee", inline = true)
	private Collection<Employee> employees;

	/**
	 * Required constructor.
	 */
	public Employees()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param employees employees element
	 */
	public Employees(Collection<Employee> employees)
	{
		this.employees = employees;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Employee> getEmployees()
	{
		return employees;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEmployees(Collection<Employee> employees)
	{
		this.employees = employees;
	}
}
