package com.example.ghost005.serviceclient.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;

/**
 * Class that stores the basic information about a gsp file.
 */
@DatabaseTable
public class GSPDocument implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 10)
	private GlobalServiceProtocol globalServiceProtocol;
	@DatabaseField
	private String fileName;
	@DatabaseField(canBeNull = true)
	private String mediaPath;

	public GSPDocument()
	{
		super();
	}

	public GSPDocument(GlobalServiceProtocol globalServiceProtocol, String fileName, String mediaPath)
	{
		this.globalServiceProtocol = globalServiceProtocol;
		this.fileName = fileName;
		this.mediaPath = mediaPath;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public GlobalServiceProtocol getGlobalServiceProtocol()
	{
		return globalServiceProtocol;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getMediaPath()
	{
		return mediaPath;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setGlobalServiceProtocol(GlobalServiceProtocol globalServiceProtocol)
	{
		this.globalServiceProtocol = globalServiceProtocol;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public void setMediaPath(String mediaPath)
	{
		this.mediaPath = mediaPath;
	}
}
