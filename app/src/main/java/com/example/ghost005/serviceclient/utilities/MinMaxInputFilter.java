package com.example.ghost005.serviceclient.utilities;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Class for setting a min and a max value for InputText fields with numbers.
 */
public class MinMaxInputFilter implements InputFilter
{
	protected double min;
	protected double max;

	public MinMaxInputFilter(double min, double max)
	{
		this.min = min;
		this.max = max;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
	{
		try
		{
			double input = Double.parseDouble(dest.toString() + source.toString());

			if (isInRange(min, max, input))
			{
				return null;
			}
		}
		catch (NumberFormatException e)
		{
			// Empty
		}

		return "";
	}

	private boolean isInRange(double a, double b, double c)
	{
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}
