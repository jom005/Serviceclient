package com.example.ghost005.serviceclient.utilities;

import android.content.Context;

import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.database.GSPDocument;
import com.example.ghost005.serviceclient.model.manifest.Document;
import com.example.ghost005.serviceclient.model.manifest.GSPManifest;
import com.example.ghost005.serviceclient.model.types.GSPInfo;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;

/**
 * Class for importing a gsp file to the database.
 * Created by me on 29.09.15.
 */
public class GSPImporter
{
	private static final String TEMP_DIR = "/temp";
	private static final String GSP_DIR = "/gsp";
	private static final String DATA_DIR = "/media";
	private static final String MANIFEST_DIR = "/manifest";
	private static final String MANIFEST_FILE = "manifest.xml";

	private Context context;

	private File tempDir;
	private File gspDir;

	public GSPImporter(Context context)
	{
		this.context = context;

		tempDir = new File(context.getExternalFilesDir(null), TEMP_DIR);
		gspDir = new File(context.getExternalFilesDir(null), GSP_DIR);
		File[] dirs = {tempDir, gspDir};

		createDirectories(dirs);
	}

	/**
	 * Imports a gsp file
	 *
	 * @param file gsp file
	 * @throws Exception
	 */
	public void importGSP(File file) throws Exception
	{
		File tempFile = file;

		if (!isGSPFile(file))
		{
			return;
		}

		if (!file.getParentFile().equals(tempDir))
		{
			tempFile = new File(tempDir.getAbsolutePath(), file.getName());
			FileUtils.copyFile(file, tempFile);
		}

		extractZipFile(tempFile);
		List<GSPDocument> gspDocuments = getGSPDocuments(tempDir);
		addGSPToDatabase(gspDocuments, tempDir);
	}

	/**
	 * Checks if a file is a gsp or zip file
	 *
	 * @param file file to check
	 * @return is file gsp or zip file
	 */
	private boolean isGSPFile(File file)
	{
		return !file.getName().endsWith(".gsp") || !file.getName().endsWith(".zip");
	}

	/**
	 * Creates temp and gsp directories.
	 *
	 * @param directories Array of directories
	 */
	private void createDirectories(File[] directories)
	{
		for (File file : directories)
		{
			if (!file.exists() || !file.isDirectory())
			{
				file.mkdirs();
			}
		}
	}

	/**
	 * Deletes the temp dir
	 *
	 * @throws IOException
	 */
	public void clearTemp() throws IOException
	{
		FileUtils.deleteDirectory(tempDir);
	}

	/**
	 * Returns a List of GSPDocuments from a directory if that directory had GSP xml files in it.
	 *
	 * @param directory file with gsp files
	 * @return List of GSPDocuments
	 * @throws Exception
	 */
	private List<GSPDocument> getGSPDocuments(File directory) throws Exception
	{
		File manifest = new File(directory.getAbsolutePath() + MANIFEST_DIR, MANIFEST_FILE);
		Serializer manifestSerializer = new Persister();
		GSPManifest gspManifest = manifestSerializer.read(GSPManifest.class, manifest);
		List<Document> documents = gspManifest.getDocuments().getDocuments();
		File[] extractedFiles = directory.listFiles();
		List<GSPDocument> gspDocuments = new ArrayList<>();

		for (File extractedFile : extractedFiles)
		{
			if (extractedFile.isFile() && extractedFile.getName().endsWith(".xml"))
			{
				String mediaPath = null;

				for (Document document : documents)
				{
					if (extractedFile.getName().equals(document.getName()))
					{
						mediaPath = document.getPath();
						break;
					}
				}

				Serializer gspSerializer = new Persister();
				GlobalServiceProtocol globalServiceProtocol = gspSerializer.read(GlobalServiceProtocol.class, extractedFile);
				gspDocuments.add(new GSPDocument(globalServiceProtocol, extractedFile.getName(), mediaPath));
			}
		}

		return gspDocuments;
	}

	/**
	 * Adds a list of GSPDocuments to the database.
	 *
	 * @param gspDocuments A List of GSPDocuments
	 * @param extractDir   Directory with the media files
	 * @throws SQLException
	 * @throws IOException
	 */
	private void addGSPToDatabase(List<GSPDocument> gspDocuments, File extractDir) throws SQLException, IOException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) context.getApplicationContext()).getDatabaseAdapter();
		List<GSPInfo> gspInfos = databaseAdapter.getAllGspInfos();

		for (GSPInfo gspInfo : gspInfos)
		{
			Iterator<GSPDocument> gspDocumentIterator = gspDocuments.iterator();

			while (gspDocumentIterator.hasNext())
			{
				GSPDocument gspDocument = gspDocumentIterator.next();

				if (gspDocument.getGlobalServiceProtocol().getGspInfo().getDocumentID().equals(gspInfo.getDocumentID()))
				{
					gspDocumentIterator.remove();
					break;
				}
			}
		}

		for (GSPDocument gspDocument : gspDocuments)
		{
			if (gspDocument.getMediaPath() != null)
			{
				String newMediaDirName = gspDocument.getFileName().substring(0, gspDocument.getFileName().lastIndexOf("."));
				File sourceDir = new File(extractDir.getAbsolutePath() + "/" + gspDocument.getMediaPath());
				File destDir = new File(gspDir.getAbsolutePath() + DATA_DIR + "/" + newMediaDirName);
				FileUtils.moveDirectory(sourceDir, destDir);
			}
		}

		databaseAdapter.insertGspDocuments(gspDocuments);
	}

	/**
	 * Extracts a zip file.
	 *
	 * @param zipFile zip file
	 * @throws IOException
	 */
	private void extractZipFile(File zipFile) throws IOException
	{
		try (ZipInputStream zipInputStream =
					 new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile))))
		{
			ZipEntry zipEntry;
			int count;
			byte[] buffer = new byte[1024];

			while ((zipEntry = zipInputStream.getNextEntry()) != null)
			{
				File file = new File(zipFile.getParentFile().getAbsolutePath(), zipEntry.getName());
				File dir = zipEntry.isDirectory() ? file : file.getParentFile();

				if (!dir.isDirectory() && !dir.mkdirs())
				{
					throw new FileNotFoundException("Failed to ensure directory: " +
							dir.getAbsolutePath());
				}

				if (zipEntry.isDirectory())
				{
					continue;
				}

				try (BufferedOutputStream bufferedOutputStream =
							 new BufferedOutputStream(new FileOutputStream(file)))
				{
					while ((count = zipInputStream.read(buffer)) != -1)
						bufferedOutputStream.write(buffer, 0, count);
				}
			}
		}
	}
}
