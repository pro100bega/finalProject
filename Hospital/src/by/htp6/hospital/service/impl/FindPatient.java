package by.htp6.hospital.service.impl;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.FindPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.FindPatientFactory;
import by.htp6.hospital.service.FindPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис по поиску пациентов
 * 
 * Patient search service
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindPatient implements FindPatientService{
	private static final Logger log = LogManager.getLogger(FindPatient.class);

	@Override
	public List<Patient> findPatientsByDoctorId(String searchData, int doctorId,
			int offset, int count) throws ServiceException {
		
		try {
			
			validate(searchData, doctorId, offset, count);
			
			Matcher matcher = PatternContainer.diagnosisPattern.matcher(searchData);
			if (!matcher.find()){
				log.error(ErrorMessage.DIAGNOSIS_FORMAT);
				throw new ServiceException(ErrorMessage.DIAGNOSIS_FORMAT);
			}
			
			FindPatientFactory daoFactory = FindPatientFactory.getInstance();
			FindPatientDAO findPatientDAO = daoFactory.getFindPatientDAO();
			
			List<Patient> patients = findPatientDAO.findPatientsByDoctorId(searchData,
					doctorId, offset, count);
			return patients;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Patient> findPatients(String searchData, int offset, int count)
			throws ServiceException {
		
		try {
			
			validate(searchData, offset, count);
			
			Matcher matcher = PatternContainer.diagnosisPattern.matcher(searchData);
			if (!matcher.find()){
				log.error(ErrorMessage.DIAGNOSIS_FORMAT);
				throw new ServiceException(ErrorMessage.DIAGNOSIS_FORMAT);
			}
			
			FindPatientFactory daoFactory = FindPatientFactory.getInstance();
			FindPatientDAO findPatientDAO = daoFactory.getFindPatientDAO();
			
			List<Patient> patients = findPatientDAO.findPatients(searchData,
					offset, count);
			return patients;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(String searchData, int doctorId,
			int offset, int count) throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == searchData || "".equals(searchData)) {
			errorMessage.append(FieldName.SEARCH_DATA + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (doctorId <= 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (offset < 0) {
			errorMessage.append(FieldName.OFFSET + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (count <= 0) {
			errorMessage.append(FieldName.COUNT + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
	
	private void validate(String searchData, int offset, int count) 
			throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == searchData || "".equals(searchData)) {
			errorMessage.append(FieldName.SEARCH_DATA + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (offset < 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (count <= 0) {
			errorMessage.append(FieldName.COUNT + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
}
