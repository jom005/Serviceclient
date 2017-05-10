package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Class for ComplexType Persons.
 */
@DatabaseTable
@Root
public class Persons implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "persons", entry = "person", inline = true)
	private Collection<Person> persons;

	/**
	 * Required constructor.
	 */
	public Persons()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param persons persons element
	 */
	public Persons(List<Person> persons)
	{
		this.persons = persons;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Person> getPersons()
	{
		return persons;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setPersons(Collection<Person> persons)
	{
		this.persons = persons;
	}
}
