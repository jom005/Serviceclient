package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType StreetAddress.
 */
@DatabaseTable
@Root
public class StreetAddress implements Serializable
{
	public static final String DATABASE_ID = "id";

	@DatabaseField(generatedId = true, columnName = DATABASE_ID)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "streetDetail", required = false)
	private StreetDetail streetDetail;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "townDetail", required = false)
	private TownDetail townDetail;

	/**
	 * Required constructor.
	 */
	public StreetAddress()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param streetDetail streetDetail element
	 * @param townDetail   townDetail element
	 */
	public StreetAddress(StreetDetail streetDetail, TownDetail townDetail)
	{
		this.streetDetail = streetDetail;
		this.townDetail = townDetail;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public StreetDetail getStreetDetail()
	{
		return streetDetail;
	}

	public TownDetail getTownDetail()
	{
		return townDetail;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setStreetDetail(StreetDetail streetDetail)
	{
		this.streetDetail = streetDetail;
	}

	public void setTownDetail(TownDetail townDetail)
	{
		this.townDetail = townDetail;
	}
}
