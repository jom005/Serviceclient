package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType TransportProcesses.
 */
@DatabaseTable
@Root
public class TransportProcesses implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "transportProcesses", entry = "transportProcess", inline = true)
	private Collection<TransportProcess> transportProcesses;

	/**
	 * Required constructor.
	 */
	public TransportProcesses()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param transportProcesses transportProcesses element
	 */
	public TransportProcesses(Collection<TransportProcess> transportProcesses)
	{
		this.transportProcesses = transportProcesses;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<TransportProcess> getTransportProcesses()
	{
		return transportProcesses;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTransportProcesses(Collection<TransportProcess> transportProcesses)
	{
		this.transportProcesses = transportProcesses;
	}
}
