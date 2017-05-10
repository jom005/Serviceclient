package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType EnergySystem.
 */
@DatabaseTable
@Root
public class EnergySystem implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "energySystemID")
	private String energySystemID;
	@DatabaseField
	@Element(name = "serialNumber")
	private String serialNumber;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "manufacturer")
	private Organisation manufacturer;
	@DatabaseField
	@Element(name = "type")
	private String type;
	@DatabaseField
	@Element(name = "series")
	private String series;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "operator")
	private Organisation operator;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "owner")
	private Organisation owner;
	@DatabaseField(canBeNull = true)
	@Element(name = "source", required = false)
	private String source;
	@DatabaseField(canBeNull = true)
	@Element(name = "operationalSince", required = false)
	private String operationalSince; //XMLGregorianCalendar
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "address", required = false)
	private Location address;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "ratedPower", required = false)
	private GeneralValue ratedPower;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "hubHeight", required = false)
	private GeneralValue hubHeight;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "rotorDiameter", required = false)
	private GeneralValue rotorDiameter;
	@DatabaseField(canBeNull = true)
	@Element(name = "dateOfManufacture", required = false)
	private String dateOfManufacture; //XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "startOfWarranty", required = false)
	private String startOfWarranty; //XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "endOfWarranty", required = false)
	private String endOfWarranty; //XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "weaNIS", required = false)
	private String weaNIS;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "rdsPPStructure", required = false)
	private RDSPPStructure rdsPPStructure;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "equipmentList", required = false)
	private EquipmentList equipmentList;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	/**
	 * Required constructor.
	 */
	public EnergySystem()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param energySystemID energySystemID element
	 * @param serialNumber serialNumber element
	 * @param manufacturer manufacturer element
	 * @param type type element
	 * @param series series element
	 * @param operator operator element
	 * @param owner owner element
	 * @param source source element
	 * @param operationalSince operationalSince element
	 * @param address address element
	 * @param ratedPower ratedPower element
	 * @param hubHeight hubHeight element
	 * @param rotorDiameter rotorDiameter element
	 * @param dateOfManufacture dateOfManufacture element
	 * @param startOfWarranty startOfWarranty element
	 * @param endOfWarranty endOfWarranty element
	 * @param weaNIS weaNIS element
	 * @param rdsPPStructure rdsPPStructure element
	 * @param equipmentList equipmentList element
	 * @param comments comments element
	 * @param userSpecificContents userSpecificContents element
	 */
	public EnergySystem(String energySystemID, String serialNumber, Organisation manufacturer,
						String type, String series, Organisation operator, Organisation owner,
						String source, String operationalSince,
						Location address, GeneralValue ratedPower, GeneralValue hubHeight,
						GeneralValue rotorDiameter, String dateOfManufacture,
						String startOfWarranty, String endOfWarranty,
						String weaNIS, RDSPPStructure rdsPPStructure, EquipmentList equipmentList,
						Comments comments, UserSpecificContents userSpecificContents)
	{
		this.energySystemID = energySystemID;
		this.serialNumber = serialNumber;
		this.manufacturer = manufacturer;
		this.type = type;
		this.series = series;
		this.operator = operator;
		this.owner = owner;
		this.source = source;
		this.operationalSince = operationalSince;
		this.address = address;
		this.ratedPower = ratedPower;
		this.hubHeight = hubHeight;
		this.rotorDiameter = rotorDiameter;
		this.dateOfManufacture = dateOfManufacture;
		this.startOfWarranty = startOfWarranty;
		this.endOfWarranty = endOfWarranty;
		this.weaNIS = weaNIS;
		this.rdsPPStructure = rdsPPStructure;
		this.equipmentList = equipmentList;
		this.comments = comments;
		this.userSpecificContents = userSpecificContents;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getEnergySystemID()
	{
		return energySystemID;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public Organisation getManufacturer()
	{
		return manufacturer;
	}

	public String getType()
	{
		return type;
	}

	public String getSeries()
	{
		return series;
	}

	public Organisation getOperator()
	{
		return operator;
	}

	public Organisation getOwner()
	{
		return owner;
	}

	public String getSource()
	{
		return source;
	}

	public String getOperationalSince()
	{
		return operationalSince;
	}

	public Location getAddress()
	{
		return address;
	}

	public GeneralValue getRatedPower()
	{
		return ratedPower;
	}

	public GeneralValue getHubHeight()
	{
		return hubHeight;
	}

	public GeneralValue getRotorDiameter()
	{
		return rotorDiameter;
	}

	public String getDateOfManufacture()
	{
		return dateOfManufacture;
	}

	public String getStartOfWarranty()
	{
		return startOfWarranty;
	}

	public String getEndOfWarranty()
	{
		return endOfWarranty;
	}

	public String getWeaNIS()
	{
		return weaNIS;
	}

	public RDSPPStructure getRdsPPStructure()
	{
		return rdsPPStructure;
	}

	public EquipmentList getEquipmentList()
	{
		return equipmentList;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setEnergySystemID(String energySystemID)
	{
		this.energySystemID = energySystemID;
	}

	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}

	public void setManufacturer(Organisation manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public void setOperator(Organisation operator)
	{
		this.operator = operator;
	}

	public void setOwner(Organisation owner)
	{
		this.owner = owner;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public void setOperationalSince(String operationalSince)
	{
		this.operationalSince = operationalSince;
	}

	public void setAddress(Location address)
	{
		this.address = address;
	}

	public void setRatedPower(GeneralValue ratedPower)
	{
		this.ratedPower = ratedPower;
	}

	public void setHubHeight(GeneralValue hubHeight)
	{
		this.hubHeight = hubHeight;
	}

	public void setRotorDiameter(GeneralValue rotorDiameter)
	{
		this.rotorDiameter = rotorDiameter;
	}

	public void setDateOfManufacture(String dateOfManufacture)
	{
		this.dateOfManufacture = dateOfManufacture;
	}

	public void setStartOfWarranty(String startOfWarranty)
	{
		this.startOfWarranty = startOfWarranty;
	}

	public void setEndOfWarranty(String endOfWarranty)
	{
		this.endOfWarranty = endOfWarranty;
	}

	public void setWeaNIS(String weaNIS)
	{
		this.weaNIS = weaNIS;
	}

	public void setRdsPPStructure(RDSPPStructure rdsPPStructure)
	{
		this.rdsPPStructure = rdsPPStructure;
	}

	public void setEquipmentList(EquipmentList equipmentList)
	{
		this.equipmentList = equipmentList;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}
}
