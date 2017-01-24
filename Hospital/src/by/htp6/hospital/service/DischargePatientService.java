package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface DischargePatientService {
	boolean dischargePatient(int patientId, String finalDiagnosis)
		throws ServiceException;
}
