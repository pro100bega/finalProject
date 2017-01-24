package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetLogDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения лога из базы данных
 * 
 * Class for getting log from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetLogDAO implements GetLogDAO{
	private static final Logger log = LogManager.getLogger(SQLGetLogDAO.class);
	
	@Override
	public List<Log> getLog() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
	
			preparedStatement = connection.prepareStatement(SqlQuery.GET_LOG);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT_HM);
			Timestamp sqlDate;
			Log log;
			List<Log> logList = new ArrayList<>();			
			while(resultSet.next()){
				int id = resultSet.getInt(DatabaseColumnName.ID);
				String message = resultSet.getString(DatabaseColumnName.MESSAGE);
				String tableName = resultSet.getString(DatabaseColumnName.TABLE_NAME);
				int rowId = resultSet.getInt(DatabaseColumnName.ROW_ID);
				sqlDate = resultSet.getTimestamp(DatabaseColumnName.TIME);
				String time = dateFormat.format(sqlDate);
				log = new Log(id, message, tableName, rowId, time);
				logList.add(log);
			}
			
			return logList;
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
