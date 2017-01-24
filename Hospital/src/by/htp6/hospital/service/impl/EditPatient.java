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
import by.htp6.hospital.dao.EditPatientDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.EditPatientFactory;
import by.htp6.hospital.service.EditPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис по измененю данных о пациенте
 * 
 * Patient edit service
 * 
 * @author Begench Shamuradov, 2017
 */
public class EditPatient implements EditPatientService {
	private static final Logger log = LogManager.getLogger(EditPatient.class);

	@Override
	public void editPatitnt(int patientId, String name, String surname, 
			String sex, String birthDate, String diagnosis,
			String note) throws ServiceException {
		
		try {
			
			validate(name, surname, sex, birthDate, diagnosis);
			
			Matcher matcher = PatternContainer.patientNamePattern.matcher(name);
			if (!matcher.find()) {
				log.error(ErrorMessage.NAME_FROMAT);
				throw new ServiceException(ErrorMessage.NAME_FROMAT);
			}

			matcher = PatternContainer.patientNamePattern.matcher(surname);
			if (!matcher.find()) {
				log.error(ErrorMessage.SURNAME_FROMAT);
				throw new ServiceException(ErrorMessage.SURNAME_FROMAT);
			}

			matcher = PatternContainer.diagnosisPattern.matcher(diagnosis);
			if (!matcher.find()) {
				log.error(ErrorMessage.DIAGNOSIS_FORMAT);
				throw new ServiceException(ErrorMessage.DIAGNOSIS_FORMAT);
			}

			Date date = null;
			matcher = PatternContainer.enDateFormatPattern.matcher(birthDate);
			if (!matcher.find()) {
				matcher = PatternContainer.ruDateFormatPattern.matcher(birthDate);
				if (!matcher.find()) {
					log.error(ErrorMessage.BIRTH_DATE_FORMAT);
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
					log.error(ErrorMessage.NOTE_FORMAT);
					throw new ServiceException(ErrorMessage.NOTE_FORMAT);
				}
			}
			
			EditPatientFactory daoFactory = EditPatientFactory.getInstance();
			EditPatientDAO editPatientDAO = daoFactory.getEditPatientDAO();

			editPatientDAO.editPatient(patientId, name, surname, sex, date, diagnosis, note);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	private void validate(String name, String surname, String sex, 
			String birthDate, String diagnosis)
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

		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
}
