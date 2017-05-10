package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Files.
 */
@DatabaseTable
@Root
public class Files implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "files", entry = "file", inline = true)
	private Collection<File> files;

	/**
	 * Required constructor.
	 */
	public Files()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param files files element
	 */
	public Files(Collection<File> files)
	{
		this.files = files;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<File> getFiles()
	{
		return files;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setFiles(Collection<File> files)
	{
		this.files = files;
	}
}
