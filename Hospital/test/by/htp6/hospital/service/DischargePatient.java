package by.htp6.hospital.service;

import org.junit.Before;
import org.junit.Test;

import by.htp6.hospital.service.DischargePatientService;
import by.htp6.hospital.service.exception.ServiceException;

public class DischargePatient {
	private DischargePatientService dischargePatient;
	
	@Before
	public void before() {
		dischargePatient = new by.htp6.hospital.service.impl.DischargePatient();
	}
	
	@Test(expected = ServiceException.class)
	public void testDischargePatient() throws ServiceException {
		dischargePatient.dischargePatient(0, "");
	}
}
