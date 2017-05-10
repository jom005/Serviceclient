package com.example.ghost005.serviceclient.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.example.ghost005.serviceclient.activities.FilesActivity;
import com.example.ghost005.serviceclient.adapter.AttachmentsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.AttachmentAddDialogFragment;
import com.example.ghost005.serviceclient.events.RefreshEvent;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.utilities.Constants;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachmentsFragment extends Fragment implements View.OnClickListener, AttachmentsAdapter.OnItemClickListener
{
	private static final String BUNDLE_ATTACHMENTS_DB_ID = "bundle_attachments_db_id";
	private static final String FRAGMENT_ATTACHMENT_ADD = "fragment_attachment_add";
	private static final int REQUEST_UPDATE = 0x0111;

	private RecyclerView rvAttachments;
	private AttachmentsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int attachmentsDBID;

	public static AttachmentsFragment newInstance(int attachmentsDBID)
	{
		AttachmentsFragment fragment = new AttachmentsFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_ATTACHMENTS_DB_ID, attachmentsDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		attachmentsDBID = getArguments().getInt(BUNDLE_ATTACHMENTS_DB_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_attachments, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.floating_button_add);

		rvAttachments = (RecyclerView) view.findViewById(R.id.recycler_view_attachments);
		rvAttachments.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvAttachments.setLayoutManager(layoutManager);
		adapter = new AttachmentsAdapter(new ArrayList<Attachment>());
		rvAttachments.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		fbtnAdd.setOnClickListener(this);
		adapter.setOnItemClickListener(this);

		try
		{
			loadAttachments();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_UPDATE)
		{
			adapter.setAttachments(new ArrayList<Attachment>());
			adapter.notifyDataSetChanged();

			try
			{
				loadAttachments();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.floating_button_add:
			{
				AttachmentAddDialogFragment fragment = AttachmentAddDialogFragment.newInstance(attachmentsDBID);
				fragment.show(getFragmentManager(), FRAGMENT_ATTACHMENT_ADD);
			}
		}
	}

	@Override
	public void onItemClick(View view, int position)
	{
		Attachment attachment = adapter.getItem(position);

		Intent intent = new Intent(getActivity(), FilesActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.BUNDLE_ATTACHMENT_DB_ID, attachment.getId());
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_UPDATE);
	}

	private void loadAttachments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		Attachments attachments = databaseAdapter.getAttachments(attachmentsDBID);

		if (attachments != null)
		{
			adapter.setAttachments(new ArrayList<>(attachments.getAttachments()));
			adapter.notifyDataSetChanged();
		}
	}

	public void onEvent(RefreshEvent event)
	{
		if (event.getRefresh() == RefreshEvent.REFRESH_ATTACHMENTS)
		{
			try
			{
				loadAttachments();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
