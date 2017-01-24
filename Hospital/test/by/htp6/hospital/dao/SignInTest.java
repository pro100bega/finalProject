package by.htp6.hospital.dao;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.dao.SignInDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.impl.SQLSignInDAO;
import by.htp6.hospital.dao.pool.ConnectionPool;
import by.htp6.hospital.dao.pool.exception.ConnectionPoolException;

public class SignInTest {
	private static final String USERNAME = "test";
	private static final String PASSWORD = "test";
	
	@Before
	public void ConnectionPoolInit() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.init();
		} catch (ConnectionPoolException e1) {
			fail("ConnectionPool was not initialized");
		}
	}
	
	@Test
	public void testSignIn() {
		
		
		SignInDAO signInDAO = new SQLSignInDAO();
		
		try {
			User user = signInDAO.signIn(USERNAME, PASSWORD);
			
			assertNotNull(user);
			
		} catch (DAOException e) {
			fail("Excepion was thrown");
		}
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
