package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class for ComplexType TimeReports.
 */
@DatabaseTable
@Root
public class TimeReports implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ForeignCollectionField
	@ElementList(name = "timeReports", entry = "report", inline = true)
	private Collection<TimeReport> timeReports;

	/**
	 * Required constructor.
	 */
	public TimeReports()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param timeReports timeReports element
	 */
	public TimeReports(Collection<TimeReport> timeReports)
	{
		this.timeReports = timeReports;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Collection<TimeReport> getTimeReports()
	{
		return timeReports;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTimeReports(Collection<TimeReport> timeReports)
	{
		this.timeReports = timeReports;
	}
}
