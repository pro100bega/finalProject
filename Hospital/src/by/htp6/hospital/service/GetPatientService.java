package by.htp6.hospital.service;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetPatientService {
	Patient getPatient(int patientId) throws ServiceException;
}
