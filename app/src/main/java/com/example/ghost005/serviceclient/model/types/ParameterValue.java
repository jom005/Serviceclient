package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.datatype.Duration;

/**
 * Class for ComplexType ParameterValue.
 */
@DatabaseTable
@Root
public class ParameterValue implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@ElementUnion({
			@Element(name = "stringParameterValue", type = String.class),
			@Element(name = "integerParameterValue", type = BigInteger.class),
			@Element(name = "decimalParameterValue", type = BigDecimal.class),
			@Element(name = "dateParameterValue", type = String.class), //XMLGregorianCalendar
			@Element(name = "timeParameterValue", type = String.class), //XMLGregorianCalendar
			@Element(name = "dateTimeParameterValue", type = String.class), //XMLGregorianCalendar
			@Element(name = "durationParameterValue", type = Duration.class),
			@Element(name = "valueParameterType", type = GeneralValue.class),
			@Element(name = "documentParameterType", type = File.class)
	})
	private Object value;

	@DatabaseField(canBeNull = true)
	private String stringParameterValue;
	@DatabaseField(canBeNull = true)
	private BigInteger integerParameterValue;
	@DatabaseField(canBeNull = true)
	private BigDecimal decimalParameterValue;
	@DatabaseField(canBeNull = true)
	private String dateParameterValue;
	@DatabaseField(canBeNull = true)
	private String timeParameterValue;
	@DatabaseField(canBeNull = true)
	private String dateTimeParameterValue;
	@DatabaseField(canBeNull = true)
	private String durationParameterValue; // Duration
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private GeneralValue valueParameterType;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private File documentParameterType;

	/**
	 * Required constructor.
	 */
	public ParameterValue()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 *
	 * @param value value element
	 */
	public ParameterValue(Object value)
	{
		this.value = value;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Object getValue()
	{
		return value;
	}

	public String getStringParameterValue()
	{
		return stringParameterValue;
	}

	public BigInteger getIntegerParameterValue()
	{
		return integerParameterValue;
	}

	public BigDecimal getDecimalParameterValue()
	{
		return decimalParameterValue;
	}

	public String getDateParameterValue()
	{
		return dateParameterValue;
	}

	public String getTimeParameterValue()
	{
		return timeParameterValue;
	}

	public String getDateTimeParameterValue()
	{
		return dateTimeParameterValue;
	}

	public String getDurationParameterValue()
	{
		return durationParameterValue;
	}

	public GeneralValue getValueParameterType()
	{
		return valueParameterType;
	}

	public File getDocumentParameterType()
	{
		return documentParameterType;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setValue(Object value)
	{
		if (value instanceof String)
		{
			setStringParameterValue(value.toString());
		}
		else if (value instanceof BigInteger)
		{
			setIntegerParameterValue((BigInteger) value);
		}
		else if (value instanceof BigDecimal)
		{
			setDecimalParameterValue((BigDecimal) value);
		}
		else if (value instanceof Duration)
		{
			setDurationParameterValue(value.toString());
		}
		else if (value instanceof GeneralValue)
		{
			setValueParameterType((GeneralValue) value);
		}
		else if (value instanceof File)
		{
			setDocumentParameterType((File) value);
		}

		this.value = value;
	}

	public void setStringParameterValue(String stringParameterValue)
	{
		this.stringParameterValue = stringParameterValue;

		value = stringParameterValue;
	}

	public void setIntegerParameterValue(BigInteger integerParameterValue)
	{
		this.integerParameterValue = integerParameterValue;

		value = integerParameterValue;
	}

	public void setDecimalParameterValue(BigDecimal decimalParameterValue)
	{
		this.decimalParameterValue = decimalParameterValue;

		value = decimalParameterValue;
	}

	public void setDateParameterValue(String dateParameterValue)
	{
		this.dateParameterValue = dateParameterValue;

		value = dateParameterValue;
	}

	public void setTimeParameterValue(String timeParameterValue)
	{
		this.timeParameterValue = timeParameterValue;

		value = timeParameterValue;
	}

	public void setDateTimeParameterValue(String dateTimeParameterValue)
	{
		this.dateTimeParameterValue = dateTimeParameterValue;

		value = dateTimeParameterValue;
	}

	public void setDurationParameterValue(String durationParameterValue)
	{
		this.durationParameterValue = durationParameterValue;

		value = durationParameterValue;
	}

	public void setValueParameterType(GeneralValue valueParameterType)
	{
		this.valueParameterType = valueParameterType;

		value = valueParameterType;
	}

	public void setDocumentParameterType(File documentParameterType)
	{
		this.documentParameterType = documentParameterType;

		value = documentParameterType;
	}
}
