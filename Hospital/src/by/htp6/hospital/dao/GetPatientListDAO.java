package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetPatientListDAO {
	List<Patient> getPatientListForDoctor(int doctorId, 
			int offset, int patientsPerPage, String orderBy) throws DAOException;
	
	List<Patient> getAllPatientList(int offset, int patientsPerPage, String orderBy)
			throws DAOException;
}
