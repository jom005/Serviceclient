package com.example.ghost005.serviceclient.fragments.work_order_item;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.database.DatabaseAdapter;
import com.example.ghost005.serviceclient.database.DatabaseApplication;
import com.example.ghost005.serviceclient.dialogs.SeekBarDialogFragment;
import com.example.ghost005.serviceclient.dialogs.SpinnerDialogFragment;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.events.SeekBarDialogEvent;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.model.comparators.ZEUSPart2DateComparator;
import com.example.ghost005.serviceclient.model.enums.StatusInfo;
import com.example.ghost005.serviceclient.model.enums.ZEUS0201;
import com.example.ghost005.serviceclient.model.enums.ZEUS0202;
import com.example.ghost005.serviceclient.model.enums.ZEUS0203;
import com.example.ghost005.serviceclient.model.enums.ZEUS0204;
import com.example.ghost005.serviceclient.model.enums.ZEUS0205;
import com.example.ghost005.serviceclient.model.enums.ZEUS0206;
import com.example.ghost005.serviceclient.model.enums.ZEUS0207;
import com.example.ghost005.serviceclient.model.enums.ZEUS0208;
import com.example.ghost005.serviceclient.model.enums.ZEUS0209;
import com.example.ghost005.serviceclient.model.enums.ZEUS0210;
import com.example.ghost005.serviceclient.model.enums.ZEUS0211;
import com.example.ghost005.serviceclient.model.enums.ZEUS0212;
import com.example.ghost005.serviceclient.model.types.Assessment;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.ZEUSPart2;
import com.example.ghost005.serviceclient.model.types.ZEUSPart2Assessment;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import com.example.ghost005.serviceclient.wizard.WorkOrderItemWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZeusPart2AssessmentFragment extends Fragment
		implements View.OnClickListener, View.OnTouchListener,
		WorkOrderItemWizardFragment.OnStatusSaveListener,
		SpinnerDialogFragment.SpinnerDialogEventListener, SeekBarDialogFragment.SeekBarDialogEventListener
{
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_WORK_ORDER_ITEM_DATABASE_ID = "bundle_work_order_item_database_id";
	private static final String BUNDLE_WORK_REPORT_ITEM_DATABASE_ID = "state_work_report_item_database_id";
	private static final String DIALOG_ZEUS_0201 = "dialog_zeus_0201";
	private static final String DIALOG_ZEUS_0202 = "dialog_zeus_0202";
	private static final String DIALOG_ZEUS_0203 = "dialog_zeus_0203";
	private static final String DIALOG_ZEUS_0204 = "dialog_zeus_0204";
	private static final String DIALOG_ZEUS_0205 = "dialog_zeus_0205";
	private static final String DIALOG_ZEUS_0206 = "dialog_zeus_0206";
	private static final String DIALOG_ZEUS_0207 = "dialog_zeus_0207";
	private static final String DIALOG_ZEUS_0208 = "dialog_zeus_0208";
	private static final String DIALOG_ZEUS_0209 = "dialog_zeus_0209";
	private static final String DIALOG_ZEUS_0210 = "dialog_zeus_0210";
	private static final String DIALOG_ZEUS_0211 = "dialog_zeus_0211";
	private static final String DIALOG_ZEUS_0212 = "dialog_zeus_0212";
	private static final String DIALOG_ZEUS_KE_01 = "dialog_zeus_ke_01";
	private static final String DIALOG_ZEUS_KE_0101 = "dialog_zeus_ke_0101";
	private static final String DIALOG_ZEUS_KE_0102 = "dialog_zeus_ke_0102";
	private static final String DIALOG_ZEUS_KE_0103 = "dialog_zeus_ke_0103";
	private static final String FRAGMENT_SPINNER = "fragment_spinner";
	private static final String FRAGMENT_SEEK_BAR = "fragment_seek_bar";
	private static final String STATE_DIALOG = "state_dialog";
	private static final String STATE_ZEUS_0201_POSITION = "state_zeus_0201_position";
	private static final String STATE_ZEUS_0202_POSITION = "state_zeus_0202_position";
	private static final String STATE_ZEUS_0203_POSITION = "state_zeus_0203_position";
	private static final String STATE_ZEUS_0204_POSITION = "state_zeus_0204_position";
	private static final String STATE_ZEUS_0205_POSITION = "state_zeus_0205_position";
	private static final String STATE_ZEUS_0206_POSITION = "state_zeus_0206_position";
	private static final String STATE_ZEUS_0207_POSITION = "state_zeus_0207_position";
	private static final String STATE_ZEUS_0208_POSITION = "state_zeus_0208_position";
	private static final String STATE_ZEUS_0209_POSITION = "state_zeus_0209_position";
	private static final String STATE_ZEUS_0210_POSITION = "state_zeus_0210_position";
	private static final String STATE_ZEUS_0211_POSITION = "state_zeus_0211_position";
	private static final String STATE_ZEUS_0212_POSITION = "state_zeus_0212_position";
	private static final String STATE_ZEUS_KE_01 = "state_zeus_ke_01";
	private static final String STATE_ZEUS_KE_0101 = "state_zeus_ke_0101";
	private static final String STATE_ZEUS_KE_0102 = "state_zeus_ke_0102";
	private static final String STATE_ZEUS_KE_0103 = "state_zeus_ke_0103";

	private CardView cvZeus0201;
	private CardView cvZeus0202;
	private CardView cvZeus0203;
	private CardView cvZeus0204;
	private CardView cvZeus0205;
	private CardView cvZeus0206;
	private CardView cvZeus0207;
	private CardView cvZeus0208;
	private CardView cvZeus0209;
	private CardView cvZeus0210;
	private CardView cvZeus0211;
	private CardView cvZeus0212;
	private CardView cvZeusKe01;
	private CardView cvZeusKe0101;
	private CardView cvZeusKe0102;
	private CardView cvZeusKe0103;
	private TextView tvZeus0201;
	private TextView tvZeus0202;
	private TextView tvZeus0203;
	private TextView tvZeus0204;
	private TextView tvZeus0205;
	private TextView tvZeus0206;
	private TextView tvZeus0207;
	private TextView tvZeus0208;
	private TextView tvZeus0209;
	private TextView tvZeus0210;
	private TextView tvZeus0211;
	private TextView tvZeus0212;
	private TextView tvZeusKe01;
	private TextView tvZeusKe0101;
	private TextView tvZeusKe0102;
	private TextView tvZeusKe0103;
	private SeekBar sbZeusKe01;
	private SeekBar sbZeusKe0101;
	private SeekBar sbZeusKe0102;
	private SeekBar sbZeusKe0103;

	private int zeus0201Position;
	private int zeus0202Position;
	private int zeus0203Position;
	private int zeus0204Position;
	private int zeus0205Position;
	private int zeus0206Position;
	private int zeus0207Position;
	private int zeus0208Position;
	private int zeus0209Position;
	private int zeus0210Position;
	private int zeus0211Position;
	private int zeus0212Position;
	private int zeusKe01;
	private int zeusKe0101;
	private int zeusKe0102;
	private int zeusKe0103;
	private String dialog;

	private int workOrderITemDBID;
	private int workReportItemDBID;
	private int position;

	public static ZeusPart2AssessmentFragment newInstance(int workOrderITemDBID, int workReportItemDBID, int position)
	{
		ZeusPart2AssessmentFragment fragment = new ZeusPart2AssessmentFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_POSITION, position);
		args.putInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID, workOrderITemDBID);
		args.putInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID, workReportItemDBID);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		workOrderITemDBID = getArguments().getInt(BUNDLE_WORK_ORDER_ITEM_DATABASE_ID);
		workReportItemDBID = getArguments().getInt(BUNDLE_WORK_REPORT_ITEM_DATABASE_ID);
		position = getArguments().getInt(BUNDLE_POSITION);

		if (savedInstanceState != null)
		{
			zeus0201Position = savedInstanceState.getInt(STATE_ZEUS_0201_POSITION);
			zeus0202Position = savedInstanceState.getInt(STATE_ZEUS_0202_POSITION);
			zeus0203Position = savedInstanceState.getInt(STATE_ZEUS_0203_POSITION);
			zeus0204Position = savedInstanceState.getInt(STATE_ZEUS_0204_POSITION);
			zeus0205Position = savedInstanceState.getInt(STATE_ZEUS_0205_POSITION);
			zeus0206Position = savedInstanceState.getInt(STATE_ZEUS_0206_POSITION);
			zeus0207Position = savedInstanceState.getInt(STATE_ZEUS_0207_POSITION);
			zeus0208Position = savedInstanceState.getInt(STATE_ZEUS_0208_POSITION);
			zeus0209Position = savedInstanceState.getInt(STATE_ZEUS_0209_POSITION);
			zeus0210Position = savedInstanceState.getInt(STATE_ZEUS_0210_POSITION);
			zeus0211Position = savedInstanceState.getInt(STATE_ZEUS_0211_POSITION);
			zeus0212Position = savedInstanceState.getInt(STATE_ZEUS_0212_POSITION);
			zeusKe01 = savedInstanceState.getInt(STATE_ZEUS_KE_01);
			zeusKe0101 = savedInstanceState.getInt(STATE_ZEUS_KE_0101);
			zeusKe0102 = savedInstanceState.getInt(STATE_ZEUS_KE_0102);
			zeusKe0103 = savedInstanceState.getInt(STATE_ZEUS_KE_0103);
			dialog = savedInstanceState.getString(STATE_DIALOG);
		}
		else
		{
			try
			{
				loadData();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_zeus_part2_assessment, container, false);

		cvZeus0201 = (CardView) view.findViewById(R.id.card_view_zeus_0201);
		cvZeus0202 = (CardView) view.findViewById(R.id.card_view_zeus_0202);
		cvZeus0203 = (CardView) view.findViewById(R.id.card_view_zeus_0203);
		cvZeus0204 = (CardView) view.findViewById(R.id.card_view_zeus_0204);
		cvZeus0205 = (CardView) view.findViewById(R.id.card_view_zeus_0205);
		cvZeus0206 = (CardView) view.findViewById(R.id.card_view_zeus_0206);
		cvZeus0207 = (CardView) view.findViewById(R.id.card_view_zeus_0207);
		cvZeus0208 = (CardView) view.findViewById(R.id.card_view_zeus_0208);
		cvZeus0209 = (CardView) view.findViewById(R.id.card_view_zeus_0209);
		cvZeus0210 = (CardView) view.findViewById(R.id.card_view_zeus_0210);
		cvZeus0211 = (CardView) view.findViewById(R.id.card_view_zeus_0211);
		cvZeus0212 = (CardView) view.findViewById(R.id.card_view_zeus_0212);
		cvZeusKe01 = (CardView) view.findViewById(R.id.card_view_zeus_ke01);
		cvZeusKe0101 = (CardView) view.findViewById(R.id.card_view_zeus_ke0101);
		cvZeusKe0102 = (CardView) view.findViewById(R.id.card_view_zeus_ke0102);
		cvZeusKe0103 = (CardView) view.findViewById(R.id.card_view_zeus_ke0103);
		tvZeus0201 = (TextView) view.findViewById(R.id.text_view_zeus_0201);
		tvZeus0202 = (TextView) view.findViewById(R.id.text_view_zeus_0202);
		tvZeus0203 = (TextView) view.findViewById(R.id.text_view_zeus_0203);
		tvZeus0204 = (TextView) view.findViewById(R.id.text_view_zeus_0204);
		tvZeus0205 = (TextView) view.findViewById(R.id.text_view_zeus_0205);
		tvZeus0206 = (TextView) view.findViewById(R.id.text_view_zeus_0206);
		tvZeus0207 = (TextView) view.findViewById(R.id.text_view_zeus_0207);
		tvZeus0208 = (TextView) view.findViewById(R.id.text_view_zeus_0208);
		tvZeus0209 = (TextView) view.findViewById(R.id.text_view_zeus_0209);
		tvZeus0210 = (TextView) view.findViewById(R.id.text_view_zeus_0210);
		tvZeus0211 = (TextView) view.findViewById(R.id.text_view_zeus_0211);
		tvZeus0212 = (TextView) view.findViewById(R.id.text_view_zeus_0212);
		sbZeusKe01 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ke01);
		sbZeusKe0101 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ke0101);
		sbZeusKe0102 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ke0102);
		sbZeusKe0103 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ke0103);
		tvZeusKe01 = (TextView) view.findViewById(R.id.text_view_zeus_ke01);
		tvZeusKe0101 = (TextView) view.findViewById(R.id.text_view_zeus_ke0101);
		tvZeusKe0102 = (TextView) view.findViewById(R.id.text_view_zeus_ke0102);
		tvZeusKe0103 = (TextView) view.findViewById(R.id.text_view_zeus_ke0103);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvZeus0201.setOnClickListener(this);
		cvZeus0202.setOnClickListener(this);
		cvZeus0203.setOnClickListener(this);
		cvZeus0204.setOnClickListener(this);
		cvZeus0205.setOnClickListener(this);
		cvZeus0206.setOnClickListener(this);
		cvZeus0207.setOnClickListener(this);
		cvZeus0208.setOnClickListener(this);
		cvZeus0209.setOnClickListener(this);
		cvZeus0210.setOnClickListener(this);
		cvZeus0211.setOnClickListener(this);
		cvZeus0212.setOnClickListener(this);
		cvZeusKe01.setOnClickListener(this);
		cvZeusKe0101.setOnClickListener(this);
		cvZeusKe0102.setOnClickListener(this);
		cvZeusKe0103.setOnClickListener(this);
		sbZeusKe01.setOnTouchListener(this);
		sbZeusKe0101.setOnTouchListener(this);
		sbZeusKe0102.setOnTouchListener(this);
		sbZeusKe0103.setOnTouchListener(this);

		String[] zeus0201 = getResources().getStringArray(R.array.zeus0201);
		tvZeus0201.setText(zeus0201[zeus0201Position]);

		if (zeus0202Position > -1)
		{
			String[] zeus0202 = getResources().getStringArray(R.array.zeus0202);
			tvZeus0202.setText(zeus0202[zeus0202Position]);
		}

		if (zeus0203Position > -1)
		{
			String[] zeus0203 = getResources().getStringArray(R.array.zeus0203);
			tvZeus0203.setText(zeus0203[zeus0203Position]);
		}

		if (zeus0204Position > -1)
		{
			String[] zeus0204 = getResources().getStringArray(R.array.zeus0204);
			tvZeus0204.setText(zeus0204[zeus0204Position]);
		}

		if (zeus0205Position > -1)
		{
			String[] zeus0205 = getResources().getStringArray(R.array.zeus0205);
			tvZeus0205.setText(zeus0205[zeus0205Position]);
		}

		if (zeus0206Position > -1)
		{
			String[] zeus0206 = getResources().getStringArray(R.array.zeus0206);
			tvZeus0206.setText(zeus0206[zeus0206Position]);
		}

		if (zeus0207Position > -1)
		{
			String[] zeus0207 = getResources().getStringArray(R.array.zeus0207);
			tvZeus0207.setText(zeus0207[zeus0207Position]);
		}

		if (zeus0208Position > -1)
		{
			String[] zeus0208 = getResources().getStringArray(R.array.zeus0208);
			tvZeus0208.setText(zeus0208[zeus0208Position]);
		}

		if (zeus0209Position > -1)
		{
			String[] zeus0209 = getResources().getStringArray(R.array.zeus0209);
			tvZeus0209.setText(zeus0209[zeus0209Position]);
		}

		if (zeus0210Position > -1)
		{
			String[] zeus0210 = getResources().getStringArray(R.array.zeus0210);
			tvZeus0210.setText(zeus0210[zeus0210Position]);
		}

		if (zeus0211Position > -1)
		{
			String[] zeus0211 = getResources().getStringArray(R.array.zeus0211);
			tvZeus0211.setText(zeus0211[zeus0211Position]);
		}

		if (zeus0212Position > -1)
		{
			String[] zeus0212 = getResources().getStringArray(R.array.zeus0212);
			tvZeus0212.setText(zeus0212[zeus0212Position]);
		}

		sbZeusKe01.setProgress(zeusKe01);
		sbZeusKe0101.setProgress(zeusKe0101);
		sbZeusKe0102.setProgress(zeusKe0102);
		sbZeusKe0103.setProgress(zeusKe0103);
		tvZeusKe01.setText(String.valueOf(zeusKe01));
		tvZeusKe0101.setText(String.valueOf(zeusKe0101));
		tvZeusKe0102.setText(String.valueOf(zeusKe0102));
		tvZeusKe0103.setText(String.valueOf(zeusKe0103));
	}

	@Override
	public void onStop()
	{
		super.onStop();

		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_ZEUS_0201_POSITION, zeus0201Position);
		outState.putInt(STATE_ZEUS_0202_POSITION, zeus0202Position);
		outState.putInt(STATE_ZEUS_0203_POSITION, zeus0203Position);
		outState.putInt(STATE_ZEUS_0204_POSITION, zeus0204Position);
		outState.putInt(STATE_ZEUS_0205_POSITION, zeus0205Position);
		outState.putInt(STATE_ZEUS_0206_POSITION, zeus0206Position);
		outState.putInt(STATE_ZEUS_0207_POSITION, zeus0207Position);
		outState.putInt(STATE_ZEUS_0208_POSITION, zeus0208Position);
		outState.putInt(STATE_ZEUS_0209_POSITION, zeus0209Position);
		outState.putInt(STATE_ZEUS_0210_POSITION, zeus0210Position);
		outState.putInt(STATE_ZEUS_0211_POSITION, zeus0211Position);
		outState.putInt(STATE_ZEUS_0212_POSITION, zeus0212Position);
		outState.putInt(STATE_ZEUS_KE_01, zeusKe01);
		outState.putInt(STATE_ZEUS_KE_0101, zeusKe0101);
		outState.putInt(STATE_ZEUS_KE_0102, zeusKe0102);
		outState.putInt(STATE_ZEUS_KE_0103, zeusKe0103);
		outState.putString(STATE_DIALOG, dialog);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.card_view_zeus_0201:
			{
				dialog = DIALOG_ZEUS_0201;
				String title = getResources().getString(R.string.zeus_0201);
				int resource = R.array.zeus0201;
				startSpinner(title, null, resource, zeus0201Position, false);
				break;
			}
			case R.id.card_view_zeus_0202:
			{
				dialog = DIALOG_ZEUS_0202;
				String title = getResources().getString(R.string.zeus_0202);
				int resource = R.array.zeus0202;
				startSpinner(title, null, resource, zeus0202Position, true);
				break;
			}
			case R.id.card_view_zeus_0203:
			{
				dialog = DIALOG_ZEUS_0203;
				String title = getResources().getString(R.string.zeus_0203);
				int resource = R.array.zeus0203;
				startSpinner(title, null, resource, zeus0203Position, true);
				break;
			}
			case R.id.card_view_zeus_0204:
			{
				dialog = DIALOG_ZEUS_0204;
				String title = getResources().getString(R.string.zeus_0204);
				int resource = R.array.zeus0204;
				startSpinner(title, null, resource, zeus0204Position, true);
				break;
			}
			case R.id.card_view_zeus_0205:
			{
				dialog = DIALOG_ZEUS_0205;
				String title = getResources().getString(R.string.zeus_0205);
				int resource = R.array.zeus0205;
				startSpinner(title, null, resource, zeus0205Position, true);
				break;
			}
			case R.id.card_view_zeus_0206:
			{
				dialog = DIALOG_ZEUS_0206;
				String title = getResources().getString(R.string.zeus_0206);
				int resource = R.array.zeus0206;
				startSpinner(title, null, resource, zeus0206Position, true);
				break;
			}
			case R.id.card_view_zeus_0207:
			{
				dialog = DIALOG_ZEUS_0207;
				String title = getResources().getString(R.string.zeus_0207);
				int resource = R.array.zeus0207;
				startSpinner(title, null, resource, zeus0207Position, true);
				break;
			}
			case R.id.card_view_zeus_0208:
			{
				dialog = DIALOG_ZEUS_0208;
				String title = getResources().getString(R.string.zeus_0208);
				int resource = R.array.zeus0208;
				startSpinner(title, null, resource, zeus0208Position, true);
				break;
			}
			case R.id.card_view_zeus_0209:
			{
				dialog = DIALOG_ZEUS_0209;
				String title = getResources().getString(R.string.zeus_0209);
				int resource = R.array.zeus0209;
				startSpinner(title, null, resource, zeus0209Position, true);
				break;
			}
			case R.id.card_view_zeus_0210:
			{
				dialog = DIALOG_ZEUS_0210;
				String title = getResources().getString(R.string.zeus_0210);
				int resource = R.array.zeus0210;
				startSpinner(title, null, resource, zeus0210Position, true);
				break;
			}
			case R.id.card_view_zeus_0211:
			{
				dialog = DIALOG_ZEUS_0211;
				String title = getResources().getString(R.string.zeus_0211);
				int resource = R.array.zeus0211;
				startSpinner(title, null, resource, zeus0211Position, true);
				break;
			}
			case R.id.card_view_zeus_0212:
			{
				dialog = DIALOG_ZEUS_0212;
				String title = getResources().getString(R.string.zeus_0212);
				int resource = R.array.zeus0212;
				startSpinner(title, null, resource, zeus0212Position, true);
				break;
			}
			case R.id.card_view_zeus_ke01:
			{
				dialog = DIALOG_ZEUS_KE_01;
				String title = getResources().getString(R.string.zeus_ke_01);
				int value = zeusKe01;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ke0101:
			{
				dialog = DIALOG_ZEUS_KE_0101;
				String title = getResources().getString(R.string.zeus_ke_0101);
				int value = zeusKe0101;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ke0102:
			{
				dialog = DIALOG_ZEUS_KE_0102;
				String title = getResources().getString(R.string.zeus_ke_0102);
				int value = zeusKe0102;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ke0103:
			{
				dialog = DIALOG_ZEUS_KE_0103;
				String title = getResources().getString(R.string.zeus_ke_0103);
				int value = zeusKe0103;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent)
	{
		if (motionEvent.getAction() == MotionEvent.ACTION_UP)
		{
			switch (view.getId())
			{
				case R.id.seek_bar_zeus_ke01:
				{
					dialog = DIALOG_ZEUS_KE_01;
					String title = getResources().getString(R.string.zeus_ke_01);
					int value = zeusKe01;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ke0101:
				{
					dialog = DIALOG_ZEUS_KE_0101;
					String title = getResources().getString(R.string.zeus_ke_0101);
					int value = zeusKe0101;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ke0102:
				{
					dialog = DIALOG_ZEUS_KE_0102;
					String title = getResources().getString(R.string.zeus_ke_0102);
					int value = zeusKe0102;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ke0103:
				{
					dialog = DIALOG_ZEUS_KE_0103;
					String title = getResources().getString(R.string.zeus_ke_0103);
					int value = zeusKe0103;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
			}
		}

		return true;
	}

	public void setSeekBar(int value)
	{
		switch (dialog)
		{
			case DIALOG_ZEUS_KE_01:
			{
				zeusKe01 = value;
				sbZeusKe01.setProgress(zeusKe01);
				tvZeusKe01.setText(String.valueOf(zeusKe01));
				break;
			}
			case DIALOG_ZEUS_KE_0101:
			{
				zeusKe0101 = value;
				sbZeusKe0101.setProgress(zeusKe0101);
				tvZeusKe0101.setText(String.valueOf(zeusKe0101));
				break;
			}
			case DIALOG_ZEUS_KE_0102:
			{
				zeusKe0102 = value;
				sbZeusKe0102.setProgress(zeusKe0102);
				tvZeusKe0102.setText(String.valueOf(zeusKe0102));
				break;
			}
			case DIALOG_ZEUS_KE_0103:
			{
				zeusKe0103 = value;
				sbZeusKe0103.setProgress(zeusKe0103);
				tvZeusKe0103.setText(String.valueOf(zeusKe0103));
				break;
			}
		}
	}

	public void setSpinner(String text, int position)
	{
		switch (dialog)
		{
			case DIALOG_ZEUS_0201:
			{
				tvZeus0201.setText(text);
				zeus0201Position = position;
				break;
			}
			case DIALOG_ZEUS_0202:
			{
				tvZeus0202.setText(text);
				zeus0202Position = position;
				break;
			}
			case DIALOG_ZEUS_0203:
			{
				tvZeus0203.setText(text);
				zeus0203Position = position;
				break;
			}
			case DIALOG_ZEUS_0204:
			{
				tvZeus0204.setText(text);
				zeus0204Position = position;
				break;
			}
			case DIALOG_ZEUS_0205:
			{
				tvZeus0205.setText(text);
				zeus0205Position = position;
				break;
			}
			case DIALOG_ZEUS_0206:
			{
				tvZeus0206.setText(text);
				zeus0206Position = position;
				break;
			}
			case DIALOG_ZEUS_0207:
			{
				tvZeus0207.setText(text);
				zeus0207Position = position;
				break;
			}
			case DIALOG_ZEUS_0208:
			{
				tvZeus0208.setText(text);
				zeus0208Position = position;
				break;
			}
			case DIALOG_ZEUS_0209:
			{
				tvZeus0209.setText(text);
				zeus0209Position = position;
				break;
			}
			case DIALOG_ZEUS_0210:
			{
				tvZeus0210.setText(text);
				zeus0210Position = position;
				break;
			}
			case DIALOG_ZEUS_0211:
			{
				tvZeus0211.setText(text);
				zeus0211Position = position;
				break;
			}
			case DIALOG_ZEUS_0212:
			{
				tvZeus0212.setText(text);
				zeus0212Position = position;
				break;
			}
		}
	}

	@Override
	public boolean save() throws SQLException
	{

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		ZEUSPart2 zeusPart2 = null;

		if (workReportItem.getZeusPart2Assessment() != null)
		{
			ZEUSPart2Assessment zeusPart2Assessment = workReportItem.getZeusPart2Assessment();
			List<ZEUSPart2> zeusPart2List = new ArrayList<>(zeusPart2Assessment.getZeusPart2());

			if (zeusPart2List.size() == 1)
			{
				zeusPart2 = zeusPart2List.get(0);
			}
			else if (zeusPart2List.size() > 1)
			{
				zeusPart2 = Collections.max(zeusPart2List, new ZEUSPart2DateComparator());
			}
		}

		if (zeusPart2 == null)
		{
			zeusPart2 = new ZEUSPart2();
		}

		ZEUS0201[] zeus0201s = ZEUS0201.values();
		zeusPart2.setZeus0201(String.valueOf(zeus0201s[zeus0201Position].getValue()));

		if (zeus0202Position > -1)
		{
			ZEUS0202[] zeus0202s = ZEUS0202.values();
			zeusPart2.setZeus0202(zeus0202s[zeus0202Position].getValue());
		}

		if (zeus0203Position > -1)
		{
			ZEUS0203[] zeus0203s = ZEUS0203.values();
			zeusPart2.setZeus0203(zeus0203s[zeus0203Position].getValue());
		}

		if (zeus0204Position > -1)
		{
			ZEUS0204[] zeus0204s = ZEUS0204.values();
			zeusPart2.setZeus0204(zeus0204s[zeus0204Position].getValue());
		}

		if (zeus0205Position > -1)
		{
			ZEUS0205[] zeus0205s = ZEUS0205.values();
			zeusPart2.setZeus0205(zeus0205s[zeus0205Position].getValue());
		}

		if (zeus0206Position > -1)
		{
			ZEUS0206[] zeus0206s = ZEUS0206.values();
			zeusPart2.setZeus0206(zeus0206s[zeus0206Position].getValue());
		}

		if (zeus0207Position > -1)
		{
			ZEUS0207[] zeus0207s = ZEUS0207.values();
			zeusPart2.setZeus0207(zeus0207s[zeus0207Position].getValue());
		}

		if (zeus0208Position > -1)
		{
			ZEUS0208[] zeus0208s = ZEUS0208.values();
			zeusPart2.setZeus0208(zeus0208s[zeus0208Position].getValue());
		}

		if (zeus0209Position > -1)
		{
			ZEUS0209[] zeus0209s = ZEUS0209.values();
			zeusPart2.setZeus0209(zeus0209s[zeus0209Position].getValue());
		}

		if (zeus0210Position > -1)
		{
			ZEUS0210[] zeus0210s = ZEUS0210.values();
			zeusPart2.setZeus0210(zeus0210s[zeus0210Position].getValue());
		}

		if (zeus0211Position > -1)
		{
			ZEUS0211[] zeus0211s = ZEUS0211.values();
			zeusPart2.setZeus0211(zeus0211s[zeus0211Position].getValue());
		}

		if (zeus0212Position > -1)
		{
			ZEUS0212[] zeus0212s = ZEUS0212.values();
			zeusPart2.setZeus0212(zeus0212s[zeus0212Position].getValue());
		}

		if (sbZeusKe01.isEnabled())
		{
			zeusPart2.setZeuske01(new BigDecimal(zeusKe01));
		}

		if (sbZeusKe0101.isEnabled())
		{
			zeusPart2.setZeuske0101(new BigDecimal(zeusKe0101));
		}

		if (sbZeusKe0102.isEnabled())
		{
			zeusPart2.setZeuske0102(new BigDecimal(zeusKe0102));
		}

		if (sbZeusKe0103.isEnabled())
		{
			zeusPart2.setZeuske0103(new BigDecimal(zeusKe0103));
		}

		if (zeusPart2.getAssessmentInfo() == null)
		{
			String date = Utilities.exportDate(new Date());
			String statusInfo = StatusInfo.GSP_SSA_102.getValue();

			Assessment assessment = new Assessment();
			assessment.setTimestamp(date);
			assessment.setStatusInfo(statusInfo);

			ZEUSPart2Assessment zeusPart2Assessment;

			if (workReportItem.getZeusPart2Assessment() == null)
			{
				zeusPart2Assessment = new ZEUSPart2Assessment();
				List<ZEUSPart2> zeusPart2List = new ArrayList<>();

				zeusPart2List.add(zeusPart2);
				zeusPart2Assessment.setZeusPart2(zeusPart2List);
			}
			else
			{
				zeusPart2Assessment = workReportItem.getZeusPart2Assessment();
			}

			zeusPart2.setZeusPart2Assessment(zeusPart2Assessment);
			zeusPart2.setAssessmentInfo(assessment);

			databaseAdapter.createOrUpdateAssessment(assessment);
			databaseAdapter.createOrUpdateZeusPart2(zeusPart2);

			if (workReportItem.getZeusPart2Assessment() == null)
			{
				workReportItem.setZeusPart2Assessment(zeusPart2Assessment);
				databaseAdapter.createOrUpdateZeusPart2Assessement(zeusPart2Assessment);
				databaseAdapter.createOrUpdateWorkReportItem(workReportItem);
			}
		}
		else
		{
			databaseAdapter.createOrUpdateZeusPart2(zeusPart2);
		}

		return true;
	}

	private void loadData() throws SQLException
	{
		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		WorkOrderItem workOrderItem = databaseAdapter.getWorkOrderItem(workOrderITemDBID);
		WorkReportItem workReportItem = databaseAdapter.getWorkReportItem(workReportItemDBID);

		ZEUSPart2 zeusPart2 = null;

		if (workReportItem.getZeusPart2Assessment() != null)
		{
			ZEUSPart2Assessment zeusPart2Assessment = workReportItem.getZeusPart2Assessment();

			if (zeusPart2Assessment.getZeusPart2().size() == 1)
			{
				List<ZEUSPart2> zeusPart2List = new ArrayList<>(zeusPart2Assessment.getZeusPart2());

				zeusPart2 = zeusPart2List.get(0);
			}
		}

		if (zeusPart2 == null)
		{
			ZEUSPart2Assessment zeusPart2Assessment = workOrderItem.getZeusPart2History();
			List<ZEUSPart2> zeusPart2List = new ArrayList<>(zeusPart2Assessment.getZeusPart2());

			if (zeusPart2Assessment.getZeusPart2().size() == 1)
			{
				zeusPart2 = zeusPart2List.get(0);
			}
			else if (zeusPart2Assessment.getZeusPart2().size() > 1)
			{
				zeusPart2 = Collections.max(zeusPart2List, new ZEUSPart2DateComparator());
			}
		}

		if (zeusPart2 != null)
		{
			Pair<String, Integer> zeus0201Pair = GSPUtilities.getZeus0201(getActivity(), zeusPart2.getZeus0201());

			if (zeus0201Pair != null)
			{
				zeus0201Position = zeus0201Pair.second;
			}

			if (zeusPart2.getZeus0202() != null)
			{
				Pair<String, Integer> zeus0202Pair = GSPUtilities.getZeus0202(getActivity(), zeusPart2.getZeus0202());

				if (zeus0202Pair != null)
				{
					zeus0202Position = zeus0202Pair.second;
				}
			}
			else
			{
				zeus0202Position = -1;
			}

			if (zeusPart2.getZeus0203() != null)
			{
				Pair<String, Integer> zeus0203Pair = GSPUtilities.getZeus0203(getActivity(), zeusPart2.getZeus0203());

				if (zeus0203Pair != null)
				{
					zeus0203Position = zeus0203Pair.second;
				}
			}
			else
			{
				zeus0203Position = -1;
			}

			if (zeusPart2.getZeus0204() != null)
			{
				Pair<String, Integer> zeus0204Pair = GSPUtilities.getZeus0204(getActivity(), zeusPart2.getZeus0204());

				if (zeus0204Pair != null)
				{
					zeus0204Position = zeus0204Pair.second;
				}
			}
			else
			{
				zeus0204Position = -1;
			}

			if (zeusPart2.getZeus0205() != null)
			{
				Pair<String, Integer> zeus0205Pair = GSPUtilities.getZeus0205(getActivity(), zeusPart2.getZeus0205());

				if (zeus0205Pair != null)
				{
					zeus0205Position = zeus0205Pair.second;
				}
			}
			else
			{
				zeus0205Position = -1;
			}

			if (zeusPart2.getZeus0206() != null)
			{
				Pair<String, Integer> zeus0206Pair = GSPUtilities.getZeus0206(getActivity(), zeusPart2.getZeus0206());

				if (zeus0206Pair != null)
				{
					zeus0206Position = zeus0206Pair.second;
				}
			}
			else
			{
				zeus0206Position = -1;
			}

			if (zeusPart2.getZeus0207() != null)
			{
				Pair<String, Integer> zeus0207Pair = GSPUtilities.getZeus0207(getActivity(), zeusPart2.getZeus0207());

				if (zeus0207Pair != null)
				{
					zeus0207Position = zeus0207Pair.second;
				}
			}
			else
			{
				zeus0207Position = -1;
			}

			if (zeusPart2.getZeus0208() != null)
			{
				Pair<String, Integer> zeus0208Pair = GSPUtilities.getZeus0208(getActivity(), zeusPart2.getZeus0208());

				if (zeus0208Pair != null)
				{
					zeus0208Position = zeus0208Pair.second;
				}
			}
			else
			{
				zeus0208Position = -1;
			}

			if (zeusPart2.getZeus0209() != null)
			{
				Pair<String, Integer> zeus0209Pair = GSPUtilities.getZeus0209(getActivity(), zeusPart2.getZeus0209());

				if (zeus0209Pair != null)
				{
					zeus0209Position = zeus0209Pair.second;
				}
			}
			else
			{
				zeus0209Position = -1;
			}

			if (zeusPart2.getZeus0210() != null)
			{
				Pair<String, Integer> zeus0210Pair = GSPUtilities.getZeus0210(getActivity(), zeusPart2.getZeus0210());

				if (zeus0210Pair != null)
				{
					zeus0210Position = zeus0210Pair.second;
				}
			}
			else
			{
				zeus0210Position = -1;
			}

			if (zeusPart2.getZeus0211() != null)
			{
				Pair<String, Integer> zeus0211Pair = GSPUtilities.getZeus0211(getActivity(), zeusPart2.getZeus0211());

				if (zeus0211Pair != null)
				{
					zeus0211Position = zeus0211Pair.second;
				}
			}
			else
			{
				zeus0211Position = -1;
			}

			if (zeusPart2.getZeus0212() != null)
			{
				Pair<String, Integer> zeus0212Pair = GSPUtilities.getZeus0212(getActivity(), zeusPart2.getZeus0212());

				if (zeus0212Pair != null)
				{
					zeus0212Position = zeus0212Pair.second;
				}
			}
			else
			{
				zeus0212Position = -1;
			}

			if (zeusPart2.getZeuske01() != null)
			{
				zeusKe01 = zeusPart2.getZeuske01().intValue();
			}

			if (zeusPart2.getZeuske0101() != null)
			{
				zeusKe0101 = zeusPart2.getZeuske0101().intValue();
			}

			if (zeusPart2.getZeuske0102() != null)
			{
				zeusKe0102 = zeusPart2.getZeuske0102().intValue();
			}

			if (zeusPart2.getZeuske0103() != null)
			{
				zeusKe0103 = zeusPart2.getZeuske0103().intValue();
			}
		}
	}

	private void startSpinner(String title, String subTitle, int resource, int position, boolean emptyItem)
	{
		SpinnerDialogFragment fragment = SpinnerDialogFragment.newInstance(title, subTitle, resource, position, emptyItem);
		fragment.show(getChildFragmentManager(), FRAGMENT_SPINNER);
	}

	private void startSeekBar(String title, String subTitle, int value, int maxValue)
	{
		SeekBarDialogFragment fragment = SeekBarDialogFragment.newInstance(title, subTitle, value, maxValue);
		fragment.show(getChildFragmentManager(), FRAGMENT_SEEK_BAR);
	}

	@Override
	public void onEvent(SpinnerDialogEvent event)
	{
		String text = event.getText();
		int position = event.getPosition();
		setSpinner(text, position);
	}

	@Override
	public void onEvent(SeekBarDialogEvent event)
	{
		int value = event.getValue();
		setSeekBar(value);
	}

	public void onEvent(SaveEvent event)
	{
		if (event.getPosition() == position)
		{
			try
			{
				if (save())
				{
					EventBus.getDefault().post(new NextEvent(true));
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
