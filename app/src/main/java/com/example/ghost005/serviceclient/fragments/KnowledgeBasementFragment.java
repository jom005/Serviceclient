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
public class KnowledgeBasementFragment extends Fragment
{
	public static KnowledgeBasementFragment newInstance()
	{
		KnowledgeBasementFragment fragment = new KnowledgeBasementFragment();

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_knowledge_basement, container, false);
	}


}
