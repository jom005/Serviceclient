package com.example.ghost005.serviceclient.model.enums;

public enum MaintenanceLevel
{
	GSP_ML_001("GSP-ML-001"),
	GSP_ML_002("GSP-ML-002"),
	GSP_ML_003("GSP-ML-003"),
	GSP_ML_004("GSP-ML-004"),
	GSP_ML_005("GSP-ML-005"),
	GSP_ML_801("GSP-ML-801"),
	GSP_ML_998("GSP-ML-998"),
	GSP_ML_999("GSP-ML-999");

	private String value;

	MaintenanceLevel(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
