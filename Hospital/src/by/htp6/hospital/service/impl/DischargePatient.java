package by.htp6.hospital.service.impl;

import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.DischargePatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.DischargePatientFactory;
import by.htp6.hospital.service.DischargePatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис выписки пациента из больницы
 * 
 * Patient discharge service
 * 
 * @author Begench Shamuradov, 2017
 */
public class DischargePatient implements DischargePatientService{
	private static final Logger log = LogManager.getLogger(DischargePatient.class);

	@Override
	public boolean dischargePatient(int patientId, String finalDiagnosis) 
			throws ServiceException {			
		try {
			validate(patientId, finalDiagnosis);
			
			Matcher matcher = PatternContainer.diagnosisPattern.matcher(finalDiagnosis);
			if (!matcher.find()){
				throw new ServiceException(ErrorMessage.DIAGNOSIS_FORMAT);
			}
			
			DischargePatientFactory daoFactory = DischargePatientFactory.getInstance();
			DischargePatientDAO dischargePatientDAO = daoFactory.getDischargePatientDAO();
			return dischargePatientDAO.dishcargePatient(patientId, finalDiagnosis);
		} catch (DAOException e) {

			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(int patientId, String finalDiagnosis) 
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");

		if (patientId <= 0) {
			errorMessage.append(FieldName.PATIENT_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (null == finalDiagnosis || "".equals(finalDiagnosis)) {
			errorMessage.append(FieldName.DIAGNOSIS + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}

		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
	
}
