package by.htp6.hospital.dao.impl;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.SignUpDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для проверки данных пользователя в базе данных и регистрации пользователя
 * 
 * Class for checking user data in database and signing up user
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLSignUpDAO implements SignUpDAO {
	private static final Logger log = LogManager.getLogger(SQLSignUpDAO.class);
	
	private static final String DEFAULT_USER = "guest";
	
	@Override
	public void signUp(String username, String password) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.take();
			if (!checkUsernameInBase(username, connection)) {
				preparedStatement = connection.prepareStatement(
						SqlQuery.SIGN_UP);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, DEFAULT_USER);
				preparedStatement.executeUpdate();

			} else {
				log.error(ErrorMessage.USERNAME_IS_ALREADY_EXIST + " :"
						+ username);
				throw new DAOException(ErrorMessage.USERNAME_IS_ALREADY_EXIST + " :"
						+ username);
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

	private boolean checkUsernameInBase(String username, Connection connection) throws DAOException {
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall(
					SqlQuery.CHECK_USERNAME);
			callableStatement.setString(1, username);
			ResultSet resultSet = callableStatement.executeQuery();
			if (resultSet.next()) {
				String answer = resultSet.getString(1);
				return (answer.equals("yes")) ? true : false;
			} else {
				log.error(ErrorMessage.DATABASE_ERROR);
				throw new DAOException();
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			try {
				if (callableStatement != null && !callableStatement.isClosed()) {
					callableStatement.close();
				}

			} catch (SQLException e) {
				log.error(ErrorMessage.UNABLE_TO_CLOSE_STATEMENT);
			}
		}
	}
}
