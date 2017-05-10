package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Class for ComplexType MeasuringInstallation.
 */
@DatabaseTable
@Root
public class MeasuringInstallation implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "installationInfo")
	private AssignedElement installationInfo;

	/**
	 * Required constructor.
	 */
	public MeasuringInstallation()
	{
		super();
	}

	/**
	 * Constructor sets elements
	 * @param installationInfo installationInfo element
	 */
	public MeasuringInstallation(AssignedElement installationInfo)
	{
		this.installationInfo = installationInfo;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public AssignedElement getInstallationInfo()
	{
		return installationInfo;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setInstallationInfo(AssignedElement installationInfo)
	{
		this.installationInfo = installationInfo;
	}
}
