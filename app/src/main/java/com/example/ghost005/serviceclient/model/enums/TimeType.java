package com.example.ghost005.serviceclient.model.enums;

public enum TimeType
{
	GSP_WTT_001("GSP-WTT-001"),
	GSP_WTT_002("GSP-WTT-002"),
	GSP_WTT_003("GSP-WTT-003"),
	GSP_WTT_004("GSP-WTT-004"),
	GSP_WTT_801("GSP-WTT-801"),
	GSP_WTT_998("GSP-WTT-998"),
	GSP_WTT_999("GSP-WTT-999");

	private String value;

	TimeType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
