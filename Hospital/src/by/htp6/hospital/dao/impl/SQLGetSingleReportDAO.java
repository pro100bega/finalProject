package by.htp6.hospital.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetSingleReportDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения конкретной жалобы и предложения из базы данных
 * 
 * Class for getting single report from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetSingleReportDAO implements GetSingleReportDAO {
	private static final Logger log = LogManager.getLogger(SQLGetSingleReportDAO.class);
	
	@Override
	public Report getReport(int reportId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.take();

			preparedStatement = connection.prepareStatement(
					SqlQuery.GET_SINGLE_REPORT);

			preparedStatement.setInt(1, reportId);
			ResultSet resultSet = preparedStatement.executeQuery();

			SimpleDateFormat timeFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT_HM);
			Report report = null;
			if (resultSet.next()) {
				changeStatus(connection, reportId);

				int id = resultSet.getInt(DatabaseColumnName.ID);
				String header = resultSet.getString(DatabaseColumnName.HEADER);
				String message = resultSet.getString(DatabaseColumnName.MESSAGE);
				Timestamp date = resultSet.getTimestamp(DatabaseColumnName.TIME);
				String time = timeFormat.format(date);
				String status = resultSet.getString(DatabaseColumnName.STATUS);
				report = new Report(id, header, message, time, status);
			} else {
				log.error(ErrorMessage.WRONG_REPORT_ID + " :" + reportId);
				throw new DAOException(ErrorMessage.WRONG_REPORT_ID + " :" + reportId);
			}

			return report;

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

	private void changeStatus(Connection connection, int reportId) throws DAOException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(
					SqlQuery.CHANGE_REPORT_STATUS);
			preparedStatement.setInt(1, reportId);
			preparedStatement.executeUpdate();

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
