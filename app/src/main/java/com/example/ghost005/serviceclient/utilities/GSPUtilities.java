package com.example.ghost005.serviceclient.utilities;

import android.content.Context;
import android.util.Pair;

import org.apache.commons.lang3.SerializationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.ghost005.serviceclient.R;
import com.example.ghost005.serviceclient.model.comparators.WorkStatusLogComparator;
import com.example.ghost005.serviceclient.model.comparators.ZEUSPart1DateComparator;
import com.example.ghost005.serviceclient.model.comparators.ZEUSPart2DateComparator;
import com.example.ghost005.serviceclient.model.enums.ActivityType;
import com.example.ghost005.serviceclient.model.enums.CloudCover;
import com.example.ghost005.serviceclient.model.enums.EnergySource;
import com.example.ghost005.serviceclient.model.enums.MaintenanceLevel;
import com.example.ghost005.serviceclient.model.enums.StatusInfo;
import com.example.ghost005.serviceclient.model.enums.TaskStatus;
import com.example.ghost005.serviceclient.model.enums.TimePaymentType;
import com.example.ghost005.serviceclient.model.enums.TimeType;
import com.example.ghost005.serviceclient.model.enums.UnitMultiplier;
import com.example.ghost005.serviceclient.model.enums.UnitSymbol;
import com.example.ghost005.serviceclient.model.enums.WorkStatus;
import com.example.ghost005.serviceclient.model.enums.ZEUS0101;
import com.example.ghost005.serviceclient.model.enums.ZEUS0102;
import com.example.ghost005.serviceclient.model.enums.ZEUS0103;
import com.example.ghost005.serviceclient.model.enums.ZEUS0104;
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
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.Task;
import com.example.ghost005.serviceclient.model.types.Tasks;
import com.example.ghost005.serviceclient.model.types.WorkOrder;
import com.example.ghost005.serviceclient.model.types.WorkOrderItem;
import com.example.ghost005.serviceclient.model.types.WorkOrderItems;
import com.example.ghost005.serviceclient.model.types.WorkReport;
import com.example.ghost005.serviceclient.model.types.WorkReportItem;
import com.example.ghost005.serviceclient.model.types.WorkReportItems;
import com.example.ghost005.serviceclient.model.types.WorkStatusLog;
import com.example.ghost005.serviceclient.model.types.WorkStatusLogs;
import com.example.ghost005.serviceclient.model.types.ZEUSPart1;
import com.example.ghost005.serviceclient.model.types.ZEUSPart1Assessment;
import com.example.ghost005.serviceclient.model.types.ZEUSPart2;
import com.example.ghost005.serviceclient.model.types.ZEUSPart2Assessment;

/**
 * Utility class for gsp related work.
 */
public class GSPUtilities
{
	public static String getActivityTypeString(Context context, String code)
	{
		ActivityType[] activityTypes = ActivityType.values();
		String[] activityTypeStrings = context.getResources().getStringArray(R.array.activitytype);

		for (int i = 0; i < activityTypes.length; i++)
		{
			if (activityTypes[i].getValue().equals(code))
			{
				return activityTypeStrings[i];
			}
		}

		return null;
	}

	public static Pair<String, Integer> getCloudCover(Context context, String code)
	{
		CloudCover[] cloudCovers = CloudCover.values();
		String[] cloudCoverStrings = context.getResources().getStringArray(R.array.cloudcover);

		for (int i = 0; i < cloudCovers.length; i++)
		{
			if (cloudCovers[i].getValue().equals(code))
			{
				return new Pair<>(cloudCoverStrings[i], i);
			}
		}

		return null;
	}

	public static String getEnergySource(Context context, String code)
	{
		EnergySource[] energySources = EnergySource.values();
		String[] energySourceStrings = context.getResources().getStringArray(R.array.energysource);

		for (int i = 0; i < energySources.length; i++)
		{
			if (energySources[i].getValue().equals(code))
			{
				return energySourceStrings[i];
			}
		}

		return null;
	}

	public static String getMaintenanceLevel(Context context, String code)
	{
		MaintenanceLevel[] maintenanceLevels = MaintenanceLevel.values();
		String[] maintenanceLevelStrings = context.getResources().getStringArray(R.array.maintenancelevel);

		for (int i = 0; i < maintenanceLevels.length; i++)
		{
			if (maintenanceLevels[i].getValue().equals(code))
			{
				return maintenanceLevelStrings[i];
			}
		}

		return null;
	}

	public static Pair<String, Integer> getTaskStatus(Context context, String code)
	{
		TaskStatus[] taskStatuses = TaskStatus.values();
		String[] taskStatusStrings = context.getResources().getStringArray(R.array.taskstatus);

		for (int i = 0; i < taskStatuses.length; i++)
		{
			if (taskStatuses[i].getValue().equals(code))
			{
				return new Pair<>(taskStatusStrings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getTimePaymentType(Context context, String code)
	{
		TimePaymentType[] timePaymentTypes = TimePaymentType.values();
		String[] timePaymentTypeStrings = context.getResources().getStringArray(R.array.timepaymenttype);

		for (int i = 0; i < timePaymentTypes.length; i++)
		{
			if (timePaymentTypes[i].getValue().equals(code))
			{
				return new Pair<>(timePaymentTypeStrings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getTimeType(Context context, String code)
	{
		TimeType[] timeTypes = TimeType.values();
		String[] timeTypeStrings = context.getResources().getStringArray(R.array.timetype);

		for (int i = 0; i < timeTypes.length; i++)
		{
			if (timeTypes[i].getValue().equals(code))
			{
				return new Pair<>(timeTypeStrings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getStatusInfo(Context context, String code)
	{
		StatusInfo[] statusInfos = StatusInfo.values();
		String[] statusInfoStrings = context.getResources().getStringArray(R.array.statusinfo);

		for (int i = 0; i < statusInfos.length; i++)
		{
			if (statusInfos[i].getValue().equals(code))
			{
				return new Pair<>(statusInfoStrings[i], i);
			}
		}

		return null;
	}

	public static String getTimePaymentTypeString(Context context, String code)
	{
		TimePaymentType[] timePaymentTypes = TimePaymentType.values();
		String[] timePaymentTypeStrings = context.getResources().getStringArray(R.array.timepaymenttype);

		for (int i = 0; i < timePaymentTypes.length; i++)
		{
			if (timePaymentTypes[i].getValue().equals(code))
			{
				return timePaymentTypeStrings[i];
			}
		}

		return null;
	}

	public static Pair<String, Integer> getWorkStatus(Context context, String code)
	{
		WorkStatus[] workStatuses = WorkStatus.values();
		String[] workStatusStrings = context.getResources().getStringArray(R.array.workstatus);

		for (int i = 0; i < workStatuses.length; i++)
		{
			if (workStatuses[i].getValue().equals(code))
			{
				return new Pair<>(workStatusStrings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0101(Context context, String code)
	{
		ZEUS0101[] zeus0101s = ZEUS0101.values();
		String[] zeus0101Strings = context.getResources().getStringArray(R.array.zeus0101);

		for (int i = 0; i < zeus0101s.length; i++)
		{
			if (zeus0101s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0101Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0102(Context context, String code)
	{
		ZEUS0102[] zeus0102s = ZEUS0102.values();
		String[] zeus0102Strings = context.getResources().getStringArray(R.array.zeus0102);

		for (int i = 0; i < zeus0102s.length; i++)
		{
			if (zeus0102s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0102Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0103(Context context, String code)
	{
		ZEUS0103[] zeus0103s = ZEUS0103.values();
		String[] zeus0103Strings = context.getResources().getStringArray(R.array.zeus0103);

		for (int i = 0; i < zeus0103s.length; i++)
		{
			if (zeus0103s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0103Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0104(Context context, String code)
	{
		ZEUS0104[] zeus0104s = ZEUS0104.values();
		String[] zeus0104Strings = context.getResources().getStringArray(R.array.zeus0104);

		for (int i = 0; i < zeus0104s.length; i++)
		{
			if (zeus0104s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0104Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0201(Context context, String code)
	{
		ZEUS0201[] zeus0201s = ZEUS0201.values();
		String[] zeus0201Strings = context.getResources().getStringArray(R.array.zeus0201);

		for (int i = 0; i < zeus0201s.length; i++)
		{
			if (zeus0201s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0201Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0202(Context context, String code)
	{
		ZEUS0202[] zeus0202s = ZEUS0202.values();
		String[] zeus0202Strings = context.getResources().getStringArray(R.array.zeus0202);

		for (int i = 0; i < zeus0202s.length; i++)
		{
			if (zeus0202s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0202Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0203(Context context, String code)
	{
		ZEUS0203[] zeus0203s = ZEUS0203.values();
		String[] zeus0203Strings = context.getResources().getStringArray(R.array.zeus0203);

		for (int i = 0; i < zeus0203s.length; i++)
		{
			if (zeus0203s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0203Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0204(Context context, String code)
	{
		ZEUS0204[] zeus0204s = ZEUS0204.values();
		String[] zeus0204Strings = context.getResources().getStringArray(R.array.zeus0204);

		for (int i = 0; i < zeus0204s.length; i++)
		{
			if (zeus0204s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0204Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0205(Context context, String code)
	{
		ZEUS0205[] zeus0205s = ZEUS0205.values();
		String[] zeus0205Strings = context.getResources().getStringArray(R.array.zeus0205);

		for (int i = 0; i < zeus0205s.length; i++)
		{
			if (zeus0205s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0205Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0206(Context context, String code)
	{
		ZEUS0206[] zeus0206s = ZEUS0206.values();
		String[] zeus0206Strings = context.getResources().getStringArray(R.array.zeus0206);

		for (int i = 0; i < zeus0206s.length; i++)
		{
			if (zeus0206s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0206Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0207(Context context, String code)
	{
		ZEUS0207[] zeus0207s = ZEUS0207.values();
		String[] zeus0207Strings = context.getResources().getStringArray(R.array.zeus0207);

		for (int i = 0; i < zeus0207s.length; i++)
		{
			if (zeus0207s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0207Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0208(Context context, String code)
	{
		ZEUS0208[] zeus0208s = ZEUS0208.values();
		String[] zeus0208Strings = context.getResources().getStringArray(R.array.zeus0208);

		for (int i = 0; i < zeus0208s.length; i++)
		{
			if (zeus0208s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0208Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0209(Context context, String code)
	{
		ZEUS0209[] zeus0209s = ZEUS0209.values();
		String[] zeus0209Strings = context.getResources().getStringArray(R.array.zeus0209);

		for (int i = 0; i < zeus0209s.length; i++)
		{
			if (zeus0209s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0209Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0210(Context context, String code)
	{
		ZEUS0210[] zeus0210s = ZEUS0210.values();
		String[] zeus0210Strings = context.getResources().getStringArray(R.array.zeus0210);

		for (int i = 0; i < zeus0210s.length; i++)
		{
			if (zeus0210s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0210Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0211(Context context, String code)
	{
		ZEUS0211[] zeus0211s = ZEUS0211.values();
		String[] zeus0211Strings = context.getResources().getStringArray(R.array.zeus0211);

		for (int i = 0; i < zeus0211s.length; i++)
		{
			if (zeus0211s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0211Strings[i], i);
			}
		}

		return null;
	}

	public static Pair<String, Integer> getZeus0212(Context context, String code)
	{
		ZEUS0212[] zeus0212s = ZEUS0212.values();
		String[] zeus0212Strings = context.getResources().getStringArray(R.array.zeus0212);

		for (int i = 0; i < zeus0212s.length; i++)
		{
			if (zeus0212s[i].getValue().equals(code))
			{
				return new Pair<>(zeus0212Strings[i], i);
			}
		}

		return null;
	}

	public static String generalValueToString(Context context, GeneralValue generalValue)
	{
		BigDecimal value = generalValue.getValue();
		String multiplier = generalValue.getMultiplier();
		String symbol = generalValue.getSymbol();

		StringBuilder generalValueStringBuilder = new StringBuilder();

		UnitMultiplier[] unitMultiplier = UnitMultiplier.values();
		String[] unitMultiplierStrings = context.getResources().getStringArray(
				R.array.unitmultiplier);

		for (int i = 0; i < unitMultiplier.length; i++)
		{
			if (unitMultiplier[i].getValue().equals(multiplier))
			{
				String multiplierString = unitMultiplierStrings[i];

				if (multiplier.contains("*"))
				{
					String powerString = multiplierString.substring(
							multiplierString.lastIndexOf("*"), multiplierString.length());

					Integer power = Integer.parseInt(powerString);
					BigDecimal ten = new BigDecimal(10);

					value = value.multiply(ten.pow(power));

					break;
				}
			}
		}

		generalValueStringBuilder.append(value);

		UnitSymbol[] unitSymbols = UnitSymbol.values();
		String[] unitSymbolStrings = context.getResources().getStringArray(R.array.unitsymbol);

		for (int i = 0; i < unitSymbols.length; i++)
		{
			if (unitSymbols[i].getValue().equals(symbol))
			{
				generalValueStringBuilder.append(" ").append(unitSymbolStrings[i]);

				break;
			}
		}

		return generalValueStringBuilder.toString();
	}

	public static WorkReport createWorkReport(WorkOrder workOrder)
	{
		WorkReport workReport = new WorkReport();
		workReport.setId(workOrder.getId());
		workReport.setName(workOrder.getName());
		workReport.setResponsibleEmployee(SerializationUtils.clone(workOrder.getResponsibleEmployee()));
		workReport.setStaff(SerializationUtils.clone(workOrder.getStaff()));

		WorkStatusLogs workStatusLogs = new WorkStatusLogs();
		List<WorkStatusLog> workStatusLogList = new ArrayList<>();
		WorkStatusLog workStatusLog = SerializationUtils.clone(workOrder.getStatus());
		workStatusLog.setWorkStatusLogs(workStatusLogs);
		workStatusLogList.add(workStatusLog);
		workStatusLogs.setWorkStatusLogs(workStatusLogList);
		workReport.setStatuses(workStatusLogs);

		ZEUSPart1Assessment zeusPart1Assessment = new ZEUSPart1Assessment();
		List<ZEUSPart1> zeusPart1List = new ArrayList<>();
		ZEUSPart1 zeusPart1 = SerializationUtils.clone(Collections.max(workOrder.getZeusPart1History().getZeusPart1(), new ZEUSPart1DateComparator()));
		zeusPart1.setZeusPart1Assessment(zeusPart1Assessment);
		zeusPart1List.add(zeusPart1);
		zeusPart1Assessment.setZeusPart1(zeusPart1List);
		workReport.setZeusPart1Assessment(zeusPart1Assessment);

		Attachments attachments = SerializationUtils.clone(workOrder.getAttachments());
		workReport.setAttachments(attachments);

		WorkReportItems workReportItems = new WorkReportItems();
		List<WorkReportItem> workReportItemList = new ArrayList<>();
		WorkOrderItems workOrderItems = workOrder.getItems();

		int count = 0;

		for (WorkOrderItem workOrderItem : workOrderItems.getWorkOrderItems())
		{
			WorkReportItem workReportItem = createWorkReportItem(workOrderItem, count);
			workReportItem.setWorkReportItems(workReportItems);
			workReportItemList.add(workReportItem);
			count++;
		}

		workReportItems.setWorkReportItems(workReportItemList);
		workReport.setItems(workReportItems);

		return workReport;
	}

	public static WorkReportItem createWorkReportItem(WorkOrderItem workOrderItem, int id)
	{
		WorkReportItem workReportItem = new WorkReportItem();
		workReportItem.setId(String.valueOf(id));
		workReportItem.setOrderItemId(workOrderItem.getId());
		workReportItem.setName(workOrderItem.getName());
		workReportItem.setLongDescription(workOrderItem.getLongDescription());
		workReportItem.setAssignedElement(SerializationUtils.clone(workOrderItem.getAssignedElement()));
		workReportItem.setEquipments(SerializationUtils.clone(workOrderItem.getEquipments()));
		workReportItem.setMaterials(SerializationUtils.clone(workOrderItem.getMaterials()));
		workReportItem.setStaff(SerializationUtils.clone(workOrderItem.getStaff()));
		workReportItem.setWorkEquipments(SerializationUtils.clone(workOrderItem.getWorkEquipments()));
		workReportItem.setAttachments(SerializationUtils.clone(workOrderItem.getAttachments()));

		WorkStatusLogs workStatusLogs = new WorkStatusLogs();
		List<WorkStatusLog> workStatusLogList = new ArrayList<>();
		WorkStatusLog workStatusLog = SerializationUtils.clone(Collections.max(workOrderItem.getStatuses().getWorkStatusLogs(), new WorkStatusLogComparator()));
		workStatusLog.setWorkStatusLogs(workStatusLogs);
		workStatusLogList.add(workStatusLog);
		workStatusLogs.setWorkStatusLogs(workStatusLogList);
		workReportItem.setStatuses(workStatusLogs);

		ZEUSPart2Assessment zeusPart2Assessment = new ZEUSPart2Assessment();
		List<ZEUSPart2> zeusPart2List = new ArrayList<>();
		ZEUSPart2 zeusPart2 = SerializationUtils.clone(Collections.max(workOrderItem.getZeusPart2History().getZeusPart2(), new ZEUSPart2DateComparator()));
		zeusPart2.setZeusPart2Assessment(zeusPart2Assessment);
		zeusPart2List.add(zeusPart2);
		zeusPart2Assessment.setZeusPart2(zeusPart2List);
		workReportItem.setZeusPart2Assessment(zeusPart2Assessment);

		Tasks tasks = new Tasks();
		List<Task> taskList = new ArrayList<>();

		if (workOrderItem.getTasks() != null)
		{
			for (Task task : workOrderItem.getTasks().getTasks())
			{
				Task workReportItemTask = createTask(task);
				workReportItemTask.setTasks(tasks);
				taskList.add(workReportItemTask);
			}

			tasks.setTasks(taskList);
			workReportItem.setTasks(tasks);
		}

		return workReportItem;
	}

	public static Task createTask(Task workOrderITemTask)
	{
		Task task = new Task();
		task.setId(workOrderITemTask.getId());
		task.setName(workOrderITemTask.getName());
		task.setWorkEquipments(SerializationUtils.clone(workOrderITemTask.getWorkEquipments()));
		task.setStatus(SerializationUtils.clone(workOrderITemTask.getStatus()));
		task.setStatus(workOrderITemTask.getStatus());
		task.setDescription(workOrderITemTask.getDescription());
		task.setType(workOrderITemTask.getType());

		return task;
	}

	public static Employee copyEmployee(Employee employee)
	{
		Employee newEmployee = SerializationUtils.clone(employee);
		employee.set_id(0);

		return newEmployee;
	}
}
