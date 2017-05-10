package com.example.ghost005.serviceclient.model.comparators;

import java.util.Comparator;
import java.util.Date;

import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Created by me on 27.08.15.
 */
public class WorkStatusLogComparator implements Comparator<WorkStatusLog>
{
	@Override
	public int compare(WorkStatusLog lhs, WorkStatusLog rhs)
	{
		Date lhsDate = Utilities.getDateFromString(lhs.getTimestamp());
		Date rhsDate = Utilities.getDateFromString(rhs.getTimestamp());

		return lhsDate.compareTo(rhsDate);
	}
}
