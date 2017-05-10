package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class for ComplexType Material
 */
@DatabaseTable
@Root
public class Material extends EquipmentInformation implements Serializable
{
	@DatabaseField(canBeNull = true)
	@Element(name = "materialCode", required = false)
	private String materialCode;
	@DatabaseField(canBeNull = true)
	@Element(name = "quantity", required = false)
	private BigInteger quantity;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private Materials materials;

	/**
	 * Required constructor.
	 */
	public Material()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param materialCode materialCode element
	 * @param quantity quantity element
	 */
	public Material(String materialCode, BigInteger quantity)
	{
		this.materialCode = materialCode;
		this.quantity = quantity;
	}

	/* ---- Getter ---- */
	public String getMaterialCode()
	{
		return materialCode;
	}

	public BigInteger getQuantity()
	{
		return quantity;
	}

	public Materials getMaterials()
	{
		return materials;
	}

	/* ---- Setter ---- */
	public void setMaterialCode(String materialCode)
	{
		this.materialCode = materialCode;
	}

	public void setQuantity(BigInteger quantity)
	{
		this.quantity = quantity;
	}

	public void setMaterials(Materials materials)
	{
		this.materials = materials;
	}
}
