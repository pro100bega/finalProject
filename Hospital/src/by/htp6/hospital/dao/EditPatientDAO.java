package by.htp6.hospital.dao;

import java.util.Date;

import by.htp6.hospital.dao.exception.DAOException;

public interface EditPatientDAO {
	void editPatient(int patientId, String name, String surname, String sex, 
			Date birthDate, String diagnosis, String note) throws DAOException;
}
