package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface DischargePatientDAO {
	boolean dishcargePatient(int patientId, String finalDiagnosis) throws DAOException;
}
