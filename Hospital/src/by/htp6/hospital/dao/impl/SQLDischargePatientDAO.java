package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.DischargePatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для удаления пациента из базы данных
 * 
 * Class for deleting patient from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLDischargePatientDAO implements DischargePatientDAO {
	private static final Logger log = LogManager.getLogger(SQLDischargePatientDAO.class);
	
	@Override
	public boolean dishcargePatient(int patientId, String finalDiagnosis) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.take();
			setFinalDiagnosis(connection, patientId, finalDiagnosis);
		
			preparedStatement = connection.prepareStatement(SqlQuery.DISCHARGE_PATIENT);
			preparedStatement.setInt(1, patientId);
			int resultOfUpdate = preparedStatement.executeUpdate();
			return (resultOfUpdate == 0) ? false : true;
			
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

	private void setFinalDiagnosis(Connection connection, int patientId, String finalDiagnosis) throws DAOException {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SqlQuery.SET_FINAL_DIAGNOSIS);
			preparedStatement.setString(1, finalDiagnosis);
			preparedStatement.setInt(2, patientId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
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
		}
	}
}