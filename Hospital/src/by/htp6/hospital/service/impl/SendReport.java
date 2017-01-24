package by.htp6.hospital.service.impl;

import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.SendReportDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.SendReportFactory;
import by.htp6.hospital.service.SendReportService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис отправки сообщений об ошибках
 * 
 * Error report sending service
 * 
 * @author Begench Shamuradov, 2017
 */
public class SendReport implements SendReportService{
	private static final Logger log = LogManager.getLogger(SendReport.class);

	@Override
	public void sendReport(String header, String message) throws ServiceException {
		
		try {
			validate(header, message);
			
			Matcher matcher = PatternContainer.headerPattern.matcher(header);
			
			if (!matcher.find()){
				log.error(ErrorMessage.HEADER_FORMAT);
				throw new ServiceException(ErrorMessage.HEADER_FORMAT);
			}
			
			matcher = PatternContainer.messagePattern.matcher(message);
			
			if (!matcher.find()){
				log.error(ErrorMessage.MESSAGE_FORMAT);
				throw new ServiceException(ErrorMessage.MESSAGE_FORMAT);
			}
			
			SendReportFactory daoFactory = SendReportFactory.getInstance();
			SendReportDAO sendReportDAO = daoFactory.getSendReportDAO();
			
			sendReportDAO.sendReport(header, message);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}
		
	}
	
	private void validate(String header, String message) 
			throws ValidationException {
		
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == header || "".equals(header)) {
			errorMessage.append(FieldName.HEADER + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (null == message || "".equals(message)) {
			errorMessage.append(FieldName.MESSAGE + " " +
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

}
