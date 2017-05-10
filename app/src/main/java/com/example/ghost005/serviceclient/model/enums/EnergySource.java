package com.example.ghost005.serviceclient.model.enums;

public enum EnergySource
{
	GSP_ES_001("GSP-ES-001"),
	GSP_ES_002("GSP-ES-002"),
	GSP_ES_003("GSP-ES-003"),
	GSP_ES_004("GSP-ES-004"),
	GSP_ES_005("GSP-ES-005"),
	GSP_ES_006("GSP-ES-006"),
	GSP_ES_007("GSP-ES-007"),
	GSP_ES_008("GSP-ES-008"),
	GSP_ES_009("GSP-ES-009"),
	GSP_ES_801("GSP-ES-801"),
	GSP_ES_998("GSP-ES-998"),
	GSP_ES_999("GSP-ES-999");

	private String value;

	EnergySource(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
