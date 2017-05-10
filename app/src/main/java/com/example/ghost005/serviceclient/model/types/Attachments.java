package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType ttachments.
 */
@DatabaseTable
@Root
public class Attachments implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "attachments", entry = "attachment", inline = true)
	private Collection<Attachment> attachments;

	/**
	 * Required constructor.
	 */
	public Attachments()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param attachments attachments element
	 */
	public Attachments(Collection<Attachment> attachments)
	{
		this.attachments = attachments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Attachment> getAttachments()
	{
		return attachments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setAttachments(Collection<Attachment> attachments)
	{
		this.attachments = attachments;
	}
}
