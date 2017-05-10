package com.example.ghost005.serviceclient.model.enums;

public enum UnitMultiplier
{
	P("p"),
	N("n"),
	MICRO("micro"),
	M("m"),
	C("c"),
	D("d"),
	K("k"),
	M_1("M"),
	G("G"),
	T("T"),
	NONE("none");

	private String value;

	UnitMultiplier(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
