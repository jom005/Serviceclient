package com.example.ghost005.serviceclient.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.utilities.Utilities;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 04.09.15.
 */
public class SpinnerDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final String BUNDLE_TITLE = "bundle_title";
	private static final String BUNDLE_SUB_TITLE = "bundle_sub_title";
	private static final String BUNDLE_RESOURCE_ID = "bundle_resource_id";
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_EMPTY_ITEM = "bundle_empty_item";
	private static final String STATE_SELECTED_POSITION = "state_selected_position";

	private Activity activity;
	private Button btnCancel;
	private Button btnOk;
	private TextView tvSubTitle;
	private Spinner sSpinner;
	private ArrayAdapter adapter;

	private String title;
	private String subTitle;
	private int resourceId;
	private int position;
	private boolean emptyItem;

	public static SpinnerDialogFragment newInstance(String title, String subTitle, int resource, int position, boolean emptyItem)
	{
		SpinnerDialogFragment fragment = new SpinnerDialogFragment();

		Bundle args = new Bundle();
		args.putString(BUNDLE_TITLE, title);
		args.putString(BUNDLE_SUB_TITLE, subTitle);
		args.putInt(BUNDLE_RESOURCE_ID, resource);
		args.putInt(BUNDLE_POSITION, position);
		args.putBoolean(BUNDLE_EMPTY_ITEM, emptyItem);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		title = getArguments().getString(BUNDLE_TITLE);
		subTitle = getArguments().getString(BUNDLE_SUB_TITLE);
		resourceId = getArguments().getInt(BUNDLE_RESOURCE_ID);
		position = getArguments().getInt(BUNDLE_POSITION);
		emptyItem = getArguments().getBoolean(BUNDLE_EMPTY_ITEM);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_spinner_dialog, container, false);

		btnCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
		btnOk = (Button) view.findViewById(R.id.button_dialog_ok);
		tvSubTitle = (TextView) view.findViewById(R.id.text_view_dialog_sub_title);
		sSpinner = (Spinner) view.findViewById(R.id.spinner_dialog);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);

		if (subTitle != null)
		{
			tvSubTitle.setText(subTitle);
		}
		else
		{
			tvSubTitle.setVisibility(View.GONE);
		}

		if (emptyItem)
		{
			String[] empty = {""};

			adapter = new ArrayAdapter<>(activity, R.layout.spinner_item,
					Utilities.concat(empty, getResources().getStringArray(resourceId)));
		}
		else
		{
			adapter = ArrayAdapter.createFromResource(activity, resourceId,
					R.layout.spinner_item);
		}

		sSpinner.setAdapter(adapter);

		if (savedInstanceState != null)
		{
			position = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			sSpinner.setSelection(position);
		}
		else
		{
			if (emptyItem)
			{
				sSpinner.setSelection(position + 1);
			}
			else
			{
				sSpinner.setSelection(position);
			}
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		// int height = metrics.heightPixels;

		getDialog().getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		this.activity = activity;
	}

	@Override
	public void onDetach()
	{
		super.onDetach();

		activity = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_SELECTED_POSITION, sSpinner.getSelectedItemPosition());
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_dialog_cancel:
			{
				dismiss();

				break;
			}
			case R.id.button_dialog_ok:
			{
				getSpinnerItem();

				dismiss();

				break;
			}
		}
	}

	private void getSpinnerItem()
	{
		String item = sSpinner.getSelectedItem().toString();
		int position = sSpinner.getSelectedItemPosition();

		if (emptyItem)
		{
			position--;
		}

		EventBus.getDefault().post(new SpinnerDialogEvent(item, position));
	}

	public interface SpinnerDialogEventListener
	{
		void onEvent(SpinnerDialogEvent event);
	}
}
