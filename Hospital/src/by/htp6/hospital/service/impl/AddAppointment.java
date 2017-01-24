package by.htp6.hospital.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.AddAppointmentDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.AddAppointmentFactory;
import by.htp6.hospital.service.AddAppointmentService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис добавления назначения
 * 
 * Appointment adding service
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddAppointment implements AddAppointmentService {
	private static final Logger log = LogManager.getLogger(AddAppointment.class);

	@Override
	public void addAppointment(int patientId, int doctorId, String type,
			String name, String termDate, String termTime)
			throws ServiceException {
		try {
			validate(patientId, doctorId, type, name, termDate, termTime);

			Matcher matcher = PatternContainer.appointmentNamePattern.matcher(name);

			if (!matcher.find()) {
				throw new ServiceException(ErrorMessage.APPOINTMENT_NAME_FORMAT);
			}

			SimpleDateFormat termDateFormat;
			matcher = PatternContainer.enDateFormatPattern.matcher(termDate);
			if (!matcher.find()) {
				matcher = PatternContainer.ruDateFormatPattern.matcher(termDate);
				if (!matcher.find()) {
					throw new ServiceException(ErrorMessage.TERM_DATE_FORMAT);
				}

				termDateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FORMAT_HM);

			} else {
				termDateFormat = new SimpleDateFormat(DateFormat.EN_DATE_FORMAT_HM);
			}

			String fullDate = termDate + " " + termTime;

			AddAppointmentFactory daoFactory = AddAppointmentFactory.getInstance();
			AddAppointmentDAO assignProcedureDAO = daoFactory.getAddAppointmentDAO();

			Timestamp termDateTimestamp = new Timestamp(termDateFormat.parse(fullDate).getTime());

			assignProcedureDAO.addAppointment(patientId, doctorId, type, name, termDateTimestamp);
		} catch (ValidationException e) {
			
			throw new ServiceException(e);
		} catch (DAOException e) {

			throw new ServiceException(e);
		} catch (ParseException e) {

			log.error(e.getMessage(), e);
			throw new ServiceException(e);
		}

	}

	private void validate(int patientId, int doctorId, String type, 
			String name, String termDate, String termTime)
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");

		if (patientId == 0) {
			errorMessage.append(FieldName.PATIENT_ID + " " + 
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}

		if (doctorId == 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " + 
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}

		if (null == type || "".equals(type)) {
			errorMessage.append(FieldName.APPOINTMENT_TYPE + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}

		if (null == name || "".equals(name)) {
			errorMessage.append(FieldName.APPOINTMENT_NAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}

		if (null == name || "".equals(name)) {
			errorMessage.append(FieldName.APPOINTMENT_NAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}

		if (null == termDate || "".equals(termDate)) {
			errorMessage.append(FieldName.APPOINTMENT_TERM_DATE + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}

		if (null == termTime || "".equals(termTime)) {
			errorMessage.append(FieldName.APPOINTMENT_TERM_TIME + " " + 
					ErrorMessage.CANT_BE_EMPTY);
		}

		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
