package com.example.ghost005.serviceclient.model.enums;

public enum TPMode
{
	GSP_TPM_001("GSP-TPM-001"),
	GSP_TPM_002("GSP-TPM-002"),
	GSP_TPM_003("GSP-TPM-003"),
	GSP_TPM_801("GSP-TPM-801"),
	GSP_TPM_998("GSP-TPM-998"),
	GSP_TPM_999("GSP-TPM-999");

	private String value;

	TPMode(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
