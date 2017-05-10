package com.example.ghost005.serviceclient.model.types;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class for ComplexType ZEUSPart2.
 */
@DatabaseTable
@Root
public class ZEUSPart2 implements Serializable
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	@Element(name = "assessmentInfo")
	private Assessment assessmentInfo;
	@Element(name = "zeus0201")
	@DatabaseField
	private String zeus0201;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0202", required = false)
	private String zeus0202;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0203", required = false)
	private String zeus0203;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0204", required = false)
	private String zeus0204;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0205", required = false)
	private String zeus0205;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0206", required = false)
	private String zeus0206;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0207", required = false)
	private String zeus0207;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0208", required = false)
	private String zeus0208;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0209", required = false)
	private String zeus0209;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0210", required = false)
	private String zeus0210;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0211", required = false)
	private String zeus0211;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeus0212", required = false)
	private String zeus0212;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeuske01", required = false)
	private BigDecimal zeuske01;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeuske0101", required = false)
	private BigDecimal zeuske0101;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeuske0102", required = false)
	private BigDecimal zeuske0102;
	@DatabaseField(canBeNull = true)
	@Element(name = "zeuske0103", required = false)
	private BigDecimal zeuske0103;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	@Element(name = "comments", required = false)
	private Comments comments;

	// Needed for ForeignCollection
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
	private ZEUSPart2Assessment zeusPart2Assessment;

	/**
	 * Required constructor.
	 */
	public ZEUSPart2()
	{
		super();
	}

	public ZEUSPart2(Assessment assessmentInfo, String zeus0201, String zeus0202,
					 String zeus0203, String zeus0204, String zeus0205, String zeus0206,
					 String zeus0207, String zeus0208, String zeus0209, String zeus0210,
					 String zeus0211, String zeus0212, BigDecimal zeuske01, BigDecimal zeuske0101,
					 BigDecimal zeuske0102, BigDecimal zeuske0103, Comments comments)
	{
		this.assessmentInfo = assessmentInfo;
		this.zeus0201 = zeus0201;
		this.zeus0202 = zeus0202;
		this.zeus0203 = zeus0203;
		this.zeus0204 = zeus0204;
		this.zeus0205 = zeus0205;
		this.zeus0206 = zeus0206;
		this.zeus0207 = zeus0207;
		this.zeus0208 = zeus0208;
		this.zeus0209 = zeus0209;
		this.zeus0210 = zeus0210;
		this.zeus0211 = zeus0211;
		this.zeus0212 = zeus0212;
		this.zeuske01 = zeuske01;
		this.zeuske0101 = zeuske0101;
		this.zeuske0102 = zeuske0102;
		this.zeuske0103 = zeuske0103;
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

	public String getZeus0201()
	{
		return zeus0201;
	}

	public String getZeus0202()
	{
		return zeus0202;
	}

	public String getZeus0203()
	{
		return zeus0203;
	}

	public String getZeus0204()
	{
		return zeus0204;
	}

	public String getZeus0205()
	{
		return zeus0205;
	}

	public String getZeus0206()
	{
		return zeus0206;
	}

	public String getZeus0207()
	{
		return zeus0207;
	}

	public String getZeus0208()
	{
		return zeus0208;
	}

	public String getZeus0209()
	{
		return zeus0209;
	}

	public String getZeus0210()
	{
		return zeus0210;
	}

	public String getZeus0211()
	{
		return zeus0211;
	}

	public String getZeus0212()
	{
		return zeus0212;
	}

	public BigDecimal getZeuske01()
	{
		return zeuske01;
	}

	public BigDecimal getZeuske0101()
	{
		return zeuske0101;
	}

	public BigDecimal getZeuske0102()
	{
		return zeuske0102;
	}

	public BigDecimal getZeuske0103()
	{
		return zeuske0103;
	}

	public Comments getComments()
	{
		return comments;
	}

	public ZEUSPart2Assessment getZeusPart2Assessment()
	{
		return zeusPart2Assessment;
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

	public void setZeus0201(String zeus0201)
	{
		this.zeus0201 = zeus0201;
	}

	public void setZeus0202(String zeus0202)
	{
		this.zeus0202 = zeus0202;
	}

	public void setZeus0203(String zeus0203)
	{
		this.zeus0203 = zeus0203;
	}

	public void setZeus0204(String zeus0204)
	{
		this.zeus0204 = zeus0204;
	}

	public void setZeus0205(String zeus0205)
	{
		this.zeus0205 = zeus0205;
	}

	public void setZeus0206(String zeus0206)
	{
		this.zeus0206 = zeus0206;
	}

	public void setZeus0207(String zeus0207)
	{
		this.zeus0207 = zeus0207;
	}

	public void setZeus0208(String zeus0208)
	{
		this.zeus0208 = zeus0208;
	}

	public void setZeus0209(String zeus0209)
	{
		this.zeus0209 = zeus0209;
	}

	public void setZeus0210(String zeus0210)
	{
		this.zeus0210 = zeus0210;
	}

	public void setZeus0211(String zeus0211)
	{
		this.zeus0211 = zeus0211;
	}

	public void setZeus0212(String zeus0212)
	{
		this.zeus0212 = zeus0212;
	}

	public void setZeuske01(BigDecimal zeuske01)
	{
		this.zeuske01 = zeuske01;
	}

	public void setZeuske0101(BigDecimal zeuske0101)
	{
		this.zeuske0101 = zeuske0101;
	}

	public void setZeuske0102(BigDecimal zeuske0102)
	{
		this.zeuske0102 = zeuske0102;
	}

	public void setZeuske0103(BigDecimal zeuske0103)
	{
		this.zeuske0103 = zeuske0103;
	}

	public void setComments(Comments comments)
	{
		this.comments = comments;
	}

	public void setZeusPart2Assessment(ZEUSPart2Assessment zeusPart2Assessment)
	{
		this.zeusPart2Assessment = zeusPart2Assessment;
	}
}
