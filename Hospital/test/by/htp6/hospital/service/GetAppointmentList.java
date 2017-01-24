package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.GetAppointmentListService;
import by.htp6.hospital.service.exception.ServiceException;

public class GetAppointmentList {
	
	private GetAppointmentListService getAppointmentList;

	@Before
	public void before() {
		getAppointmentList = new by.htp6.hospital.service.impl.GetAppointmentList();
	}

	@Test(expected = ServiceException.class)
	public void testGetAppointmentList() throws ServiceException {
		getAppointmentList.getAppointmentList(10, null);
	}
}
