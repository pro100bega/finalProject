package by.htp6.hospital.service;

import java.util.List;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.service.exception.ServiceException;

public interface FindPatientService {
	List<Patient> findPatientsByDoctorId(String searchData, int doctorId,
			int offset, int count) throws ServiceException;
	
	List<Patient> findPatients(String searchData, int offset, int count)
			throws ServiceException;
}