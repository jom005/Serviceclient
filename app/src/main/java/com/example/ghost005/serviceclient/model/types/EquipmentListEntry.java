package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType EquipmentListEntry.
 */
@Root
public class EquipmentListEntry extends EquipmentInformation implements Serializable
{
	@DatabaseField(canBeNull = true)
	@Element(name = "rdsPPReference", required = false)
	private String rdsPPReference;
	@DatabaseField(canBeNull = true)
	@Element(name = "dateOfInstallation", required = false)
	private String dateOfInstallation; // XMLGregorianCalendar

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private EquipmentList equipmentList;

	/**
	 * Required constructor.
	 */
	public EquipmentListEntry()
	{
		super();
	}

	public EquipmentListEntry(String rdsPPReference, String dateOfInstallation)
	{
		this.rdsPPReference = rdsPPReference;
		this.dateOfInstallation = dateOfInstallation;
	}

	/* ---- Getter ---- */
	public String getRdsPPReference()
	{
		return rdsPPReference;
	}

	public String getDateOfInstallation()
	{
		return dateOfInstallation;
	}

	/* ---- Setter ---- */
	public void setRdsPPReference(String rdsPPReference)
	{
		this.rdsPPReference = rdsPPReference;
	}

	public void setDateOfInstallation(String dateOfInstallation)
	{
		this.dateOfInstallation = dateOfInstallation;
	}
}
