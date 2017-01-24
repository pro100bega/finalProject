package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.dao.exception.DAOException;

public interface FindPatientDAO {
	List<Patient> findPatientsByDoctorId(String searchData,
			int doctorId, int offset, int count) throws DAOException;
	
	List<Patient> findPatients(String searchData, int offset, int count)
			throws DAOException;
}
