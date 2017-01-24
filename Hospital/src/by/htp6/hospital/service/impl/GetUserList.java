package by.htp6.hospital.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.GetUserListDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetUserListFactory;
import by.htp6.hospital.service.GetUserListService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения списка пользователей
 * 
 * User list getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUserList implements GetUserListService{
	private static final Logger log = LogManager.getLogger(GetUserList.class);
	
	@Override
	public List<User> getUserList(String userType, int offset, int count) 
			throws ServiceException {
		
		try {
			validate(userType, offset, count);
			
			if (!UserType.ADMIN.equals(userType)) {
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}
			
			GetUserListFactory daoFactory = GetUserListFactory.getInstance();
			GetUserListDAO getUserListDAO = daoFactory.getGetUserListDAO();
			
			List<User> users = getUserListDAO.getUserList(offset, count);
			return users;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(String userType, int offset, int count) 
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == userType || "".equals(userType)) {
			errorMessage.append(FieldName.USER_TYPE + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}
		
		if (offset < 0) {
			errorMessage.append(FieldName.DOCTOR_ID + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (count <= 0) {
			errorMessage.append(FieldName.PATIENTS_PER_PAGE + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
