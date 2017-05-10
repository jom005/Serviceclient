package com.example.ghost005.serviceclient.model.enums;

public enum Travelway
{
	GSP_TW_001("GSP-TW-001"),
	GSP_TW_002("GSP-TW-002"),
	GSP_TW_003("GSP-TW-003"),
	GSP_TW_801("GSP-TW-801"),
	GSP_TW_998("GSP-TW-998"),
	GSP_TW_999("GSP-TW-999");

	private String value;

	Travelway(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
