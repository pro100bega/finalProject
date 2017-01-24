package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения конкретного пациента из базы данных
 * 
 * Class for getting single patient from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetPatientDAO implements GetPatientDAO{
	private static final Logger log = LogManager.getLogger(SQLGetPatientDAO.class);
	
	@Override
	public Patient getPatient(int patientId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
			
			preparedStatement = connection.prepareStatement(SqlQuery.GET_PATIENT);
			preparedStatement.setInt(1, patientId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			SimpleDateFormat birthDateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT);
			SimpleDateFormat receiptDateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);
			
			int id = resultSet.getInt(DatabaseColumnName.ID);
			String name = resultSet.getString(DatabaseColumnName.NAME);
			String surname = resultSet.getString(DatabaseColumnName.SURNAME);
			String diagnosis = resultSet.getString(DatabaseColumnName.DIAGNOSIS);
			String sex = resultSet.getString(DatabaseColumnName.SEX);
			Date birthDate = resultSet.getDate(DatabaseColumnName.BIRTH_DATE);
			String stringBirthDate = birthDateFormat.format(birthDate);
			Timestamp receiptDate = resultSet.getTimestamp(DatabaseColumnName.RECEIPT_DATE);
			int doctorId = resultSet.getInt(DatabaseColumnName.DOCTOR_ID);
			String stringReceiptDate = receiptDateFormat.format(receiptDate);
			String note = resultSet.getString(DatabaseColumnName.NOTE);
			Patient patient = new Patient(id, name, surname, sex, stringBirthDate,
					diagnosis, doctorId, stringReceiptDate, note);
			
			return patient;
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
