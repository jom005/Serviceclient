package com.example.ghost005.serviceclient.events;

/**
 * Created by me on 03.10.15.
 */
public class EmployeeSpinnerDialogEvent
{
	private String name;
	private String personnelNumber;
	private int dbID;

	public EmployeeSpinnerDialogEvent(String name, String personnelNumber, int dbID)
	{
		this.name = name;
		this.personnelNumber = personnelNumber;
		this.dbID = dbID;
	}

	public String getName()
	{
		return name;
	}

	public String getPersonnelNumber()
	{
		return personnelNumber;
	}

	public int getDbID()
	{
		return dbID;
	}
}
