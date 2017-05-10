package com.example.ghost005.serviceclient.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for several utility methods.
 */
public class Utilities
{
	private static final SimpleDateFormat XML_DATE_FORMAT =
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat DATE = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * Import a date from xml format to app format.
	 *
	 * @param dateString date in xml format as string
	 * @return date in app format
	 */
	public static String importDate(String dateString)
	{
		if (dateString != null)
		{
			Date date = Utilities.getDateFromString(dateString);

			String dateMedium = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
			String timeShort = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(date);

			return dateMedium + " - " + timeShort;
		}

		return null;
	}

	/**
	 * Export a date from app format to xml format.
	 *
	 * @param dateString date in app format as string
	 * @return date in xml format
	 */
	public static String exportDateString(String dateString)
	{
		try
		{
			if (dateString != null)
			{
				// Date formats are not synchronized
				synchronized (XML_DATE_FORMAT)
				{
					Date date = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM,
							SimpleDateFormat.SHORT).parse(dateString.replace(" - ", ""));

					return XML_DATE_FORMAT.format(date);
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static String exportDate(Date date)
	{
		if (date != null)
		{
			// Date formats are not synchronized
			synchronized (XML_DATE_FORMAT)
			{
				return XML_DATE_FORMAT.format(date);
			}
		}

		return null;
	}

	/**
	 * Get a Date Object from a String.
	 *
	 * @param dateString Date as String
	 * @return Date
	 */
	public static Date getDateFromString(String dateString)
	{
		try
		{
			if (dateString != null)
			{
				// Date formats are not synchronized
				synchronized (XML_DATE_FORMAT)
				{
					return XML_DATE_FORMAT.parse(dateString);
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static String getTime(Date date)
	{
		if (date != null)
		{
			synchronized (TIME)
			{
				return TIME.format(date);
			}
		}

		return null;
	}

	public static String getDate(Date date)
	{
		if (date != null)
		{
			synchronized (DATE)
			{
				return DATE.format(date);
			}
		}

		return null;
	}

	public static String getDurationAsString(String start, String end)
	{
		String durationString = null;

		if (start != null && end != null)
		{
			Date startDate = Utilities.getDateFromString(start);
			Date endDate = Utilities.getDateFromString(end);

			if (startDate != null && endDate != null)
			{
				long duration = endDate.getTime() - startDate.getTime();
				durationString = String.valueOf(duration);
			}
		}

		return durationString;
	}

	public static String getDurationString(String durationString)
	{
		long duration = Long.parseLong(durationString);
		long minutes = duration / (60 * 1000) % 60;
		long hours = duration / (60 * 60 * 1000) % 24;
		long days = duration / (24 * 60 * 60 * 1000);

		StringBuilder stringBuilder = new StringBuilder();

		if (days > 0)
		{
			stringBuilder.append(String.valueOf(days));
			stringBuilder.append("d ");
		}

		if (hours > 0)
		{
			stringBuilder.append(String.valueOf(hours));
			stringBuilder.append("h ");
		}

		if (minutes > 0)
		{
			stringBuilder.append(String.valueOf(minutes));
			stringBuilder.append("min");
		}

		return stringBuilder.toString();
	}

	/**
	 * Concatenates two String arrays.
	 *
	 * @param s1 first array
	 * @param s2 second array
	 * @return concatenated array
	 */
	public static String[] concat(String[] s1, String[] s2)
	{
		String[] result = new String[s1.length + s2.length];

		System.arraycopy(s1, 0, result, 0, s1.length);
		System.arraycopy(s2, 0, result, s1.length, s2.length);

		return result;
	}


}
