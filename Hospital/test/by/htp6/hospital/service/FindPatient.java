package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.FindPatientService;
import by.htp6.hospital.service.exception.ServiceException;

public class FindPatient {

	private FindPatientService findPatient;

	@Before
	public void before() {
		findPatient = new by.htp6.hospital.service.impl.FindPatient();
	}

	@Test(expected = ServiceException.class)
	public void testFindPatients() throws ServiceException {
		findPatient.findPatients("Вася", -1, 5);
	}

	@Test(expected = ServiceException.class)
	public void testFindPatientByDoctorId() throws ServiceException {
		findPatient.findPatientsByDoctorId("Вася", 21, -1, 5);
	}
}
