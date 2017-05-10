package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class for ComplexType GeneralValue
 */
@DatabaseTable
@Root
public class GeneralValue implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	@Element(name = "value")
	private BigDecimal value;
	@DatabaseField
	@Element(name = "symbol")
	private String symbol; // restrictions
	@DatabaseField
	@Element(name = "multiplier")
	private String multiplier; // restrictions

	/**
	 * Required constructor.
	 */
	public GeneralValue()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param value value element
	 * @param symbol symbol element
	 * @param multiplier multiplier element
	 */
	public GeneralValue(BigDecimal value, String symbol, String multiplier)
	{
		this.value = value;
		this.symbol = symbol;
		this.multiplier = multiplier;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public BigDecimal getValue()
	{
		return value;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public String getMultiplier()
	{
		return multiplier;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setValue(BigDecimal value)
	{
		this.value = value;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public void setMultiplier(String multiplier)
	{
		this.multiplier = multiplier;
	}
}
