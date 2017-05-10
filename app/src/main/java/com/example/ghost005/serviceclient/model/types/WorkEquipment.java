package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType WorkEquipment.
 */
@DatabaseTable
@Root
public class WorkEquipment extends EquipmentInformation implements Serializable
{
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "latestChecks", required = false)
	private WorkLog latestChecks;
	@DatabaseField(canBeNull = true)
	@Element(name = "equipmentCategory", required = false)
	private String equipmentCategory;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "provider", required = false)
	private Organisation provider;
	@DatabaseField(canBeNull = true)
	@Element(name = "usageStart", required = false)
	private String usageStart; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "usageEnd", required = false)
	private String usageEnd; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "usageDuration", required = false)
	private String usageDuration; // Duration

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private WorkEquipments workEquipments;

	/**
	 * Required constructor.
	 */
	public WorkEquipment()
	{
		super();
	}

	public WorkEquipment(WorkLog latestChecks, String equipmentCategory, Organisation provider,
						 String usageStart, String usageEnd,
						 String usageDuration)
	{
		this.latestChecks = latestChecks;
		this.equipmentCategory = equipmentCategory;
		this.provider = provider;
		this.usageStart = usageStart;
		this.usageEnd = usageEnd;
		this.usageDuration = usageDuration;
	}

	/* ---- Getter ---- */

	public WorkLog getLatestChecks()
	{
		return latestChecks;
	}

	public String getEquipmentCategory()
	{
		return equipmentCategory;
	}

	public Organisation getProvider()
	{
		return provider;
	}

	public String getUsageStart()
	{
		return usageStart;
	}

	public String getUsageEnd()
	{
		return usageEnd;
	}

	public String getUsageDuration()
	{
		return usageDuration;
	}

	public WorkEquipments getWorkEquipments()
	{
		return workEquipments;
	}

	/* ---- Setter ---- */

	public void setLatestChecks(WorkLog latestChecks)
	{
		this.latestChecks = latestChecks;
	}

	public void setEquipmentCategory(String equipmentCategory)
	{
		this.equipmentCategory = equipmentCategory;
	}

	public void setProvider(Organisation provider)
	{
		this.provider = provider;
	}

	public void setUsageStart(String usageStart)
	{
		this.usageStart = usageStart;
	}

	public void setUsageEnd(String usageEnd)
	{
		this.usageEnd = usageEnd;
	}

	public void setUsageDuration(String usageDuration)
	{
		this.usageDuration = usageDuration;
	}

	public void setWorkEquipments(WorkEquipments workEquipments)
	{
		this.workEquipments = workEquipments;
	}
}
