package by.htp6.hospital.dao;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetPatientDAO {
	Patient getPatient(int patientId) throws DAOException;
}
