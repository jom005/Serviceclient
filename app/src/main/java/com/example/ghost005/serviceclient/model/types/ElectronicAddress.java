package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType ElectronicAddress.
 */
@DatabaseTable
@Root
public class ElectronicAddress implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "email1", required = false)
	private String email1;
	@DatabaseField(canBeNull = true)
	@Element(name = "email2", required = false)
	private String email2;
	@DatabaseField(canBeNull = true)
	@Element(name = "web", required = false)
	private String web;

	/**
	 * Required constructor.
	 */
	public ElectronicAddress()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param email1 email element
	 * @param email2 email2 element
	 * @param web web element
	 */
	public ElectronicAddress(String email1, String email2, String web)
	{
		this.email1 = email1;
		this.email2 = email2;
		this.web = web;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getEmail()
	{
		return email1;
	}

	public String getEmail2()
	{
		return email2;
	}

	public String getWeb()
	{
		return web;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEmail1(String email1)
	{
		this.email1 = email1;
	}

	public void setEmail2(String email2)
	{
		this.email2 = email2;
	}

	public void setWeb(String web)
	{
		this.web = web;
	}
}
