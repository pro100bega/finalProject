package by.htp6.hospital.service.impl;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.FindLogDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.FindLogFactory;
import by.htp6.hospital.service.FindLogService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис по поиску логов базы данных
 * 
 * Database log search service
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindLog implements FindLogService{
	private static final Logger log = LogManager.getLogger(FindLog.class);

	@Override
	public List<Log> findLog(String searchData, String userType) throws ServiceException {	
		
		try {
			
			if (!UserType.ADMIN.equals(userType)){
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}
			
			validate(searchData);
			
			Matcher matcher = PatternContainer.logSearchPattern.matcher(searchData);
			if (!matcher.find()){
				log.error(ErrorMessage.SEARCH_DATA_FORMAT);
				throw new ServiceException(ErrorMessage.SEARCH_DATA_FORMAT);
			}
			
			FindLogFactory daoFactory = FindLogFactory.getInstance();
			FindLogDAO findPatientDAO = daoFactory.getFindLogDAO();
			
			List<Log> logList = findPatientDAO.findLog(searchData);
			return logList;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
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

}
