package com.example.ghost005.serviceclient.model.types;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType GSPInfo.
 */
@DatabaseTable
@Root
public class GSPInfo implements Serializable
{
	public static final String DOCUMENT_ID = "documentID";

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "version")
	private String version;
	@DatabaseField
	@Element(name = "createDate")
	private String createDate; //XMLGRegorianCalandar
	@DatabaseField(columnName = DOCUMENT_ID)
	@Element(name = "documentID")
	private String documentID;
	@DatabaseField
	@Element(name = "lang")
	private String lang;

	/**
	 * Required constructor.
	 */
	public GSPInfo()
	{

	}

	/**
	 * Constructor sets elements
	 * @param version version element
	 * @param createDate createDate element
	 * @param documentID documentID element
	 * @param lang lang element
	 */
	public GSPInfo(String version, String createDate, String documentID, String lang)
	{
		this.version = version;
		this.createDate = createDate;
		this.documentID = documentID;
		this.lang = lang;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getVersion()
	{
		return version;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public String getDocumentID()
	{
		return documentID;
	}

	public String getLang()
	{
		return lang;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	public void setDocumentID(String documentID)
	{
		this.documentID = documentID;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}
}
