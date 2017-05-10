package com.example.ghost005.serviceclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Employee;

/**
 * Adapter for the attachments RecyclerView.
 */
public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.AttachmentViewHolder>
{
	private List<Attachment> attachments;
	private OnItemClickListener onItemClickListener;

	public AttachmentsAdapter(List<Attachment> attachments)
	{
		this.attachments = attachments;
	}

	@Override
	public AttachmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_attachment, parent, false);

		return new AttachmentViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(AttachmentViewHolder holder, int position)
	{
		Attachment attachment = attachments.get(position);

		holder.tvName.setText(attachment.getName());

		if (attachment.getLongDescription() != null)
		{
			holder.tvDescription.setVisibility(View.VISIBLE);
			holder.tvDescription.setText(attachment.getLongDescription());
		}
		else
		{
			holder.tvDescription.setVisibility(View.GONE);
		}

		if (attachment.getFiles() != null)
		{
			holder.tvFiles.setText(String.valueOf(attachment.getFiles().getFiles().size()));
		}
		else
		{
			holder.tvFiles.setText("0");
		}

		if (attachment.getResponsibleEmployee() != null)
		{
			Employee employee = attachment.getResponsibleEmployee();

			if (employee.getLastName() != null)
			{
				StringBuilder stringBuilder = new StringBuilder();

				if (employee.getFirstName() != null)
				{
					stringBuilder.append(employee.getFirstName() + " ");
				}

				stringBuilder.append(employee.getLastName());

				holder.llEmployee.setVisibility(View.VISIBLE);
				holder.tvEmployee.setText(stringBuilder.toString());
			}
		}
		else
		{
			holder.llEmployee.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return attachments.size();
	}

	public Attachment getItem(int position)
	{

		return attachments.get(position);
	}

	public List<Attachment> getAttachments()
	{
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments)
	{
		this.attachments = attachments;
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

	public class AttachmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected LinearLayout llEmployee;
		protected TextView tvName;
		protected TextView tvDescription;
		protected TextView tvFiles;
		protected TextView tvEmployee;

		public AttachmentViewHolder(View itemView)
		{
			super(itemView);

			llEmployee = (LinearLayout) itemView.findViewById(R.id.linear_layout_employee);
			tvName = (TextView) itemView.findViewById(R.id.text_view_attachment_name);
			tvDescription = (TextView) itemView.findViewById(R.id.text_view_attachment_description);
			tvFiles = (TextView) itemView.findViewById(R.id.text_view_attachment_files);
			tvEmployee = (TextView) itemView.findViewById(R.id.text_view_attachment_employee);

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
