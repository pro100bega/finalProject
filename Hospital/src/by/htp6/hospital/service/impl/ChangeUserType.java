package by.htp6.hospital.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.ChangeUserTypeDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.ChangeUserTypeFactory;
import by.htp6.hospital.service.ChangeUserTypeService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис по изменению типа пользователя
 * 
 * User type changing service
 * 
 * @author Begench Shamuradov, 2016
 */
public class ChangeUserType implements ChangeUserTypeService {
	private static final Logger log = LogManager.getLogger(ChangeUserType.class);

	@Override
	public void changeUserType(String authorisedUserType, String type, int userId) 
			throws ServiceException {
		if (!UserType.ADMIN.equals(authorisedUserType)) {
			throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
		}
		try {
			validate(type, userId);
			
			ChangeUserTypeFactory daoFactory = ChangeUserTypeFactory.getInstance();
			ChangeUserTypeDAO changeUserTypeDao = daoFactory.getChangeUserTypeDAO();
			changeUserTypeDao.changeUserType(type, userId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	private void validate(String type, int userId) throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (!UserType.ADMIN.equals(type) && !UserType.DOCTOR.equals(type)
				&& !UserType.NURSE.equals(type)
				&& !UserType.GUEST.equals(type)) {
			errorMessage.append(ErrorMessage.USER_TYPE_FORMAT + " ");
		}

		if (userId <= 0) {
			errorMessage.append(FieldName.USER_ID + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}

		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
