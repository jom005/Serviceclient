package com.example.ghost005.serviceclient.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for file related operations.
 */
public class FileUtilities
{
	public static final int NO_ERROR = 1;
	public static final int ERROR_OTHER = 2;
	public static final int ERROR_FILE_EXISTS = 3;
	public static final String TEMP_DIR = "/temp";
	public static final String GSP_SUBFOLDER = "/gsp";

	public static int saveBitmap(String tempFile, String filePath) throws IOException
	{
		if (tempFile == null || filePath == null)
		{
			return ERROR_OTHER;
		}

		File tempImageFile = new File(tempFile);
		File imageFile = new File(filePath);

		if (imageFile.exists() && imageFile.isFile())
		{
			return ERROR_FILE_EXISTS;
		}

		FileUtils.moveFile(tempImageFile, imageFile);

		return NO_ERROR;
	}

	public static int saveAudio(String srcFilePath, String destFilePath) throws IOException
	{
		if (srcFilePath == null || destFilePath == null)
		{
			return ERROR_OTHER;
		}

		String extension = getFileExtension(srcFilePath, true);

		File destFile = new File(destFilePath + extension);

		if (destFile.exists() && destFile.isFile())
		{
			return ERROR_FILE_EXISTS;
		}

		File srcFile = new File(srcFilePath);
		FileUtils.moveFile(srcFile, destFile);

		return NO_ERROR;
	}

	public static String getPathFromUri(Context context, Uri uri)
	{
		if (uri == null)
		{
			return null;
		}

		String path = null;
		String[] proj = {MediaStore.Audio.Media.DATA};
		Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

		if (cursor.moveToFirst())
		{
			path = cursor.getString(column_index);
		}

		cursor.close();

		return path;
	}

	public static String getFileExtension(String path, boolean withDot)
	{
		int lastDot = path.lastIndexOf(".");

		if (withDot)
		{
			return path.substring(lastDot, path.length());
		}
		else
		{
			return path.substring(lastDot + 1, path.length());
		}
	}

	public static File createImageFile(Context context) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = new File(context.getExternalFilesDir(null) + TEMP_DIR);
		storageDir.mkdirs();
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);

		return image;
	}

}
