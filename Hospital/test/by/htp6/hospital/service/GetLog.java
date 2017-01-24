package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.GetLogService;
import by.htp6.hospital.service.exception.ServiceException;

public class GetLog {
	
	private GetLogService getLog;

	@Before
	public void before() {

		getLog = new by.htp6.hospital.service.impl.GetLog();

	}

	@Test(expected = ServiceException.class)
	public void testGetLog() throws ServiceException {

		getLog.getLog("doctor");
	}

	@Test(expected = ServiceException.class)
	public void testGetLog2() throws ServiceException {

		getLog.getLog("doctor");
	}
}
