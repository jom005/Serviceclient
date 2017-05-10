package com.example.ghost005.serviceclient.model.comparators;

import java.util.Comparator;
import java.util.Date;

import com.example.ghost005.serviceclient.model.types.ZEUSPart2;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Created by me on 10.09.15.
 */
public class ZEUSPart2DateComparator implements Comparator<ZEUSPart2>
{
	@Override
	public int compare(ZEUSPart2 lhs, ZEUSPart2 rhs)
	{
		Date lhsDate = Utilities.getDateFromString(lhs.getAssessmentInfo().getTimestamp());
		Date rhsDate = Utilities.getDateFromString(rhs.getAssessmentInfo().getTimestamp());

		return lhsDate.compareTo(rhsDate);
	}
}
