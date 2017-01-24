package by.htp6.hospital.dao.impl;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.FindLogDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для поиска лога в базе данных
 * 
 * Class for searching log in database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLFindLogDAO implements FindLogDAO {
	private static final Logger log = LogManager.getLogger(SQLFindLogDAO.class);

	@Override
	public List<Log> findLog(String searchData) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		try {
			connection = connectionPool.take();
			
			callableStatement = connection.prepareCall(SqlQuery.FIND_LOG);
			callableStatement.setString(1, searchData);
			ResultSet resultSet = callableStatement.executeQuery();
			
			Timestamp sqlTime;
			SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT_HM);
			// Log list
			List<Log> logList = new ArrayList<>();
			// Single log object
			Log log;
			while (resultSet.next()){
				int id = resultSet.getInt(DatabaseColumnName.ID);
				String message = resultSet.getString(DatabaseColumnName.MESSAGE);
				String tableName = resultSet.getString(DatabaseColumnName.TABLE_NAME);
				int rowId = resultSet.getInt(DatabaseColumnName.ROW_ID);
				sqlTime = resultSet.getTimestamp(DatabaseColumnName.TIME);
				String time = dateFormat.format(sqlTime);
				log = new Log(id, message, tableName, rowId, time);
				logList.add(log);
			}
			
			if (logList.size() == 0){
				logList = Collections.emptyList();
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
