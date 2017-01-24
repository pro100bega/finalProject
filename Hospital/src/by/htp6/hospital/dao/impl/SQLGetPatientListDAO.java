package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetPatientListDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения списка пациентов из базы данных
 * 
 * Class for getting patients list from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetPatientListDAO implements GetPatientListDAO {
	private static final Logger log = LogManager.getLogger(SQLGetPatientListDAO.class);

	@Override
	public List<Patient> getPatientListForDoctor(int doctorId, int offset, 
			int patientsPerPage, String orderBy)
			throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.take();
			String sql;

			if (orderBy != null && !"".equals(orderBy)) {
				switch (orderBy) {
				case "name":
					sql = SqlQuery.GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_NAME;
					break;
				case "surname":
					sql = SqlQuery.GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_SURNAME;
					break;
				case "diagnosis":
					sql = SqlQuery.GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_DIAGNOSIS;
					break;
				default:
					sql = SqlQuery.GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_RECEIPT;
					break;
				}
			} else {
				sql = SqlQuery.GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_RECEIPT;
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, patientsPerPage);

			SimpleDateFormat birthDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT);
			SimpleDateFormat receiptDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt(DatabaseColumnName.ID);
				String name = resultSet.getString(DatabaseColumnName.NAME);
				String surname = resultSet.getString(DatabaseColumnName.SURNAME);
				String diagnosis = resultSet.getString(DatabaseColumnName.DIAGNOSIS);
				String sex = resultSet.getString(DatabaseColumnName.SEX);
				Date birthDate = resultSet.getDate(DatabaseColumnName.BIRTH_DATE);
				String stringBirthDate = birthDateFormat.format(birthDate);
				Timestamp receiptDate = resultSet.getTimestamp(DatabaseColumnName.RECEIPT_DATE);
				String stringReceiptDate = receiptDateFormat.format(receiptDate);
				String note = resultSet.getString(DatabaseColumnName.NOTE);
				Patient patient = new Patient(id, name, surname, sex, stringBirthDate,
						diagnosis, doctorId,
						stringReceiptDate, note);
				patients.add(patient);
			}

			return patients;

		} catch (InterruptedException e) {
			log.error(ErrorMessage.UNABLE_TO_TAKE_CONNECTION, e);
			throw new DAOException(e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				log.error(ErrorMessage.UNABLE_TO_CLOSE_STATEMENT, e);
			}

			try {
				if (connection != null) {
					connectionPool.free(connection);
				}
			} catch (InterruptedException e) {
				log.error(ErrorMessage.UNABLE_TO_FREE_CONNECTION, e);
			}

		}
	}

	@Override
	public List<Patient> getAllPatientList(int offset, int patientsPerPage,
			String orderBy) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
			String sql;

			if (orderBy != null && !"".equals(orderBy)) {
				switch (orderBy) {
				case "name":
					sql = SqlQuery.GET_ALL_PATIENTS_LIST_ORDER_BY_NAME;
					break;
				case "surname":
					sql = SqlQuery.GET_ALL_PATIENTS_LIST_ORDER_BY_SURNAME;
					break;
				case "diagnosis":
					sql = SqlQuery.GET_ALL_PATIENTS_LIST_ORDER_BY_DIAGNOSIS;
					break;
				default:
					sql = SqlQuery.GET_ALL_PATIENTS_LIST_ORDER_BY_RECEIPT;
					break;
				}
			} else {
				sql = SqlQuery.GET_ALL_PATIENTS_LIST_ORDER_BY_RECEIPT;
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, patientsPerPage);

			SimpleDateFormat birthDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT);
			SimpleDateFormat receiptDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while (resultSet.next()) {

				int id = resultSet.getInt(DatabaseColumnName.ID);
				String name = resultSet.getString(DatabaseColumnName.NAME);
				String surname = resultSet.getString(DatabaseColumnName.SURNAME);
				String diagnosis = resultSet.getString(DatabaseColumnName.DIAGNOSIS);
				String sex = resultSet.getString(DatabaseColumnName.SEX);
				Date birthDate = resultSet.getDate(DatabaseColumnName.BIRTH_DATE);
				int doctorId = resultSet.getInt(DatabaseColumnName.DOCTOR_ID);
				String stringBirthDate = birthDateFormat.format(birthDate);
				Timestamp receiptDate = resultSet.getTimestamp(DatabaseColumnName.RECEIPT_DATE);
				String stringReceiptDate = receiptDateFormat.format(receiptDate);
				String note = resultSet.getString(DatabaseColumnName.NOTE);
				Patient patient = new Patient(id, name, surname, sex, stringBirthDate,
						diagnosis, doctorId, stringReceiptDate, note);

				patients.add(patient);
			}

			return patients;

		} catch (InterruptedException e) {
			log.error(ErrorMessage.UNABLE_TO_TAKE_CONNECTION, e);
			throw new DAOException(e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				log.error(ErrorMessage.UNABLE_TO_CLOSE_STATEMENT, e);
			}

			try {
				if (connection != null) {
					connectionPool.free(connection);
				}
			} catch (InterruptedException e) {
				log.error(ErrorMessage.UNABLE_TO_FREE_CONNECTION, e);
			}

		}
	}
}
