package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class for ComplexType ZEUSPart1.
 */
@DatabaseTable
@Root
public class ZEUSPart1 implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "assessmentInfo")
	private Assessment assessmentInfo;
	@DatabaseField
	@Element(name = "zeus0101")
	private String zeus0101;
	@DatabaseField
	@Element(name = "zeus0102")
	private String zeus0102;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0103", required = false)
	private String zeus0103;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0104", required = false)
	private String zeus0104;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA01", required = false)
	private BigDecimal zeusKA01;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA0101", required = false)
	private BigDecimal zeusKA0101;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA0102", required = false)
	private BigDecimal zeusKA0102;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA0103", required = false)
	private BigDecimal zeusKA0103;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA02", required = false)
	private BigDecimal zeusKA02;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA03", required = false)
	private BigDecimal zeusKA03;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA04", required = false)
	private BigDecimal zeusKA04;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeusKA05", required = false)
	private BigDecimal zeusKA05;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private ZEUSPart1Assessment zeusPart1Assessment;

	/**
	 * Required constructor.
	 */
	public ZEUSPart1()
	{
		super();
	}

	public ZEUSPart1(Assessment assessmentInfo, String zeus0101, String zeus0102,
					 String zeus0103, String zeus0104, BigDecimal zeusKA01, BigDecimal zeusKA0101,
					 BigDecimal zeusKA0102, BigDecimal zeusKA0103, BigDecimal zeusKA02,
					 BigDecimal zeusKA03, BigDecimal zeusKA04, BigDecimal zeusKA05,
					 Comments comments)
	{
		this.assessmentInfo = assessmentInfo;
		this.zeus0101 = zeus0101;
		this.zeus0102 = zeus0102;
		this.zeus0103 = zeus0103;
		this.zeus0104 = zeus0104;
		this.zeusKA01 = zeusKA01;
		this.zeusKA0101 = zeusKA0101;
		this.zeusKA0102 = zeusKA0102;
		this.zeusKA0103 = zeusKA0103;
		this.zeusKA02 = zeusKA02;
		this.zeusKA03 = zeusKA03;
		this.zeusKA04 = zeusKA04;
		this.zeusKA05 = zeusKA05;
		this.comments = comments;
	}

	/* ---- Getter ---- */

	public int getId()
	{
		return id;
	}

	public Assessment getAssessmentInfo()
	{
		return assessmentInfo;
	}

	public String getZeus0101()
	{
		return zeus0101;
	}

	public String getZeus0102()
	{
		return zeus0102;
	}

	public String getZeus0103()
	{
		return zeus0103;
	}

	public String getZeus0104()
	{
		return zeus0104;
	}

	public BigDecimal getZeusKA01()
	{
		return zeusKA01;
	}

	public BigDecimal getZeusKA0101()
	{
		return zeusKA0101;
	}

	public BigDecimal getZeusKA0102()
	{
		return zeusKA0102;
	}

	public BigDecimal getZeusKA0103()
	{
		return zeusKA0103;
	}

	public BigDecimal getZeusKA02()
	{
		return zeusKA02;
	}

	public BigDecimal getZeusKA03()
	{
		return zeusKA03;
	}

	public BigDecimal getZeusKA04()
	{
		return zeusKA04;
	}

	public BigDecimal getZeusKA05()
	{
		return zeusKA05;
	}

	public Comments getComments()
	{
		return comments;
	}

	public ZEUSPart1Assessment getZeusPart1Assessment()
	{
		return zeusPart1Assessment;
	}

	/* ---- Setter ---- */

	public void setId(int id)
	{
		this.id = id;
	}

	public void setAssessmentInfo(Assessment assessmentInfo)
	{
		this.assessmentInfo = assessmentInfo;
	}

	public void setZeus0101(String zeus0101)
	{
		this.zeus0101 = zeus0101;
	}

	public void setZeus0102(String zeus0102)
	{
		this.zeus0102 = zeus0102;
	}

	public void setZeus0103(String zeus0103)
	{
		this.zeus0103 = zeus0103;
	}

	public void setZeus0104(String zeus0104)
	{
		this.zeus0104 = zeus0104;
	}

	public void setZeusKA01(BigDecimal zeusKA01)
	{
		this.zeusKA01 = zeusKA01;
	}

	public void setZeusKA0101(BigDecimal zeusKA0101)
	{
		this.zeusKA0101 = zeusKA0101;
	}

	public void setZeusKA0102(BigDecimal zeusKA0102)
	{
		this.zeusKA0102 = zeusKA0102;
	}

	public void setZeusKA0103(BigDecimal zeusKA0103)
	{
		this.zeusKA0103 = zeusKA0103;
	}

	public void setZeusKA02(BigDecimal zeusKA02)
	{
		this.zeusKA02 = zeusKA02;
	}

	public void setZeusKA03(BigDecimal zeusKA03)
	{
		this.zeusKA03 = zeusKA03;
	}

	public void setZeusKA04(BigDecimal zeusKA04)
	{
		this.zeusKA04 = zeusKA04;
	}

	public void setZeusKA05(BigDecimal zeusKA05)
	{
		this.zeusKA05 = zeusKA05;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setZeusPart1Assessment(ZEUSPart1Assessment zeusPart1Assessment)
	{
		this.zeusPart1Assessment = zeusPart1Assessment;
	}
}
