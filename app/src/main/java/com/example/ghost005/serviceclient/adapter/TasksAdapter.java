package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Task;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * Adapter for the tasks RecyclerView.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>
{
	private Context context;
	private List<Task> tasks;
	private OnItemClickListener onItemClickListener;

	public TasksAdapter(Context context, List<Task> tasks)
	{
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_task,
				parent, false);

		return new TaskViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(TaskViewHolder holder, int position)
	{
		Task task = tasks.get(position);

		holder.tvTaskId.setText(task.getId());
		holder.tvTaskName.setText(task.getName());

		if (task.getType() != null)
		{
			holder.tvTaskType.setText(task.getType());
		}
		else
		{
			holder.tvTaskType.setVisibility(View.GONE);
		}

		if (task.getStatus() != null)
		{
			String status = task.getStatus().getStatus();
			Pair<String, Integer> statusPair = GSPUtilities.getTaskStatus(context, status);

			if (statusPair != null)
			{
				status = statusPair.first;
			}

			holder.llTaskStatus.setVisibility(View.VISIBLE);
			holder.tvTaskStatus.setText(status);
		}
		else
		{
			holder.llTaskStatus.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return tasks.size();
	}

	public Task getItem(int position)
	{
		return tasks.get(position);
	}

	public List<Task> getTasks()
	{
		return tasks;
	}

	public void setTasks(List<Task> tasks)
	{
		this.tasks = tasks;
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

	public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected LinearLayout llTaskId;
		protected LinearLayout llTaskStatus;
		private TextView tvTaskId;
		private TextView tvTaskName;
		private TextView tvTaskType;
		private TextView tvTaskStatus;

		public TaskViewHolder(View itemView)
		{
			super(itemView);

			llTaskId = (LinearLayout) itemView.findViewById(R.id.linear_layout_task_id);
			llTaskStatus = (LinearLayout) itemView.findViewById(R.id.linear_layout_task_status);
			tvTaskId = (TextView) itemView.findViewById(R.id.text_view_task_id);
			tvTaskName = (TextView) itemView.findViewById(R.id.text_view_task_name);
			tvTaskType = (TextView) itemView.findViewById(R.id.text_view_task_type);
			tvTaskStatus = (TextView) itemView.findViewById(R.id.text_view_task_status);

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
