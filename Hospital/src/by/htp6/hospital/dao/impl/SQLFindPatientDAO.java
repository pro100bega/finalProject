package by.htp6.hospital.dao.impl;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import by.htp6.hospital.dao.FindPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для поиска пациента в базе данных
 * 
 * Class for searching patient in database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLFindPatientDAO implements FindPatientDAO{
	private static final Logger log = LogManager.getLogger(SQLFindPatientDAO.class);

	
	@Override
	public List<Patient> findPatientsByDoctorId(String searchData,
			int doctorId, int offset, int count) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		try {
			
			connection = connectionPool.take();
			callableStatement = connection.prepareCall(SqlQuery.FIND_PATIENT_BY_DOCTOR_ID);
			callableStatement.setString(1, searchData);
			callableStatement.setInt(2, doctorId);
			callableStatement.setInt(3, offset);
			callableStatement.setInt(4, count);
			ResultSet resultSet = callableStatement.executeQuery();
			
			// Patients list
			List<Patient> patients = new ArrayList<Patient>();
			// Single patient object
			
			SimpleDateFormat birthDateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT);
			SimpleDateFormat receiptDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);
			Patient patient = null;
			while (resultSet.next()){
				int patientId = resultSet.getInt(DatabaseColumnName.ID);
				String name = resultSet.getString(DatabaseColumnName.NAME);
				String surname = resultSet.getString(DatabaseColumnName.SURNAME);
				String sex = resultSet.getString(DatabaseColumnName.SEX);
				String birthDate = birthDateFormat.format(resultSet.getDate(
						DatabaseColumnName.BIRTH_DATE));
				String diagnosis = resultSet.getString(DatabaseColumnName.DIAGNOSIS);
				String receiptDate = receiptDateFormat.format(resultSet.getTimestamp(
						DatabaseColumnName.RECEIPT_DATE));
				String note = resultSet.getString(DatabaseColumnName.NOTE);
				patient = new Patient(patientId, name, surname, sex, birthDate,
						diagnosis, doctorId, receiptDate, note);
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
				if (callableStatement != null && !callableStatement.isClosed()) {
					callableStatement.close();
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
	public List<Patient> findPatients(String searchData, int offset, int count)
			throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		try {
			
			connection = connectionPool.take();
			callableStatement = connection.prepareCall(SqlQuery.FIND_PATIENT);
			callableStatement.setString(1, searchData);
			callableStatement.setInt(2, offset);
			callableStatement.setInt(3, count);
			ResultSet resultSet = callableStatement.executeQuery();
			
			// Patients list
			List<Patient> patients = new ArrayList<Patient>();
			// Single patient object
			
			SimpleDateFormat birthDateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT);
			SimpleDateFormat receiptDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);
			Patient patient = null;
			while (resultSet.next()){
				int patientId = resultSet.getInt(DatabaseColumnName.ID);
				String name = resultSet.getString(DatabaseColumnName.NAME);
				String surname = resultSet.getString(DatabaseColumnName.SURNAME);
				String sex = resultSet.getString(DatabaseColumnName.SEX);
				String birthDate = birthDateFormat.format(resultSet.getDate(
						DatabaseColumnName.BIRTH_DATE));
				String diagnosis = resultSet.getString(DatabaseColumnName.DIAGNOSIS);
				String receiptDate = receiptDateFormat.format(resultSet.getTimestamp(
						DatabaseColumnName.RECEIPT_DATE));
				int doctorId = resultSet.getInt(DatabaseColumnName.DOCTOR_ID);
				String note = resultSet.getString(DatabaseColumnName.NOTE);
				patient = new Patient(patientId, name, surname, sex, birthDate,
						diagnosis, doctorId, receiptDate, note);
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
				if (callableStatement != null && !callableStatement.isClosed()) {
					callableStatement.close();
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
