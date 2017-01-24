package by.htp6.hospital.dao.pool;

import java.io.Closeable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.dao.pool.exception.ConnectionIsNullException;
import by.htp6.hospital.dao.pool.exception.ConnectionPoolException;

/**
 * Класс пула соединений
 * 
 * Connection pool class
 * 
 * @author Begench Shamuradov, 2017
 */
public class ConnectionPool implements Closeable {
	private static final Logger log = LogManager.getLogger(ConnectionPool.class);

	private static final String DRIVER_NAME = "org.gjt.mm.mysql.Driver";
	private static final String BUNDLE = "resources.dbResource";
	private static final String CONNECTION_STRING_KEY = "db.localConnectionString";
	private static final String USERNAME_KEY = "db.username";
	private static final String PASSWORD_KEY = "db.localPassword";

	static final int POOL_SIZE = 5;

	private BlockingQueue<Connection> freeConnections;
	private BlockingQueue<Connection> busyConnections;

	private final static ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {

	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public void init() throws ConnectionPoolException {
		try {
			Class.forName(DRIVER_NAME);
			ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE);
			String dbUrl = resourceBundle.getString(CONNECTION_STRING_KEY);
			String dbUsername = resourceBundle.getString(USERNAME_KEY);
			String dbPassword = resourceBundle.getString(PASSWORD_KEY);

			freeConnections = new ArrayBlockingQueue<>(POOL_SIZE);
			busyConnections = new ArrayBlockingQueue<>(POOL_SIZE);

			for (int i = 0; i < POOL_SIZE; i++) {
				freeConnections.add(DriverManager.getConnection(dbUrl, dbUsername, dbPassword));
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(ErrorMessage.DRIVER_LOAD_ERROR);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(ErrorMessage.DATABASE_ERROR);
		}
	}

	public Connection take() throws InterruptedException {
		Connection connection = freeConnections.take();
		busyConnections.put(connection);
		return connection;
	}

	public void free(Connection connection) throws InterruptedException {
		if (connection == null) {
			log.error(ErrorMessage.CONNECTION_IS_NULL);
			throw new ConnectionIsNullException(ErrorMessage.CONNECTION_IS_NULL);
		}
		Connection temporaryConnection = connection;
		connection = null;
		busyConnections.remove(temporaryConnection);
		freeConnections.put(temporaryConnection);

	}

	@Override
	public void close() throws IOException {
		if (busyConnections.size() != 0) {
			for (int i = 0; i < busyConnections.size(); i++) {
				try {
					Connection temporaryConnection = busyConnections.take();
					if (temporaryConnection.isClosed() == false) {
						temporaryConnection.close();
					}
				} catch (InterruptedException e) {
					log.error(ErrorMessage.DATABASE_ERROR, e);
				} catch (SQLException e) {
					log.error(ErrorMessage.DATABASE_ERROR, e);
				}
			}
		}

		if (freeConnections.size() != 0) {
			for (int i = 0; i < freeConnections.size(); i++) {
				try {
					Connection temporaryConnection = freeConnections.take();
					if (temporaryConnection.isClosed() == false) {
						temporaryConnection.close();
					}
				} catch (InterruptedException e) {
					log.error(ErrorMessage.DATABASE_ERROR, e);
				} catch (SQLException e) {
					log.error(ErrorMessage.DATABASE_ERROR, e);
				}
			}
		}

	}
}
