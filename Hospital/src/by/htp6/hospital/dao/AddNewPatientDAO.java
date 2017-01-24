package by.htp6.hospital.dao;

import java.util.Date;

import by.htp6.hospital.dao.exception.DAOException;

public interface AddNewPatientDAO {
	void addNewPatient(String name, String surname, String sex, 
			Date birthDate, String diagnosis, int doctorId, String note) 
					throws DAOException;
}
