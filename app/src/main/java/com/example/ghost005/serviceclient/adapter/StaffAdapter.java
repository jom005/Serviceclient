package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.Skill;
import com.example.ghost005.serviceclient.model.types.Skills;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;

/**
 * Adapter for the staff RecyclerView.
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.EmployeeViewHolder>
{
	private Context context;
	private List<Employee> employees;
	private List<Boolean> isChecked;
	private WorkOrderItem workOrderItem;
	private OnItemClickListener onItemClickListener;

	public StaffAdapter(Context context, List<Employee> employees, WorkOrderItem work)
	{
		this.context = context;
		this.employees = employees;
		this.workOrderItem = work;

		isChecked = new ArrayList<>(Arrays.asList(new Boolean[employees.size()]));

		for (int i = 0; i < employees.size(); i++)
		{
			isChecked.add(Boolean.TRUE);
		}
	}

	@Override
	public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_employee, parent, false);

		return new EmployeeViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(EmployeeViewHolder holder, final int position)
	{
		Employee employee = employees.get(position);

		if (employee.getLastName() != null)
		{
			if (employee.getLastName() != null)
			{
				StringBuilder stringBuilder = new StringBuilder();

				if (employee.getFirstName() != null)
				{
					stringBuilder.append(employee.getFirstName() + " ");
				}

				stringBuilder.append(employee.getLastName());

				holder.tvName.setText(stringBuilder.toString());
			}
		}

		holder.tvId.setText(employee.getId());
		holder.chbIsSelected.setChecked(isChecked.get(position));

		if (workOrderItem != null)
		{
			holder.llMissingSkillsContent.removeAllViews();

			if (workOrderItem.getSkills() != null)
			{
				Skills workOrderItemSkills = workOrderItem.getSkills();

				if (employee.getSkills() != null)
				{
					Skills employeeSkills = employee.getSkills();

					boolean missingSkills = false;

					for (Skill skill : workOrderItemSkills.getSkills())
					{
						boolean hasSkill = false;

						for (Skill employeeSkill : employeeSkills.getSkills())
						{
							if (skill.getId().equals(employeeSkill.getId()))
							{
								hasSkill = true;

								break;
							}
						}

						if (!hasSkill)
						{
							addSkill(holder, skill.getName(), skill.getId());
							missingSkills = true;
						}
					}

					if (!missingSkills)
					{
						holder.llMissingSkills.setVisibility(View.GONE);
					}
				}
				else
				{
					for (Skill skill : workOrderItemSkills.getSkills())
					{
						addSkill(holder, skill.getName(), skill.getId());
					}
				}
			}
			else
			{
				holder.llMissingSkills.setVisibility(View.GONE);
			}
		}
		else
		{
			holder.llMissingSkills.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return employees.size();
	}

	public void setEmployees(List<Employee> employees)
	{
		this.employees = employees;

		isChecked = new ArrayList<>(Arrays.asList(new Boolean[employees.size()]));

		for (int i = 0; i < employees.size(); i++)
		{
			isChecked.add(Boolean.TRUE);
		}
	}

	private void addSkill(EmployeeViewHolder viewHolder, String skillName, String skillId)
	{
		TextView tvSkill = new TextView(context);
		tvSkill.setTextSize(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getInteger(R.integer.text_size_medium_value));
		tvSkill.setTextColor(ContextCompat.getColor(context, R.color.employee_missing_skills));

		String text = skillName + " " + skillId;
		tvSkill.setText(text);

		viewHolder.llMissingSkillsContent.addView(tvSkill);
	}

	public void setEmployees(List<Employee> employees, List<Boolean> isSelected)
	{
		this.employees = employees;
		this.isChecked = isSelected;
	}

	public List<Employee> getEmployees()
	{
		return employees;
	}

	public WorkOrderItem getWorkOrderItem()
	{
		return workOrderItem;
	}

	public void setWorkOrderItem(WorkOrderItem workOrderItem)
	{
		this.workOrderItem = workOrderItem;
	}

	public List<Boolean> getIsChecked()
	{
		return isChecked;
	}

	public void setChecked(int position, CheckBox checkBox)
	{
		checkBox.setChecked(!checkBox.isChecked());
		isChecked.set(position, checkBox.isChecked());
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
	 * ViewHolder for the RecyclerView.Adapter.
	 */
	public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected CheckBox chbIsSelected;
		protected LinearLayout llMissingSkills;
		protected LinearLayout llMissingSkillsContent;
		protected TextView tvId;
		protected TextView tvName;

		public EmployeeViewHolder(View itemView)
		{
			super(itemView);

			llMissingSkills = (LinearLayout) itemView.findViewById(R.id.linear_layout_missing_skills);
			llMissingSkillsContent = (LinearLayout) itemView.findViewById(R.id.linear_layout_missing_skills_content_frame);
			chbIsSelected = (CheckBox) itemView.findViewById(R.id.check_box_employee);
			tvId = (TextView) itemView.findViewById(R.id.text_view_item);
			tvName = (TextView) itemView.findViewById(R.id.text_view_name);

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