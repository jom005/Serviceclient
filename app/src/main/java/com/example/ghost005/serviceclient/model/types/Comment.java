package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Comment.
 */
@DatabaseTable
@Root
public class Comment implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField
	@Element(name = "message")
	private String message;
	@DatabaseField
	@Element(name = "timestamp")
	private String timestamp; //XMLGregorianCalendar
	@DatabaseField
	@Element(name = "editor")
	private String editor;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Comments comments;

	/**
	 * Required constructor.
	 */
	public Comment()
	{
		super();
	}

	/**
	 * Constructor sets elements.
	 * @param message message element
	 * @param timestamp timestamp element
	 * @param editor editor element
	 */
	public Comment(String message, String timestamp, String editor)
	{
		this.message = message;
		this.timestamp = timestamp;
		this.editor = editor;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getMessage()
	{
		return message;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public String getEditor()
	{
		return editor;
	}

	public Comments getComments()
	{
		return comments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setEditor(String editor)
	{
		this.editor = editor;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}
}
