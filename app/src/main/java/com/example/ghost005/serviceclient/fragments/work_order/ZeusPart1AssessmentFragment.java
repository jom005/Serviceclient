package com.example.ghost005.serviceclient.fragments.work_order;


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
import com.example.ghost005.serviceclient.dialogs.ShortEditTextDialogFragment;
import com.example.ghost005.serviceclient.dialogs.SpinnerDialogFragment;
import com.example.ghost005.serviceclient.events.NextEvent;
import com.example.ghost005.serviceclient.events.SaveEvent;
import com.example.ghost005.serviceclient.events.SeekBarDialogEvent;
import com.example.ghost005.serviceclient.events.ShortEditTextDialogEvent;
import com.example.ghost005.serviceclient.events.SpinnerDialogEvent;
import com.example.ghost005.serviceclient.model.comparators.ZEUSPart1DateComparator;
import com.example.ghost005.serviceclient.model.enums.StatusInfo;
import com.example.ghost005.serviceclient.model.enums.ZEUS0101;
import com.example.ghost005.serviceclient.model.enums.ZEUS0102;
import com.example.ghost005.serviceclient.model.enums.ZEUS0103;
import com.example.ghost005.serviceclient.model.enums.ZEUS0104;
import com.example.ghost005.serviceclient.model.types.Assessment;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.ZEUSPart1;
import com.example.ghost005.serviceclient.model.types.ZEUSPart1Assessment;
import com.example.ghost005.serviceclient.utilities.GSPUtilities;
import com.example.ghost005.serviceclient.utilities.Utilities;
import com.example.ghost005.serviceclient.wizard.WorkOrderWizardFragment;
import de.greenrobot.event.EventBus;

/**
 * Fragment for adding a Zeus part 1 assessment.
 */
public class ZeusPart1AssessmentFragment extends Fragment
		implements View.OnClickListener, View.OnTouchListener,
		WorkOrderWizardFragment.OnStatusSaveListener,
		ShortEditTextDialogFragment.ShortEditTextDialogEventListener,
		SpinnerDialogFragment.SpinnerDialogEventListener,
		SeekBarDialogFragment.SeekBarDialogEventListener
{
	public static final int FIRST_ASSESSMENT = 1;
	public static final int SECOND_ASSESSMENT = 2;
	private static final String BUNDLE_GSP_DB_ID = "bundle_gsp_db_id";
	private static final String BUNDLE_POSITION = "bundle_position";
	private static final String BUNDLE_ASSESSMENT = "bundle_assessment";
	private static final String DIALOG_ZEUS_0101 = "dialog_zeus_0101";
	private static final String DIALOG_ZEUS_0102 = "dialog_zeus_0102";
	private static final String DIALOG_ZEUS_0103 = "dialog_zeus_0103";
	private static final String DIALOG_ZEUS_0104 = "dialog_zeus_0104";
	private static final String DIALOG_ZEUS_KA_01 = "dialog_zeus_ka_01";
	private static final String DIALOG_ZEUS_KA_0101 = "dialog_zeus_ka_0101";
	private static final String DIALOG_ZEUS_KA_0102 = "dialog_zeus_ka_0102";
	private static final String DIALOG_ZEUS_KA_0103 = "dialog_zeus_ka_0103";
	private static final String DIALOG_ZEUS_KA_02 = "dialog_zeus_ka_02";
	private static final String DIALOG_ZEUS_KA_03 = "dialog_zeus_ka_03";
	private static final String DIALOG_ZEUS_KA_04 = "dialog_zeus_ka_04";
	private static final String DIALOG_ZEUS_KA_05 = "dialog_zeus_ka_05";
	private static final String FRAGMENT_SPINNER = "fragment_spinner";
	private static final String FRAGMENT_SEEK_BAR = "fragment_seek_bar";
	private static final String FRAGMENT_EDIT_TEXT_SHORT = "fragment_edit_text_short";
	private static final String STATE_ZEUS_0101_POSITION = "state_zeus_0101_position";
	private static final String STATE_ZEUS_0102_POSITION = "state_zeus_0102_position";
	private static final String STATE_ZEUS_0103_POSITION = "state_zeus_0103_position";
	private static final String STATE_ZEUS_0104_POSITION = "state_zeus_0104_position";
	private static final String STATE_ZEUS_KA_01 = "state_zeus_ka_01";
	private static final String STATE_ZEUS_KA_0101 = "state_zeus_ka_0101";
	private static final String STATE_ZEUS_KA_0102 = "state_zeus_ka_0102";
	private static final String STATE_ZEUS_KA_0103 = "state_zeus_ka_0103";
	private static final String STATE_ZEUS_KA_02 = "state_zeus_ka_02";
	private static final String STATE_ZEUS_KA_03 = "state_zeus_ka_03";
	private static final String STATE_ZEUS_KA_04 = "state_zeus_ka_04";
	private static final String STATE_ZEUS_KA_05 = "state_zeus_ka_05";
	private static final String STATE_DIALOG = "state_dialog";

	private CardView cvZeus0101;
	private CardView cvZeus0102;
	private CardView cvZeus0103;
	private CardView cvZeus0104;
	private CardView cvZeusKa01;
	private CardView cvZeusKa0101;
	private CardView cvZeusKa0102;
	private CardView cvZeusKa0103;
	private CardView cvZeusKa02;
	private CardView cvZeusKa03;
	private CardView cvZeusKa04;
	private CardView cvZeusKa05;
	private TextView tvZeus0101;
	private TextView tvZeus0102;
	private TextView tvZeus0103;
	private TextView tvZeus0104;
	private TextView tvZeusKa01;
	private TextView tvZeusKa0101;
	private TextView tvZeusKa0102;
	private TextView tvZeusKa0103;
	private TextView tvZeusKa02;
	private TextView tvZeusKa02Unit;
	private TextView tvZeusKa03;
	private TextView tvZeusKa03Unit;
	private TextView tvZeusKa04;
	private TextView tvZeusKa04Unit;
	private TextView tvZeusKa05;
	private SeekBar sbZeusKa01;
	private SeekBar sbZeusKa0101;
	private SeekBar sbZeusKa0102;
	private SeekBar sbZeusKa0103;

	private int zeus0101Position;
	private int zeus0102Position;
	private int zeus0103Position;
	private int zeus0104Position;
	private int zeusKa01;
	private int zeusKa0101;
	private int zeusKa0102;
	private int zeusKa0103;
	private String zeusKa02;
	private String zeusKa03;
	private String zeusKa04;
	private String zeusKa05;
	private String dialog;

	private int gspDBID;
	private int type;
	private int position;

	public static ZeusPart1AssessmentFragment newInstance(int type, int gspDBID, int position)
	{
		ZeusPart1AssessmentFragment fragment = new ZeusPart1AssessmentFragment();

		Bundle args = new Bundle();
		args.putInt(BUNDLE_POSITION, position);
		args.putInt(BUNDLE_GSP_DB_ID, gspDBID);
		args.putInt(BUNDLE_ASSESSMENT, type);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		gspDBID = getArguments().getInt(BUNDLE_GSP_DB_ID);
		type = getArguments().getInt(BUNDLE_ASSESSMENT);
		position = getArguments().getInt(BUNDLE_POSITION);

		if (savedInstanceState != null)
		{
			zeus0101Position = savedInstanceState.getInt(STATE_ZEUS_0101_POSITION);
			zeus0102Position = savedInstanceState.getInt(STATE_ZEUS_0102_POSITION);
			zeus0103Position = savedInstanceState.getInt(STATE_ZEUS_0103_POSITION);
			zeus0104Position = savedInstanceState.getInt(STATE_ZEUS_0104_POSITION);
			zeusKa01 = savedInstanceState.getInt(STATE_ZEUS_KA_01);
			zeusKa0101 = savedInstanceState.getInt(STATE_ZEUS_KA_0101);
			zeusKa0102 = savedInstanceState.getInt(STATE_ZEUS_KA_0102);
			zeusKa0103 = savedInstanceState.getInt(STATE_ZEUS_KA_0103);
			zeusKa02 = savedInstanceState.getString(STATE_ZEUS_KA_02);
			zeusKa03 = savedInstanceState.getString(STATE_ZEUS_KA_03);
			zeusKa04 = savedInstanceState.getString(STATE_ZEUS_KA_04);
			zeusKa05 = savedInstanceState.getString(STATE_ZEUS_KA_05);
			dialog = savedInstanceState.getString(STATE_DIALOG);
		}
		else
		{
			try
			{
				loadZeusPart1();
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
		View view = inflater.inflate(R.layout.fragment_zeus_part1_assessment, container, false);

		cvZeus0101 = (CardView) view.findViewById(R.id.card_view_zeus_0101);
		cvZeus0102 = (CardView) view.findViewById(R.id.card_view_zeus_0102);
		cvZeus0103 = (CardView) view.findViewById(R.id.card_view_zeus_0103);
		cvZeus0104 = (CardView) view.findViewById(R.id.card_view_zeus_0104);
		cvZeusKa01 = (CardView) view.findViewById(R.id.card_view_zeus_ka_01);
		cvZeusKa0101 = (CardView) view.findViewById(R.id.card_view_zeus_ka_0101);
		cvZeusKa0102 = (CardView) view.findViewById(R.id.card_view_zeus_ka_0102);
		cvZeusKa0103 = (CardView) view.findViewById(R.id.card_view_zeus_ka_0103);
		cvZeusKa02 = (CardView) view.findViewById(R.id.card_view_zeus_ka_02);
		cvZeusKa03 = (CardView) view.findViewById(R.id.card_view_zeus_ka_03);
		cvZeusKa04 = (CardView) view.findViewById(R.id.card_view_zeus_ka_04);
		cvZeusKa05 = (CardView) view.findViewById(R.id.card_view_zeus_ka_05);

		tvZeus0101 = (TextView) view.findViewById(R.id.text_view_zeus_0101);
		tvZeus0102 = (TextView) view.findViewById(R.id.text_view_zeus_0102);
		tvZeus0103 = (TextView) view.findViewById(R.id.text_view_zeus_0103);
		tvZeus0104 = (TextView) view.findViewById(R.id.text_view_zeus_0104);
		tvZeusKa01 = (TextView) view.findViewById(R.id.text_view_zeus_ka01);
		tvZeusKa0101 = (TextView) view.findViewById(R.id.text_view_zeus_ka0101);
		tvZeusKa0102 = (TextView) view.findViewById(R.id.text_view_zeus_ka0102);
		tvZeusKa0103 = (TextView) view.findViewById(R.id.text_view_zeus_ka0103);
		tvZeusKa02 = (TextView) view.findViewById(R.id.text_view_zeus_ka_02);
		tvZeusKa02Unit = (TextView) view.findViewById(R.id.text_view_zeus_ka_02_unit);
		tvZeusKa03 = (TextView) view.findViewById(R.id.text_view_zeus_ka_03);
		tvZeusKa03Unit = (TextView) view.findViewById(R.id.text_view_zeus_ka_03_unit);
		tvZeusKa04 = (TextView) view.findViewById(R.id.text_view_zeus_ka_04);
		tvZeusKa04Unit = (TextView) view.findViewById(R.id.text_view_zeus_ka_04_unit);
		tvZeusKa05 = (TextView) view.findViewById(R.id.text_view_zeus_ka_05);

		sbZeusKa01 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ka01);
		sbZeusKa0101 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ka0101);
		sbZeusKa0102 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ka0102);
		sbZeusKa0103 = (SeekBar) view.findViewById(R.id.seek_bar_zeus_ka0103);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		cvZeus0101.setOnClickListener(this);
		cvZeus0102.setOnClickListener(this);
		cvZeus0103.setOnClickListener(this);
		cvZeus0104.setOnClickListener(this);
		cvZeusKa01.setOnClickListener(this);
		cvZeusKa0101.setOnClickListener(this);
		cvZeusKa0102.setOnClickListener(this);
		cvZeusKa0103.setOnClickListener(this);
		cvZeusKa02.setOnClickListener(this);
		cvZeusKa03.setOnClickListener(this);
		cvZeusKa04.setOnClickListener(this);
		cvZeusKa05.setOnClickListener(this);

		// Disable SeekBar touch events for the SeekBars with custom TouchListener
		sbZeusKa01.setOnTouchListener(this);
		sbZeusKa0101.setOnTouchListener(this);
		sbZeusKa0102.setOnTouchListener(this);
		sbZeusKa0103.setOnTouchListener(this);

		String[] zeus0101s = getResources().getStringArray(R.array.zeus0101);
		String[] zeus0102s = getResources().getStringArray(R.array.zeus0102);
		String[] zeus0103s = getResources().getStringArray(R.array.zeus0103);
		String[] zeus0104s = getResources().getStringArray(R.array.zeus0104);

		tvZeus0101.setText(zeus0101s[zeus0101Position]);
		tvZeus0102.setText(zeus0102s[zeus0102Position]);

		if (zeus0103Position > -1)
		{
			tvZeus0103.setText(zeus0103s[zeus0103Position]);
		}

		if (zeus0104Position > -1)
		{
			tvZeus0104.setText(zeus0104s[zeus0104Position]);
		}

		sbZeusKa01.setProgress(zeusKa01);
		sbZeusKa0101.setProgress(zeusKa0101);
		sbZeusKa0102.setProgress(zeusKa0102);
		sbZeusKa0103.setProgress(zeusKa0103);
		tvZeusKa01.setText(String.valueOf(zeusKa01));
		tvZeusKa0101.setText(String.valueOf(zeusKa0101));
		tvZeusKa0102.setText(String.valueOf(zeusKa0102));
		tvZeusKa0103.setText(String.valueOf(zeusKa0103));
		tvZeusKa02.setText(zeusKa02);
		tvZeusKa03.setText(zeusKa03);
		tvZeusKa04.setText(zeusKa04);
		tvZeusKa05.setText(zeusKa05);

		if (zeusKa02.length() > 0)
		{
			tvZeusKa02Unit.setVisibility(View.VISIBLE);
		}
		else
		{
			tvZeusKa02Unit.setVisibility(View.GONE);
		}

		if (zeusKa03.length() > 0)
		{
			tvZeusKa03Unit.setVisibility(View.VISIBLE);
		}
		else
		{
			tvZeusKa03Unit.setVisibility(View.GONE);
		}

		if (zeusKa04.length() > 0)
		{
			tvZeusKa04Unit.setVisibility(View.VISIBLE);
		}
		else
		{
			tvZeusKa04Unit.setVisibility(View.GONE);
		}

		tvZeusKa01.setText(String.valueOf(zeusKa01));
		tvZeusKa0101.setText(String.valueOf(zeusKa0101));
		tvZeusKa0102.setText(String.valueOf(zeusKa0102));
		tvZeusKa0103.setText(String.valueOf(zeusKa0103));
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

		outState.putString(STATE_DIALOG, dialog);
		outState.putInt(STATE_ZEUS_0101_POSITION, zeus0101Position);
		outState.putInt(STATE_ZEUS_0102_POSITION, zeus0102Position);
		outState.putInt(STATE_ZEUS_0103_POSITION, zeus0103Position);
		outState.putInt(STATE_ZEUS_0104_POSITION, zeus0104Position);
		outState.putInt(STATE_ZEUS_KA_01, zeusKa01);
		outState.putInt(STATE_ZEUS_KA_0101, zeusKa0101);
		outState.putInt(STATE_ZEUS_KA_0102, zeusKa0102);
		outState.putInt(STATE_ZEUS_KA_0103, zeusKa0103);
		outState.putString(STATE_ZEUS_KA_02, zeusKa02);
		outState.putString(STATE_ZEUS_KA_03, zeusKa03);
		outState.putString(STATE_ZEUS_KA_04, zeusKa04);
		outState.putString(STATE_ZEUS_KA_05, zeusKa05);
	}

	@Override
	public boolean save() throws SQLException
	{
		ZEUSPart1 zeusPart1 = null;

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkReport workReport = gsp.getWorkReport();

		if (type == FIRST_ASSESSMENT)
		{
			if (workReport.getZeusPart1Assessment() != null)
			{
				ZEUSPart1Assessment zeusPart1Assessment = workReport.getZeusPart1Assessment();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>(zeusPart1Assessment.getZeusPart1());

				if (zeusPart1List.size() == 1)
				{
					zeusPart1 = zeusPart1List.get(0);
				}
				else if (zeusPart1List.size() > 1)
				{
					zeusPart1 = Collections.min(zeusPart1List, new ZEUSPart1DateComparator());
				}
			}
		}
		else if (type == SECOND_ASSESSMENT)
		{
			if (workReport.getZeusPart1Assessment() != null)
			{
				ZEUSPart1Assessment zeusPart1Assessment = workReport.getZeusPart1Assessment();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>(zeusPart1Assessment.getZeusPart1());

				if (zeusPart1List.size() == 1)
				{
					zeusPart1 = zeusPart1List.get(0);
				}
				else if (zeusPart1List.size() > 1)
				{
					zeusPart1 = Collections.max(zeusPart1List, new ZEUSPart1DateComparator());
				}
			}
		}

		if (zeusPart1 == null)
		{
			zeusPart1 = new ZEUSPart1();
		}

		ZEUS0101[] zeus0101s = ZEUS0101.values();
		zeusPart1.setZeus0101(zeus0101s[zeus0101Position].getValue());

		ZEUS0102[] zeus0102s = ZEUS0102.values();
		zeusPart1.setZeus0102(zeus0102s[zeus0102Position].getValue());

		if (zeus0103Position > -1)
		{
			ZEUS0103[] zeus0103s = ZEUS0103.values();
			zeusPart1.setZeus0103(zeus0103s[zeus0103Position].getValue());
		}

		if (zeus0104Position > -1)
		{
			ZEUS0104[] zeus0104s = ZEUS0104.values();
			zeusPart1.setZeus0104(zeus0104s[zeus0104Position].getValue());
		}

		if (sbZeusKa01.isEnabled())
		{
			zeusPart1.setZeusKA01(new BigDecimal(zeusKa01));
		}

		if (sbZeusKa0101.isEnabled())
		{
			zeusPart1.setZeusKA0101(new BigDecimal(zeusKa0101));
		}

		if (sbZeusKa0102.isEnabled())
		{
			zeusPart1.setZeusKA0102(new BigDecimal(zeusKa0102));
		}

		if (sbZeusKa0103.isEnabled())
		{
			zeusPart1.setZeusKA0103(new BigDecimal(zeusKa0103));
		}

		if (tvZeusKa02.getText().toString().length() > 0)
		{
			zeusPart1.setZeusKA02(new BigDecimal(zeusKa02));
		}

		if (tvZeusKa03.getText().toString().length() > 0)
		{
			zeusPart1.setZeusKA03(new BigDecimal(zeusKa03));
		}

		if (tvZeusKa04.getText().toString().length() > 0)
		{
			zeusPart1.setZeusKA04(new BigDecimal(zeusKa04));
		}

		if (tvZeusKa05.getText().toString().length() > 0)
		{
			zeusPart1.setZeusKA05(new BigDecimal(zeusKa05));
		}

		if (zeusPart1.getAssessmentInfo() == null)
		{
			String date = Utilities.exportDate(new Date());
			String statusInfo;

			if (type == FIRST_ASSESSMENT)
			{
				statusInfo = StatusInfo.GSP_SSA_101.getValue();
			}
			else if (type == SECOND_ASSESSMENT)
			{
				statusInfo = StatusInfo.GSP_SSA_201.getValue();
			}
			else
			{
				throw new IllegalArgumentException("Wrong type for assessment: " + type);
			}

			Assessment assessment = new Assessment();
			assessment.setTimestamp(date);
			assessment.setStatusInfo(statusInfo);

			ZEUSPart1Assessment zeusPart1Assessment;

			if (workReport.getZeusPart1Assessment() == null)
			{
				zeusPart1Assessment = new ZEUSPart1Assessment();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>();

				zeusPart1List.add(zeusPart1);
				zeusPart1Assessment.setZeusPart1(zeusPart1List);
			}
			else
			{
				zeusPart1Assessment = workReport.getZeusPart1Assessment();
			}

			zeusPart1.setZeusPart1Assessment(zeusPart1Assessment);
			zeusPart1.setAssessmentInfo(assessment);

			databaseAdapter.createOrUpdateAssessment(assessment);
			databaseAdapter.createOrUpdateZeusPart1(zeusPart1);

			if (workReport.getZeusPart1Assessment() == null)
			{
				workReport.setZeusPart1Assessment(zeusPart1Assessment);
				databaseAdapter.createOrUpdateZeusPart1Assessement(zeusPart1Assessment);
				databaseAdapter.createOrUpdateWorkReport(workReport);
			}
		}
		else
		{
			databaseAdapter.createOrUpdateZeusPart1(zeusPart1);
		}

		return true;
	}

	/**
	 * Load ZeusPar1 data from database, set GlobalServiceProtocol and if loaded from WorkReport
	 * or WorkOrder.
	 */
	private void loadZeusPart1() throws SQLException
	{
		ZEUSPart1 zeusPart1 = null;

		DatabaseAdapter databaseAdapter = ((DatabaseApplication) getActivity().getApplication()).getDatabaseAdapter();
		GlobalServiceProtocol gsp = databaseAdapter.getGlobalServiceProtocol(gspDBID);
		WorkReport workReport = gsp.getWorkReport();

		if (type == FIRST_ASSESSMENT)
		{
			if (workReport.getZeusPart1Assessment() != null)
			{
				ZEUSPart1Assessment zeusPart1Assessment = workReport.getZeusPart1Assessment();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>(zeusPart1Assessment.getZeusPart1());

				if (zeusPart1List.size() == 1)
				{
					zeusPart1 = zeusPart1List.get(0);
				}
				else if (zeusPart1List.size() > 1)
				{
					zeusPart1 = Collections.min(zeusPart1List, new ZEUSPart1DateComparator());
				}
			}

			if (zeusPart1 == null)
			{
				WorkOrder workOrder = gsp.getWorkOrder();

				ZEUSPart1Assessment zeusPart1Assessment = workOrder.getZeusPart1History();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>(zeusPart1Assessment.getZeusPart1());

				if (zeusPart1List.size() == 1)
				{
					zeusPart1 = zeusPart1List.get(0);
				}
				else if (zeusPart1List.size() > 1)
				{
					zeusPart1 = Collections.max(zeusPart1List, new ZEUSPart1DateComparator());
				}
			}
		}
		else if (type == SECOND_ASSESSMENT)
		{
			if (workReport.getZeusPart1Assessment() != null)
			{
				ZEUSPart1Assessment zeusPart1Assessment = workReport.getZeusPart1Assessment();
				List<ZEUSPart1> zeusPart1List = new ArrayList<>(zeusPart1Assessment.getZeusPart1());

				if (zeusPart1List.size() == 1)
				{
					zeusPart1 = zeusPart1List.get(0);
				}
				else if (zeusPart1List.size() > 1)
				{
					zeusPart1 = Collections.max(zeusPart1List, new ZEUSPart1DateComparator());
				}
			}
		}

		if (zeusPart1 != null)
		{
			Pair<String, Integer> zeus0101Pair = GSPUtilities.getZeus0101(getActivity(), zeusPart1.getZeus0101());

			if (zeus0101Pair != null)
			{
				zeus0101Position = zeus0101Pair.second;
			}

			Pair<String, Integer> zeus0102Pair = GSPUtilities.getZeus0102(getActivity(), zeusPart1.getZeus0102());

			if (zeus0102Pair != null)
			{
				zeus0102Position = zeus0102Pair.second;
			}

			if (zeusPart1.getZeus0103() != null)
			{
				Pair<String, Integer> zeus0103Pair = GSPUtilities.getZeus0103(getActivity(), zeusPart1.getZeus0103());

				if (zeus0103Pair != null)
				{
					zeus0103Position = zeus0103Pair.second;
				}
			}
			else
			{
				zeus0103Position = -1;
			}

			if (zeusPart1.getZeus0104() != null)
			{
				Pair<String, Integer> zeus0104Pair = GSPUtilities.getZeus0104(getActivity(), zeusPart1.getZeus0104());

				if (zeus0104Pair != null)
				{
					zeus0104Position = zeus0104Pair.second;
				}
			}
			else
			{
				zeus0104Position = -1;
			}

			if (zeusPart1.getZeusKA01() != null)
			{
				zeusKa01 = zeusPart1.getZeusKA01().intValue();
			}

			if (zeusPart1.getZeusKA0101() != null)
			{
				zeusKa0101 = zeusPart1.getZeusKA0101().intValue();
			}

			if (zeusPart1.getZeusKA0102() != null)
			{
				zeusKa0102 = zeusPart1.getZeusKA0102().intValue();
			}

			if (zeusPart1.getZeusKA0103() != null)
			{
				zeusKa0103 = zeusPart1.getZeusKA0103().intValue();
			}

			if (zeusPart1.getZeusKA02() != null)
			{
				zeusKa02 = String.valueOf(zeusPart1.getZeusKA02());
			}
			else
			{
				zeusKa02 = "";
			}

			if (zeusPart1.getZeusKA03() != null)
			{
				zeusKa03 = String.valueOf(zeusPart1.getZeusKA03());
			}
			else
			{
				zeusKa03 = "";
			}

			if (zeusPart1.getZeusKA04() != null)
			{
				zeusKa04 = String.valueOf(zeusPart1.getZeusKA04());
			}
			else
			{
				zeusKa04 = "";
			}

			if (zeusPart1.getZeusKA05() != null)
			{
				zeusKa05 = String.valueOf(zeusPart1.getZeusKA05());
			}
			else
			{
				zeusKa05 = "";
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.card_view_zeus_0101:
			{
				dialog = DIALOG_ZEUS_0101;
				String title = getResources().getString(R.string.zeus_0101);
				int resource = R.array.zeus0101;
				startSpinner(title, null, resource, zeus0101Position, false);
				break;
			}
			case R.id.card_view_zeus_0102:
			{
				dialog = DIALOG_ZEUS_0102;
				String title = getResources().getString(R.string.zeus_0102);
				int resource = R.array.zeus0102;
				startSpinner(title, null, resource, zeus0102Position, false);
				break;
			}
			case R.id.card_view_zeus_0103:
			{
				dialog = DIALOG_ZEUS_0103;
				String title = getResources().getString(R.string.zeus_0103);
				int resource = R.array.zeus0103;
				startSpinner(title, null, resource, zeus0103Position, true);
				break;
			}
			case R.id.card_view_zeus_0104:
			{
				dialog = DIALOG_ZEUS_0104;
				String title = getResources().getString(R.string.zeus_0104);
				int resource = R.array.zeus0104;
				startSpinner(title, null, resource, zeus0104Position, true);
				break;
			}
			case R.id.card_view_zeus_ka_01:
			{
				dialog = DIALOG_ZEUS_KA_01;
				String title = getResources().getString(R.string.zeus_ka_01);
				int value = zeusKa01;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_0101:
			{
				dialog = DIALOG_ZEUS_KA_0101;
				String title = getResources().getString(R.string.zeus_ka_0101);
				int value = zeusKa0101;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_0102:
			{
				dialog = DIALOG_ZEUS_KA_0102;
				String title = getResources().getString(R.string.zeus_ka_0102);
				int value = zeusKa0102;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_0103:
			{
				dialog = DIALOG_ZEUS_KA_0103;
				String title = getResources().getString(R.string.zeus_ka_0103);
				int value = zeusKa0103;
				int maxValue = 10;
				startSeekBar(title, null, value, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_02:
			{
				dialog = DIALOG_ZEUS_KA_02;
				String title = getResources().getString(R.string.zeus_ka_02);
				String unit = getResources().getString(R.string.zeus_ka_02_unit);
				String text = zeusKa02;
				int minValue = 0;
				int maxValue = 100;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_03:
			{
				dialog = DIALOG_ZEUS_KA_03;
				String title = getResources().getString(R.string.zeus_ka_03);
				String unit = getResources().getString(R.string.zeus_ka_03_unit);
				String text = zeusKa03;
				int minValue = -100;
				int maxValue = 200;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_04:
			{
				dialog = DIALOG_ZEUS_KA_04;
				String title = getResources().getString(R.string.zeus_ka_04);
				String unit = getResources().getString(R.string.zeus_ka_04_unit);
				String text = zeusKa04;
				int minValue = 0;
				int maxValue = 100;
				startShortEditText(title, text, unit, minValue, maxValue);
				break;
			}
			case R.id.card_view_zeus_ka_05:
			{
				dialog = DIALOG_ZEUS_KA_05;
				String title = getResources().getString(R.string.zeus_ka_05);
				String text = zeusKa05;
				int minValue = 0;
				int maxValue = 100;
				startShortEditText(title, text, null, minValue, maxValue);
				break;
			}
		}
	}

	public void setSeekBar(int value)
	{
		switch (dialog)
		{
			case DIALOG_ZEUS_KA_01:
			{
				zeusKa01 = value;
				sbZeusKa01.setProgress(zeusKa01);
				tvZeusKa01.setText(String.valueOf(zeusKa01));
				break;
			}
			case DIALOG_ZEUS_KA_0101:
			{
				zeusKa0101 = value;
				sbZeusKa0101.setProgress(zeusKa0101);
				tvZeusKa0101.setText(String.valueOf(zeusKa0101));
				break;
			}
			case DIALOG_ZEUS_KA_0102:
			{
				zeusKa0102 = value;
				sbZeusKa0102.setProgress(zeusKa0102);
				tvZeusKa0102.setText(String.valueOf(zeusKa0102));
				break;
			}
			case DIALOG_ZEUS_KA_0103:
			{
				zeusKa0103 = value;
				sbZeusKa0103.setProgress(zeusKa0103);
				tvZeusKa0103.setText(String.valueOf(zeusKa0103));
				break;
			}
		}
	}

	public void setText(String text)
	{
		switch (dialog)
		{
			case DIALOG_ZEUS_KA_02:
			{
				if (text.length() > 0)
				{
					zeusKa02 = text;
					tvZeusKa02.setText(text);
					tvZeusKa02Unit.setVisibility(View.VISIBLE);
				}
				else
				{
					zeusKa02 = "";
					tvZeusKa02.setText("");
					tvZeusKa02Unit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_ZEUS_KA_03:
			{
				if (text.length() > 0)
				{
					zeusKa03 = text;
					tvZeusKa03.setText(text);
					tvZeusKa03Unit.setVisibility(View.VISIBLE);
				}
				else
				{
					zeusKa03 = "";
					tvZeusKa03.setText("");
					tvZeusKa03Unit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_ZEUS_KA_04:
			{
				if (text.length() > 0)
				{
					zeusKa04 = text;
					tvZeusKa04.setText(text);
					tvZeusKa04Unit.setVisibility(View.VISIBLE);
				}
				else
				{
					zeusKa04 = "";
					tvZeusKa04.setText("");
					tvZeusKa04Unit.setVisibility(View.GONE);
				}

				break;
			}
			case DIALOG_ZEUS_KA_05:
			{
				zeusKa05 = text;
				tvZeusKa05.setText(text);
				break;
			}
		}
	}

	public void setSpinner(String text, int position)
	{
		switch (dialog)
		{
			case DIALOG_ZEUS_0101:
			{
				tvZeus0101.setText(text);
				zeus0101Position = position;
				break;
			}
			case DIALOG_ZEUS_0102:
			{
				tvZeus0102.setText(text);
				zeus0102Position = position;
				break;
			}
			case DIALOG_ZEUS_0103:
			{
				tvZeus0103.setText(text);
				zeus0103Position = position;
				break;
			}
			case DIALOG_ZEUS_0104:
			{
				tvZeus0104.setText(text);
				zeus0104Position = position;
				break;
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

	private void startShortEditText(String title, String text, String unit, int minValue, int maxValue)
	{
		ShortEditTextDialogFragment fragment = ShortEditTextDialogFragment.newInstance(title, text, unit, minValue, maxValue);
		fragment.show(getChildFragmentManager(), FRAGMENT_EDIT_TEXT_SHORT);
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent)
	{
		if (motionEvent.getAction() == MotionEvent.ACTION_UP)
		{
			switch (view.getId())
			{
				case R.id.seek_bar_zeus_ka01:
				{
					dialog = DIALOG_ZEUS_KA_01;
					String title = getResources().getString(R.string.zeus_ka_01);
					int value = zeusKa01;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ka0101:
				{
					dialog = DIALOG_ZEUS_KA_0101;
					String title = getResources().getString(R.string.zeus_ka_0101);
					int value = zeusKa0101;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ka0102:
				{
					dialog = DIALOG_ZEUS_KA_0102;
					String title = getResources().getString(R.string.zeus_ka_0102);
					int value = zeusKa0102;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
				case R.id.seek_bar_zeus_ka0103:
				{
					dialog = DIALOG_ZEUS_KA_0103;
					String title = getResources().getString(R.string.zeus_ka_0103);
					int value = zeusKa0103;
					int maxValue = 10;
					startSeekBar(title, null, value, maxValue);
					break;
				}
			}
		}

		return true;
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

	@Override
	public void onEvent(ShortEditTextDialogEvent event)
	{
		String text = event.getText();
		setText(text);
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
}
