package by.htp6.hospital.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.GetReportsDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetReportsFactory;
import by.htp6.hospital.service.GetReportsService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения списка жалоб и предложений
 * 
 * Report list getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetReports implements GetReportsService {
	private static final Logger log = LogManager.getLogger(GetReports.class);

	@Override
	public List<Report> getReports(String userType, int offset, int count) throws ServiceException {

		try {
			validate(userType, offset, count);
			
			if (!UserType.ADMIN.equals(userType)) {
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}

			GetReportsFactory daoFactory = GetReportsFactory.getInstance();
			GetReportsDAO getReportsDAO = daoFactory.getGetReportsDAO();
			
			List<Report> reports = getReportsDAO.getReports(offset, count);
			return reports;

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
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (offset < 0) {
			errorMessage.append(FieldName.OFFSET + " " +
					ErrorMessage.CANT_BE_LESS_THAN_ZERO + " ");
		}
		
		if (count <= 0) {
			errorMessage.append(FieldName.COUNT + " " +
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
