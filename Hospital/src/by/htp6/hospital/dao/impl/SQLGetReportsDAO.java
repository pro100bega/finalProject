package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetReportsDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения списка жалоб и предложений из базы данных
 * 
 * Class for getting reports list from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetReportsDAO implements GetReportsDAO {
	private static final Logger log = LogManager.getLogger(SQLGetPatientsCountDAO.class);
	
	@Override
	public List<Report> getReports(int offset, int count) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
			
			preparedStatement = connection.prepareStatement(SqlQuery.GET_REPORTS);
			
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, count);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Report> reports = new ArrayList<>();
			
			
			SimpleDateFormat timeFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT_HM);
			while (resultSet.next()){
				int id = resultSet.getInt(DatabaseColumnName.ID);
				String header = resultSet.getString(DatabaseColumnName.HEADER);
				String message = resultSet.getString(DatabaseColumnName.MESSAGE);
				Timestamp date = resultSet.getTimestamp(DatabaseColumnName.TIME);
				String time = timeFormat.format(date);
				String status = resultSet.getString(DatabaseColumnName.STATUS);
				reports.add(new Report(id, header, message, time, status));
			}
			
			if (reports.size() == 0){
				reports = Collections.emptyList();
			}

			return reports;
			
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
