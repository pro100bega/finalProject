package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface GetPatientsCountDAO {
	int getPatientsCountByDoctorId(int doctorId) throws DAOException;
	
	int getPatientsCountByDoctorId(String searchData, int doctorId)
			throws DAOException;
	
	int getPatientsCount() throws DAOException;
	
	int getPatientsCount(String searchData) throws DAOException;
}
