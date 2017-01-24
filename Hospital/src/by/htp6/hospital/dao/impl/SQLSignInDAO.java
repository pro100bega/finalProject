package by.htp6.hospital.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.SignInDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для проверки данных пользователя в базе данных и авторизации пользователя
 * 
 * Class for checking user data in database and signing in user
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLSignInDAO implements SignInDAO {
	private static final Logger log = LogManager.getLogger(SQLSignInDAO.class);
	
	@Override
	public User signIn(String username, String password) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();

			preparedStatement = connection.prepareStatement(
					SqlQuery.SIGN_IN);
			preparedStatement.setString(1, username);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DateFormat.RU_DATE_FORMAT_HM);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (password.equals(resultSet.getString(DatabaseColumnName.PASSWORD))) {
					int id = resultSet.getInt(DatabaseColumnName.ID);
					String type = resultSet.getString(DatabaseColumnName.TYPE);
					Timestamp dbTime = resultSet.getTimestamp(DatabaseColumnName.CREATE_TIME);
					String createTime = dateFormat.format(dbTime);
					
					User user = new User(id, username, password, type, createTime);
					
					return user;
				} else {
					log.error(ErrorMessage.INCORRECT_PASSWORD_OR_USERNAME);
					throw new DAOException(ErrorMessage.INCORRECT_PASSWORD_OR_USERNAME);
				}
			} else {
				throw new DAOException(ErrorMessage.INCORRECT_PASSWORD_OR_USERNAME);
			}
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
