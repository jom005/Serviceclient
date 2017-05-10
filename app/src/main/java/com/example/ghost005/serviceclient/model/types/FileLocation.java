package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.net.URI;

/**
 * Class for ComplexType FileLocation.
 */
@DatabaseTable
@Root
public class FileLocation implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ElementUnion({
			@Element(name = "path", type = String.class),
			@Element(name = "url", type = URI.class)
	})
	private Object value;

	@DatabaseField(canBeNull = true, useGetSet = true)
	private String path;
	@DatabaseField(canBeNull = true, useGetSet = true)
	private String url;

	/**
	 * Required constructor.
	 */
	public FileLocation()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param value value element
	 */
	public FileLocation(Object value)
	{
		this.value = value;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Object getValue()
	{
		return value;
	}

	public String getPath()
	{
		return path;
	}

	public String getUrl()
	{
		return url;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setValue(Object value)
	{
		this.value = value;

		if (value instanceof String)
		{
			path = value.toString();
		}
		else if (value instanceof URI)
		{
			url = value.toString();
		}
	}

	public void setPath(String path)
	{
		this.path = path;

		if (path != null)
		{
			value = path;
		}
	}

	public void setUrl(String url)
	{
		this.url = url;

		if (url != null)
		{
			value = url;
		}
	}
}