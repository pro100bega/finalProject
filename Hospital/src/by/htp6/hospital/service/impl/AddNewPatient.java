package by.htp6.hospital.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.AddNewPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.AddNewPatientFactory;
import by.htp6.hospital.service.AddNewPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис добавления пациента
 * 
 * Patient adding service
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddNewPatient implements AddNewPatientService {
	private static final Logger log = LogManager.getLogger(AddNewPatient.class);

	@Override
	public void addNewPatient(String name, String surname, String sex, 
			String birthDate, String diagnosis, int doctorId,
			String note) throws ServiceException {

		try {
			
			validate(name, surname, sex, birthDate, diagnosis, doctorId);
			
			Matcher matcher = PatternContainer.patientNamePattern.matcher(name);
			if (!matcher.find()) {
				throw new ServiceException(ErrorMessage.NAME_FROMAT);
			}

			matcher = PatternContainer.patientNamePattern.matcher(surname);
			if (!matcher.find()) {
				throw new ServiceException(ErrorMessage.SURNAME_FROMAT);
			}

			matcher = PatternContainer.diagnosisPattern.matcher(diagnosis);
			if (!matcher.find()) {
				throw new ServiceException(ErrorMessage.DIAGNOSIS_FORMAT);
			}

			Date date = null;
			matcher = PatternContainer.enDateFormatPattern.matcher(birthDate);
			if (!matcher.find()) {
				matcher = PatternContainer.ruDateFormatPattern.matcher(birthDate);
				if (!matcher.find()) {
					throw new ServiceException(ErrorMessage.BIRTH_DATE_FORMAT);
				}

				SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT);
				try {
					date = dateFormat.parse(birthDate);
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
					throw new ServiceException(e);
				}
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.EN_DATE_FORMAT);
				try {
					date = dateFormat.parse(birthDate);
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
					throw new ServiceException(e);
				}
			}

			if (note != null) {
				matcher = PatternContainer.notePatern.matcher(note);
				if (!matcher.find()) {
					throw new ServiceException(ErrorMessage.NOTE_FORMAT);
				}
			}

			AddNewPatientFactory daoFactory = AddNewPatientFactory.getInstance();
			AddNewPatientDAO addNewPatientDAO = daoFactory.getAddNewPatientDAO();

			addNewPatientDAO.addNewPatient(name, surname, sex, date, diagnosis, doctorId, note);
		} catch (DAOException e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e);
			
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	private void validate(String name, String surname, String sex,
			String birthDate, String diagnosis, int doctorId)
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");

		if (null == name || "".equals(name)) {
			errorMessage.append(FieldName.PATIENT_NAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (null == surname || "".equals(surname)) {
			errorMessage.append(FieldName.PATIENT_SURNAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (!"м".equals(sex) && !"ж".equals(sex)) {
			errorMessage.append(FieldName.SEX + ": " + 
					ErrorMessage.WRONG_FORMAT + " ");
		}
		
		if (null == birthDate || "".equals(birthDate)) {
			errorMessage.append(FieldName.BIRTH_DATE + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (null == diagnosis || "".equals(diagnosis)) {
			errorMessage.append(FieldName.PATIENT_NAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (doctorId <= 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " + 
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
