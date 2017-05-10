package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType TransportProcess.
 */
@DatabaseTable
@Root
public class TransportProcess implements Serializable
{
	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	@Element(name = "id")
	private String id;
	@DatabaseField
	@Element(name = "travelway")
	private String travelway; // restrictions
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "carrier", required = false)
	private Organisation carrier;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "distance", required = false)
	private GeneralValue distance;
	@DatabaseField(canBeNull = true)
	@Element(name = "unitDesignation", required = false)
	private String unitDesignation;
	@DatabaseField(canBeNull = true)
	@Element(name = "unitDescription", required = false)
	private String unitDescription;
	@DatabaseField(canBeNull = true)
	@Element(name = "mode", required = false)
	private String mode; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "cargoType", required = false)
	private String cargoType; // restrictions
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "cargoCapacity", required = false)
	private GeneralValue cargoCapacity;
	@DatabaseField(canBeNull = true)
	@Element(name = "passengerCapacity", required = false)
	private BigInteger passengerCapacity;
	@DatabaseField(canBeNull = true)
	@Element(name = "start", required = false)
	private String start; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "end", required = false)
	private String end; // XMLGregorianCalendar
	@DatabaseField(canBeNull = true)
	@Element(name = "duration", required = false)
	private String duration; // Duration
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "endPlace", required = false)
	private Location endPlace;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "startPlace", required = false)
	private Location startPlace;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "userSpecificContents", required = false)
	private UserSpecificContents userSpecificContents;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private TransportProcesses transportProcesses;

	/**
	 * Required constructor.
	 */
	public TransportProcess()
	{
		super();
	}

	public TransportProcess(String id, String travelway, Organisation carrier,
							GeneralValue distance, String unitDesignation, String unitDescription,
							String mode, String cargoType, GeneralValue cargoCapacity,
							BigInteger passengerCapacity, String start,
							String end, String duration, Location endPlace,
							Location startPlace, Comments comments,
							UserSpecificContents userSpecificContents)
	{
		this.id = id;
		this.travelway = travelway;
		this.carrier = carrier;
		this.distance = distance;
		this.unitDesignation = unitDesignation;
		this.unitDescription = unitDescription;
		this.mode = mode;
		this.cargoType = cargoType;
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.endPlace = endPlace;
		this.startPlace = startPlace;
		this.comments = comments;
		this.userSpecificContents = userSpecificContents;
	}

	/* ---- Getter ---- */

	public int get_id()
	{
		return _id;
	}

	public String getId()
	{
		return id;
	}

	public String getTravelway()
	{
		return travelway;
	}

	public Organisation getCarrier()
	{
		return carrier;
	}

	public GeneralValue getDistance()
	{
		return distance;
	}

	public String getUnitDesignation()
	{
		return unitDesignation;
	}

	public String getUnitDescription()
	{
		return unitDescription;
	}

	public String getMode()
	{
		return mode;
	}

	public String getCargoType()
	{
		return cargoType;
	}

	public GeneralValue getCargoCapacity()
	{
		return cargoCapacity;
	}

	public BigInteger getPassengerCapacity()
	{
		return passengerCapacity;
	}

	public String getStart()
	{
		return start;
	}

	public String getEnd()
	{
		return end;
	}

	public String getDuration()
	{
		return duration;
	}

	public Location getEndPlace()
	{
		return endPlace;
	}

	public Location getStartPlace()
	{
		return startPlace;
	}

	public Comments getComments()
	{
		return comments;
	}

	public UserSpecificContents getUserSpecificContents()
	{
		return userSpecificContents;
	}

	public TransportProcesses getTransportProcesses()
	{
		return transportProcesses;
	}

	/* ---- Setter ---- */

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setTravelway(String travelway)
	{
		this.travelway = travelway;
	}

	public void setCarrier(Organisation carrier)
	{
		this.carrier = carrier;
	}

	public void setDistance(GeneralValue distance)
	{
		this.distance = distance;
	}

	public void setUnitDesignation(String unitDesignation)
	{
		this.unitDesignation = unitDesignation;
	}

	public void setUnitDescription(String unitDescription)
	{
		this.unitDescription = unitDescription;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public void setCargoType(String cargoType)
	{
		this.cargoType = cargoType;
	}

	public void setCargoCapacity(GeneralValue cargoCapacity)
	{
		this.cargoCapacity = cargoCapacity;
	}

	public void setPassengerCapacity(BigInteger passengerCapacity)
	{
		this.passengerCapacity = passengerCapacity;
	}

	public void setStart(String start)
	{
		this.start = start;
	}

	public void setEnd(String end)
	{
		this.end = end;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public void setEndPlace(Location endPlace)
	{
		this.endPlace = endPlace;
	}

	public void setStartPlace(Location startPlace)
	{
		this.startPlace = startPlace;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setUserSpecificContents(UserSpecificContents userSpecificContents)
	{
		this.userSpecificContents = userSpecificContents;
	}

	public void setTransportProcesses(TransportProcesses transportProcesses)
	{
		this.transportProcesses = transportProcesses;
	}
}
