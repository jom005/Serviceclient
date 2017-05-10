package com.example.ghost005.serviceclient.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.ShortEditTextDialogEvent;
import com.example.ghost005.serviceclient.utilities.AndroidUtilities;
import com.example.ghost005.serviceclient.utilities.MinMaxInputFilter;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 04.09.15.
 */
public class ShortEditTextDialogFragment extends DialogFragment implements View.OnClickListener
{
	private static final String BUNDLE_TITLE = "bundle_title";
	private static final String BUNDLE_SUB_TITLE = "bundle_sub_title";
	private static final String BUNDLE_TEXT = "bundle_text";
	private static final String BUNDLE_UNIT = "bundle_unit";
	private static final String BUNDLE_MIN_VALUE = "bundle_min_value";
	private static final String BUNDLE_MAX_VALUE = "bundle_max_value";
	private static final String STATE_TEXT = "state_text";

	private Activity activity;
	private Button btnCancel;
	private Button btnOk;
	private EditText etText;
	private TextView tvSubTitle;
	private TextView tvUnit;

	private String title;
	private String subTitle;
	private String text;
	private String unit;
	private double minValue;
	private double maxValue;

	public static ShortEditTextDialogFragment newInstance(String title, String text, String unit,
														  double minValue, double maxValue)
	{
		ShortEditTextDialogFragment fragment = new ShortEditTextDialogFragment();

		Bundle args = new Bundle();
		args.putString(BUNDLE_TITLE, title);
		args.putString(BUNDLE_TEXT, text);
		args.putString(BUNDLE_UNIT, unit);
		args.putDouble(BUNDLE_MIN_VALUE, minValue);
		args.putDouble(BUNDLE_MAX_VALUE, maxValue);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		title = getArguments().getString(BUNDLE_TITLE);
		text = getArguments().getString(BUNDLE_TEXT);
		subTitle = null;
		unit = getArguments().getString(BUNDLE_UNIT);
		minValue = getArguments().getDouble(BUNDLE_MIN_VALUE);
		maxValue = getArguments().getDouble(BUNDLE_MAX_VALUE);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_short_edit_text_dialog, container, false);

		btnCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
		btnOk = (Button) view.findViewById(R.id.button_dialog_ok);
		etText = (EditText) view.findViewById(R.id.edit_text_dialog);
		tvSubTitle = (TextView) view.findViewById(R.id.text_view_dialog_sub_title);
		tvUnit = (TextView) view.findViewById(R.id.text_view_dialog_unit);

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

		if (unit != null)
		{
			tvUnit.setText(unit);
		}

		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);

		if (savedInstanceState != null)
		{
			String text = savedInstanceState.getString(STATE_TEXT);

			etText.setText(text);
		}
		else
		{
			etText.setText(text);
		}

		etText.setFilters(new InputFilter[]{new MinMaxInputFilter(minValue, maxValue)});
		etText.setSelection(etText.getText().length());
		etText.requestFocus();
		etText.requestFocusFromTouch();

		AndroidUtilities.showInputMethod(activity, etText, true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		String text = etText.getText().toString();

		outState.putString(STATE_TEXT, text);
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
				getText();

				dismiss();

				break;
			}
		}
	}

	private void getText()
	{
		String text = etText.getText().toString().trim();

		EventBus.getDefault().post(new ShortEditTextDialogEvent(text));
	}

	public interface ShortEditTextDialogEventListener
	{
		void onEvent(ShortEditTextDialogEvent event);
	}
}
