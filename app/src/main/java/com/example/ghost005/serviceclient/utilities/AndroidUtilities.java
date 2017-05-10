package com.example.ghost005.serviceclient.utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.ghost005.serviceclient.R;

/**
 * Class for several android related utility methods.
 */
public class AndroidUtilities
{
	/**
	 * Checks if the device has a camera.
	 *
	 * @param context Application context
	 * @return is camera available
	 */
	public static boolean isCameraAvailable(Context context)
	{
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	/**
	 * Checks if the device has a flashlight.
	 *
	 * @param context Application context
	 * @return is flashlight available
	 */
	public static boolean isFlashLightAvailable(Context context)
	{
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
	}

	/**
	 * Checks if the device is connected to a network.
	 *
	 * @param context application context
	 * @return is connected
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * Returns the current system language.
	 *
	 * @return system language
	 */
	public static String getSystemLanguage(Context context)
	{
		return context.getResources().getConfiguration().locale.getLanguage();
	}

	/**
	 * Checks if a process is running.
	 *
	 * @param context      application context
	 * @param serviceClass service class
	 * @return is running
	 */
	public static boolean isProcessRunning(Context context, Class<?> serviceClass)
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(
				Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(
				Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Shows or hides the soft keyboard for a view.
	 *
	 * @param context Application context
	 * @param view    View the keyboard is shown
	 * @param visible show or hide
	 */
	public static void showInputMethod(Context context, View view, boolean visible)
	{
		InputMethodManager imm =
				(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (visible)
		{
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
		else
		{
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * Make a Snackbar with the correct text color and text.
	 * @param context Application context
	 * @param view view for the snackbar
	 * @param text text to display
	 * @param length duration
	 * @return snackbar
	 */
	public static Snackbar makeSnackbar (Context context, View view, String text, int length)
	{
		Snackbar snackbar = Snackbar.make(view, text, length);
		View snackbarView = snackbar.getView();
		snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.primary));
		TextView snackbarText = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
		snackbarText.setTextColor(ContextCompat.getColor(context, R.color.snackbar_text));
		snackbar.setActionTextColor(Color.WHITE);

		return snackbar;
	}
}
