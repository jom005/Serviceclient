package com.example.ghost005.serviceclient.model.enums;

public enum CloudCover
{
	GSP_CC_001("GSP-CC-001"),
	GSP_CC_002("GSP-CC-002"),
	GSP_CC_003("GSP-CC-003"),
	GSP_CC_004("GSP-CC-004"),
	GSP_CC_005("GSP-CC-005"),
	GSP_CC_006("GSP-CC-006"),
	GSP_CC_007("GSP-CC-007"),
	GSP_CC_008("GSP-CC-008"),
	GSP_CC_009("GSP-CC-009"),
	GSP_CC_010("GSP-CC-010");

	private String value;

	CloudCover(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
