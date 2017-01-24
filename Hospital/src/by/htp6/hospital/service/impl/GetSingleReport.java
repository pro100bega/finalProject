package by.htp6.hospital.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.dao.GetSingleReportDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetSingleReportFactory;
import by.htp6.hospital.service.GetSingleReportService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;

/**
 * Сервис получения конкретной жалобы
 * 
 * Single report getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetSingleReport implements GetSingleReportService{
	private static final Logger log = LogManager.getLogger(GetSingleReport.class);

	@Override
	public Report getReport(String userType, int reportId) throws ServiceException {
		
		try {
			validate(userType, reportId);
			
			if (!UserType.ADMIN.equals(userType)) {
				log.error(ErrorMessage.INSUFFICIENT_PERMISSION);
				throw new ServiceException(ErrorMessage.INSUFFICIENT_PERMISSION);
			}
			
			GetSingleReportFactory daoFactory = GetSingleReportFactory.getInstance();
			GetSingleReportDAO getSingleReportDAO = daoFactory.getGetSingleReportDAO();
			
			Report report = getSingleReportDAO.getReport(reportId);
			
			return report;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
	}
	
	private void validate(String userType, int reportId) throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == userType || "".equals(userType)) {
			errorMessage.append(FieldName.USER_TYPE + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (reportId <= 0) {
			errorMessage.append(FieldName.REPORT_ID + " " + 
					ErrorMessage.CANT_BE_ZERO_OR_LESS);
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
