package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.GetPatientService;
import by.htp6.hospital.service.exception.ServiceException;

public class GetPatient {

	private GetPatientService getPatient;
	
	@Before
	public void before() {
			getPatient = new by.htp6.hospital.service.impl.GetPatient();
	}

	@Test(expected = ServiceException.class)
	public void testGetPatient() throws ServiceException {
		getPatient.getPatient(0);
	}
}
