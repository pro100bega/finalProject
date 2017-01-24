package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.AddAppointmentDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для добавления назначения в базу данных
 * 
 * Class for adding new appointment to database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLAddAppointmentDAO implements AddAppointmentDAO {
	private static final Logger log = LogManager.getLogger(SQLAddAppointmentDAO.class);
	
	private static final String DEFAULT_STATUS = "undone";

	@Override
	public void addAppointment(int patientId, int doctorId, String type, String name, Timestamp term)
			throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connectionPool.take();

			preparedStatement = connection.prepareStatement(SqlQuery.ADD_APPOINTMENT);
			preparedStatement.setInt(1, patientId);
			preparedStatement.setInt(2, doctorId);
			preparedStatement.setString(3, type);
			preparedStatement.setString(4, name);
			preparedStatement.setString(5, DEFAULT_STATUS);
			preparedStatement.setTimestamp(6, term);
			preparedStatement.executeUpdate();

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
