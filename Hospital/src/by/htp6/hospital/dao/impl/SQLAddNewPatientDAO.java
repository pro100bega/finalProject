package by.htp6.hospital.dao.impl;

import java.sql.Connection;


import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.AddNewPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для добавления нового пациента в базу данных
 * 
 * Class for adding new patient to database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLAddNewPatientDAO implements AddNewPatientDAO {
	private static final Logger log = LogManager.getLogger(SQLAddNewPatientDAO.class);
	
	@Override
	public void addNewPatient(String name, String surname, String sex, 
			Date birthDate, String diagnosis, int doctorId,
				String note) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connectionPool.take();

			preparedStatement = connection.prepareStatement(SqlQuery.ADD_NEW_PATIENT);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.setString(3, sex);
			preparedStatement.setDate(4, new java.sql.Date(birthDate.getTime()));
			preparedStatement.setString(5, diagnosis);
			preparedStatement.setInt(6, doctorId);
			preparedStatement.setString(7, note);
			preparedStatement.executeUpdate();
			
			log.debug("Patient " + name + " " + surname + " was added");

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
