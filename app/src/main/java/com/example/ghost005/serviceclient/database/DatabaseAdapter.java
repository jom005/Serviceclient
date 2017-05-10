package com.example.ghost005.serviceclient.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import com.example.ghost005.serviceclient.model.types.Assessment;
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
import com.example.ghost005.serviceclient.model.types.Equipments;
import com.example.ghost005.serviceclient.model.types.File;
import com.example.ghost005.serviceclient.model.types.FileLocation;
import com.example.ghost005.serviceclient.model.types.Files;
import com.example.ghost005.serviceclient.model.types.GSPInfo;
import com.example.ghost005.serviceclient.model.types.GeneralValue;
import com.example.ghost005.serviceclient.model.types.GeographicLocation;
import com.example.ghost005.serviceclient.model.types.GlobalServiceProtocol;
import com.example.ghost005.serviceclient.model.types.Material;
import com.example.ghost005.serviceclient.model.types.Materials;
import com.example.ghost005.serviceclient.model.types.Organisation;
import com.example.ghost005.serviceclient.model.types.Person;
import com.example.ghost005.serviceclient.model.types.Persons;
import com.example.ghost005.serviceclient.model.types.PowerPlant;
import com.example.ghost005.serviceclient.model.types.Skill;
import com.example.ghost005.serviceclient.model.types.Skills;
import com.example.ghost005.serviceclient.model.types.StreetAddress;
import com.example.ghost005.serviceclient.model.types.Task;
import com.example.ghost005.serviceclient.model.types.Tasks;
import com.example.ghost005.serviceclient.model.types.TelephoneNumber;
import com.example.ghost005.serviceclient.model.types.TimeReport;
import com.example.ghost005.serviceclient.model.types.TimeReports;
import com.example.ghost005.serviceclient.model.types.TransportProcess;
import com.example.ghost005.serviceclient.model.types.TransportProcesses;
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
import com.example.ghost005.serviceclient.utilities.GSPUtilities;

/**
 * DatabaseAdapter provides several methods for retrieving from and inserting data into the database.
 */
public class DatabaseAdapter
{
	private volatile DatabaseHelper databaseHelper = null;

	/**
	 * Constructor sets the databaseHelper
	 *
	 * @param context Application context
	 */
	public DatabaseAdapter(Context context)
	{
		if (databaseHelper == null)
		{
			databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
	}

	/**
	 * Closes the database connection
	 */
	public void close()
	{
		databaseHelper.close();
		OpenHelperManager.releaseHelper();
	}

	/**
	 * Inserts a list of GSPDocuments into the database
	 *
	 * @param gspDocuments List of GSPDocuments
	 * @throws SQLException
	 */
	public void insertGspDocuments(List<GSPDocument> gspDocuments) throws SQLException
	{
		for (GSPDocument gspDocument : gspDocuments)
		{
			WorkReport workReport = GSPUtilities.createWorkReport(gspDocument.getGlobalServiceProtocol().getWorkOrder());
			gspDocument.getGlobalServiceProtocol().setWorkReport(workReport);

			PowerPlant powerPlant = gspDocument.getGlobalServiceProtocol().getPowerPlant();
			EnergySystem energySystem = gspDocument.getGlobalServiceProtocol().getEnergySystem();
			WorkOrder workOrder = gspDocument.getGlobalServiceProtocol().getWorkOrder();

			Dao<Comment, Integer> commentDao = databaseHelper.getCommentDao();
			createForeignKeys(workOrder.getComments(), commentDao);

			Dao<EnvironmentalCondition, Integer> environmentalConditionsDao = databaseHelper.getEnvironmentalConditionDao();
			createForeignKeys(workOrder.getEnvironmentalConditions(), environmentalConditionsDao);

			Dao<ZEUSPart1, Integer> zeusPart1AssessmentDao = databaseHelper.getZeusPart1Dao();
			createForeignKeys(workOrder.getZeusPart1History(), zeusPart1AssessmentDao);

			Dao<Attachment, Integer> attachmentDao = databaseHelper.getAttachmentDao();
			createForeignKeys(workOrder.getAttachments(), attachmentDao);

			Dao<WorkLogEntry, Integer> workLogDao = databaseHelper.getWorkLogEntryDao();
			createForeignKeys(workOrder.getWorkLogHistory(), workLogDao);

			Dao<TransportProcess, Integer> transportProcessDao = databaseHelper.getTransportProcessDao();
			createForeignKeys(workOrder.getTransportProcesses(), transportProcessDao);

			Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
			createForeignKeys(workOrder.getStaff(), employeeDao);

			Dao<WorkOrderItem, Integer> workOrderItemDoa = databaseHelper.getWorkOrderItemDao();
			createForeignKeys(workOrder.getItems(), workOrderItemDoa);

			if (workOrder.getAttachments() != null)
			{
				for (Attachment attachment : workOrder.getAttachments().getAttachments())
				{
					Dao<File, Integer> fileDao = databaseHelper.getFileDao();
					createForeignKeys(attachment.getFiles(), fileDao);
				}
			}

			if (workOrder.getStaff() != null)
			{
				for (Employee employee : workOrder.getStaff().getEmployees())
				{
					Dao<Skill, Integer> skillDao = databaseHelper.getSkillDao();
					createForeignKeys(employee.getSkills(), skillDao);
				}
			}

			for (WorkOrderItem workOrderItem : workOrder.getItems().getWorkOrderItems())
			{
				Dao<WorkStatusLog, Integer> workStatusLogDao = databaseHelper.getWorkStatusLogDao();
				createForeignKeys(workOrderItem.getStatuses(), workStatusLogDao);

				commentDao = databaseHelper.getCommentDao();
				createForeignKeys(workOrderItem.getComments(), commentDao);

				attachmentDao = databaseHelper.getAttachmentDao();
				createForeignKeys(workOrderItem.getAttachments(), attachmentDao);

				Dao<ZEUSPart2, Integer> zeusPart2Dao = databaseHelper.getZeusPart2Dao();
				createForeignKeys(workOrderItem.getZeusPart2History(), zeusPart2Dao);

				Dao<Skill, Integer> skillDao = databaseHelper.getSkillDao();
				createForeignKeys(workOrderItem.getSkills(), skillDao);

				Dao<Task, Integer> taskDao = databaseHelper.getTaskDao();
				createForeignKeys(workOrderItem.getTasks(), taskDao);

				employeeDao = databaseHelper.getEmployeeDao();
				createForeignKeys(workOrderItem.getStaff(), employeeDao);

				Dao<Equipment, Integer> equipmentDao = databaseHelper.getEquipmentDao();
				createForeignKeys(workOrderItem.getEquipments(), equipmentDao);

				Dao<WorkEquipment, Integer> workEquipmentDao = databaseHelper.getWorkEquipmentDao();
				createForeignKeys(workOrderItem.getWorkEquipments(), workEquipmentDao);

				Dao<Material, Integer> materialDao = databaseHelper.getMaterialDao();
				createForeignKeys(workOrderItem.getMaterials(), materialDao);

				if (workOrderItem.getAttachments() != null)
				{
					for (Attachment attachment : workOrderItem.getAttachments().getAttachments())
					{
						Dao<File, Integer> fileDao = databaseHelper.getFileDao();
						createForeignKeys(attachment.getFiles(), fileDao);
					}
				}

				if (workOrderItem.getStaff() != null)
				{
					for (Employee employee : workOrderItem.getStaff().getEmployees())
					{
						skillDao = databaseHelper.getSkillDao();
						createForeignKeys(employee.getSkills(), skillDao);
					}
				}
			}

			attachmentDao = databaseHelper.getAttachmentDao();
			createForeignKeys(workReport.getAttachments(), attachmentDao);

			employeeDao = databaseHelper.getEmployeeDao();
			createForeignKeys(workReport.getStaff(), employeeDao);

			Dao<WorkStatusLog, Integer> workStatusLogDao = databaseHelper.getWorkStatusLogDao();
			createForeignKeys(workReport.getStatuses(), workStatusLogDao);

			if (workReport.getAttachments() != null)
			{
				for (Attachment attachment : workReport.getAttachments().getAttachments())
				{
					Dao<File, Integer> fileDao = databaseHelper.getFileDao();
					createForeignKeys(attachment.getFiles(), fileDao);
				}
			}

			if (workReport.getStaff() != null)
			{
				for (Employee employee : workReport.getStaff().getEmployees())
				{
					Dao<Skill, Integer> skillDao = databaseHelper.getSkillDao();
					createForeignKeys(employee.getSkills(), skillDao);
				}
			}

			Dao<WorkReportItem, Integer> workReportItemDao = databaseHelper.getWorkReportItemDao();
			createForeignKeys(workReport.getItems(), workReportItemDao);

			for (WorkReportItem workReportItem : workReport.getItems().getWorkReportItems())
			{
				Dao<ZEUSPart2, Integer> zeusPart2Dao = databaseHelper.getZeusPart2Dao();
				createForeignKeys(workReportItem.getZeusPart2Assessment(), zeusPart2Dao);

				Dao<Equipment, Integer> equipmentDao = databaseHelper.getEquipmentDao();
				createForeignKeys(workReportItem.getEquipments(), equipmentDao);

				Dao<WorkEquipment, Integer> workEquipmentDao = databaseHelper.getWorkEquipmentDao();
				createForeignKeys(workReportItem.getWorkEquipments(), workEquipmentDao);

				Dao<Material, Integer> materialDao = databaseHelper.getMaterialDao();
				createForeignKeys(workReportItem.getMaterials(), materialDao);

				workStatusLogDao = databaseHelper.getWorkStatusLogDao();
				createForeignKeys(workReportItem.getStatuses(), workStatusLogDao);

				Dao<Task, Integer> taskDao = databaseHelper.getTaskDao();
				createForeignKeys(workReportItem.getTasks(), taskDao);

				employeeDao = databaseHelper.getEmployeeDao();
				createForeignKeys(workReportItem.getStaff(), employeeDao);

				attachmentDao = databaseHelper.getAttachmentDao();
				createForeignKeys(workReportItem.getAttachments(), attachmentDao);

				if (workReportItem.getTasks() != null)
				{
					for (Task task : workReportItem.getTasks().getTasks())
					{
						workEquipmentDao = databaseHelper.getWorkEquipmentDao();
						createForeignKeys(task.getWorkEquipments(), workEquipmentDao);
					}
				}

				if (workReportItem.getStaff() != null)
				{
					for (Employee employee : workReportItem.getStaff().getEmployees())
					{
						Dao<Skill, Integer> skillDao = databaseHelper.getSkillDao();
						createForeignKeys(employee.getSkills(), skillDao);
					}
				}
			}

			Dao<Person, Integer> personDao = databaseHelper.getPersonDao();
			createForeignKeys(powerPlant.getOperator().getContacts(), personDao);
			personDao = databaseHelper.getPersonDao();
			createForeignKeys(powerPlant.getOwner().getContacts(), personDao);
			personDao = databaseHelper.getPersonDao();
			createForeignKeys(energySystem.getOperator().getContacts(), personDao);
			personDao = databaseHelper.getPersonDao();
			createForeignKeys(energySystem.getOwner().getContacts(), personDao);
			personDao = databaseHelper.getPersonDao();
			createForeignKeys(energySystem.getManufacturer().getContacts(), personDao);

			Dao<GSPDocument, Integer> gspDocumentDao = databaseHelper.getGspDocumentDao();
			gspDocumentDao.create(gspDocument);
		}
	}

	/**
	 * Sets the field for the class which contains the collection, the given class is in.
	 * Also creates the database entry for the class
	 *
	 * @param collectionClass the class which contains the collection
	 * @param dao             the Dao for the class which is in the collection
	 * @throws SQLException
	 */
	private void createForeignKeys(Object collectionClass, Dao dao) throws SQLException
	{
		if (collectionClass instanceof Comments)
		{
			Comments comments = ((Comments) collectionClass);

			for (Comment comment : comments.getComments())
			{
				comment.setComments(comments);
				((Dao<Comment, Integer>) dao).create(comment);
			}
		}
		else if (collectionClass instanceof WorkOrderItems)
		{
			WorkOrderItems workOrderItems = ((WorkOrderItems) collectionClass);

			for (WorkOrderItem workOrderItem : workOrderItems.getWorkOrderItems())
			{
				workOrderItem.setWorkOrderItems(workOrderItems);
				((Dao<WorkOrderItem, Integer>) dao).create(workOrderItem);
			}
		}
		else if (collectionClass instanceof WorkReportItems)
		{
			WorkReportItems workReportItems = (WorkReportItems) collectionClass;

			for (WorkReportItem workReportItem : workReportItems.getWorkReportItems())
			{
				workReportItem.setWorkReportItems(workReportItems);
				((Dao<WorkReportItem, Integer>) dao).create(workReportItem);
			}
		}
		else if (collectionClass instanceof WorkStatusLogs)
		{
			WorkStatusLogs workStatusLogs = ((WorkStatusLogs) collectionClass);

			for (WorkStatusLog workStatusLog : workStatusLogs.getWorkStatusLogs())
			{
				workStatusLog.setWorkStatusLogs(workStatusLogs);
				((Dao<WorkStatusLog, Integer>) dao).create(workStatusLog);
			}
		}
		else if (collectionClass instanceof EnvironmentalConditions)
		{
			EnvironmentalConditions environmentalConditions = (EnvironmentalConditions) collectionClass;

			for (EnvironmentalCondition environmentalCondition : environmentalConditions.getEnvironmentalConditions())
			{
				environmentalCondition.setEnvironmentalConditions(environmentalConditions);
				((Dao<EnvironmentalCondition, Integer>) dao).create(environmentalCondition);
			}
		}
		else if (collectionClass instanceof TransportProcesses)
		{
			TransportProcesses transportProcesses = (TransportProcesses) collectionClass;

			for (TransportProcess transportProcess : transportProcesses.getTransportProcesses())
			{
				transportProcess.setTransportProcesses(transportProcesses);
				((Dao<TransportProcess, Integer>) dao).create(transportProcess);
			}
		}
		else if (collectionClass instanceof ZEUSPart1Assessment)
		{
			ZEUSPart1Assessment zeusPart1Assessment = (ZEUSPart1Assessment) collectionClass;

			for (ZEUSPart1 zeusPart1 : zeusPart1Assessment.getZeusPart1())
			{
				zeusPart1.setZeusPart1Assessment(zeusPart1Assessment);
				((Dao<ZEUSPart1, Integer>) dao).create(zeusPart1);
			}
		}
		else if (collectionClass instanceof Attachments)
		{
			Attachments attachments = (Attachments) collectionClass;

			for (Attachment attachment : attachments.getAttachments())
			{
				attachment.setAttachments(attachments);
				((Dao<Attachment, Integer>) dao).create(attachment);
			}
		}
		else if (collectionClass instanceof WorkLog)
		{
			WorkLog workLog = (WorkLog) collectionClass;

			for (WorkLogEntry workLogEntry : workLog.getWorkLogEntries())
			{
				workLogEntry.setWorkLog(workLog);
				((Dao<WorkLogEntry, Integer>) dao).create(workLogEntry);
			}
		}
		else if (collectionClass instanceof ZEUSPart2Assessment)
		{
			ZEUSPart2Assessment zeusPart2Assessment = (ZEUSPart2Assessment) collectionClass;

			for (ZEUSPart2 zeusPart2 : zeusPart2Assessment.getZeusPart2())
			{
				zeusPart2.setZeusPart2Assessment(zeusPart2Assessment);
				((Dao<ZEUSPart2, Integer>) dao).create(zeusPart2);
			}
		}
		else if (collectionClass instanceof Skills)
		{
			Skills skills = (Skills) collectionClass;

			for (Skill skill : skills.getSkills())
			{
				skill.setSkills(skills);
				((Dao<Skill, Integer>) dao).create(skill);
			}
		}
		else if (collectionClass instanceof Tasks)
		{
			Tasks tasks = (Tasks) collectionClass;

			for (Task task : tasks.getTasks())
			{
				task.setTasks(tasks);
				((Dao<Task, Integer>) dao).create(task);
			}
		}
		else if (collectionClass instanceof Persons)
		{
			Persons persons = (Persons) collectionClass;

			for (Person person : persons.getPersons())
			{
				person.setPersons(persons);
				((Dao<Person, Integer>) dao).create(person);
			}
		}
		else if (collectionClass instanceof Employees)
		{
			Employees employees = (Employees) collectionClass;

			for (Employee employee : employees.getEmployees())
			{
				employee.setEmployees(employees);
				((Dao<Employee, Integer>) dao).create(employee);
			}
		}
		else if (collectionClass instanceof Equipments)
		{
			Equipments equipments = (Equipments) collectionClass;

			for (Equipment equipment : equipments.getEquipments())
			{
				equipment.setEquipments(equipments);
				((Dao<Equipment, Integer>) dao).create(equipment);
			}
		}
		else if (collectionClass instanceof WorkEquipments)
		{
			WorkEquipments workEquipments = (WorkEquipments) collectionClass;

			for (WorkEquipment workEquipment : workEquipments.getWorkEquipments())
			{
				workEquipment.setWorkEquipments(workEquipments);
				((Dao<WorkEquipment, Integer>) dao).create(workEquipment);
			}
		}
		else if (collectionClass instanceof Material)
		{
			Materials materials = (Materials) collectionClass;

			for (Material material : materials.getMaterials())
			{
				material.setMaterials(materials);
				((Dao<Material, Integer>) dao).create(material);
			}
		}
		else if (collectionClass instanceof Files)
		{
			Files files = (Files) collectionClass;

			for (File file : files.getFiles())
			{
				if (file.getLocation() != null)
				{
					Object value = file.getLocation().getValue();

					if (value != null)
					{
						if (value instanceof String)
						{
							file.getLocation().setPath(value.toString());
						}
						else if (value instanceof URI)
						{
							file.getLocation().setUrl(value.toString());
						}
					}
				}

				file.setFiles(files);
				((Dao<File, Integer>) dao).create(file);
			}
		}
	}

	// GSPDocument

	/**
	 * Returns a GSPDocument from the database.
	 *
	 * @param documentId GSP document id, which is set in GSPInfo
	 * @return GSPDocument
	 * @throws SQLException
	 */
	public GSPDocument getGSPDocument(String documentId) throws SQLException
	{
		Dao<GSPDocument, Integer> gspDocumentDao = databaseHelper.getGspDocumentDao();
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();
		Dao<GSPInfo, Integer> gspInfoDao = databaseHelper.getGspInfoDao();

		QueryBuilder<GSPDocument, Integer> gspDocumentQb = gspDocumentDao.queryBuilder();
		QueryBuilder<GlobalServiceProtocol, Integer> globalServiceProtocolQb = globalServiceProtocolDao.queryBuilder();
		QueryBuilder<GSPInfo, Integer> gspInfoQb = gspInfoDao.queryBuilder();

		gspInfoQb.where().eq(GSPInfo.DOCUMENT_ID, documentId);
		globalServiceProtocolQb.join(gspDocumentQb);
		gspDocumentQb.join(globalServiceProtocolQb);

		return gspDocumentQb.queryForFirst();
	}

	public GSPDocument getGSPDocument(int gspDBID) throws SQLException
	{
		Dao<GSPDocument, Integer> gspDocumentDao = databaseHelper.getGspDocumentDao();
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();

		QueryBuilder<GSPDocument, Integer> gspDocumentQB = gspDocumentDao.queryBuilder();
		QueryBuilder<GlobalServiceProtocol, Integer> gspQB = globalServiceProtocolDao.queryBuilder();

		gspQB.where().eq(GlobalServiceProtocol.DATABASE_ID, gspDBID);
		gspDocumentQB.join(gspQB);

		return gspDocumentQB.queryForFirst();
	}

	// Assessment

	public void createOrUpdateAssessment(Assessment assessment) throws SQLException
	{
		Dao<Assessment, Integer> assessmentDao = databaseHelper.getAssessmentDao();
		assessmentDao.createOrUpdate(assessment);
	}

	// Attachment

	public Attachment getAttachment(int id) throws SQLException
	{
		Dao<Attachment, Integer> attachmentDao = databaseHelper.getAttachmentDao();
		QueryBuilder<Attachment, Integer> attachmentQueryBuilder = attachmentDao.queryBuilder();

		attachmentQueryBuilder.where().eq(Attachment.DATABASE_ID, id);

		return attachmentQueryBuilder.queryForFirst();
	}

	public void createOrUpdateAttachment(Attachment attachment) throws SQLException
	{
		Dao<Attachment, Integer> attachmentDao = databaseHelper.getAttachmentDao();
		attachmentDao.createOrUpdate(attachment);
	}

	public void deleteAttachments(Attachment attachment) throws SQLException
	{
		Dao<Attachment, Integer> attachmentDao = databaseHelper.getAttachmentDao();
		attachmentDao.delete(attachment);
	}

	// Attachments

	public Attachments getAttachments(int id) throws SQLException
	{
		Dao<Attachments, Integer> attachmentsDao = databaseHelper.getAttachmentsDao();
		QueryBuilder<Attachments, Integer> attachmentsQueryBuilder = attachmentsDao.queryBuilder();

		attachmentsQueryBuilder.where().eq(Attachments.DATABASE_ID, id);

		return attachmentsQueryBuilder.queryForFirst();
	}

	public void createOrUpdateAttachments(Attachments attachments) throws SQLException
	{
		Dao<Attachments, Integer> attachmentsDao = databaseHelper.getAttachmentsDao();
		attachmentsDao.createOrUpdate(attachments);
	}

	public void deleteAttachments(Attachments attachments) throws SQLException
	{
		Dao<Attachments, Integer> attachmentsDao = databaseHelper.getAttachmentsDao();
		attachmentsDao.delete(attachments);
	}

	// Comment

	public Comment getComment(int id) throws SQLException
	{
		Dao<Comment, Integer> commentDao = databaseHelper.getCommentDao();
		QueryBuilder<Comment, Integer> commentQueryBuilder = commentDao.queryBuilder();

		commentQueryBuilder.where().eq(Comment.DATABASE_ID, id);

		return commentQueryBuilder.queryForFirst();
	}

	public void createOrUpdateComment(Comment comment) throws SQLException
	{
		Dao<Comment, Integer> commentDao = databaseHelper.getCommentDao();
		commentDao.createOrUpdate(comment);
	}

	public void deleteComment(Comment comment) throws SQLException
	{
		Dao<Comment, Integer> commentDao = databaseHelper.getCommentDao();
		commentDao.delete(comment);
	}

	// Comments

	public Comments getComments(int id) throws SQLException
	{
		Dao<Comments, Integer> commentsDao = databaseHelper.getCommentsDao();
		QueryBuilder<Comments, Integer> commentsQueryBuilder = commentsDao.queryBuilder();

		commentsQueryBuilder.where().eq(Comment.DATABASE_ID, id);

		return commentsQueryBuilder.queryForFirst();
	}

	public void createOrUpdateComments(Comments comments) throws SQLException
	{
		Dao<Comments, Integer> commentsDao = databaseHelper.getCommentsDao();
		commentsDao.createOrUpdate(comments);
	}

	public void deleteComments(Comments comments) throws SQLException
	{
		Dao<Comments, Integer> commentsDao = databaseHelper.getCommentsDao();
		commentsDao.delete(comments);
	}

	// ElectronicAddress

	public ElectronicAddress getElectronicAddress(int id) throws SQLException
	{
		Dao<ElectronicAddress, Integer> electronicAddressDao = databaseHelper.getElectronicAddressDao();
		QueryBuilder<ElectronicAddress, Integer> electronicAddressQB = electronicAddressDao.queryBuilder();

		electronicAddressQB.where().eq(ElectronicAddress.DATABASE_ID, id);

		return electronicAddressQB.queryForFirst();
	}

	// Employee

	public Employee getEmployee(int id) throws SQLException
	{
		Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
		QueryBuilder<Employee, Integer> employeeQueryBuilder = employeeDao.queryBuilder();

		employeeQueryBuilder.where().eq(Employee.DATABASE_ID, id);

		return employeeQueryBuilder.queryForFirst();
	}

	public Employee getEmployee(String id) throws SQLException
	{
		Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
		QueryBuilder<Employee, Integer> employeeQueryBuilder = employeeDao.queryBuilder();

		employeeQueryBuilder.where().eq(Employee.ID, id);

		return employeeQueryBuilder.queryForFirst();
	}

	public void createOrUpdateEmployee(Employee employee) throws SQLException
	{
		Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
		employeeDao.createOrUpdate(employee);
	}

	public void createEmployee(Employee employee) throws SQLException
	{
		Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
		employeeDao.create(employee);
	}

	public void deleteEmployee(Employee employee) throws SQLException
	{
		Dao<Employee, Integer> employeeDao = databaseHelper.getEmployeeDao();
		employeeDao.delete(employee);
	}

	// Employees

	public void createOrUpdateEmployees(Employees employees) throws SQLException
	{
		Dao<Employees, Integer> employeesDao = databaseHelper.getEmployeesDao();
		employeesDao.createOrUpdate(employees);
	}

	public void deleteEmployees(Employees employees) throws SQLException
	{
		Dao<Employees, Integer> employeesDao = databaseHelper.getEmployeesDao();
		employeesDao.delete(employees);
	}

	// EnvironmentalCondition

	public EnvironmentalCondition getEnvironmentalCondition(int id) throws SQLException
	{
		Dao<EnvironmentalCondition, Integer> envCondDao = databaseHelper.getEnvironmentalConditionDao();
		QueryBuilder<EnvironmentalCondition, Integer> envCondQB = envCondDao.queryBuilder();

		envCondQB.where().eq(EnvironmentalCondition.DATABASE_ID, id);

		return envCondQB.queryForFirst();
	}

	public void createOrUpdateEnvironmentalCondition(EnvironmentalCondition environmentalCondition) throws SQLException
	{
		Dao<EnvironmentalCondition, Integer> environmentalConditionDao = databaseHelper.getEnvironmentalConditionDao();
		environmentalConditionDao.createOrUpdate(environmentalCondition);
	}

	public void deleteEnvironmentalCondition(EnvironmentalCondition environmentalCondition) throws SQLException
	{
		Dao<EnvironmentalCondition, Integer> environmentalConditionDao = databaseHelper.getEnvironmentalConditionDao();
		environmentalConditionDao.delete(environmentalCondition);
	}

	// EnvironmentalConditions

	public void createOrUpdateEnvironmentalConditions(EnvironmentalConditions environmentalConditions) throws SQLException
	{
		Dao<EnvironmentalConditions, Integer> environmentalConditionsDao = databaseHelper.getEnvironmentalConditionsDao();
		environmentalConditionsDao.createOrUpdate(environmentalConditions);
	}

	public void deleteEnvironmentalConditions(EnvironmentalConditions environmentalConditions) throws SQLException
	{
		Dao<EnvironmentalConditions, Integer> environmentalConditionsDao = databaseHelper.getEnvironmentalConditionsDao();
		environmentalConditionsDao.delete(environmentalConditions);
	}

	// Equipment

	public Equipment getEquipment(int id) throws SQLException
	{
		Dao<Equipment, Integer> equipmentDao = databaseHelper.getEquipmentDao();
		QueryBuilder<Equipment, Integer> equipmentQueryBuilder = equipmentDao.queryBuilder();

		equipmentQueryBuilder.where().eq(Equipment.DATABASE_ID, id);

		return equipmentQueryBuilder.queryForFirst();
	}

	public void createOrUpdateEquipment(Equipment equipment) throws SQLException
	{
		Dao<Equipment, Integer> equipmentDao = databaseHelper.getEquipmentDao();
		equipmentDao.createOrUpdate(equipment);
	}

	public void deleteEquipment(Equipment equipment) throws SQLException
	{
		Dao<Equipment, Integer> equipmentDao = databaseHelper.getEquipmentDao();
		equipmentDao.delete(equipment);
	}

	// Equipments

	public Equipments getEquipments(int id) throws SQLException
	{
		Dao<Equipments, Integer> equipmentsDao = databaseHelper.getEquipmentsDao();
		QueryBuilder<Equipments, Integer> equipmentsQB = equipmentsDao.queryBuilder();

		equipmentsQB.where().eq(Equipments.DATABASE_ID, id);

		return equipmentsQB.queryForFirst();
	}

	public void createOrUpdateEquipments(Equipments equipments) throws SQLException
	{
		Dao<Equipments, Integer> equipmentsDao = databaseHelper.getEquipmentsDao();
		equipmentsDao.createOrUpdate(equipments);
	}

	public void deleteEquipments(Equipments equipments) throws SQLException
	{
		Dao<Equipments, Integer> equipmentsDao = databaseHelper.getEquipmentsDao();
		equipmentsDao.delete(equipments);
	}

	// File

	public void createOrUpdateFile(File file) throws SQLException
	{
		Dao<File, Integer> fileDao = databaseHelper.getFileDao();
		fileDao.createOrUpdate(file);
	}

	public void deleteFile(File file) throws SQLException
	{
		Dao<File, Integer> fileDao = databaseHelper.getFileDao();
		fileDao.delete(file);
	}

	// Files

	public void createOrUpdateFiles(Files files) throws SQLException
	{
		Dao<Files, Integer> filesDao = databaseHelper.getFilesDao();
		filesDao.createOrUpdate(files);
	}

	public void deleteFiles(Files files) throws SQLException
	{
		Dao<Files, Integer> filesDao = databaseHelper.getFilesDao();
		filesDao.delete(files);
	}

	// FileLocation

	public void createOrUpdateFileLocation(FileLocation fileLocation) throws SQLException
	{
		Dao<FileLocation, Integer> fileLocationDao = databaseHelper.getFileLocationDao();
		fileLocationDao.createOrUpdate(fileLocation);
	}

	// GeneralValue

	public void createOrUpdateGeneralValue(GeneralValue generalValue) throws SQLException
	{
		Dao<GeneralValue, Integer> generalValueDao = databaseHelper.getGeneralValueDao();
		generalValueDao.createOrUpdate(generalValue);
	}

	// GeographicLocation

	public GeographicLocation getGeographicLocation(int id) throws SQLException
	{
		Dao<GeographicLocation, Integer> geographicLocationDao = databaseHelper.getGeographicLocationDao();
		QueryBuilder<GeographicLocation, Integer> geographicLocationQB = geographicLocationDao.queryBuilder();

		geographicLocationQB.where().eq(GeographicLocation.DATABASE_ID, id);

		return geographicLocationQB.queryForFirst();
	}

	// GSP

	public List<GSPInfo> getAllGspInfos() throws SQLException
	{
		Dao<GSPInfo, Integer> gspInfoDao = databaseHelper.getGspInfoDao();
		List<GSPInfo> gspInfos = gspInfoDao.queryForAll();

		return gspInfos;
	}

	public GlobalServiceProtocol getGlobalServiceProtocol(String documentId) throws SQLException
	{
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();
		Dao<GSPInfo, Integer> gspInfoDao = databaseHelper.getGspInfoDao();

		QueryBuilder<GlobalServiceProtocol, Integer> globalServiceProtocolQueryBuilder = globalServiceProtocolDao.queryBuilder();
		QueryBuilder<GSPInfo, Integer> gspInfoQueryBuilder = gspInfoDao.queryBuilder();

		gspInfoQueryBuilder.where().eq(GSPInfo.DOCUMENT_ID, documentId);
		globalServiceProtocolQueryBuilder.join(gspInfoQueryBuilder);

		GlobalServiceProtocol globalServiceProtocol = globalServiceProtocolQueryBuilder.queryForFirst();

		return globalServiceProtocol;
	}

	public GlobalServiceProtocol getGlobalServiceProtocol(int id) throws SQLException
	{
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();
		QueryBuilder<GlobalServiceProtocol, Integer> globalServiceProtocolQueryBuilder = globalServiceProtocolDao.queryBuilder();

		globalServiceProtocolQueryBuilder.where().eq(GlobalServiceProtocol.DATABASE_ID, id);

		GlobalServiceProtocol globalServiceProtocol = globalServiceProtocolQueryBuilder.queryForFirst();

		return globalServiceProtocol;
	}

	public List<GlobalServiceProtocol> getGlobalServiceProtocols() throws SQLException
	{
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();

		return globalServiceProtocolDao.queryForAll();
	}

	public void updateGlobalServiceProtocol(GlobalServiceProtocol globalServiceProtocol) throws SQLException
	{
		Dao<GlobalServiceProtocol, Integer> globalServiceProtocolDao = databaseHelper.getGlobalServiceProtocolDao();
		globalServiceProtocolDao.update(globalServiceProtocol);
	}

	// Material

	public Material getMaterial(int id) throws SQLException
	{
		Dao<Material, Integer> materialDao = databaseHelper.getMaterialDao();
		QueryBuilder<Material, Integer> materialQueryBuilder = materialDao.queryBuilder();

		materialQueryBuilder.where().eq(Material.DATABASE_ID, id);

		return materialQueryBuilder.queryForFirst();
	}

	public void createOrUpdateMaterial(Material material) throws SQLException
	{
		Dao<Material, Integer> materialDao = databaseHelper.getMaterialDao();
		materialDao.createOrUpdate(material);
	}

	public void deleteMaterial(Material material) throws SQLException
	{
		Dao<Material, Integer> materialDao = databaseHelper.getMaterialDao();
		materialDao.delete(material);
	}

	// Materials

	public Materials getMaterials(int id) throws SQLException
	{
		Dao<Materials, Integer> materialsDao = databaseHelper.getMaterialsDao();
		QueryBuilder<Materials, Integer> materialsQB = materialsDao.queryBuilder();

		materialsQB.where().eq(Materials.DATABASE_ID, id);

		return materialsQB.queryForFirst();
	}

	public void createOrUpdateMaterials(Materials materials) throws SQLException
	{
		Dao<Materials, Integer> materialsDao = databaseHelper.getMaterialsDao();
		materialsDao.createOrUpdate(materials);
	}

	public void deleteMaterials(Materials materials) throws SQLException
	{
		Dao<Materials, Integer> materialsDao = databaseHelper.getMaterialsDao();
		materialsDao.delete(materials);
	}

	// Organisation

	public void createOrUpdateOrganisation(Organisation organisation) throws SQLException
	{
		Dao<Organisation, Integer> organisationDao = databaseHelper.getOrganisationDao();
		organisationDao.createOrUpdate(organisation);
	}

	// Persons

	public Persons getPersons(int id) throws SQLException
	{
		Dao<Persons, Integer> personsDao = databaseHelper.getPersonsDao();
		QueryBuilder<Persons, Integer> personsQB = personsDao.queryBuilder();

		personsQB.where().eq(Persons.DATABASE_ID, id);

		return personsQB.queryForFirst();
	}

	// StreetAdress

	public StreetAddress getStreetAddress(int id) throws SQLException
	{
		Dao<StreetAddress, Integer> streetAddressDao = databaseHelper.getStreetAddressDao();
		QueryBuilder<StreetAddress, Integer> streetAddressQB = streetAddressDao.queryBuilder();

		streetAddressQB.where().eq(StreetAddress.DATABASE_ID, id);

		return streetAddressQB.queryForFirst();
	}

	// TelephoneNumber

	public TelephoneNumber getTelephoneNumber(int id) throws SQLException
	{
		Dao<TelephoneNumber, Integer> telephoneNumberDao = databaseHelper.getTelephoneNumberDao();
		QueryBuilder<TelephoneNumber, Integer> telephoneNumberQB = telephoneNumberDao.queryBuilder();

		telephoneNumberQB.where().eq(TelephoneNumber.DATABASE_ID, id);

		return telephoneNumberQB.queryForFirst();
	}

	// TimeReport
	public TimeReport getTimeReport(int id) throws SQLException
	{
		Dao<TimeReport, Integer> timeReportDao = databaseHelper.getTimeReportDao();
		QueryBuilder<TimeReport, Integer> timeReportQueryBuilder = timeReportDao.queryBuilder();

		timeReportQueryBuilder.where().eq(TimeReport.DATABASE_ID, id);

		return timeReportQueryBuilder.queryForFirst();
	}

	public void createOrUpdateTimeReport(TimeReport timeReport) throws SQLException
	{
		Dao<TimeReport, Integer> timeReportDao = databaseHelper.getTimeReportDao();
		timeReportDao.createOrUpdate(timeReport);
	}

	public void deleteTimeReport(TimeReport timeReport) throws SQLException
	{
		Dao<TimeReport, Integer> timeReportDao = databaseHelper.getTimeReportDao();
		timeReportDao.delete(timeReport);
	}

	// TimeReports
	public void createOrUpdateTimeReports(TimeReports timeReports) throws SQLException
	{
		Dao<TimeReports, Integer> timeReportsDao = databaseHelper.getTimeReportsDao();
		timeReportsDao.createOrUpdate(timeReports);
	}

	public void deleteTimeReports(TimeReports timeReports) throws SQLException
	{
		Dao<TimeReports, Integer> timeReportsDao = databaseHelper.getTimeReportsDao();
		timeReportsDao.delete(timeReports);
	}

	// WorkEquipment

	public WorkEquipment getWorkEquipment(int id) throws SQLException
	{
		Dao<WorkEquipment, Integer> workEquipmentDao = databaseHelper.getWorkEquipmentDao();
		QueryBuilder<WorkEquipment, Integer> workEquipmentQueryBuilder = workEquipmentDao.queryBuilder();

		workEquipmentQueryBuilder.where().eq(WorkEquipment.DATABASE_ID, id);

		return workEquipmentQueryBuilder.queryForFirst();
	}

	public void createOrUpdateWorkEquipment(WorkEquipment workEquipment) throws SQLException
	{
		Dao<WorkEquipment, Integer> workEquipmentDao = databaseHelper.getWorkEquipmentDao();
		workEquipmentDao.createOrUpdate(workEquipment);
	}

	public void deleteWorkEquipment(WorkEquipment workEquipment) throws SQLException
	{
		Dao<WorkEquipment, Integer> workEquipmentDao = databaseHelper.getWorkEquipmentDao();
		workEquipmentDao.delete(workEquipment);
	}

	// WorkEquipments

	public WorkEquipments getWorkEquipments(int id) throws SQLException
	{
		Dao<WorkEquipments, Integer> workEquipmentsDao = databaseHelper.getWorkEquipmentsDao();
		QueryBuilder<WorkEquipments, Integer> workEquipmentsQB = workEquipmentsDao.queryBuilder();

		workEquipmentsQB.where().eq(WorkEquipments.DATABASE_ID, id);

		return workEquipmentsQB.queryForFirst();
	}

	public void createOrUpdateWorkEquipments(WorkEquipments workEquipments) throws SQLException
	{
		Dao<WorkEquipments, Integer> workEquipmentsDao = databaseHelper.getWorkEquipmentsDao();
		workEquipmentsDao.createOrUpdate(workEquipments);
	}

	public void deleteWorkEquipments(WorkEquipments workEquipments) throws SQLException
	{
		Dao<WorkEquipments, Integer> workEquipmentsDao = databaseHelper.getWorkEquipmentsDao();
		workEquipmentsDao.delete(workEquipments);
	}

	// WorkOrder

	public WorkOrder getWorkOrder(int id) throws SQLException
	{
		Dao<WorkOrder, Integer> workOrderDao = databaseHelper.getWorkOrderDao();
		QueryBuilder<WorkOrder, Integer> workOrderQueryBuilder = workOrderDao.queryBuilder();

		workOrderQueryBuilder.where().eq(WorkOrder.DATABASE_ID, id);

		return workOrderQueryBuilder.queryForFirst();
	}

	public void updateWorkOrder(WorkOrder workOrder) throws SQLException
	{
		Dao<WorkOrder, Integer> workOrderDao = databaseHelper.getWorkOrderDao();
		workOrderDao.update(workOrder);
	}

	// WorkOrderItem

	public WorkOrderItem getWorkOrderItem(int id) throws SQLException
	{
		Dao<WorkOrderItem, Integer> workOrderItemDao = databaseHelper.getWorkOrderItemDao();
		QueryBuilder<WorkOrderItem, Integer> workOrderItemQueryBuilder = workOrderItemDao.queryBuilder();

		workOrderItemQueryBuilder.where().eq(WorkOrderItem.DATABASE_ID, id);

		return workOrderItemQueryBuilder.queryForFirst();
	}

	public void updateWorkOrderItem(WorkOrderItem workOrderItem) throws SQLException
	{
		Dao<WorkOrderItem, Integer> workOrderItemDao = databaseHelper.getWorkOrderItemDao();
		workOrderItemDao.update(workOrderItem);
	}

	// WorkReport

	public WorkReport getWorkReport(int id) throws SQLException
	{
		Dao<WorkReport, Integer> workReportDao = databaseHelper.getWorkReportDao();
		QueryBuilder<WorkReport, Integer> workReportQueryBuilder = workReportDao.queryBuilder();

		workReportQueryBuilder.where().eq(WorkReport.DATABASE_ID, id);

		return workReportQueryBuilder.queryForFirst();
	}

	public void createWorkReport(WorkReport workReport) throws SQLException
	{
		Dao<WorkReport, Integer> workReportDao = databaseHelper.getWorkReportDao();
		workReportDao.create(workReport);
	}

	public void createOrUpdateWorkReport(WorkReport workReport) throws SQLException
	{
		Dao<WorkReport, Integer> workReportDao = databaseHelper.getWorkReportDao();
		workReportDao.createOrUpdate(workReport);
	}

	// WorkReportItem

	public WorkReportItem getWorkReportItem(String workOrderItemId) throws SQLException
	{
		Dao<WorkReportItem, Integer> workReportItemDao = databaseHelper.getWorkReportItemDao();
		QueryBuilder<WorkReportItem, Integer> workReportItemQueryBuilder = workReportItemDao.queryBuilder();

		workReportItemQueryBuilder.where().eq(WorkReportItem.WORK_ORDER_ID, workOrderItemId);

		return workReportItemQueryBuilder.queryForFirst();
	}

	public WorkReportItem getWorkReportItem(int id) throws SQLException
	{
		Dao<WorkReportItem, Integer> workReportItemDao = databaseHelper.getWorkReportItemDao();
		QueryBuilder<WorkReportItem, Integer> workReportItemQueryBuilder = workReportItemDao.queryBuilder();

		workReportItemQueryBuilder.where().eq(WorkReportItem.DATABASE_ID, id);

		return workReportItemQueryBuilder.queryForFirst();
	}

	public void createOrUpdateWorkReportItem(WorkReportItem workReportItem) throws SQLException
	{
		Dao<WorkReportItem, Integer> workReportItemDao = databaseHelper.getWorkReportItemDao();
		workReportItemDao.createOrUpdate(workReportItem);
	}

	// WorkReportItems

	public void createOrUpdateWorkReportItems(WorkReportItems workReportItems) throws SQLException
	{
		Dao<WorkReportItems, Integer> workReportItemsDao = databaseHelper.getWorkReportItemsDao();
		workReportItemsDao.createOrUpdate(workReportItems);
	}

	// WorkStatusLog
	public void createWorkStatusLog(WorkStatusLog workStatusLog) throws SQLException
	{
		Dao<WorkStatusLog, Integer> workStatusLogDoa = databaseHelper.getWorkStatusLogDao();
		workStatusLogDoa.create(workStatusLog);
	}

	public void createOrUpdateWorkStatusLog(WorkStatusLog workStatusLog) throws SQLException
	{
		Dao<WorkStatusLog, Integer> workStatusLogDoa = databaseHelper.getWorkStatusLogDao();
		workStatusLogDoa.createOrUpdate(workStatusLog);
	}

	// WorkStatusLogs
	public void createWorkStatusLogs(WorkStatusLogs workStatusLogs) throws SQLException
	{
		Dao<WorkStatusLogs, Integer> workStatusLogsDao = databaseHelper.getWorkStatusLogsDao();
		workStatusLogsDao.create(workStatusLogs);
	}

	public void createOrUpdateWorkStatusLogs(WorkStatusLogs workStatusLogs) throws SQLException
	{
		Dao<WorkStatusLogs, Integer> workStatusLogsDao = databaseHelper.getWorkStatusLogsDao();
		workStatusLogsDao.createOrUpdate(workStatusLogs);
	}

	// ZeusPart1

	public void createZeusPart1(ZEUSPart1 zeusPart1) throws SQLException
	{
		Dao<ZEUSPart1, Integer> zeusPart1Dao = databaseHelper.getZeusPart1Dao();
		zeusPart1Dao.create(zeusPart1);
	}

	public void createOrUpdateZeusPart1(ZEUSPart1 zeusPart1) throws SQLException
	{
		Dao<ZEUSPart1, Integer> zeusPart1Dao = databaseHelper.getZeusPart1Dao();
		zeusPart1Dao.createOrUpdate(zeusPart1);
	}

	// ZeusPart1Assessment

	public void createZeusPart1Assessement(ZEUSPart1Assessment zeusPart1Assessment) throws SQLException
	{
		Dao<ZEUSPart1Assessment, Integer> zeusPart1AssessmentDao = databaseHelper.getZeusPart1AssessmentDao();
		zeusPart1AssessmentDao.create(zeusPart1Assessment);
	}

	public void createOrUpdateZeusPart1Assessement(ZEUSPart1Assessment zeusPart1Assessment) throws SQLException
	{
		Dao<ZEUSPart1Assessment, Integer> zeusPart1AssessmentDao = databaseHelper.getZeusPart1AssessmentDao();
		zeusPart1AssessmentDao.createOrUpdate(zeusPart1Assessment);
	}

	// ZeusPart2

	public void createZeusPart2(ZEUSPart2 zeusPart2) throws SQLException
	{
		Dao<ZEUSPart2, Integer> zeusPart2Dao = databaseHelper.getZeusPart2Dao();
		zeusPart2Dao.create(zeusPart2);
	}

	public void createOrUpdateZeusPart2(ZEUSPart2 zeusPart2) throws SQLException
	{
		Dao<ZEUSPart2, Integer> zeusPart2Dao = databaseHelper.getZeusPart2Dao();
		zeusPart2Dao.createOrUpdate(zeusPart2);
	}

	// ZeusPart2Assessment

	public void createZeusPart2Assessement(ZEUSPart2Assessment zeusPart2Assessment) throws SQLException
	{
		Dao<ZEUSPart2Assessment, Integer> zeusPart2AssessmentDao = databaseHelper.getZeusPart2AssessmentDao();
		zeusPart2AssessmentDao.create(zeusPart2Assessment);
	}

	public void createOrUpdateZeusPart2Assessement(ZEUSPart2Assessment zeusPart2Assessment) throws SQLException
	{
		Dao<ZEUSPart2Assessment, Integer> zeusPart2AssessmentDao = databaseHelper.getZeusPart2AssessmentDao();
		zeusPart2AssessmentDao.createOrUpdate(zeusPart2Assessment);
	}
}
