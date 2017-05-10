package com.example.ghost005.serviceclient.model.enums;

public enum StatusInfo
{
	GSP_SSA_101("GSP-SSA-101"),
	GSP_SSA_102("GSP-SSA-102"),
	GSP_SSA_201("GSP-SSA-201"),
	GSP_SSA_801("GSP-SSA-801"),
	GSP_SSA_998("GSP-SSA-998"),
	GSP_SSA_999("GSP-SSA-999");

	private String value;

	StatusInfo(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
