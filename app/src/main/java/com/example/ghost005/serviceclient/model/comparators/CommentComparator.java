package com.example.ghost005.serviceclient.model.comparators;

import java.util.Comparator;
import java.util.Date;

import com.example.ghost005.serviceclient.model.types.Comment;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Created by me on 27.08.15.
 */
public class CommentComparator implements Comparator<Comment>
{
	@Override
	public int compare(Comment lhs, Comment rhs)
	{
		Date lhsDate = Utilities.getDateFromString(lhs.getTimestamp());
		Date rhsDate = Utilities.getDateFromString(rhs.getTimestamp());

		return lhsDate.compareTo(rhsDate);
	}
}
