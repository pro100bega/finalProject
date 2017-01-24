package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface EditPatientService {
	void editPatitnt(int patientId, String name, String surname, String sex,
			String birthDate, String diagnosis, String note) throws ServiceException;
}
