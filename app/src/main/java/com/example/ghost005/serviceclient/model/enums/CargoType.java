package com.example.ghost005.serviceclient.model.enums;

public enum CargoType
{
	GSP_CT_001("GSP-CT-001"),
	GSP_CT_002("GSP-CT-002"),
	GSP_CT_003("GSP-CT-003"),
	GSP_CT_801("GSP-CT-801"),
	GSP_CT_998("GSP-CT-998"),
	GSP_CT_999("GSP-CT-999");

	private String value;

	CargoType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
