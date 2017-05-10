package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType Equipment
 */
@DatabaseTable
@Root
public class Equipment extends EquipmentInformation implements Serializable
{
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "latestChecks", required = false)
	private WorkLog latestChecks;
	@DatabaseField(canBeNull = true)
	@Element(name = "dateOfInstallation", required = false)
	private String dateOfInstallation; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "dateOfRemoval", required = false)
	private String dateOfRemoval; // XMLGregorianCalendar

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Equipments equipments;

	/**
	 * Required constructor.
	 */
	public Equipment()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param latestChecks latestChecks element
	 * @param dateOfInstallation dateOfInstallation element
	 * @param dateOfRemoval dateOfRemoval element
	 */
	public Equipment(WorkLog latestChecks, String dateOfInstallation,
					 String dateOfRemoval)
	{
		this.latestChecks = latestChecks;
		this.dateOfInstallation = dateOfInstallation;
		this.dateOfRemoval = dateOfRemoval;
	}

	/* ---- Getter ---- */

	public WorkLog getLatestChecks()
	{
		return latestChecks;
	}

	public String getDateOfInstallation()
	{
		return dateOfInstallation;
	}

	public String getDateOfRemoval()
	{
		return dateOfRemoval;
	}

	public Equipments getEquipments()
	{
		return equipments;
	}

	/* ---- Setter ---- */

	public void setLatestChecks(WorkLog latestChecks)
	{
		this.latestChecks = latestChecks;
	}

	public void setDateOfInstallation(String dateOfInstallation)
	{
		this.dateOfInstallation = dateOfInstallation;
	}

	public void setDateOfRemoval(String dateOfRemoval)
	{
		this.dateOfRemoval = dateOfRemoval;
	}

	public void setEquipments(Equipments equipments)
	{
		this.equipments = equipments;
	}
}
