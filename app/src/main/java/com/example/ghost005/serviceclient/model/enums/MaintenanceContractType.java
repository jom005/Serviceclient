package com.example.ghost005.serviceclient.model.enums;

public enum MaintenanceContractType
{
	GSP_MCT_001("GSP-MCT-001"),
	GSP_MCT_002("GSP-MCT-002"),
	GSP_MCT_801("GSP-MCT-801"),
	GSP_MCT_998("GSP-MCT-998"),
	GSP_MCT_999("GSP-MCT-999");

	private String value;

	MaintenanceContractType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
