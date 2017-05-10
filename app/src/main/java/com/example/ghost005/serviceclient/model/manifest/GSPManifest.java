package com.example.ghost005.serviceclient.model.manifest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class for ComplexType gspManifest.
 */
@Root(name = "gspManifest")
public class GSPManifest
{
	@Element(name = "documents")
	private Documents documents;

	/**
	 * Required constructor.
	 */
	public GSPManifest()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param documents documents element
	 */
	public GSPManifest(Documents documents)
	{
		this.documents = documents;
	}

	/* ---- Getter ---- */
	public Documents getDocuments()
	{
		return documents;
	}

	/* ---- Setter ---- */
	public void setDocuments(Documents documents)
	{
		this.documents = documents;
	}
}
