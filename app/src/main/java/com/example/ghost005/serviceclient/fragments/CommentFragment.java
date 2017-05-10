package com.example.ghost005.serviceclient.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.SQLException;
import java.util.Date;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Comment;
import com.example.ghost005.serviceclient.model.types.Comments;
import com.example.ghost005.serviceclient.utilities.AndroidUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment
		implements View.OnClickListener, View.OnFocusChangeListener
{
	private static final String BUNDLE_COMMENTS_DB_ID = "bundle_comments_db_id";
	private static final String BUNDLE_COMMENT_DB_ID = "bundle_comment_db_id";
	private static final String BUNDLE_NEW_COMMENT = "bundle_new_comment";
	private static final String STATE_COMMENT = "state_comment";

	private EditText etComment;
	private LinearLayout llButtons;
	private Button btnOk;
	private Button btnCancel;

	private int commentsDBID;
	private int commentDBID;
	private boolean newComment;

	public static CommentFragment newInstance(int commentsDBID, int commentDBID, boolean newComment)
	{
		CommentFragment fragment = new CommentFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_COMMENTS_DB_ID, commentsDBID);
		args.putInt(BUNDLE_COMMENT_DB_ID, commentDBID);
		args.putBoolean(BUNDLE_NEW_COMMENT, newComment);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		commentsDBID = getArguments().getInt(BUNDLE_COMMENTS_DB_ID);
		commentDBID = getArguments().getInt(BUNDLE_COMMENT_DB_ID);
		newComment = getArguments().getBoolean(BUNDLE_NEW_COMMENT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_comment, container, false);

		etComment = (EditText) view.findViewById(R.id.edit_text_comment);
		llButtons = (LinearLayout) view.findViewById(R.id.layout_buttons);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		etComment.setOnFocusChangeListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			etComment.setText(savedInstanceState.getString(STATE_COMMENT));
		}
		else
		{
			try
			{
				loadData();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(STATE_COMMENT, etComment.getText().toString());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_comment, menu);

		MenuItem itemDelete = menu.findItem(R.id.action_delete);

		if (newComment)
		{
			itemDelete.setVisible(false);
		}
		else
		{
			itemDelete.setVisible(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_scan:
			{
				new IntentIntegrator(getActivity()).initiateScan();
				return true;
			}
			case R.id.action_delete:
			{
				try
				{
					DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
					Comments comments = databaseAdapter.getComments(commentsDBID);
					Comment comment = databaseAdapter.getComment(commentDBID);

					databaseAdapter.deleteComment(comment);

					if (comments != null && comments.getComments().size() == 0)
					{
						databaseAdapter.deleteComments(comments);
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK)
		{
			if (intentResult != null)
			{
				String code = intentResult.getContents();

				if (etComment.hasFocus())
				{
					int selectionStart = etComment.getSelectionStart();
					int selectionEnd = etComment.getSelectionEnd();
					int textLength = etComment.getText().length();

					String start = etComment.getText().subSequence(0, selectionStart).toString();
					String end = etComment.getText().subSequence(selectionEnd, textLength).toString();
					String text = start + code + end;

					etComment.setText(text);
					etComment.setSelection(code.length());
				}
				else
				{
					String comment = etComment.getText().toString();
					String text = comment + code;

					etComment.setText(text);
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_cancel:
			{
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
				break;
			}
			case R.id.button_ok:
			{
				try
				{
					save();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				break;
			}
		}
	}

	private void loadData() throws SQLException
	{
		if (!newComment)
		{
			DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
			Comment comment = databaseAdapter.getComment(commentDBID);

			etComment.setText(comment.getMessage());
			etComment.setSelection(etComment.getText().length());
		}
	}

	private void save() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		Comment comment;

		if (newComment)
		{
			comment = new Comment();
		}
		else
		{
			comment = databaseAdapter.getComment(commentDBID);
		}

		comment.setMessage(etComment.getText().toString());
		comment.setEditor("Jonas Mahlke"); //TODO: set responsible employee
		comment.setTimestamp(Utilities.exportDate(new Date()));

		if (newComment)
		{
			Comments comments = databaseAdapter.getComments(commentsDBID);
			comment.setComments(comments);
		}

		databaseAdapter.createOrUpdateComment(comment);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		AndroidUtilities.showInputMethod(getActivity(), etComment, hasFocus);
	}
}
