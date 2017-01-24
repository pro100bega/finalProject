package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface AddNewPatientService {
	void addNewPatient(String name, String surname, String sex,
			String birthDate, String diagnosis, int doctorId, String note) 
					throws ServiceException;
}
