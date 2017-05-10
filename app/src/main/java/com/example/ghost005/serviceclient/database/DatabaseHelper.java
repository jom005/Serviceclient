package com.example.ghost005.serviceclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import com.example.ghost005.serviceclient.model.types.ApplierElement;
import com.example.ghost005.serviceclient.model.types.Assessment;
import com.example.ghost005.serviceclient.model.types.AssignedElement;
import com.example.ghost005.serviceclient.model.types.Attachment;
import com.example.ghost005.serviceclient.model.types.Attachments;
import com.example.ghost005.serviceclient.model.types.Comment;
import com.example.ghost005.serviceclient.model.types.Comments;
import com.example.ghost005.serviceclient.model.types.ElectronicAddress;
import com.example.ghost005.serviceclient.model.types.Employee;
import com.example.ghost005.serviceclient.model.types.Employees;
import com.example.ghost005.serviceclient.model.types.EnergySystem;
import com.example.ghost005.serviceclient.model.types.EnvironmentalCondition;
import com.example.ghost005.serviceclient.model.types.EnvironmentalConditions;
import com.example.ghost005.serviceclient.model.types.Equipment;
import com.example.ghost005.serviceclient.model.types.EquipmentInformation;
import com.example.ghost005.serviceclient.model.types.EquipmentList;
import com.example.ghost005.serviceclient.model.types.EquipmentListEntry;
import com.example.ghost005.serviceclient.model.types.Equipments;
import com.example.ghost005.serviceclient.model.types.File;
import com.example.ghost005.serviceclient.model.types.FileLocation;
import com.example.ghost005.serviceclient.model.types.Files;
import com.example.ghost005.serviceclient.model.types.GSPInfo;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.GeographicLocation;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Location;
import com.example.ghost005.serviceclient.model.types.LogicalNodeInformation;
import com.example.ghost005.serviceclient.model.types.Material;
import com.example.ghost005.serviceclient.model.types.Materials;
import com.example.ghost005.serviceclient.model.types.Measurement;
import com.example.ghost005.serviceclient.model.types.MeasurementSeries;
import com.example.ghost005.serviceclient.model.types.MeasurementValue;
import com.example.ghost005.serviceclient.model.types.Measurements;
import com.example.ghost005.serviceclient.model.types.MeasuringEquipment;
import com.example.ghost005.serviceclient.model.types.MeasuringInstallation;
import com.example.ghost005.serviceclient.model.types.Organisation;
import com.example.ghost005.serviceclient.model.types.Parameter;
import com.example.ghost005.serviceclient.model.types.ParameterValue;
import com.example.ghost005.serviceclient.model.types.Person;
import com.example.ghost005.serviceclient.model.types.Persons;
import com.example.ghost005.serviceclient.model.types.PostalAddress;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.PriorityEntry;
import com.example.ghost005.serviceclient.model.types.PriorityLog;
import com.example.ghost005.serviceclient.model.types.RDSPPElement;
import com.example.ghost005.serviceclient.model.types.RDSPPStructure;
import com.example.ghost005.serviceclient.model.types.Skill;
import com.example.ghost005.serviceclient.model.types.Skills;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.StreetDetail;
import com.example.ghost005.serviceclient.model.types.Task;
import com.example.ghost005.serviceclient.model.types.TaskStatusLog;
import com.example.ghost005.serviceclient.model.types.Tasks;
import com.example.ghost005.serviceclient.model.types.TelephoneNumber;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.model.types.TimeReports;
import com.example.ghost005.serviceclient.model.types.TownDetail;
import com.example.ghost005.serviceclient.model.types.TransportProcess;
import com.example.ghost005.serviceclient.model.types.TransportProcesses;
import com.example.ghost005.serviceclient.model.types.UserSpecificContents;
import com.example.ghost005.serviceclient.model.types.UserSpecificParameterSet;
import com.example.ghost005.serviceclient.model.types.WorkEquipment;
import com.example.ghost005.serviceclient.model.types.WorkEquipments;
import com.example.ghost005.serviceclient.model.types.WorkLog;
import com.example.ghost005.serviceclient.model.types.WorkLogEntry;
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
 * Database helper for the gsp database.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private static final String DATABASE_NAME = "gsp_database.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<GSPDocument, Integer> gspDocumentDao = null;

	private Dao<ApplierElement, Integer> applierElementDao = null;
	private Dao<Assessment, Integer> assessmentDao = null;
	private Dao<AssignedElement, Integer> assignedElementDao = null;
	private Dao<Attachment, Integer> attachmentDao = null;
	private Dao<Attachments, Integer> attachmentsDao = null;
	private Dao<Comment, Integer> commentDao = null;
	private Dao<Comments, Integer> commentsDao = null;
	private Dao<ElectronicAddress, Integer> electronicAddressDao = null;
	private Dao<Employee, Integer> employeeDao = null;
	private Dao<Employees, Integer> employeesDao = null;
	private Dao<EnergySystem, Integer> energySystemDao = null;
	private Dao<EnvironmentalCondition, Integer> environmentalConditionDao = null;
	private Dao<EnvironmentalConditions, Integer> environmentalConditionsDao = null;
	private Dao<Equipment, Integer> equipmentDao = null;
	private Dao<EquipmentInformation, Integer> equipmentInformationDao = null;
	private Dao<EquipmentList, Integer> equipmentListDao = null;
	private Dao<EquipmentListEntry, Integer> equipmentListEntryDao = null;
	private Dao<Equipments, Integer> equipmentsDao = null;
	private Dao<File, Integer> fileDao = null;
	private Dao<FileLocation, Integer> fileLocationDao = null;
	private Dao<Files, Integer> filesDao = null;
	private Dao<GeneralValue, Integer> generalValueDao = null;
	private Dao<GeographicLocation, Integer> geographicLocationDao = null;
	private Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = null;
	private Dao<GSPInfo, Integer> gspInfoDao = null;
	private Dao<Location, Integer> locationDao = null;
	private Dao<LogicalNodeInformation, Integer> logicalNodeInformationDao = null;
	private Dao<Material, Integer> materialDao = null;
	private Dao<Materials, Integer> materialsDao = null;
	private Dao<Measurement, Integer> measurementDao = null;
	private Dao<Measurements, Integer> measurementsDao = null;
	private Dao<MeasurementSeries, Integer> measurementSeriesDao = null;
	private Dao<MeasurementValue, Integer> measurementValueDao = null;
	private Dao<MeasuringEquipment, Integer> measuringEquipmentDao = null;
	private Dao<MeasuringInstallation, Integer> measuringInstallationDao = null;
	private Dao<Organisation, Integer> organisationDao = null;
	private Dao<Parameter, Integer> parameterDao = null;
	private Dao<ParameterValue, Integer> parameterValueDao = null;
	private Dao<Person, Integer> personDao = null;
	private Dao<Persons, Integer> personsDao = null;
	private Dao<PostalAddress, Integer> postalAddressDao = null;
	private Dao<PowerPlant, Integer> powerPlantDao = null;
	private Dao<PriorityEntry, Integer> priorityEntryDao = null;
	private Dao<PriorityLog, Integer> priorityLogDao = null;
	private Dao<RDSPPElement, Integer> rdsppElementDao = null;
	private Dao<RDSPPStructure, Integer> rdsppStructureDao = null;
	private Dao<Skill, Integer> skillDao = null;
	private Dao<Skills, Integer> skillsDao = null;
	private Dao<StreetAddress, Integer> streetAddressDao = null;
	private Dao<StreetDetail, Integer> streetDetailDao = null;
	private Dao<Task, Integer> taskDao = null;
	private Dao<Tasks, Integer> tasksDao = null;
	private Dao<TaskStatusLog, Integer> taskStatusLogDao = null;
	private Dao<TelephoneNumber, Integer> telephoneNumberDao = null;
	private Dao<TimeReport, Integer> timeReportDao = null;
	private Dao<TimeReports, Integer> timeReportsDao = null;
	private Dao<TownDetail, Integer> townDetailDao = null;
	private Dao<TransportProcess, Integer> transportProcessDao = null;
	private Dao<TransportProcesses, Integer> transportProcessesDao = null;
	private Dao<UserSpecificContents, Integer> userSpecificContentsDao = null;
	private Dao<UserSpecificParameterSet, Integer> userSpecificParameterSetDao = null;
	private Dao<WorkEquipment, Integer> workEquipmentDao = null;
	private Dao<WorkEquipments, Integer> workEquipmentsDao = null;
	private Dao<WorkLog, Integer> workLogDao = null;
	private Dao<WorkLogEntry, Integer> workLogEntryDao = null;
	private Dao<WorkOrder, Integer> workOrderDao = null;
	private Dao<WorkOrderItem, Integer> workOrderItemDao = null;
	private Dao<WorkOrderItems, Integer> workOrderItemsDao = null;
	private Dao<WorkReport, Integer> workReportDao = null;
	private Dao<WorkReportItem, Integer> workReportItemDao = null;
	private Dao<WorkReportItems, Integer> workReportItemsDao = null;
	private Dao<WorkStatusLog, Integer> workStatusLogDao = null;
	private Dao<WorkStatusLogs, Integer> workStatusLogsDao = null;
	private Dao<ZEUSPart1, Integer> zeusPart1Dao = null;
	private Dao<ZEUSPart1Assessment, Integer> zeusPart1AssessmentDao = null;
	private Dao<ZEUSPart2, Integer> zeusPart2Dao = null;
	private Dao<ZEUSPart2Assessment, Integer> zeusPart2AssessmentDao = null;

	/**
	 * Constructor sets context, database name and version
	 *
	 * @param context application context
	 */
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
	{
		try
		{
			TableUtils.createTableIfNotExists(connectionSource, Attachment.class);
			TableUtils.createTableIfNotExists(connectionSource, Attachments.class);
			TableUtils.createTableIfNotExists(connectionSource, Comment.class);
			TableUtils.createTableIfNotExists(connectionSource, Comments.class);
			TableUtils.createTableIfNotExists(connectionSource, Employee.class);
			TableUtils.createTableIfNotExists(connectionSource, Employees.class);
			TableUtils.createTableIfNotExists(connectionSource, EnvironmentalCondition.class);
			TableUtils.createTableIfNotExists(connectionSource, EnvironmentalConditions.class);
			TableUtils.createTableIfNotExists(connectionSource, EquipmentListEntry.class);
			TableUtils.createTableIfNotExists(connectionSource, EquipmentList.class);
			TableUtils.createTableIfNotExists(connectionSource, Equipment.class);
			TableUtils.createTableIfNotExists(connectionSource, Equipments.class);
			TableUtils.createTableIfNotExists(connectionSource, File.class);
			TableUtils.createTableIfNotExists(connectionSource, Files.class);
			TableUtils.createTableIfNotExists(connectionSource, GSPInfo.class);
			TableUtils.createTableIfNotExists(connectionSource, Material.class);
			TableUtils.createTableIfNotExists(connectionSource, Materials.class);
			TableUtils.createTableIfNotExists(connectionSource, Measurement.class);
			TableUtils.createTableIfNotExists(connectionSource, Measurements.class);
			TableUtils.createTableIfNotExists(connectionSource, MeasurementSeries.class);
			TableUtils.createTableIfNotExists(connectionSource, MeasurementValue.class);
			TableUtils.createTableIfNotExists(connectionSource, Person.class);
			TableUtils.createTableIfNotExists(connectionSource, Persons.class);
			TableUtils.createTableIfNotExists(connectionSource, PriorityEntry.class);
			TableUtils.createTableIfNotExists(connectionSource, PriorityLog.class);
			TableUtils.createTableIfNotExists(connectionSource, RDSPPElement.class);
			TableUtils.createTableIfNotExists(connectionSource, RDSPPStructure.class);
			TableUtils.createTableIfNotExists(connectionSource, Skill.class);
			TableUtils.createTableIfNotExists(connectionSource, Skills.class);
			TableUtils.createTableIfNotExists(connectionSource, Task.class);
			TableUtils.createTableIfNotExists(connectionSource, Tasks.class);
			TableUtils.createTableIfNotExists(connectionSource, TimeReport.class);
			TableUtils.createTableIfNotExists(connectionSource, TimeReports.class);
			TableUtils.createTableIfNotExists(connectionSource, TransportProcess.class);
			TableUtils.createTableIfNotExists(connectionSource, TransportProcesses.class);
			TableUtils.createTableIfNotExists(connectionSource, UserSpecificParameterSet.class);
			TableUtils.createTableIfNotExists(connectionSource, UserSpecificContents.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkEquipment.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkEquipments.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkLogEntry.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkLog.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkOrderItem.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkOrderItems.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkReportItem.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkReportItems.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkStatusLog.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkStatusLogs.class);
			TableUtils.createTableIfNotExists(connectionSource, ZEUSPart1.class);
			TableUtils.createTableIfNotExists(connectionSource, ZEUSPart1Assessment.class);
			TableUtils.createTableIfNotExists(connectionSource, ZEUSPart2.class);
			TableUtils.createTableIfNotExists(connectionSource, ZEUSPart2Assessment.class);
			TableUtils.createTableIfNotExists(connectionSource, ApplierElement.class);
			TableUtils.createTableIfNotExists(connectionSource, Assessment.class);
			TableUtils.createTableIfNotExists(connectionSource, AssignedElement.class);
			TableUtils.createTableIfNotExists(connectionSource, ElectronicAddress.class);
			TableUtils.createTableIfNotExists(connectionSource, EnergySystem.class);
			TableUtils.createTableIfNotExists(connectionSource, EquipmentInformation.class);
			TableUtils.createTableIfNotExists(connectionSource, FileLocation.class);
			TableUtils.createTableIfNotExists(connectionSource, GeneralValue.class);
			TableUtils.createTableIfNotExists(connectionSource, GeographicLocation.class);
			TableUtils.createTableIfNotExists(connectionSource, GlobalServiceProtocol.class);
			TableUtils.createTableIfNotExists(connectionSource, Location.class);
			TableUtils.createTableIfNotExists(connectionSource, LogicalNodeInformation.class);
			TableUtils.createTableIfNotExists(connectionSource, MeasuringEquipment.class);
			TableUtils.createTableIfNotExists(connectionSource, MeasuringInstallation.class);
			TableUtils.createTableIfNotExists(connectionSource, Organisation.class);
			TableUtils.createTableIfNotExists(connectionSource, Parameter.class);
			TableUtils.createTableIfNotExists(connectionSource, ParameterValue.class);
			TableUtils.createTableIfNotExists(connectionSource, PostalAddress.class);
			TableUtils.createTableIfNotExists(connectionSource, PowerPlant.class);
			TableUtils.createTableIfNotExists(connectionSource, StreetAddress.class);
			TableUtils.createTableIfNotExists(connectionSource, StreetDetail.class);
			TableUtils.createTableIfNotExists(connectionSource, TaskStatusLog.class);
			TableUtils.createTableIfNotExists(connectionSource, TelephoneNumber.class);
			TableUtils.createTableIfNotExists(connectionSource, TownDetail.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkOrder.class);
			TableUtils.createTableIfNotExists(connectionSource, WorkReport.class);
			TableUtils.createTableIfNotExists(connectionSource, GSPDocument.class);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		try
		{
			TableUtils.dropTable(connectionSource, Attachment.class, true);
			TableUtils.dropTable(connectionSource, Attachments.class, true);
			TableUtils.dropTable(connectionSource, Comment.class, true);
			TableUtils.dropTable(connectionSource, Comments.class, true);
			TableUtils.dropTable(connectionSource, Employee.class, true);
			TableUtils.dropTable(connectionSource, Employees.class, true);
			TableUtils.dropTable(connectionSource, EnvironmentalCondition.class, true);
			TableUtils.dropTable(connectionSource, EnvironmentalConditions.class, true);
			TableUtils.dropTable(connectionSource, EquipmentListEntry.class, true);
			TableUtils.dropTable(connectionSource, EquipmentList.class, true);
			TableUtils.dropTable(connectionSource, Equipment.class, true);
			TableUtils.dropTable(connectionSource, Equipments.class, true);
			TableUtils.dropTable(connectionSource, File.class, true);
			TableUtils.dropTable(connectionSource, Files.class, true);
			TableUtils.dropTable(connectionSource, GSPInfo.class, true);
			TableUtils.dropTable(connectionSource, Material.class, true);
			TableUtils.dropTable(connectionSource, Materials.class, true);
			TableUtils.dropTable(connectionSource, Measurement.class, true);
			TableUtils.dropTable(connectionSource, Measurements.class, true);
			TableUtils.dropTable(connectionSource, MeasurementSeries.class, true);
			TableUtils.dropTable(connectionSource, MeasurementValue.class, true);
			TableUtils.dropTable(connectionSource, Person.class, true);
			TableUtils.dropTable(connectionSource, Persons.class, true);
			TableUtils.dropTable(connectionSource, PriorityEntry.class, true);
			TableUtils.dropTable(connectionSource, PriorityLog.class, true);
			TableUtils.dropTable(connectionSource, RDSPPElement.class, true);
			TableUtils.dropTable(connectionSource, RDSPPStructure.class, true);
			TableUtils.dropTable(connectionSource, Skill.class, true);
			TableUtils.dropTable(connectionSource, Skills.class, true);
			TableUtils.dropTable(connectionSource, Task.class, true);
			TableUtils.dropTable(connectionSource, Tasks.class, true);
			TableUtils.dropTable(connectionSource, TimeReport.class, true);
			TableUtils.dropTable(connectionSource, TimeReports.class, true);
			TableUtils.dropTable(connectionSource, TransportProcess.class, true);
			TableUtils.dropTable(connectionSource, TransportProcesses.class, true);
			TableUtils.dropTable(connectionSource, UserSpecificParameterSet.class, true);
			TableUtils.dropTable(connectionSource, UserSpecificContents.class, true);
			TableUtils.dropTable(connectionSource, WorkEquipment.class, true);
			TableUtils.dropTable(connectionSource, WorkEquipments.class, true);
			TableUtils.dropTable(connectionSource, WorkLogEntry.class, true);
			TableUtils.dropTable(connectionSource, WorkLog.class, true);
			TableUtils.dropTable(connectionSource, WorkOrderItem.class, true);
			TableUtils.dropTable(connectionSource, WorkOrderItems.class, true);
			TableUtils.dropTable(connectionSource, WorkReportItem.class, true);
			TableUtils.dropTable(connectionSource, WorkReportItems.class, true);
			TableUtils.dropTable(connectionSource, WorkStatusLog.class, true);
			TableUtils.dropTable(connectionSource, WorkStatusLogs.class, true);
			TableUtils.dropTable(connectionSource, ZEUSPart1.class, true);
			TableUtils.dropTable(connectionSource, ZEUSPart1Assessment.class, true);
			TableUtils.dropTable(connectionSource, ZEUSPart2.class, true);
			TableUtils.dropTable(connectionSource, ZEUSPart2Assessment.class, true);
			TableUtils.dropTable(connectionSource, ApplierElement.class, true);
			TableUtils.dropTable(connectionSource, Assessment.class, true);
			TableUtils.dropTable(connectionSource, AssignedElement.class, true);
			TableUtils.dropTable(connectionSource, ElectronicAddress.class, true);
			TableUtils.dropTable(connectionSource, EnergySystem.class, true);
			TableUtils.dropTable(connectionSource, EquipmentInformation.class, true);
			TableUtils.dropTable(connectionSource, FileLocation.class, true);
			TableUtils.dropTable(connectionSource, GeneralValue.class, true);
			TableUtils.dropTable(connectionSource, GeographicLocation.class, true);
			TableUtils.dropTable(connectionSource, GlobalServiceProtocol.class, true);
			TableUtils.dropTable(connectionSource, Location.class, true);
			TableUtils.dropTable(connectionSource, LogicalNodeInformation.class, true);
			TableUtils.dropTable(connectionSource, MeasuringEquipment.class, true);
			TableUtils.dropTable(connectionSource, MeasuringInstallation.class, true);
			TableUtils.dropTable(connectionSource, Organisation.class, true);
			TableUtils.dropTable(connectionSource, Parameter.class, true);
			TableUtils.dropTable(connectionSource, ParameterValue.class, true);
			TableUtils.dropTable(connectionSource, PostalAddress.class, true);
			TableUtils.dropTable(connectionSource, PowerPlant.class, true);
			TableUtils.dropTable(connectionSource, StreetAddress.class, true);
			TableUtils.dropTable(connectionSource, StreetDetail.class, true);
			TableUtils.dropTable(connectionSource, TaskStatusLog.class, true);
			TableUtils.dropTable(connectionSource, TelephoneNumber.class, true);
			TableUtils.dropTable(connectionSource, TownDetail.class, true);
			TableUtils.dropTable(connectionSource, WorkOrder.class, true);
			TableUtils.dropTable(connectionSource, WorkReport.class, true);
			TableUtils.dropTable(connectionSource, GSPDocument.class, true);

			onCreate(database, connectionSource);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Closes the database connection.
	 */
	@Override
	public void close()
	{
		super.close();

		applierElementDao = null;
		assessmentDao = null;
		assignedElementDao = null;
		attachmentDao = null;
		attachmentsDao = null;
		commentDao = null;
		commentsDao = null;
		electronicAddressDao = null;
		employeeDao = null;
		employeesDao = null;
		energySystemDao = null;
		environmentalConditionDao = null;
		environmentalConditionsDao = null;
		equipmentDao = null;
		equipmentInformationDao = null;
		equipmentListDao = null;
		equipmentListEntryDao = null;
		equipmentsDao = null;
		fileDao = null;
		fileLocationDao = null;
		filesDao = null;
		generalValueDao = null;
		geographicLocationDao = null;
		globalServiceProtocolDao = null;
		gspInfoDao = null;
		locationDao = null;
		logicalNodeInformationDao = null;
		materialDao = null;
		materialsDao = null;
		measurementDao = null;
		measurementsDao = null;
		measurementSeriesDao = null;
		measurementValueDao = null;
		measuringEquipmentDao = null;
		measuringInstallationDao = null;
		organisationDao = null;
		parameterDao = null;
		parameterValueDao = null;
		personDao = null;
		personsDao = null;
		postalAddressDao = null;
		powerPlantDao = null;
		priorityEntryDao = null;
		priorityLogDao = null;
		rdsppElementDao = null;
		rdsppStructureDao = null;
		skillDao = null;
		skillsDao = null;
		streetAddressDao = null;
		streetDetailDao = null;
		taskDao = null;
		tasksDao = null;
		taskStatusLogDao = null;
		telephoneNumberDao = null;
		timeReportDao = null;
		timeReportsDao = null;
		townDetailDao = null;
		transportProcessDao = null;
		transportProcessesDao = null;
		userSpecificContentsDao = null;
		userSpecificParameterSetDao = null;
		workEquipmentDao = null;
		workEquipmentsDao = null;
		workLogDao = null;
		workLogEntryDao = null;
		workOrderDao = null;
		workOrderItemDao = null;
		workOrderItemsDao = null;
		workReportDao = null;
		workReportItemDao = null;
		workReportItemsDao = null;
		workStatusLogDao = null;
		workStatusLogsDao = null;
		zeusPart1Dao = null;
		zeusPart1AssessmentDao = null;
		zeusPart2Dao = null;
		zeusPart2AssessmentDao = null;
	}

	public Dao<GSPDocument, Integer> getGspDocumentDao() throws SQLException
	{
		if (gspDocumentDao == null)
		{
			gspDocumentDao = getDao(GSPDocument.class);
		}

		return gspDocumentDao;
	}

	public Dao<ApplierElement, Integer> getApplierElementDao() throws SQLException
	{
		if (applierElementDao == null)
		{
			applierElementDao = getDao(ApplierElement.class);
		}

		return applierElementDao;
	}

	public Dao<Assessment, Integer> getAssessmentDao() throws SQLException
	{
		if (assessmentDao == null)
		{
			assessmentDao = getDao(Assessment.class);
		}

		return assessmentDao;
	}

	public Dao<AssignedElement, Integer> getAssignedElementDao() throws SQLException
	{
		if (assignedElementDao == null)
		{
			assignedElementDao = getDao(AssignedElement.class);
		}

		return assignedElementDao;
	}

	public Dao<Attachment, Integer> getAttachmentDao() throws SQLException
	{
		if (attachmentDao == null)
		{
			attachmentDao = getDao(Attachment.class);
		}

		return attachmentDao;
	}

	public Dao<Attachments, Integer> getAttachmentsDao() throws SQLException
	{
		if (attachmentsDao == null)
		{
			attachmentsDao = getDao(Attachments.class);
		}

		return attachmentsDao;
	}

	public Dao<Comment, Integer> getCommentDao() throws SQLException
	{
		if (commentDao == null)
		{
			commentDao = getDao(Comment.class);
		}

		return commentDao;
	}

	public Dao<Comments, Integer> getCommentsDao() throws SQLException
	{
		if (commentsDao == null)
		{
			commentsDao = getDao(Comments.class);
		}

		return commentsDao;
	}

	public Dao<ElectronicAddress, Integer> getElectronicAddressDao() throws SQLException
	{
		if (electronicAddressDao == null)
		{
			electronicAddressDao = getDao(ElectronicAddress.class);
		}

		return electronicAddressDao;
	}

	public Dao<Employee, Integer> getEmployeeDao() throws SQLException
	{
		if (employeeDao == null)
		{
			employeeDao = getDao(Employee.class);
		}

		return employeeDao;
	}

	public Dao<Employees, Integer> getEmployeesDao() throws SQLException
	{
		if (employeesDao == null)
		{
			employeesDao = getDao(Employees.class);
		}

		return employeesDao;
	}

	public Dao<EnergySystem, Integer> getEnergySystemDao() throws SQLException
	{
		if (energySystemDao == null)
		{
			energySystemDao = getDao(EnergySystem.class);
		}

		return energySystemDao;
	}

	public Dao<EnvironmentalCondition, Integer> getEnvironmentalConditionDao() throws SQLException
	{
		if (environmentalConditionDao == null)
		{
			environmentalConditionDao = getDao(EnvironmentalCondition.class);
		}

		return environmentalConditionDao;
	}

	public Dao<EnvironmentalConditions, Integer> getEnvironmentalConditionsDao() throws SQLException
	{
		if (environmentalConditionsDao == null)
		{
			environmentalConditionsDao = getDao(EnvironmentalConditions.class);
		}

		return environmentalConditionsDao;
	}

	public Dao<Equipment, Integer> getEquipmentDao() throws SQLException
	{
		if (equipmentDao == null)
		{
			equipmentDao = getDao(Equipment.class);
		}

		return equipmentDao;
	}

	public Dao<EquipmentInformation, Integer> getEquipmentInformationDao() throws SQLException
	{
		if (equipmentInformationDao == null)
		{
			equipmentInformationDao = getDao(EquipmentInformation.class);
		}

		return equipmentInformationDao;
	}

	public Dao<EquipmentList, Integer> getEquipmentListDao() throws SQLException
	{
		if (equipmentListDao == null)
		{
			equipmentListDao = getDao(EquipmentList.class);
		}

		return equipmentListDao;
	}

	public Dao<EquipmentListEntry, Integer> getEquipmentListEntryDao() throws SQLException
	{
		if (equipmentListEntryDao == null)
		{
			equipmentListEntryDao = getDao(EquipmentListEntry.class);
		}

		return equipmentListEntryDao;
	}

	public Dao<Equipments, Integer> getEquipmentsDao() throws SQLException
	{
		if (equipmentsDao == null)
		{
			equipmentsDao = getDao(Equipments.class);
		}

		return equipmentsDao;
	}

	public Dao<File, Integer> getFileDao() throws SQLException
	{
		if (fileDao == null)
		{
			fileDao = getDao(File.class);
		}

		return fileDao;
	}

	public Dao<FileLocation, Integer> getFileLocationDao() throws SQLException
	{
		if (fileLocationDao == null)
		{
			fileLocationDao = getDao(FileLocation.class);
		}

		return fileLocationDao;
	}

	public Dao<Files, Integer> getFilesDao() throws SQLException
	{
		if (filesDao == null)
		{
			filesDao = getDao(Files.class);
		}

		return filesDao;
	}

	public Dao<GeneralValue, Integer> getGeneralValueDao() throws SQLException
	{
		if (generalValueDao == null)
		{
			generalValueDao = getDao(GeneralValue.class);
		}

		return generalValueDao;
	}

	public Dao<GeographicLocation, Integer> getGeographicLocationDao() throws SQLException
	{
		if (geographicLocationDao == null)
		{
			geographicLocationDao = getDao(GeographicLocation.class);
		}

		return geographicLocationDao;
	}

	public Dao<GlobalServiceProtocol, Integer> getGlobalServiceProtocolDao() throws SQLException
	{
		if (globalServiceProtocolDao == null)
		{
			globalServiceProtocolDao = getDao(GlobalServiceProtocol.class);
		}

		return globalServiceProtocolDao;
	}

	public Dao<GSPInfo, Integer> getGspInfoDao() throws SQLException
	{
		if (gspInfoDao == null)
		{
			gspInfoDao = getDao(GSPInfo.class);
		}

		return gspInfoDao;
	}

	public Dao<Location, Integer> getLocationDao() throws SQLException
	{
		if (locationDao == null)
		{
			locationDao = getDao(Location.class);
		}

		return locationDao;
	}

	public Dao<LogicalNodeInformation, Integer> getLogicalNodeInformationDao() throws SQLException
	{
		if (logicalNodeInformationDao == null)
		{
			logicalNodeInformationDao = getDao(LogicalNodeInformation.class);
		}

		return logicalNodeInformationDao;
	}

	public Dao<Material, Integer> getMaterialDao() throws SQLException
	{
		if (materialDao == null)
		{
			materialDao = getDao(Material.class);
		}

		return materialDao;
	}

	public Dao<Materials, Integer> getMaterialsDao() throws SQLException
	{
		if (materialsDao == null)
		{
			materialsDao = getDao(Materials.class);
		}

		return materialsDao;
	}

	public Dao<Measurement, Integer> getMeasurementDao() throws SQLException
	{
		if (measurementDao == null)
		{
			measurementDao = getDao(Measurement.class);
		}

		return measurementDao;
	}

	public Dao<Measurements, Integer> getMeasurementsDao() throws SQLException
	{
		if (measurementsDao == null)
		{
			measurementsDao = getDao(Measurements.class);
		}

		return measurementsDao;
	}

	public Dao<MeasurementSeries, Integer> getMeasurementSeriesDao() throws SQLException
	{
		if (measurementSeriesDao == null)
		{
			measurementSeriesDao = getDao(MeasurementSeries.class);
		}

		return measurementSeriesDao;
	}

	public Dao<MeasurementValue, Integer> getMeasurementValueDao() throws SQLException
	{
		if (measurementValueDao == null)
		{
			measurementValueDao = getDao(MeasurementValue.class);
		}

		return measurementValueDao;
	}

	public Dao<MeasuringEquipment, Integer> getMeasuringEquipmentDao() throws SQLException
	{
		if (measuringEquipmentDao == null)
		{
			measuringEquipmentDao = getDao(MeasuringEquipment.class);
		}

		return measuringEquipmentDao;
	}

	public Dao<MeasuringInstallation, Integer> getMeasuringInstallationDao() throws SQLException
	{
		if (measuringInstallationDao == null)
		{
			measuringInstallationDao = getDao(MeasuringInstallation.class);
		}

		return measuringInstallationDao;
	}

	public Dao<Organisation, Integer> getOrganisationDao() throws SQLException
	{
		if (organisationDao == null)
		{
			organisationDao = getDao(Organisation.class);
		}

		return organisationDao;
	}

	public Dao<Parameter, Integer> getParameterDao() throws SQLException
	{
		if (parameterDao == null)
		{
			parameterDao = getDao(Parameter.class);
		}

		return parameterDao;
	}

	public Dao<ParameterValue, Integer> getParameterValueDao() throws SQLException
	{
		if (parameterValueDao == null)
		{
			parameterValueDao = getDao(ParameterValue.class);
		}

		return parameterValueDao;
	}

	public Dao<Person, Integer> getPersonDao() throws SQLException
	{
		if (personDao == null)
		{
			personDao = getDao(Person.class);
		}

		return personDao;
	}

	public Dao<Persons, Integer> getPersonsDao() throws SQLException
	{
		if (personsDao == null)
		{
			personsDao = getDao(Persons.class);
		}

		return personsDao;
	}

	public Dao<PostalAddress, Integer> getPostalAddressDao() throws SQLException
	{
		if (postalAddressDao == null)
		{
			postalAddressDao = getDao(PostalAddress.class);
		}

		return postalAddressDao;
	}

	public Dao<PowerPlant, Integer> getPowerPlantDao() throws SQLException
	{
		if (powerPlantDao == null)
		{
			powerPlantDao = getDao(PowerPlant.class);
		}

		return powerPlantDao;
	}

	public Dao<PriorityEntry, Integer> getPriorityEntryDao() throws SQLException
	{
		if (priorityEntryDao == null)
		{
			priorityEntryDao = getDao(PriorityEntry.class);
		}

		return priorityEntryDao;
	}

	public Dao<PriorityLog, Integer> getPriorityLogDao() throws SQLException
	{
		if (priorityLogDao == null)
		{
			priorityLogDao = getDao(PriorityLog.class);
		}

		return priorityLogDao;
	}

	public Dao<RDSPPElement, Integer> getRdsppElementDao() throws SQLException
	{
		if (rdsppElementDao == null)
		{
			rdsppElementDao = getDao(RDSPPElement.class);
		}

		return rdsppElementDao;
	}

	public Dao<RDSPPStructure, Integer> getRdsppStructureDao() throws SQLException
	{
		if (rdsppStructureDao == null)
		{
			rdsppStructureDao = getDao(RDSPPStructure.class);
		}

		return rdsppStructureDao;
	}

	public Dao<Skill, Integer> getSkillDao() throws SQLException
	{
		if (skillDao == null)
		{
			skillDao = getDao(Skill.class);
		}

		return skillDao;
	}

	public Dao<Skills, Integer> getSkillsDao() throws SQLException
	{
		if (skillsDao == null)
		{
			skillsDao = getDao(Skills.class);
		}

		return skillsDao;
	}

	public Dao<StreetAddress, Integer> getStreetAddressDao() throws SQLException
	{
		if (streetAddressDao == null)
		{
			streetAddressDao = getDao(StreetAddress.class);
		}

		return streetAddressDao;
	}

	public Dao<StreetDetail, Integer> getStreetDetailDao() throws SQLException
	{
		if (streetDetailDao == null)
		{
			streetDetailDao = getDao(StreetDetail.class);
		}

		return streetDetailDao;
	}

	public Dao<Task, Integer> getTaskDao() throws SQLException
	{
		if (taskDao == null)
		{
			taskDao = getDao(Task.class);
		}

		return taskDao;
	}

	public Dao<Tasks, Integer> getTasksDao() throws SQLException
	{
		if (tasksDao == null)
		{
			tasksDao = getDao(Tasks.class);
		}

		return tasksDao;
	}

	public Dao<TaskStatusLog, Integer> getTaskStatusLogDao() throws SQLException
	{
		if (taskStatusLogDao == null)
		{
			taskStatusLogDao = getDao(TaskStatusLog.class);
		}

		return taskStatusLogDao;
	}

	public Dao<TelephoneNumber, Integer> getTelephoneNumberDao() throws SQLException
	{
		if (telephoneNumberDao == null)
		{
			telephoneNumberDao = getDao(TelephoneNumber.class);
		}

		return telephoneNumberDao;
	}

	public Dao<TimeReport, Integer> getTimeReportDao() throws SQLException
	{
		if (timeReportDao == null)
		{
			timeReportDao = getDao(TimeReport.class);
		}

		return timeReportDao;
	}

	public Dao<TimeReports, Integer> getTimeReportsDao() throws SQLException
	{
		if (timeReportsDao == null)
		{
			timeReportsDao = getDao(TimeReports.class);
		}

		return timeReportsDao;
	}

	public Dao<TownDetail, Integer> getTownDetailDao() throws SQLException
	{
		if (townDetailDao == null)
		{
			townDetailDao = getDao(TownDetail.class);
		}

		return townDetailDao;
	}

	public Dao<TransportProcess, Integer> getTransportProcessDao() throws SQLException
	{
		if (transportProcessDao == null)
		{
			transportProcessDao = getDao(TransportProcess.class);
		}

		return transportProcessDao;
	}

	public Dao<TransportProcesses, Integer> getTransportProcessesDao() throws SQLException
	{
		if (transportProcessesDao == null)
		{
			transportProcessesDao = getDao(TransportProcesses.class);
		}

		return transportProcessesDao;
	}

	public Dao<UserSpecificContents, Integer> getUserSpecificContentsDao() throws SQLException
	{
		if (userSpecificContentsDao == null)
		{
			userSpecificContentsDao = getDao(UserSpecificContents.class);
		}

		return userSpecificContentsDao;
	}

	public Dao<UserSpecificParameterSet, Integer> getUserSpecificParameterSetDao() throws SQLException
	{
		if (userSpecificParameterSetDao == null)
		{
			userSpecificParameterSetDao = getDao(UserSpecificParameterSet.class);
		}

		return userSpecificParameterSetDao;
	}

	public Dao<WorkEquipment, Integer> getWorkEquipmentDao() throws SQLException
	{
		if (workEquipmentDao == null)
		{
			workEquipmentDao = getDao(WorkEquipment.class);
		}

		return workEquipmentDao;
	}

	public Dao<WorkEquipments, Integer> getWorkEquipmentsDao() throws SQLException
	{
		if (workEquipmentsDao == null)
		{
			workEquipmentsDao = getDao(WorkEquipments.class);
		}

		return workEquipmentsDao;
	}

	public Dao<WorkLog, Integer> getWorkLogDao() throws SQLException
	{
		if (workLogDao == null)
		{
			workLogDao = getDao(WorkLog.class);
		}

		return workLogDao;
	}

	public Dao<WorkLogEntry, Integer> getWorkLogEntryDao() throws SQLException
	{
		if (workLogEntryDao == null)
		{
			workLogEntryDao = getDao(WorkLogEntry.class);
		}

		return workLogEntryDao;
	}

	public Dao<WorkOrder, Integer> getWorkOrderDao() throws SQLException
	{
		if (workOrderDao == null)
		{
			workOrderDao = getDao(WorkOrder.class);
		}

		return workOrderDao;
	}

	public Dao<WorkOrderItem, Integer> getWorkOrderItemDao() throws SQLException
	{
		if (workOrderItemDao == null)
		{
			workOrderItemDao = getDao(WorkOrderItem.class);
		}

		return workOrderItemDao;
	}

	public Dao<WorkOrderItems, Integer> getWorkOrderItemsDao() throws SQLException
	{
		if (workOrderItemsDao == null)
		{
			workOrderItemsDao = getDao(WorkOrderItems.class);
		}

		return workOrderItemsDao;
	}

	public Dao<WorkReport, Integer> getWorkReportDao() throws SQLException
	{
		if (workReportDao == null)
		{
			workReportDao = getDao(WorkReport.class);
		}

		return workReportDao;
	}

	public Dao<WorkReportItem, Integer> getWorkReportItemDao() throws SQLException
	{
		if (workReportItemDao == null)
		{
			workReportItemDao = getDao(WorkReportItem.class);
		}

		return workReportItemDao;
	}

	public Dao<WorkReportItems, Integer> getWorkReportItemsDao() throws SQLException
	{
		if (workReportItemsDao == null)
		{
			workReportItemsDao = getDao(WorkReportItems.class);
		}

		return workReportItemsDao;
	}

	public Dao<WorkStatusLog, Integer> getWorkStatusLogDao() throws SQLException
	{
		if (workStatusLogDao == null)
		{
			workStatusLogDao = getDao(WorkStatusLog.class);
		}

		return workStatusLogDao;
	}

	public Dao<WorkStatusLogs, Integer> getWorkStatusLogsDao() throws SQLException
	{
		if (workStatusLogsDao == null)
		{
			workStatusLogsDao = getDao(WorkStatusLogs.class);
		}

		return workStatusLogsDao;
	}

	public Dao<ZEUSPart1, Integer> getZeusPart1Dao() throws SQLException
	{
		if (zeusPart1Dao == null)
		{
			zeusPart1Dao = getDao(ZEUSPart1.class);
		}

		return zeusPart1Dao;
	}

	public Dao<ZEUSPart1Assessment, Integer> getZeusPart1AssessmentDao() throws SQLException
	{
		if (zeusPart1AssessmentDao == null)
		{
			zeusPart1AssessmentDao = getDao(ZEUSPart1Assessment.class);
		}

		return zeusPart1AssessmentDao;
	}

	public Dao<ZEUSPart2, Integer> getZeusPart2Dao() throws SQLException
	{
		if (zeusPart2Dao == null)
		{
			zeusPart2Dao = getDao(ZEUSPart2.class);
		}

		return zeusPart2Dao;
	}

	public Dao<ZEUSPart2Assessment, Integer> getZeusPart2AssessmentDao() throws SQLException
	{
		if (zeusPart2AssessmentDao == null)
		{
			zeusPart2AssessmentDao = getDao(ZEUSPart2Assessment.class);
		}

		return zeusPart2AssessmentDao;
	}
}