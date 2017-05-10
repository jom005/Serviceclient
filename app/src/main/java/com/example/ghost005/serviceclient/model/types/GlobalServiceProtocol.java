package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType GlobalServiceProtocol.
 */
@DatabaseTable
@Root
public class GlobalServiceProtocol implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "gspInfo")
	private GSPInfo gspInfo;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 9)
	@Element(name = "powerPlant")
	private PowerPlant powerPlant;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 9)
	@Element(name = "energySystem")
	private EnergySystem energySystem;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 9)
	@Element(name = "workOrder")
	private WorkOrder workOrder;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true, maxForeignAutoRefreshLevel = 9)
	@Element(name = "workReport", required = false)
	private WorkReport workReport;

	/**
	 * Required constructor.
	 */
	public GlobalServiceProtocol()
	{
		super();
	}

	/**
	 * Constructor sets elements.
	 * @param gspInfo gspInfo element
	 * @param powerPlant powerPlant element
	 * @param energySystem energySystem element
	 * @param workOrder workOrder element
	 * @param workReport workReport element
	 */
	public GlobalServiceProtocol(GSPInfo gspInfo, PowerPlant powerPlant, EnergySystem energySystem,
								 WorkOrder workOrder, WorkReport workReport)
	{
		this.gspInfo = gspInfo;
		this.powerPlant = powerPlant;
		this.energySystem = energySystem;
		this.workOrder = workOrder;
		this.workReport = workReport;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public GSPInfo getGspInfo()
	{
		return gspInfo;
	}

	public PowerPlant getPowerPlant()
	{
		return powerPlant;
	}

	public EnergySystem getEnergySystem()
	{
		return energySystem;
	}

	public WorkOrder getWorkOrder()
	{
		return workOrder;
	}

	public WorkReport getWorkReport()
	{
		return workReport;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setGspInfo(GSPInfo gspInfo)
	{
		this.gspInfo = gspInfo;
	}

	public void setPowerPlant(PowerPlant powerPlant)
	{
		this.powerPlant = powerPlant;
	}

	public void setEnergySystem(EnergySystem energySystem)
	{
		this.energySystem = energySystem;
	}

	public void setWorkOrder(WorkOrder workOrder)
	{
		this.workOrder = workOrder;
	}

	public void setWorkReport(WorkReport workReport)
	{
		this.workReport = workReport;
	}
}
