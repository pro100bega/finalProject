package by.htp6.hospital.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.GetLogDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetLogFactory;
import by.htp6.hospital.service.GetLogService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения лога базы данных
 * 
 * Database log getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetLog implements GetLogService {
	private static final Logger log = LogManager.getLogger(GetLog.class);

	@Override
	public List<Log> getLog(String userType) throws ServiceException {
		
		try {
			
			validate(userType);

			if (!UserType.ADMIN.equals(userType)) {
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}

			GetLogFactory daoFactory = GetLogFactory.getInstance();
			GetLogDAO getLogDAO = daoFactory.getGetLogDAO();
			List<Log> logs;
			
			logs = getLogDAO.getLog();
			return logs;
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}

	private void validate(String userType) throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");

		if (null == userType || "".equals(userType)) {
			errorMessage.append(FieldName.USER_TYPE + " " +
					ErrorMessage.CANT_BE_EMPTY);
		}

		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}
}
