package by.htp6.hospital.dao.pool;

import java.io.IOException;

import by.htp6.hospital.dao.pool.exception.ConnectionPoolException;

/**
 * Класс инициализации пула соединений
 * 
 * Connection pool initializing class
 * 
 * @author Begench Shamuradov, 2017
 */
public class SourceInit {
	private static final SourceInit instance = new SourceInit();

	private SourceInit() {}
	
	public static SourceInit getInstance(){
		return instance;
	}

	public void init() throws ConnectionPoolException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
	}

	public void destroy() throws IOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.close();
	}
}
