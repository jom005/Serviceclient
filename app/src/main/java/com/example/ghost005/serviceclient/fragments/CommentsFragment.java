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
import com.example.ghost005.serviceclient.activities.CommentActivity;
import com.example.ghost005.serviceclient.adapter.CommentsAdapter;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.model.types.Comment;
import com.example.ghost005.serviceclient.model.types.Comments;

/**
 * Fragment displays a RecyclerView with different items relevant for the comments.
 */
public class CommentsFragment extends Fragment
		implements View.OnClickListener, CommentsAdapter.OnItemClickListener
{
	private static final int REQUEST_UPDATE = 11001;
	private static final String BUNDLE_COMMENTS_1_DB_ID = "bundle_comments_1_db_id";
	private static final String BUNDLE_COMMENTS_2_DB_ID = "bundle_comments_2_db_id";

	private RecyclerView rvComments;
	private CommentsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private FloatingActionButton fbtnAdd;

	private int comments1DBID;
	private int comments2DBID;

	public static CommentsFragment newInstance(int comments1DBID, int comments2DBID)
	{
		CommentsFragment fragment = new CommentsFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_COMMENTS_1_DB_ID, comments1DBID);
		args.putInt(BUNDLE_COMMENTS_2_DB_ID, comments2DBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		comments1DBID = getArguments().getInt(BUNDLE_COMMENTS_1_DB_ID);
		comments2DBID = getArguments().getInt(BUNDLE_COMMENTS_2_DB_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_comments, container, false);

		fbtnAdd = (FloatingActionButton) view.findViewById(R.id.fbtnAdd);
		fbtnAdd.setOnClickListener(this);

		rvComments = (RecyclerView) view.findViewById(R.id.rvComments);
		rvComments.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		rvComments.setLayoutManager(layoutManager);
		adapter = new CommentsAdapter(new ArrayList<Comment>(), new ArrayList<Comment>());
		adapter.setOnItemClickListener(this);
		rvComments.setAdapter(adapter);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		try
		{
			loadComments();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_UPDATE && resultCode == Activity.RESULT_OK)
		{
			try
			{
				adapter.setComments(new ArrayList<Comment>());
				adapter.setComments2(new ArrayList<Comment>());
				adapter.notifyDataSetChanged();
				loadComments();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(getActivity(), CommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(CommentActivity.BUNDLE_COMMENTS_DB_ID, comments2DBID);
		bundle.putInt(CommentActivity.BUNDLE_COMMENT_DB_ID, -1);
		bundle.putBoolean(CommentActivity.BUNDLE_NEW_COMMENT, true);
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_UPDATE);
	}

	@Override
	public void onItemClick(View view, int position)
	{
		if (position >= adapter.getComments().size())
		{
			Comment comment = adapter.getItem(position);

			Intent intent = new Intent(getActivity(), CommentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt(CommentActivity.BUNDLE_COMMENTS_DB_ID, comments2DBID);
			bundle.putInt(CommentActivity.BUNDLE_COMMENT_DB_ID, comment.getId());
			bundle.putBoolean(CommentActivity.BUNDLE_NEW_COMMENT, false);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_UPDATE);
		}
	}

	private void loadComments() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();

		if (comments1DBID != -1)
		{
			Comments comments = databaseAdapter.getComments(comments1DBID);

			if (comments != null)
			{
				adapter.setComments(new ArrayList<>(comments.getComments()));
			}
		}

		if (comments2DBID != -1)
		{
			Comments comments2 = databaseAdapter.getComments(comments2DBID);

			if (comments2 != null)
			{
				adapter.setComments2(new ArrayList<>(comments2.getComments()));
			}
		}

		adapter.sortComments();
		adapter.notifyDataSetChanged();
	}
}
