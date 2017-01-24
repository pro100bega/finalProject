package by.htp6.hospital.service;

import org.junit.Before;

import org.junit.Test;

import by.htp6.hospital.service.FindLogService;
import by.htp6.hospital.service.exception.ServiceException;

public class FindLog {
	
	private FindLogService findLog;

	@Before
	public void before() {
		findLog = new by.htp6.hospital.service.impl.FindLog();
	}

	@Test(expected = ServiceException.class)
	public void testFindLog() throws ServiceException {
		findLog.findLog("ins", "doctor");
	}
}
