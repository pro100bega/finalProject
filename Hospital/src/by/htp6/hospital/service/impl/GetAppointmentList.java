package by.htp6.hospital.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Appointment;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.GetAppointmentListDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetAppointmentListFactory;
import by.htp6.hospital.service.GetAppointmentListService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения списка назначений
 * 
 * Appointment list getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetAppointmentList implements GetAppointmentListService{
	private static final Logger log = LogManager.getLogger(GetAppointmentList.class);

	@Override
	public List<Appointment> getAppointmentList(int patientId, String status) 
			throws ServiceException {
		
		try {
			
			validate(patientId, status);

			GetAppointmentListFactory daoFactory = GetAppointmentListFactory.getInstance();
			GetAppointmentListDAO getAppointmentLitDAO = daoFactory.getGetAppointmentListDAO();
			
			List<Appointment> appointments = getAppointmentLitDAO.
					getAppointmentList(patientId, status);
			return appointments;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(int patientId, String status) throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (patientId <= 0) {
			errorMessage.append(FieldName.PATIENT_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (null == status || "".equals(status)) {
			errorMessage.append(FieldName.STATUS + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
}
