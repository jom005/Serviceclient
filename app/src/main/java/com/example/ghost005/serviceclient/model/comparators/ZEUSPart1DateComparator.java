package com.example.ghost005.serviceclient.model.comparators;

import java.util.Comparator;
import java.util.Date;

import com.example.ghost005.serviceclient.model.types.ZEUSPart1;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Created by me on 17.08.15.
 */
public class ZEUSPart1DateComparator implements Comparator<ZEUSPart1>
{
	@Override
	public int compare(ZEUSPart1 lhs, ZEUSPart1 rhs)
	{
		Date lhsDate = Utilities.getDateFromString(lhs.getAssessmentInfo().getTimestamp());
		Date rhsDate = Utilities.getDateFromString(rhs.getAssessmentInfo().getTimestamp());

		return lhsDate.compareTo(rhsDate);
	}
}
