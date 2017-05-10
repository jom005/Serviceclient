package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Attachment.
 */
@DatabaseTable
@Root
public class Attachment implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "name", required = false)
	private String name;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "files")
	private Files files;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "responsibleEmployee", required = false)
	private Employee responsibleEmployee;
	@DatabaseField(canBeNull = true)
	@Element(name = "longDescription", required = false)
	private String longDescription;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Attachments attachments;

	/**
	 * Required constructor.
	 */
	public Attachment()
	{
		super();
	}

	/**
	 * Constructor sets Elements
	 *
	 * @param name                name element
	 * @param files               files element
	 * @param responsibleEmployee responsibleEmployee element
	 * @param longDescription     longDescription element
	 */
	public Attachment(String name, Files files, Employee responsibleEmployee, String longDescription)
	{
		this.name = name;
		this.files = files;
		this.responsibleEmployee = responsibleEmployee;
		this.longDescription = longDescription;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Files getFiles()
	{
		return files;
	}

	public Employee getResponsibleEmployee()
	{
		return responsibleEmployee;
	}

	public String getLongDescription()
	{
		return longDescription;
	}

	public Attachments getAttachments()
	{
		return attachments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setFiles(Files files)
	{
		this.files = files;
	}

	public void setResponsibleEmployee(Employee responsibleEmployee)
	{
		this.responsibleEmployee = responsibleEmployee;
	}

	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription;
	}

	public void setAttachments(Attachments attachments)
	{
		this.attachments = attachments;
	}
}
