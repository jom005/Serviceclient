package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType PostalAddress.
 */
@DatabaseTable
@Root
public class PostalAddress implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = true)
	@Element(name = "poBox", required = false)
	private String poBox;
	@DatabaseField(canBeNull = true)
	@Element(name = "postalCode", required = false)
	private String postalCode;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "streetDetail", required = false)
	private StreetDetail streetDetail;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "townDetail", required = false)
	private TownDetail townDetail;

	/**
	 * Required constructor.
	 */
	public PostalAddress()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param poBox poBox element
	 * @param postalCode postalCode element
	 * @param streetDetail streetDetail element
	 * @param townDetail townDetail element
	 */
	public PostalAddress(String poBox, String postalCode, StreetDetail streetDetail, TownDetail townDetail)
	{
		this.poBox = poBox;
		this.postalCode = postalCode;
		this.streetDetail = streetDetail;
		this.townDetail = townDetail;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public String getPoBox()
	{
		return poBox;
	}

	public String getPostalCode()
	{
		return postalCode;
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

	public void setPoBox(String poBox)
	{
		this.poBox = poBox;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
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
