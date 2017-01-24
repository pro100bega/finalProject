package by.htp6.hospital.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.GetPatientsCountDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetPatientsCountFactory;
import by.htp6.hospital.service.GetPatientsCountService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения количества пациентов
 * 
 * Single patient getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientsCount implements GetPatientsCountService {
	private static final Logger log = LogManager.getLogger(GetPatientsCount.class);

	@Override
	public int getPatientsCountByDoctorId(int doctorId) throws ServiceException {

		try {
			validate(doctorId);

			GetPatientsCountFactory daoFactory = GetPatientsCountFactory.getInstance();
			GetPatientsCountDAO getPatientsCountDAO = daoFactory.getGetPatientsCountDAO();
			
			int patientsCount = getPatientsCountDAO.getPatientsCountByDoctorId(doctorId);
			return patientsCount;
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getPatientsCount() throws ServiceException {
		GetPatientsCountFactory daoFactory = GetPatientsCountFactory.getInstance();
		GetPatientsCountDAO getPatientsCountDAO = daoFactory.getGetPatientsCountDAO();

		try {
			int patientsCount = getPatientsCountDAO.getPatientsCount();
			return patientsCount;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getPatientsCountByDoctorId(String searchData, int doctorId) 
			throws ServiceException {
		
		try {
			validate(doctorId, searchData);

			GetPatientsCountFactory daoFactory = GetPatientsCountFactory.getInstance();
			GetPatientsCountDAO getPatientsCountDAO = daoFactory.getGetPatientsCountDAO();
			
			int patientsCount = getPatientsCountDAO.
					getPatientsCountByDoctorId(searchData, doctorId);
			return patientsCount;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getPatientsCount(String searchData) throws ServiceException {

		try {
			validate(searchData);

			GetPatientsCountFactory daoFactory = GetPatientsCountFactory.getInstance();
			GetPatientsCountDAO getPatientsCountDAO = daoFactory.getGetPatientsCountDAO();
			
			int patientsCount = getPatientsCountDAO.getPatientsCount(searchData);
			return patientsCount;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	private void validate(int doctorId) throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (doctorId <= 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

	private void validate(String searchData) throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == searchData || "".equals(searchData)) {
			errorMessage.append(FieldName.SEARCH_DATA + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

	private void validate(int doctorId, String searchData) 
			throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (doctorId <= 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}
		
		if (null == searchData || "".equals(searchData)) {
			errorMessage.append(FieldName.SEARCH_DATA + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
}
