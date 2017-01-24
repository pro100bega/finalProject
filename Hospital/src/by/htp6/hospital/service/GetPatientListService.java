package by.htp6.hospital.service;

import java.util.List;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetPatientListService {
	List<Patient> getPatientsListForDoctor(int doctorId, int offset,
			int patientsPerPage, String orderBy) throws ServiceException;
	
	List<Patient> getAllPatientsList(int offset, int patientsPerPage,
			String orderBy) throws ServiceException;
}
