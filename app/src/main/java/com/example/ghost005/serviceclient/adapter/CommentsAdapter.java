package com.example.ghost005.serviceclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.comparators.CommentComparator;
import com.example.ghost005.serviceclient.model.types.Comment;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for comments RecyclerView.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>
{
	private List<Comment> comments;
	private List<Comment> comments2;
	private OnItemClickListener onItemClickListener;

	public CommentsAdapter(List<Comment> comments, List<Comment> comments2)
	{
		this.comments = comments;
		this.comments2 = comments2;
	}

	@Override
	public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_comment, parent, false);

		return new CommentViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(CommentViewHolder holder, int position)
	{
		Comment comment = null;

		if (position < comments.size())
		{
			comment = comments.get(position);
		}
		else if (position >= comments.size() && comments2.size() > 0)
		{
			int commentsSize = comments.size();

			comment = comments2.get(position - commentsSize);
		}

		if (comment != null)
		{
			String commentDate = Utilities.importDate(comment.getTimestamp());

			holder.tvCommentName.setText(comment.getEditor());
			holder.tvCommentDate.setText(commentDate);
			holder.tvComment.setText(comment.getMessage());
		}
	}

	@Override
	public int getItemCount()
	{
		return comments.size() + comments2.size();
	}

	public List<Comment> getComments()
	{
		return comments;
	}

	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}

	public List<Comment> getComments2()
	{
		return comments2;
	}

	public void setComments2(List<Comment> comments2)
	{
		this.comments2 = comments2;
	}

	public void sortComments()
	{
		if (comments.size() > 1)
		{
			Collections.sort(comments, new CommentComparator());
		}

		if (comments2.size() > 1)
		{
			Collections.sort(comments2, new CommentComparator());
		}
	}

	public Comment getItem(int position)
	{
		Comment comment = null;

		if (position < comments.size())
		{
			comment = comments.get(position);
		}
		else if (position >= comments.size() && comments2.size() > 0)
		{
			int commentsSize = comments.size();

			comment = comments2.get(position - commentsSize);
		}

		return comment;
	}

	/**
	 * Sets the custom on ItemClickListener for clicks on items.
	 *
	 * @param onItemClickListener onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.onItemClickListener = onItemClickListener;
	}

	/**
	 * ViewHolder for the CommentsAdapter.
	 */
	public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected TextView tvCommentName;
		protected TextView tvCommentDate;
		protected TextView tvComment;

		public CommentViewHolder(View itemView)
		{
			super(itemView);

			tvCommentName = (TextView) itemView.findViewById(R.id.tvCommentName);
			tvCommentDate = (TextView) itemView.findViewById(R.id.tvCommentDate);
			tvComment = (TextView) itemView.findViewById(R.id.tvComment);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v)
		{
			if (onItemClickListener != null)
			{
				onItemClickListener.onItemClick(v, getAdapterPosition());
			}
		}
	}

	/**
	 * Interface for the OnItemClickListener.
	 */
	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
	}
}
