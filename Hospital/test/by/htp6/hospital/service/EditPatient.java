package by.htp6.hospital.service;

import org.junit.Before;

import org.junit.Test;

import by.htp6.hospital.service.EditPatientService;
import by.htp6.hospital.service.exception.ServiceException;

public class EditPatient {

	private EditPatientService editPatient;

	@Before
	public void before() {
		editPatient = new by.htp6.hospital.service.impl.EditPatient();
	}

	@Test(expected = ServiceException.class)
	public void testEditPatient() throws ServiceException {
		editPatient = new by.htp6.hospital.service.impl.EditPatient();
		editPatient.editPatitnt(0, "Вася", "Иванов", "м", "awf", "111", null);
	}
}
