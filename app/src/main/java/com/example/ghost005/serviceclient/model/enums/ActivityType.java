package com.example.ghost005.serviceclient.model.enums;

public enum ActivityType
{
	GSP_ACT_001("GSP-ACT-001"),
	GSP_ACT_002("GSP-ACT-002"),
	GSP_ACT_003("GSP-ACT-003"),
	GSP_ACT_004("GSP-ACT-004"),
	GSP_ACT_005("GSP-ACT-005"),
	GSP_ACT_006("GSP-ACT-006"),
	GSP_ACT_007("GSP-ACT-007"),
	GSP_ACT_008("GSP-ACT-008"),
	GSP_ACT_009("GSP-ACT-009"),
	GSP_ACT_010("GSP-ACT-010"),
	GSP_ACT_011("GSP-ACT-011"),
	GSP_ACT_012("GSP-ACT-012"),
	GSP_ACT_013("GSP-ACT-013"),
	GSP_ACT_014("GSP-ACT-014"),
	GSP_ACT_801("GSP-ACT-801"),
	GSP_ACT_998("GSP-ACT-998"),
	GSP_ACT_999("GSP-ACT-999");

	private String value;

	ActivityType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
