package com.example.ghost005.serviceclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.types.Material;

/**
 * Adapter for the material RecyclerView.
 */
public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialViewHolder>
{
	private Context context;
	private List<Material> materials;
	private OnItemClickListener onItemClickListener;

	public MaterialsAdapter(Context context, List<Material> materials)
	{
		this.context = context;
		this.materials = materials;
	}

	@Override
	public MaterialViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
				.card_view_material, parent, false);

		return new MaterialViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MaterialViewHolder holder, int position)
	{
		Material material = materials.get(position);

		holder.tvName.setText(material.getName());

		if (material.getMaterialCode() != null)
		{
			holder.tvMaterialCode.setVisibility(View.VISIBLE);
			holder.tvMaterialCode.setText(material.getMaterialCode());
		}
		else
		{
			holder.tvMaterialCode.setVisibility(View.GONE);
		}

		if (material.getQuantity() != null)
		{
			holder.llQuantity.setVisibility(View.VISIBLE);
			holder.tvQuantity.setText(String.valueOf(material.getQuantity()));
		}
		else
		{
			holder.llQuantity.setVisibility(View.GONE);
		}

		if (material.getSerialNumber() != null)
		{
			holder.llSerialNumber.setVisibility(View.VISIBLE);
			holder.tvSerialNumber.setText(material.getSerialNumber());
		}
		else
		{
			holder.llSerialNumber.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return materials.size();
	}

	public Material getItem(int position)
	{
		return materials.get(position);
	}

	public List<Material> getMaterials()
	{
		return materials;
	}

	public void setMaterials(List<Material> materials)
	{
		this.materials = materials;
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

	public class MaterialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		protected LinearLayout llQuantity;
		protected LinearLayout llSerialNumber;
		protected TextView tvName;
		protected TextView tvMaterialCode;
		protected TextView tvQuantity;
		protected TextView tvSerialNumber;

		public MaterialViewHolder(View itemView)
		{
			super(itemView);

			llQuantity = (LinearLayout) itemView.findViewById(R.id.linear_layout_quantity);
			llSerialNumber = (LinearLayout) itemView.findViewById(R.id.linear_layout_serial_number);
			tvName = (TextView) itemView.findViewById(R.id.text_view_material_name);
			tvMaterialCode = (TextView) itemView.findViewById(R.id.text_view_material_code);
			tvQuantity = (TextView) itemView.findViewById(R.id.text_view_material_quantity);
			tvSerialNumber = (TextView) itemView.findViewById(R.id.text_view_material_serial_number);

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
