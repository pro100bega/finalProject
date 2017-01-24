package by.htp6.hospital.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.GetPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetPatientFactory;
import by.htp6.hospital.service.GetPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения конкретного пациента
 * 
 * Single patient getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatient implements GetPatientService{
	private static final Logger log = LogManager.getLogger(GetPatient.class);

	@Override
	public Patient getPatient(int patientId) throws ServiceException {
		
		try {
			
			validate(patientId);
			
			GetPatientFactory daoFactory = GetPatientFactory.getInstance();
			GetPatientDAO getPatientDAO = daoFactory.getGetPatientDAO();
			
			Patient patient = getPatientDAO.getPatient(patientId);
			return patient;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(int patientId) throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (patientId <= 0) {
			errorMessage.append(FieldName.PATIENT_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
	
}
