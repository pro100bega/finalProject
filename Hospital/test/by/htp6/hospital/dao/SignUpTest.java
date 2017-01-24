package by.htp6.hospital.dao;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.dao.SignUpDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.impl.SQLSignUpDAO;
import by.htp6.hospital.dao.pool.ConnectionPool;
import by.htp6.hospital.dao.pool.exception.ConnectionPoolException;

public class SignUpTest {
	private static final String USERNAME = "test";
	private static final String PASSWORD = "test";

	@Before
	public void ConnectionPoolInit() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.init();
		} catch (ConnectionPoolException e) {
			fail("ConnectionPool was not initialized");
		}
	}

	@Test(expected = DAOException.class)
	public void testSignUp() throws DAOException {
		SignUpDAO signUpDAO = new SQLSignUpDAO();

		signUpDAO.signUp(USERNAME, PASSWORD);

	}

	@After
	public void ConnectionPoolClose() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.close();
		} catch (IOException e) {
			fail("Connection pool was not closed");
		}
	}

}
