package com.example.ghost005.serviceclient.model.manifest;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Class for ComplexType Documents.
 */
@Root
public class Documents
{
	@ElementList(name = "document", entry = "document", inline = true)
	private List<Document> documents;

	/**
	 * Required constructor.
	 */
	public Documents()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param documents documents element
	 */
	public Documents(List<Document> documents)
	{
		this.documents = documents;
	}

	/* ---- Getter ---- */
	public List<Document> getDocuments()
	{
		return documents;
	}

	/* ---- Setter ---- */
	public void setDocuments(List<Document> documents)
	{
		this.documents = documents;
	}
}
