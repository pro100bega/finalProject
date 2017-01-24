package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface GetPatientsCountService {
	int getPatientsCountByDoctorId(int doctorId) throws ServiceException;
	
	int getPatientsCountByDoctorId(String searchData, int doctorId) 
			throws ServiceException;
	
	int getPatientsCount() throws ServiceException;
	
	int getPatientsCount(String searchData) throws ServiceException;
}
