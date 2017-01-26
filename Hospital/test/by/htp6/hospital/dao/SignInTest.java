package by.htp6.hospital.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.dao.SignInDAO;
import by.htp6.hospital.dao.exception.DAOException;

public class SignInTest {
	private static final String USERNAME = "test";
	private static final String PASSWORD = "test";
	private SignInDAO signInDAO;
	private User expected = new User(1, USERNAME, PASSWORD, null, null);
	
	@Before
	public void ConnectionPoolInit() {

		try {
			signInDAO = mock(SignInDAO.class);
			when(signInDAO.signIn(USERNAME, PASSWORD)).thenReturn(
					new User(1, USERNAME, PASSWORD, null, null));
		} catch (DAOException e) {
			fail();
		}
	}
	
	@Test
	public void testSignIn() {
		
		try {
			User actual;
			actual = signInDAO.signIn(USERNAME, PASSWORD);
			assertEquals(expected, actual);
		} catch (DAOException e) {
			fail();
		}
	}	
}
