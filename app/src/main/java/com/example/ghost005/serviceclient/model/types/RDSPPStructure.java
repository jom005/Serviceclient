package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType RDSPPStructure.
 */
@DatabaseTable
@Root
public class RDSPPStructure implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "rdsPPElements", entry = "rdsPPElement", inline = true)
	private Collection<RDSPPElement> rdsPPElements;

	/**
	 * Required constructor.
	 */
	public RDSPPStructure()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param rdsPPElements rdsPPElements element
	 */
	public RDSPPStructure(Collection<RDSPPElement> rdsPPElements)
	{
		this.rdsPPElements = rdsPPElements;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<RDSPPElement> getRdsPPElements()
	{
		return rdsPPElements;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setRdsPPElements(Collection<RDSPPElement> rdsPPElements)
	{
		this.rdsPPElements = rdsPPElements;
	}
}
