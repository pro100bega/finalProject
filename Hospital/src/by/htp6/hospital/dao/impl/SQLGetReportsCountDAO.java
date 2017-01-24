package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetReportsCountDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения количества жалоб и предложений в базе данных
 * 
 * Class for getting reports count in database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetReportsCountDAO implements GetReportsCountDAO{
	private static final Logger log = LogManager.getLogger(SQLGetReportsCountDAO.class);
	
	@Override
	public int getReportsCount() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connectionPool.take();

			preparedStatement = connection.prepareStatement(SqlQuery.GET_REPORTS_COUNT);
			ResultSet resultSet = preparedStatement.executeQuery();
			int reportsCount = 0;
			if (resultSet.next()) {
				reportsCount = resultSet.getInt(1);
			}

			return reportsCount;
			
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
