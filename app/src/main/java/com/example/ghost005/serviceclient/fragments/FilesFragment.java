package com.example.ghost005.serviceclient.fragments;


import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.FilesAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.File;
import com.example.ghost005.serviceclient.model.types.Files;
import com.example.ghost005.serviceclient.utilities.FileUtilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilesFragment extends Fragment
		implements View.OnClickListener, FilesAdapter.OnItemClickListener
{
	private static final String BUNDLE_ATTACHMENT_DB_ID = "bundle_attachment_db_id";

	private RecyclerView rvFiles;
	private FilesAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int attachmentDbId;

	public static FilesFragment newInstance(int attachmentDbId)
	{
		FilesFragment fragment = new FilesFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_ATTACHMENT_DB_ID, attachmentDbId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		attachmentDbId = getArguments().getInt(BUNDLE_ATTACHMENT_DB_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_files, container, false);
		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);
		rvFiles = (RecyclerView) view.findViewById(R.id.recycler_view_files);
		rvFiles.setHasFixedSize(true);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fbtnAdd.setOnClickListener(this);

		layoutManager = new LinearLayoutManager(getActivity());
		rvFiles.setLayoutManager(layoutManager);
		adapter = new FilesAdapter(getActivity(), new ArrayList<File>());
		rvFiles.setAdapter(adapter);
		adapter.setOnItemClickListener(this);

		try
		{
			loadData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view)
	{

	}

	@Override
	public void onItemClick(View view, int position)
	{
		File file = adapter.getItem(position);
		String mimeContentType = file.getMimeContentType();

		if (file.getLocation() != null && file.getLocation().getPath() != null)
		{
			String path = file.getLocation().getPath();
			String fileName = file.getName();
			String filePath = getActivity().getExternalFilesDir(null) + FileUtilities.GSP_SUBFOLDER +
					java.io.File.separator + path + java.io.File.separator + fileName;
			java.io.File mediaFile = new java.io.File(filePath);
			Uri uri = Uri.fromFile(mediaFile);

			if (mimeContentType == null)
			{
				ContentResolver contentResolver = getActivity().getContentResolver();
				mimeContentType = contentResolver.getType(uri);
			}

			if (mimeContentType == null)
			{
				mimeContentType = "*/*";
			}

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uri, mimeContentType);
			startActivity(intent);
		}
	}

	private void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		Attachment attachment = databaseAdapter.getAttachment(attachmentDbId);
		Files files = attachment.getFiles();

		if (files != null)
		{
			ArrayList<File> fileList = new ArrayList<>(files.getFiles());

			adapter.setFiles(fileList);
			adapter.notifyDataSetChanged();
		}
	}
}
