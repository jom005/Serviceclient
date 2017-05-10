package com.example.ghost005.serviceclient.model.enums;

public enum Gender
{
	GSP_GEN_001("GSP-GEN-001"),
	GSP_GEN_002("GSP-GEN-002"),
	GSP_GEN_998("GSP-GEN-998"),
	GSP_GEN_999("GSP-GEN-999");

	private String value;

	Gender(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
