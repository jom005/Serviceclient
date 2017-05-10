package com.example.ghost005.serviceclient.model.enums;

public enum UnitSymbol
{
	VA("VA"),
	W("W"),
	VAR("VAr"),
	VAH("VAh"),
	WH("Wh"),
	VARH("VArh"),
	V("V"),
	OHM("ohm"),
	A("A"),
	F("F"),
	H("H"),
	DEGC("degC"),
	S("s"),
	MIN("min"),
	H_1("h"),
	DEG("deg"),
	RAD("rad"),
	J("J"),
	N("N"),
	S_1("S"),
	NONE("none"),
	HZ("Hz"),
	G("g"),
	PA("Pa"),
	M("m"),
	M2("m2"),
	M3("m3");

	private String value;

	UnitSymbol(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
