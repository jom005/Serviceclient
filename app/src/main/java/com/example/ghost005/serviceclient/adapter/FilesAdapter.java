package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.enums.MIMEMediaType;
import com.example.ghost005.serviceclient.model.types.File;
import com.example.ghost005.serviceclient.utilities.FileUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;

/**
 * Adapter for the files RecyclerView.
 */
public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder>
{
	private Context context;
	private List<File> files;
	private OnItemClickListener onItemClickListener;

	public FilesAdapter(Context context, List<File> files)
	{
		this.context = context;
		this.files = files;
	}

	@Override
	public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_file, parent, false);

		return new FileViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(FileViewHolder holder, int position)
	{
		File file = files.get(position);

		if (file.getMimeMediaType().equals(MIMEMediaType.IMAGE.getValue()))
		{
			String mediaPath = file.getLocation().getPath();
			String fileName = file.getName();
			String path = context.getExternalFilesDir(null) + FileUtilities.GSP_SUBFOLDER +
					java.io.File.separator + mediaPath + java.io.File.separator + fileName;
			java.io.File imageFile = new java.io.File(path);

			Picasso.with(context).load(imageFile).centerInside().fit().into(holder.ivFile);
		}
		else if (file.getMimeMediaType().equals(MIMEMediaType.TEXT.getValue()))
		{
			if (file.getMimeContentType() != null && file.getMimeContentType().contains("pdf"))
			{
				Picasso.with(context).load(R.drawable.pdf).centerInside().fit().into(holder.ivFile);
			}
			else
			{
				Picasso.with(context).load(R.drawable.docs).centerInside().fit().into(holder.ivFile);
			}
		}
		else if (file.getMimeMediaType().equals(MIMEMediaType.AUDIO.getValue()))
		{
			Picasso.with(context).load(R.drawable.audio).centerInside().fit().into(holder.ivFile);
		}

		holder.tvFileName.setText(file.getName());
		holder.tvFileMimeMediaType.setText(file.getMimeMediaType());

		if (file.getDescription() != null)
		{
			holder.tvFileDescription.setVisibility(View.VISIBLE);
			holder.tvFileDescription.setText(file.getDescription());
		}
		else
		{
			holder.tvFileDescription.setVisibility(View.GONE);
		}

		if (file.getMimeContentType() != null)
		{
			holder.llFileMimeContentType.setVisibility(View.VISIBLE);
			holder.tvFileMimeContentType.setText(file.getMimeContentType());
		}
		else
		{
			holder.llFileMimeContentType.setVisibility(View.GONE);
		}

		if (file.getCreationDate() != null)
		{
			holder.llFileCreateDate.setVisibility(View.VISIBLE);
			String date = Utilities.importDate(file.getCreationDate());
			holder.tvFileCreateDate.setText(date);
		}
		else
		{
			holder.llFileCreateDate.setVisibility(View.GONE);
		}

		if (file.getLastModification() != null)
		{
			holder.llFileLastModified.setVisibility(View.VISIBLE);
			String date = Utilities.importDate(file.getLastModification());
			holder.tvFileLastModified.setText(date);
		}
		else
		{
			holder.llFileLastModified.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return files.size();
	}

	public File getItem(int position)
	{
		return files.get(position);
	}

	public List<File> getFiles()
	{
		return files;
	}

	public void setFiles(List<File> files)
	{
		this.files = files;
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

	public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected ImageView ivFile;
		protected LinearLayout llFileMimeContentType;
		protected LinearLayout llFileCreateDate;
		protected LinearLayout llFileLastModified;
		protected TextView tvFileName;
		protected TextView tvFileDescription;
		protected TextView tvFileMimeMediaType;
		protected TextView tvFileMimeContentType;
		protected TextView tvFileCreateDate;
		protected TextView tvFileLastModified;

		public FileViewHolder(View itemView)
		{
			super(itemView);

			ivFile = (ImageView) itemView.findViewById(R.id.image_view_file);
			llFileMimeContentType = (LinearLayout) itemView.findViewById(R.id.linear_layout_file_mime_content_type);
			llFileCreateDate = (LinearLayout) itemView.findViewById(R.id.linear_layout_file_create_date);
			llFileLastModified = (LinearLayout) itemView.findViewById(R.id.linear_layout_file_modification_date);
			tvFileName = (TextView) itemView.findViewById(R.id.text_view_file_name);
			tvFileDescription = (TextView) itemView.findViewById(R.id.text_view_file_description);
			tvFileMimeMediaType = (TextView) itemView.findViewById(R.id.text_view_file_mime_media_type);
			tvFileMimeContentType = (TextView) itemView.findViewById(R.id.text_view_file_mime_content_type);
			tvFileCreateDate = (TextView) itemView.findViewById(R.id.text_view_file_create_date);
			tvFileLastModified = (TextView) itemView.findViewById(R.id.text_view_file_modification_date);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view)
		{
			if (onItemClickListener != null)
			{
				onItemClickListener.onItemClick(view, getAdapterPosition());
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
