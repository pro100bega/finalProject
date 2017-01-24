package by.htp6.hospital.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.PerformAppointmentDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.PerformAppointmentFactory;
import by.htp6.hospital.service.PerformAppointmentService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис по выполнению назначения
 * 
 * Appointment performing service
 * 
 * @author Begench Shamuradov, 2017
 */
public class PerformAppointment implements PerformAppointmentService{
	private static final Logger log = LogManager.getLogger(PerformAppointment.class);
	
	private static final String OPERATION = "операция";
	
	@Override
	public void performAppointment(int appointmentId, String userType, String appointmentType)
			throws ServiceException {
		
		try {
			validate(userType, appointmentId, appointmentType);
			
			if (!UserType.DOCTOR.equals(userType) && 
					!UserType.NURSE.equals(userType)){
				
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}
			
			if (UserType.NURSE.equals(userType) && OPERATION.equals(appointmentType)){
				
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}
			
			PerformAppointmentFactory daoFactory = PerformAppointmentFactory.getInstance();
			PerformAppointmentDAO performAppointmentDAO = daoFactory.getPerformAppointmentDAO();
			
			performAppointmentDAO.performAppointment(appointmentId);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(String userType, int appointmentId, String appointmentType) 
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == userType || "".equals(userType)) {
			errorMessage.append(FieldName.USER_TYPE + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (appointmentId <= 0) {
			errorMessage.append(FieldName.APPOINTMENT_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (null == appointmentType || "".equals(appointmentType)) {
			errorMessage.append(FieldName.APPOINTMENT_TYPE + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
			
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
