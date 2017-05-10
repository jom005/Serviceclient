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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.SeekBarDialogEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 05.09.15.
 */
public class SeekBarDialogFragment extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener
{
	private static final String BUNDLE_TITLE = "bundle_title";
	private static final String BUNDLE_SUB_TITLE = "bundle_sub_title";
	private static final String BUNDLE_VALUE = "bundle_value";
	private static final String BUNDLE_MAX_VALUE = "bundle_max_value";
	private static final String STATE_VALUE = "state_value";

	private Button btnCancel;
	private Button btnOk;
	private SeekBar sbSeekBar;
	private TextView tvSubTitle;
	private TextView tvValue;

	private String title;
	private String subTitle;
	private int value;
	private int maxValue;

	public static SeekBarDialogFragment newInstance(String title, String subTitle, int value, int maxValue)
	{
		SeekBarDialogFragment fragment = new SeekBarDialogFragment();

		Bundle args = new Bundle();
		args.putString(BUNDLE_TITLE, title);
		args.putString(BUNDLE_SUB_TITLE, subTitle);
		args.putInt(BUNDLE_VALUE, value);
		args.putInt(BUNDLE_MAX_VALUE, maxValue);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		title = getArguments().getString(BUNDLE_TITLE);
		subTitle = getArguments().getString(BUNDLE_SUB_TITLE);
		value = getArguments().getInt(BUNDLE_VALUE);
		maxValue = getArguments().getInt(BUNDLE_MAX_VALUE);
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
		View view = inflater.inflate(R.layout.fragment_seek_bar_dialog, container, false);

		btnCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
		btnOk = (Button) view.findViewById(R.id.button_dialog_ok);
		sbSeekBar = (SeekBar) view.findViewById(R.id.seek_bar_dialog);
		tvSubTitle = (TextView) view.findViewById(R.id.text_view_dialog_sub_title);
		tvValue = (TextView) view.findViewById(R.id.text_view_dialog_value);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (subTitle != null)
		{
			tvSubTitle.setText(subTitle);
		}
		else
		{
			tvSubTitle.setVisibility(View.GONE);
		}

		if (savedInstanceState != null)
		{
			value = savedInstanceState.getInt(BUNDLE_VALUE);
		}

		sbSeekBar.setOnSeekBarChangeListener(this);
		sbSeekBar.setMax(maxValue);
		sbSeekBar.setProgress(value);
		tvValue.setText(String.valueOf(value));

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
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
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		int value = sbSeekBar.getProgress();

		outState.putInt(STATE_VALUE, value);
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
				getValue();

				dismiss();

				break;
			}
		}
	}

	private void getValue()
	{
		int value = sbSeekBar.getProgress();

		EventBus.getDefault().post(new SeekBarDialogEvent(value));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b)
	{
		switch (seekBar.getId())
		{
			case R.id.seek_bar_dialog:
			{
				tvValue.setText(String.valueOf(i));
				break;
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{

	}

	public interface SeekBarDialogEventListener
	{
		void onEvent(SeekBarDialogEvent event);
	}
}
