package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType PriorityEntry.
 */
@DatabaseTable
@Root
public class PriorityEntry implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "rankingScale")
	private BigInteger rankingScale; // restrictions
	@DatabaseField(canBeNull = true)
	@Element(name = "rankingDescription", required = false)
	private String rankingDescription;
	@DatabaseField(canBeNull = true)
	@Element(name = "timestamp", required = false)
	private String timestamp; //XMLGregorianCalendar

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private PriorityLog priorityLog;

	/**
	 * Required constructor.
	 */
	public PriorityEntry()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param rankingScale rankingScale element
	 * @param rankingDescription rankingDescription element
	 * @param timestamp timestamp element
	 */
	public PriorityEntry(BigInteger rankingScale, String rankingDescription, String timestamp)
	{
		this.rankingScale = rankingScale;
		this.rankingDescription = rankingDescription;
		this.timestamp = timestamp;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public BigInteger getRankingScale()
	{
		return rankingScale;
	}

	public String getRankingDescription()
	{
		return rankingDescription;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setRankingScale(BigInteger rankingScale)
	{
		this.rankingScale = rankingScale;
	}

	public void setRankingDescription(String rankingDescription)
	{
		this.rankingDescription = rankingDescription;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
}
