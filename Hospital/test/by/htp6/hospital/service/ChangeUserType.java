package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.ChangeUserTypeService;
import by.htp6.hospital.service.exception.ServiceException;

public class ChangeUserType {
	
	private ChangeUserTypeService changeUserType;

	@Before
	public void before() {
		changeUserType = new by.htp6.hospital.service.impl.ChangeUserType();
	}
	
	@Test(expected = ServiceException.class)
	public void testChangeUserType() throws ServiceException {
		changeUserType.changeUserType("doctor", "doctor", 6);
	}
}
