package by.htp6.hospital.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.GetPatientListDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetPatientListFactory;
import by.htp6.hospital.service.GetPatientListService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения списка пациентов
 * 
 * Patient list getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientList implements GetPatientListService {
	private static final Logger log = LogManager.getLogger(GetPatientList.class);

	@Override
	public List<Patient> getPatientsListForDoctor(int doctorId, int offset,
			int patientsPerPage, String orderBy) throws ServiceException {
		
		try {
			
			validate(doctorId, offset, patientsPerPage);

			GetPatientListFactory daoFactory = GetPatientListFactory.getInstance();
			GetPatientListDAO getPatientListDAO = daoFactory.getGetPatientListDAO();
			
			List<Patient> patients = getPatientListDAO.getPatientListForDoctor(doctorId,
					offset, patientsPerPage, orderBy);
			return patients;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Patient> getAllPatientsList(int offset, int patientsPerPage,
			String orderBy) throws ServiceException {
		
		try {
			
			validate(offset, patientsPerPage);

			GetPatientListFactory daoFactory = GetPatientListFactory.getInstance();
			GetPatientListDAO getPatientListDAO = daoFactory.getGetPatientListDAO();
			
			List<Patient> patients = getPatientListDAO.getAllPatientList(offset,
					patientsPerPage, orderBy);
			return patients;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(int doctorId, int offset,
			int patientsPerPage) throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (doctorId <= 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (offset < 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (patientsPerPage <= 0) {
			errorMessage.append(FieldName.PATIENTS_PER_PAGE + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
	
	private void validate(int offset, int patientsPerPage) 
			throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (offset < 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (patientsPerPage <= 0) {
			errorMessage.append(FieldName.PATIENTS_PER_PAGE + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
