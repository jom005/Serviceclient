package com.example.ghost005.serviceclient.model.enums;

public enum TimePaymentType
{
	GSP_PAT_001("GSP-PAT-001"),
	GSP_PAT_002("GSP-PAT-002"),
	GSP_PAT_003("GSP-PAT-003"),
	GSP_PAT_004("GSP-PAT-004"),
	GSP_PAT_005("GSP-PAT-005"),
	GSP_PAT_801("GSP-PAT-801"),
	GSP_PAT_998("GSP-PAT-998"),
	GSP_PAT_999("GSP-PAT-999");

	private String value;

	TimePaymentType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
