package com.example.ghost005.serviceclient.model.enums;

public enum WorkStatus
{
	GSP_SWO_101("GSP-SWO-101"),
	GSP_SWO_102("GSP-SWO-102"),
	GSP_SWO_103("GSP-SWO-103"),
	GSP_SWO_104("GSP-SWO-104"),
	GSP_SWO_105("GSP-SWO-105"),
	GSP_SWO_106("GSP-SWO-106"),
	GSP_SWO_107("GSP-SWO-107"),
	GSP_SWO_108("GSP-SWO-108"),
	GSP_SWO_109("GSP-SWO-109"),
	GSP_SWO_110("GSP-SWO-110"),
	GSP_SWO_111("GSP-SWO-111"),
	GSP_SWO_112("GSP-SWO-112"),
	GSP_SWO_113("GSP-SWO-113"),
	GSP_SWO_114("GSP-SWO-114"),
	GSP_SWO_801("GSP-SWO-801"),
	GSP_SWO_998("GSP-SWO-998"),
	GSP_SWO_999("GSP-SWO-999");

	private String value;

	WorkStatus(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
