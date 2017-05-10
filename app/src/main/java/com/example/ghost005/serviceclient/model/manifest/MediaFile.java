package com.example.ghost005.serviceclient.model.manifest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Class for ComplexType MediaFile.
 */
@Root
public class MediaFile
{
	@Attribute(name = "name")
	private String name;
	@Attribute(name = "mimeType")
	private String mimeType;

	/**
	 * Required constructor.
	 */
	public MediaFile()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param name     name element
	 * @param mimeType mimeType element
	 */
	public MediaFile(String name, String mimeType)
	{
		this.name = name;
		this.mimeType = mimeType;
	}

	/* ---- Getter ---- */
	public String getName()
	{
		return name;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	/* ---- Setter ---- */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
	}
}
