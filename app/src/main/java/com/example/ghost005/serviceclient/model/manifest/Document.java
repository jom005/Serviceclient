package com.example.ghost005.serviceclient.model.manifest;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Class for ComplexType Document.
 */
@Root
public class Document
{
	@Attribute(name = "name")
	private String name;
	@Attribute(name = "path", required = false)
	private String path;
	@ElementList(name = "mediaFile", required = false, inline = true)
	private List<MediaFile> mediaFiles;

	/**
	 * Required constructor.
	 */
	public Document()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param name       name element
	 * @param path       path element
	 * @param mediaFiles mediaFile element
	 */
	public Document(String name, String path, List<MediaFile> mediaFiles)
	{
		this.name = name;
		this.path = path;
		this.mediaFiles = mediaFiles;
	}

	/* ---- Getter ---- */
	public String getName()
	{
		return name;
	}

	public String getPath()
	{
		return path;
	}

	public List<MediaFile> getMediaFiles()
	{
		return mediaFiles;
	}

	/* ---- Setter ---- */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public void setMediaFiles(List<MediaFile> mediaFiles)
	{
		this.mediaFiles = mediaFiles;
	}
}
