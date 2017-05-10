package com.example.ghost005.serviceclient.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghost005.serviceclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleInventoryFragment extends Fragment
{
	public static VehicleInventoryFragment newInstance()
	{
		VehicleInventoryFragment fragment = new VehicleInventoryFragment();

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_vehicle_inventory, container, false);
	}
}
