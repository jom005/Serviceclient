package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType File.
 */
@DatabaseTable
@Root
public class File implements Serializable
{
	public static final String DATABASE_ID = "_id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int _id;
	@DatabaseField
	@Element(name = "name")
	private String name;
	@DatabaseField
	@Element(name = "mimeMediaType")
	private String mimeMediaType;
	@DatabaseField(canBeNull = true)
	@Element(name = "id", required = false)
	private String id;
	@DatabaseField(canBeNull = true)
	@Element(name = "mimeContentType", required = false)
	private String mimeContentType;
	@DatabaseField(canBeNull = true)
	@Element(name = "creationDate", required = false)
	private String creationDate; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "lastModification", required = false)
	private String lastModification; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "description", required = false)
	private String description;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "location")
	private FileLocation location;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Files files;

	/**
	 * Required constructor.
	 */
	public File()
	{
		super();
	}

	public File(String name, String mimeMediaType, String id, String mimeContentType,
				String creationDate, String lastModification,
				String description, FileLocation location)
	{
		this.name = name;
		this.mimeMediaType = mimeMediaType;
		this.id = id;
		this.mimeContentType = mimeContentType;
		this.creationDate = creationDate;
		this.lastModification = lastModification;
		this.description = description;
		this.location = location;
	}

	/* ---- Getter ---- */

	public int get_id()
	{
		return _id;
	}

	public String getName()
	{
		return name;
	}

	public String getMimeMediaType()
	{
		return mimeMediaType;
	}

	public String getId()
	{
		return id;
	}

	public String getMimeContentType()
	{
		return mimeContentType;
	}

	public String getCreationDate()
	{
		return creationDate;
	}

	public String getLastModification()
	{
		return lastModification;
	}

	public String getDescription()
	{
		return description;
	}

	public FileLocation getLocation()
	{
		return location;
	}

	public Files getFiles()
	{
		return files;
	}

	/* ---- Setter ---- */

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setMimeMediaType(String mimeMediaType)
	{
		this.mimeMediaType = mimeMediaType;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setMimeContentType(String mimeContentType)
	{
		this.mimeContentType = mimeContentType;
	}

	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setLastModification(String lastModification)
	{
		this.lastModification = lastModification;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setLocation(FileLocation location)
	{
		this.location = location;
	}

	public void setFiles(Files files)
	{
		this.files = files;
	}
}
