package com.example.ghost005.serviceclient.model.enums;

public enum TaskStatus
{
	GSP_STS_101("GSP-STS-101"),
	GSP_STS_103("GSP-STS-103"),
	GSP_STS_105("GSP-STS-105"),
	GSP_STS_113("GSP-STS-113"),
	GSP_STS_114("GSP-STS-114"),
	GSP_STS_801("GSP-STS-801"),
	GSP_STS_998("GSP-STS-998"),
	GSP_STS_999("GSP-STS-999");

	private String value;

	TaskStatus(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
