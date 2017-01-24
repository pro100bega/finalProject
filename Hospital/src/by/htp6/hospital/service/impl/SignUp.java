package by.htp6.hospital.service.impl;

import java.security.NoSuchAlgorithmException;

import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.SignUpDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.SignUpFactory;
import by.htp6.hospital.service.SignUpService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.MD5Encryptor;
import by.htp6.hospital.tool.PatternContainer;

public class SignUp implements SignUpService{
	private static final Logger log = LogManager.getLogger(SignUp.class);

	@Override
	public void signUp(String username, String password) throws ServiceException {

		try {
			validate(username, password);
			
			Matcher matcher = PatternContainer.usernamePattern.matcher(username);
			if (!matcher.find()){
				log.error(ErrorMessage.USERNAME_FORMAT);
				throw new ServiceException(ErrorMessage.USERNAME_FORMAT);
			}
			
			matcher = PatternContainer.passwordPattern.matcher(password);
			if (!matcher.find()){
				log.error(ErrorMessage.PASSWORD_FORMAT);
				throw new ServiceException(ErrorMessage.PASSWORD_FORMAT);
			}
			
			SignUpFactory daoFactory = SignUpFactory.getInstance();
			SignUpDAO logUpDAO = daoFactory.getSignUpDAO();
			
			String encryptedPassword = MD5Encryptor.getHashCode(password);
			logUpDAO.signUp(username, encryptedPassword);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ServiceException(e);
		}	
	}
	
	private void validate(String username, String password) 
			throws ValidationException {
		StringBuilder errorMessage = new StringBuilder("");
		
		if (null == username || "".equals(username)) {
			errorMessage.append(FieldName.USERNAME + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (null == password || "".equals(password)) {
			errorMessage.append(FieldName.PASSWORD + " " + 
					ErrorMessage.CANT_BE_EMPTY + " ");
		}
		
		if (errorMessage.length() != 0) {
			log.error(ErrorMessage.ILLEGAL_ARGUMENTS);
			log.error(errorMessage);
			throw new ValidationException(ErrorMessage.ILLEGAL_ARGUMENTS);
		}
	}

	
}
