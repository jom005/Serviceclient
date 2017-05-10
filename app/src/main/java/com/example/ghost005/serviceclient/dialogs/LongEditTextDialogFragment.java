package com.example.ghost005.serviceclient.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.events.LongEditTextDialogEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by me on 04.09.15.
 */
public class LongEditTextDialogFragment extends DialogFragment implements View.OnClickListener, TextWatcher
{
	private static final String BUNDLE_TITLE = "bundle_title";
	private static final String BUNDLE_SUB_TITLE = "bundle_sub_title";
	private static final String BUNDLE_TEXT = "bundle_text";
	private static final String BUNDLE_SCAN = "bundle_scan";
	private static final String STATE_TEXT = "state_text";
	private static final int REQUEST_CODE = 0x001;

	private Activity activity;
	private Button btnScan;
	private Button btnCancel;
	private Button btnOk;
	private EditText etText;
	private ImageButton ibtnClear;
	private TextView tvSubTitle;

	private String title;
	private String subTitle;
	private String text;
	private boolean scan;

	public static LongEditTextDialogFragment newInstance(String title, String subTitle, String text, boolean scan)
	{
		LongEditTextDialogFragment fragment = new LongEditTextDialogFragment();

		Bundle args = new Bundle();
		args.putString(BUNDLE_TITLE, title);
		args.putString(BUNDLE_SUB_TITLE, subTitle);
		args.putString(BUNDLE_TEXT, text);
		args.putBoolean(BUNDLE_SCAN, scan);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		title = getArguments().getString(BUNDLE_TITLE);
		subTitle = getArguments().getString(BUNDLE_SUB_TITLE);
		text = getArguments().getString(BUNDLE_TEXT);
		scan = getArguments().getBoolean(BUNDLE_SCAN);
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
		View view = inflater.inflate(R.layout.fragment_long_edit_text_dialog, container, false);

		btnScan = (Button) view.findViewById(R.id.button_dialog_scan);
		btnCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
		btnOk = (Button) view.findViewById(R.id.button_dialog_ok);
		ibtnClear = (ImageButton) view.findViewById(R.id.image_button_dialog_text_clear);
		etText = (EditText) view.findViewById(R.id.edit_text_dialog);
		tvSubTitle = (TextView) view.findViewById(R.id.text_view_dialog_sub_title);

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

		btnScan.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		ibtnClear.setOnClickListener(this);
		etText.addTextChangedListener(this);

		if (savedInstanceState != null)
		{
			String text = savedInstanceState.getString(STATE_TEXT);

			etText.setText(text);
		}
		else
		{
			etText.setText(text);
		}

		etText.setSelection(etText.getText().length());
		etText.requestFocus();
		etText.requestFocusFromTouch();

		if (etText.getText().length() == 0)
		{
			ibtnClear.setVisibility(View.GONE);
		}

		if (!scan)
		{
			btnScan.setVisibility(View.GONE);
		}
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
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK)
		{
			String code = data.getStringExtra("SCAN_RESULT");

			switch (requestCode)
			{
				case REQUEST_CODE:
				{
					etText.setText(code);
					etText.setSelection(etText.length());

					break;
				}
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button_dialog_scan:
			{
				IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
				Intent intent = intentIntegrator.createScanIntent();
				startActivityForResult(intent, REQUEST_CODE);

				break;
			}
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
			case R.id.image_button_dialog_text_clear:
			{
				etText.setText("");

				break;
			}
		}
	}

	private void getText()
	{
		String text = etText.getText().toString().trim();

		EventBus.getDefault().post(new LongEditTextDialogEvent(text));
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
	{

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
	{

	}

	@Override
	public void afterTextChanged(Editable editable)
	{
		if (etText.getText().length() > 0)
		{
			ibtnClear.setVisibility(View.VISIBLE);
		}
		else
		{
			ibtnClear.setVisibility(View.GONE);
		}
	}

	public interface LongEditTextDialogEventListener
	{
		void onEvent(LongEditTextDialogEvent event);
	}
}
