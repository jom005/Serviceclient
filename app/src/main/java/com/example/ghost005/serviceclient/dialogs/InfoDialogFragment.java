package com.example.ghost005.serviceclient.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import com.example.ghost005.serviceclient.R;

/**
 * Created by me on 06.09.15.
 */
public class InfoDialogFragment extends DialogFragment implements View.OnClickListener
{
	private Activity activity;
	private Button btnClose;
	private TextView tvOrmLiteLicense;
	private TextView tvApacheLicense;
	private TextView tvPicassoLicense;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(STYLE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info_dialog, container, false);

		btnClose = (Button) view.findViewById(R.id.button_dialog_close);
		tvOrmLiteLicense = (TextView) view.findViewById(R.id.text_view_license_orm_lite);
		tvApacheLicense = (TextView) view.findViewById(R.id.text_view_license_apache);
		tvPicassoLicense = (TextView) view.findViewById(R.id.text_view_license_picasso);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		btnClose.setOnClickListener(this);

		AssetManager assetManager = activity.getAssets();

		try (InputStream inputStream = assetManager.open("ormlite_license.txt"))
		{
			int size = inputStream.available();
			byte[] buffer = new byte[size];
			inputStream.read(buffer);
			inputStream.close();

			String text = new String(buffer);

			tvOrmLiteLicense.setText(text);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try (InputStream inputStream = assetManager.open("apache2.0_license.txt"))
		{
			int size = inputStream.available();
			byte[] buffer = new byte[size];
			inputStream.read(buffer);
			inputStream.close();

			String text = new String(buffer);

			tvApacheLicense.setText(text);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try (InputStream inputStream = assetManager.open("picasso_license.txt"))
		{
			int size = inputStream.available();
			byte[] buffer = new byte[size];
			inputStream.read(buffer);
			inputStream.close();

			String text = new String(buffer);

			tvPicassoLicense.setText(text);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		//DisplayMetrics metrics = getResources().getDisplayMetrics();
		//int width = metrics.widthPixels;
		//int height = metrics.heightPixels;

		getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
			case R.id.button_dialog_close:
			{
				dismiss();
				break;
			}
		}
	}
}
