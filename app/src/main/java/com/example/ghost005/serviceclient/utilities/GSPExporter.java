package com.example.ghost005.serviceclient.utilities;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.example.ghost005.serviceclient.database.GSPDocument;
import com.example.ghost005.serviceclient.model.manifest.Document;
import com.example.ghost005.serviceclient.model.manifest.Documents;
import com.example.ghost005.serviceclient.model.manifest.GSPManifest;
import com.example.ghost005.serviceclient.model.manifest.MediaFile;

/**
 * Class for exporting a work report from the database into a .gsp file.
 */
public class GSPExporter
{
	private static final String TEMP_DIR = "/temp";
	private static final String GSP_DIR = "/gsp";
	private static final String DATA_DIR_NAME = "media";
	private static final String DATA_DIR = "/" + DATA_DIR_NAME;
	private static final String MANIFEST_DIR = "/manifest";
	private static final String MANIFEST_FILE = "manifest.xml";

	private Context context;

	private File tempDir;
	private File gspDir;

	public GSPExporter(Context context)
	{
		this.context = context;

		tempDir = new File(context.getExternalFilesDir(null), TEMP_DIR);
		gspDir = new File(context.getExternalFilesDir(null), GSP_DIR);

		createDirectory(tempDir);
	}

	public void exportGSP(List<GSPDocument> gspDocuments, String fileName, File exportTo) throws Exception
	{
		clearTemp();
		createDirectory(tempDir);

		File exportTempDir = new File(tempDir.getAbsolutePath(), fileName);
		File exportTempDataDir = new File(exportTempDir.getAbsolutePath() + DATA_DIR);
		File manifestDir = new File(exportTempDir.getAbsolutePath() + MANIFEST_DIR);
		File manifestFile = new File(manifestDir.getAbsolutePath(), MANIFEST_FILE);
		exportTempDir.mkdirs();
		exportTempDataDir.mkdirs();
		manifestDir.mkdirs();

		GSPManifest manifest = new GSPManifest();
		Documents documents = new Documents();
		List<Document> documentList = new ArrayList<>();

		for (GSPDocument gspDocument : gspDocuments)
		{
			Document document = new Document();
			document.setName(gspDocument.getFileName());

			File exportFile = new File(exportTempDir.getAbsolutePath(), gspDocument.getFileName());
			String dataDirName = gspDocument.getFileName().substring(0, gspDocument.getFileName().lastIndexOf("."));
			File dataDir = new File(gspDir.getAbsolutePath() + DATA_DIR, dataDirName);

			Serializer serializer = new Persister();
			serializer.write(gspDocument.getGlobalServiceProtocol(), exportFile);

			if (dataDir.exists() && dataDir.isDirectory())
			{
				document.setPath(DATA_DIR_NAME + "/" + dataDirName);

				File exportFileDataDir = new File(exportTempDataDir.getAbsolutePath(), dataDirName);
				FileUtils.copyDirectory(dataDir, exportFileDataDir);

				List<MediaFile> mediaFiles = new ArrayList<>();
				File[] mediaFilesToAdd = exportFileDataDir.listFiles();

				for (File mediaFile : mediaFilesToAdd)
				{
					if (mediaFile.isFile())
					{
						Uri fileUri = Uri.fromFile(mediaFile);
						String extension = MimeTypeMap.getFileExtensionFromUrl(
								fileUri.toString());
						String mimeType = MimeTypeMap.getSingleton()
								.getMimeTypeFromExtension(extension);

						mediaFiles.add(new MediaFile(mediaFile.getName(), mimeType));
					}
				}

				if (mediaFiles.size() > 0)
				{
					document.setMediaFiles(mediaFiles);
				}
			}

			documentList.add(document);
		}

		documents.setDocuments(documentList);
		manifest.setDocuments(documents);

		Serializer serializer = new Persister();
		serializer.write(manifest, manifestFile);

		if (exportTempDataDir.listFiles().length == 0)
		{
			exportTempDataDir.delete();
		}

		File exportFile = compressDirectory(exportTempDir, fileName);

		if (exportTo.isDirectory() && exportTo.canWrite())
		{
			File destFile = new File(exportTo, exportFile.getName());

			FileUtils.moveFile(exportFile, destFile);
		}
	}

	/**
	 * Creates temp adirectory.
	 *
	 * @param directory Array of directories
	 */
	private void createDirectory(File directory)
	{
		if (!directory.exists() || !directory.isDirectory())
		{
			directory.mkdirs();
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
	 * Compresses a file or path to a gsp file.
	 *
	 * @param directory directory to compress
	 */
	private File compressDirectory(File directory, String zipFileName)
			throws IOException
	{
		if (!directory.isDirectory())
		{
			throw new IOException("No directory");
		}

		File zipFile = new File(directory.getParentFile().getAbsolutePath(), zipFileName + ".gsp");

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile)))
		{
			addDirectory(directory, directory, zipOutputStream);
		}

		return zipFile;
	}


	/**
	 * Helper method for compressZipFile, which can called recursively to compress a subfolder.
	 *
	 * @param directory       directory to compress
	 * @param zipOutputStream ZipOutputStream
	 */
	private void addDirectory(File root, File directory, ZipOutputStream zipOutputStream)
			throws IOException
	{
		File[] files = directory.listFiles();
		byte[] buffer = new byte[1024];

		for (File file : files)
		{
			if (file.isDirectory())
			{
				addDirectory(root, file, zipOutputStream);
			}
			else
			{
				try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
						new FileInputStream(file.getAbsolutePath())))
				{
					String relativePath = root.toURI().relativize(file.toURI()).getPath();
					zipOutputStream.putNextEntry(new ZipEntry(relativePath));

					int length;

					while ((length = bufferedInputStream.read(buffer)) != -1)
					{
						zipOutputStream.write(buffer, 0, length);
					}
				}
			}
		}
	}
}
