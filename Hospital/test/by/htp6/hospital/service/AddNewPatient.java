package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.AddNewPatientService;
import by.htp6.hospital.service.exception.ServiceException;

public class AddNewPatient {

	private AddNewPatientService addNewPatient;

	@Before
	public void before() {
		addNewPatient = new by.htp6.hospital.service.impl.AddNewPatient();
	}
	
	@Test(expected = ServiceException.class)
	public void testAddNewPatient() throws ServiceException {
		
		addNewPatient.addNewPatient("Вася", "Иванов", "м", "awf", "111", 0, null);
	}
}
