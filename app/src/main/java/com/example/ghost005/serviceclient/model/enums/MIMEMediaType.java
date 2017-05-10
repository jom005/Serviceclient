package com.example.ghost005.serviceclient.model.enums;

public enum MIMEMediaType
{
	IMAGE("image"),
	AUDIO("audio"),
	VIDEO("video"),
	TEXT("text"),
	APPLICATION("application"),
	MULTIPART("multipart"),
	OTHER("other");

	private String value;

	MIMEMediaType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
