package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.AddAppointmentService;
import by.htp6.hospital.service.exception.ServiceException;

public class AddAppointment {
	
	private AddAppointmentService addAppointment;
	
	@Before
	public void before() {
		addAppointment = new by.htp6.hospital.service.impl.AddAppointment();
	}
	
	@Test(expected = ServiceException.class)
	public void testAddAppointment() throws ServiceException {
		
		addAppointment.addAppointment(0, 21, "укол", "ампицелин", "", "");
	}
}
