package by.htp6.hospital.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.PerformAppointmentDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для выполнения назначения в базе данных
 * 
 * Class for performing appointment in database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLPerformAppointmentDAO implements PerformAppointmentDAO{
	private static final Logger log = LogManager.getLogger(SQLPerformAppointmentDAO.class);
	
	@Override
	public void performAppointment(int appointmentId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
			
			preparedStatement = connection.prepareStatement(
					SqlQuery.PERFORM_APPOINTMENT);
			preparedStatement.setInt(1, appointmentId);
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
