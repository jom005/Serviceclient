package com.example.ghost005.serviceclient.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.adapter.AttachmentsArrayAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.events.RefreshEvent;
import com.example.ghost005.serviceclient.model.enums.MIMEMediaType;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.model.types.File;
import com.example.ghost005.serviceclient.model.types.FileLocation;
import com.example.ghost005.serviceclient.model.types.Files;
import com.example.ghost005.serviceclient.utilities.FileUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachmentDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final int TYPE_IMAGE = 1;
	private static final int TYPE_AUDIO = 2;
	private static final String BUNDLE_ATTACHMENTS = "bundle_attachments";
	private static final String BUNDLE_DATA = "bundle_data";
	private static final String BUNDLE_MEDIA_PATH = "bundle_media_path";
	private static final String BUNDLE_TYPE = "bundle_type";
	private static final String STATE_FILE_NAME = "state_file_name";
	private static final String STATE_FILE_DESCRIPTION = "state_file_description";
	private static final String STATE_SELECTED_POSITION = "state_selected_position";
	private static final String STATE_ATTACHMENT_NAME = "state_attachment_name";
	private static final String STATE_ATTACHMENT_DESCRIPTION = "state_attachment_description";
	private static final String STATE_NEW = "state_new";

	private Button btnCancel;
	private Button btnOk;
	private EditText etFileName;
	private EditText etFileDescription;
	private EditText etAttachmentName;
	private EditText etAttachmentDescription;
	private ImageView ivNew;
	private ImageView ivCancelNew;
	private LinearLayout llButtons;
	private LinearLayout llSpinner;
	private LinearLayout llEditText;
	private Spinner sSpinner;
	private TextView tvAttachment;

	private boolean newAttachment = false;
	private int position;
	private int type;
	private Attachments attachments;
	private ArrayList<Attachment> attachmentList;
	private String mediaPath;
	private String imageFilePath;
	private Uri uri;

	public static AttachmentDialogFragment newInstance(Attachments attachments, String mediaPath, String imageFilePath)
	{
		AttachmentDialogFragment fragment = new AttachmentDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable(BUNDLE_ATTACHMENTS, attachments);
		args.putString(BUNDLE_MEDIA_PATH, mediaPath);
		args.putString(BUNDLE_DATA, imageFilePath);
		args.putInt(BUNDLE_TYPE, TYPE_IMAGE);
		fragment.setArguments(args);
		return fragment;
	}

	public static AttachmentDialogFragment newInstance(Attachments attachments, String mediaPath, Uri uri)
	{
		AttachmentDialogFragment fragment = new AttachmentDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable(BUNDLE_ATTACHMENTS, attachments);
		args.putString(BUNDLE_MEDIA_PATH, mediaPath);
		args.putString(BUNDLE_DATA, uri.toString());
		args.putInt(BUNDLE_TYPE, TYPE_AUDIO);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		attachments = (Attachments) getArguments().getSerializable(BUNDLE_ATTACHMENTS);
		attachmentList = new ArrayList<>(attachments.getAttachments());
		mediaPath = getArguments().getString(BUNDLE_MEDIA_PATH);
		type = getArguments().getInt(BUNDLE_TYPE);

		if (type == TYPE_IMAGE)
		{
			imageFilePath = getArguments().getString(BUNDLE_DATA);
		}
		else if (type == TYPE_AUDIO)
		{
			uri = Uri.parse(getArguments().getString(BUNDLE_DATA));
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(getResources().getString(R.string.title_dialog_attachment_select));
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_attachment_dialog, container, false);

		llButtons = (LinearLayout) view.findViewById(R.id.layout_buttons);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		etFileName = (EditText) view.findViewById(R.id.edit_text_file_name);
		etFileDescription = (EditText) view.findViewById(R.id.edit_text_file_description);
		etAttachmentName = (EditText) view.findViewById(R.id.edit_text_attachment_name);
		etAttachmentDescription = (EditText) view.findViewById(R.id.edit_text_attachment_description);
		ivNew = (ImageView) view.findViewById(R.id.image_view_new_attachment);
		ivCancelNew = (ImageView) view.findViewById(R.id.image_view_cancel_new);
		sSpinner = (Spinner) view.findViewById(R.id.spinner_attachments);
		llSpinner = (LinearLayout) view.findViewById(R.id.linear_layout_spinner);
		llEditText = (LinearLayout) view.findViewById(R.id.linear_layout_edit_text);
		tvAttachment = (TextView) view.findViewById(R.id.text_view_attachment);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		ivNew.setOnClickListener(this);
		ivCancelNew.setOnClickListener(this);

		sSpinner.setAdapter(new AttachmentsArrayAdapter(getActivity(), R.layout.spinner_item, attachmentList));

		if (savedInstanceState != null)
		{
			etFileName.setText(savedInstanceState.getString(STATE_FILE_NAME));
			etFileDescription.setText(savedInstanceState.getString(STATE_FILE_DESCRIPTION));
			newAttachment = savedInstanceState.getBoolean(STATE_NEW);
			position = savedInstanceState.getInt(STATE_SELECTED_POSITION);

			if (attachments.getAttachments() != null && attachments.getAttachments().size() > 0)
			{
				sSpinner.setSelection(position);
			}

			if (newAttachment)
			{
				etAttachmentName.setText(savedInstanceState.getString(STATE_ATTACHMENT_NAME));
				etAttachmentDescription.setText(savedInstanceState.getString(STATE_ATTACHMENT_DESCRIPTION));
			}
		}

		setViews();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putBoolean(STATE_NEW, newAttachment);
		outState.putString(STATE_FILE_NAME, etFileName.getText().toString());
		outState.putString(STATE_FILE_DESCRIPTION, etFileDescription.getText().toString());

		if (attachments.getAttachments() != null && attachments.getAttachments().size() > 0)
		{
			outState.putInt(STATE_SELECTED_POSITION, sSpinner.getSelectedItemPosition());
		}

		if (newAttachment)
		{
			outState.putString(STATE_ATTACHMENT_NAME, etAttachmentName.getText().toString());
			outState.putString(STATE_ATTACHMENT_DESCRIPTION, etAttachmentDescription.getText().toString());
		}
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
				int error = FileUtilities.ERROR_OTHER;

				if (checkMinInput())
				{
					try
					{
						error = save();
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}

					if (error == FileUtilities.NO_ERROR)
					{
						dismiss();
						EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH_ATTACHMENTS));
					}
				}

				break;
			}
			case R.id.image_view_new_attachment:
			{
				newAttachment = true;
				setViews();
				break;
			}
			case R.id.image_view_cancel_new:
			{
				newAttachment = false;
				setViews();
				break;
			}
		}
	}

	private void setViews()
	{
		if (attachments.getAttachments() != null && attachments.getAttachments().size() > 0)
		{
			if (newAttachment)
			{
				tvAttachment.setText(getResources().getString(R.string.dialog_attachment_name));
				llSpinner.setVisibility(View.GONE);
				llEditText.setVisibility(View.VISIBLE);
			}
			else
			{
				tvAttachment.setText(getResources().getString(R.string.dialog_attachment));
				llSpinner.setVisibility(View.VISIBLE);
				llEditText.setVisibility(View.GONE);
			}
		}
		else
		{
			tvAttachment.setText(getResources().getString(R.string.dialog_attachment_name));
			newAttachment = true;
			llSpinner.setVisibility(View.GONE);
			llEditText.setVisibility(View.VISIBLE);
			ivCancelNew.setVisibility(View.GONE);
		}
	}

	private int save() throws SQLException, IOException
	{
		String fileName = etFileName.getText().toString().trim();
		String description = etFileDescription.getText().toString();
		String date = Utilities.exportDate(new Date());

		File file = new File();

		if (description.trim().length() > 0)
		{
			file.setDescription(description);
		}
		else
		{
			file.setDescription(null);
		}

		file.setCreationDate(date);
		file.setLastModification(date);
		FileLocation fileLocation = new FileLocation();
		fileLocation.setPath(mediaPath);
		file.setLocation(fileLocation);

		String path = getActivity().getExternalFilesDir(null) + FileUtilities.GSP_SUBFOLDER +
				java.io.File.separator + mediaPath + java.io.File.separator + fileName;

		int errorCode = FileUtilities.ERROR_OTHER;

		if (type == TYPE_IMAGE)
		{
			String extension = FileUtilities.getFileExtension(imageFilePath, true);
			path = path + extension;
			file.setMimeMediaType(MIMEMediaType.IMAGE.getValue());
			file.setMimeContentType("image/jpg");
			errorCode = FileUtilities.saveBitmap(imageFilePath, path);
			file.setName(fileName + extension);
		}
		else if (type == TYPE_AUDIO)
		{
			file.setMimeMediaType(MIMEMediaType.AUDIO.getValue());
			String uriPath = FileUtilities.getPathFromUri(getActivity(), uri);
			String extension = FileUtilities.getFileExtension(uriPath, false);
			file.setMimeContentType("audio/" + extension);
			errorCode = FileUtilities.saveAudio(uriPath, path);
			file.setName(fileName + "." + extension);
		}

		if (errorCode == FileUtilities.ERROR_FILE_EXISTS)
		{
			Toast.makeText(getActivity(), getResources().getString(R.string.toast_file_name_exists), Toast.LENGTH_LONG).show();
		}

		if (errorCode != FileUtilities.NO_ERROR)
		{
			return errorCode;
		}

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		databaseAdapter.createOrUpdateFileLocation(file.getLocation());

		if (newAttachment)
		{
			Attachment attachment = new Attachment();
			attachment.setName(etAttachmentName.getText().toString().trim());

			if (etAttachmentDescription.getText().toString().trim().length() > 0)
			{
				attachment.setLongDescription(etAttachmentDescription.getText().toString().trim());
			}

			attachment.setAttachments(attachments);
			Files files = new Files();
			ArrayList<File> fileList = new ArrayList<>();
			file.setFiles(files);
			fileList.add(file);
			files.setFiles(fileList);
			attachment.setFiles(files);

			databaseAdapter.createOrUpdateFile(file);
			databaseAdapter.createOrUpdateFiles(files);
			databaseAdapter.createOrUpdateAttachment(attachment);
		}
		else
		{
			Attachment attachment = attachmentList.get(sSpinner.getSelectedItemPosition());

			if (attachment.getFiles() != null)
			{
				Files files = attachment.getFiles();
				file.setFiles(files);

				databaseAdapter.createOrUpdateFile(file);
			}
			else
			{
				Files files = new Files();
				ArrayList<File> fileList = new ArrayList<>();
				file.setFiles(files);
				fileList.add(file);
				files.setFiles(fileList);
				attachment.setFiles(files);

				databaseAdapter.createOrUpdateFile(file);
				databaseAdapter.createOrUpdateFiles(files);
				databaseAdapter.createOrUpdateAttachment(attachment);
			}
		}

		return errorCode;
	}

	private boolean checkMinInput()
	{
		if (etFileName.getText().toString().trim().length() == 0)
		{
			return false;
		}

		if (newAttachment)
		{
			if (etAttachmentName.getText().toString().trim().length() == 0)
			{
				return false;
			}
		}

		return true;
	}
}
