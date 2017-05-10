package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Attachment;

/**
 * Custom ArrayAdapter for attachments drop down menu.
 */
public class AttachmentsArrayAdapter extends ArrayAdapter
{
	private Context context;
	private List<Attachment> attachments;
	private int resource;

	public AttachmentsArrayAdapter(Context context, int resource, ArrayList<Attachment> attachments)
	{
		super(context, resource, attachments);

		this.context = context;
		this.resource = resource;
		this.attachments = attachments;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return createView(position, convertView, parent);
	}

	private View createView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);

			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Attachment attachment = attachments.get(position);

		holder.tvName.setText(attachment.getName());

		return convertView;
	}

	public class ViewHolder
	{
		protected TextView tvName;

		public ViewHolder(View convertView)
		{
			tvName = (TextView) convertView.findViewById(R.id.text_view_spinner_item);
		}
	}
}
