package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Employee;

/**
 * ArrayAdapter for the employee select spinner.
 * Created by me on 24.08.15.
 */
public class EmployeeArrayAdapter extends ArrayAdapter
{
	private Context context;
	private List<Employee> employees;
	private int resource;

	public EmployeeArrayAdapter(Context context, int resource, List<Employee> employees)
	{
		super(context, resource, employees);

		this.context = context;
		this.employees = employees;
		this.resource = resource;
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

	public View createView(int position, View convertView, ViewGroup parent)
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

		Employee employee = employees.get(position);

		StringBuilder stringBuilder = new StringBuilder();

		if (employee.getLastName() != null)
		{
			if (employee.getFirstName() != null)
			{
				stringBuilder.append(employee.getFirstName());
				stringBuilder.append(" ");
			}

			stringBuilder.append(employee.getLastName());
		}

		String id = employee.getId();

		holder.tvName.setText(stringBuilder.toString());
		holder.tvId.setText(id);

		return convertView;
	}

	public class ViewHolder
	{
		protected TextView tvName;
		protected TextView tvId;

		public ViewHolder(View convertView)
		{
			tvName = (TextView) convertView.findViewById(R.id.text_view_employee_name);
			tvId = (TextView) convertView.findViewById(R.id.text_view_employee_id);
		}
	}
}
