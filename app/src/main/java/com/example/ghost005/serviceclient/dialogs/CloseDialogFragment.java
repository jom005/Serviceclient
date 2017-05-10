package com.example.ghost005.serviceclient.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.CloseEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 08.10.15.
 */
public class CloseDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final String BUNDLE_TITLE = "bundle_title";
	private static final String BUNDLE_MESSAGE = "bundle_message";

	private TextView tvMessage;
	private LinearLayout llButtons;
	private Button btnCancel;
	private Button btnOk;

	private String title;
	private String message;

	public static CloseDialogFragment newInstance(String title, String message)
	{
		CloseDialogFragment fragment = new CloseDialogFragment();
		Bundle args = new Bundle();
		args.putString(BUNDLE_TITLE, title);
		args.putString(BUNDLE_MESSAGE, message);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		title = getArguments().getString(BUNDLE_TITLE);
		message = getArguments().getString(BUNDLE_MESSAGE);
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
		View view = inflater.inflate(R.layout.fragment_close_dialog, container, false);

		tvMessage = (TextView) view.findViewById(R.id.text_view_message);
		llButtons = (LinearLayout) view.findViewById(R.id.layout_buttons);
		btnCancel = (Button) llButtons.findViewById(R.id.button_cancel);
		btnOk = (Button) llButtons.findViewById(R.id.button_ok);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		tvMessage.setText(message);
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
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_cancel:
			{
				dismiss();
				break;
			}
			case R.id.button_ok:
			{
				dismiss();
				EventBus.getDefault().post(new CloseEvent(true));
				break;
			}
		}
	}
}
