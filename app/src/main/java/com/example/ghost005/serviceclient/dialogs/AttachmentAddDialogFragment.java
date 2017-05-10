package com.example.ghost005.serviceclient.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.sql.SQLException;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.RefreshEvent;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 10.10.15.
 */
public class AttachmentAddDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final String BUNDLE_ATTACHMENTS_DB_ID = "bundle_attachments_db_id";
	private static final String STATE_ATTACHMENT_NAME = "state_attachment_name";
	private static final String STATE_ATTACHMENT_DESCRIPTION = "state_attachment_description";

	private Button btnCancel;
	private Button btnOk;
	private EditText etAttachmentName;
	private EditText etAttachmentDescription;
	private LinearLayout llButtons;

	private int attachmentsDBID;

	public static AttachmentAddDialogFragment newInstance(int attachmentsDBID)
	{
		AttachmentAddDialogFragment fragment = new AttachmentAddDialogFragment();
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
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(getResources().getString(R.string.title_dialog_attachment_new));
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_attachment_add_dialog, container, false);

		llButtons = (LinearLayout) view.findViewById(R.id.layout_buttons);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		etAttachmentName = (EditText) view.findViewById(R.id.edit_text_attachment_name);
		etAttachmentDescription = (EditText) view.findViewById(R.id.edit_text_attachment_description);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			etAttachmentName.setText(savedInstanceState.getString(STATE_ATTACHMENT_NAME));
			etAttachmentDescription.setText(savedInstanceState.getString(STATE_ATTACHMENT_DESCRIPTION));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(STATE_ATTACHMENT_NAME, etAttachmentName.getText().toString());
		outState.putString(STATE_ATTACHMENT_DESCRIPTION, etAttachmentDescription.getText().toString());
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_cancel:
			{
				dismiss();
				break;
			}
			case R.id.button_ok:
			{
				if (checkMinInput())
				{
					try
					{
						save();
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}

					dismiss();
					EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH_ATTACHMENTS));
				}

				break;
			}
		}
	}

	private void save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		Attachments attachments = databaseAdapter.getAttachments(attachmentsDBID);

		String name = etAttachmentName.getText().toString();
		String description = etAttachmentDescription.getText().toString();

		Attachment attachment = new Attachment();
		attachment.setName(name);
		attachment.setAttachments(attachments);

		if (description.length() > 0)
		{
			attachment.setLongDescription(description);
		}

		databaseAdapter.createOrUpdateAttachment(attachment);
	}

	private boolean checkMinInput()
	{
		return etAttachmentName.getText().toString().trim().length() == 0;

	}
}
