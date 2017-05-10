package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType Comments.
 */
@DatabaseTable
@Root
public class Comments implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@ForeignCollectionField(eager = false)
	@ElementList(name = "comments", entry = "comment", inline = true)
	private Collection<Comment> comments;

	/**
	 * Required constructor.
	 */
	public Comments()
	{
		super();
	}

	/**
	 * Constructor sets elements.
	 * @param comments list of comment elements
	 */
	public Comments(Collection<Comment> comments)
	{
		this.comments = comments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<Comment> getComments()
	{
		return comments;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setComments(Collection<Comment> comments)
	{
		this.comments = comments;
	}
}
