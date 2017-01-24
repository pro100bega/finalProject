package by.htp6.hospital.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.FieldName;
import by.htp6.hospital.dao.SignInDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.SignInFactory;
import by.htp6.hospital.service.SignInService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.exception.ValidationException;
import by.htp6.hospital.tool.MD5Encryptor;
import by.htp6.hospital.tool.PatternContainer;

/**
 * Сервис входа пользователя в систему
 * 
 * User log in service
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignIn implements SignInService{
	private static final Logger log = LogManager.getLogger(SignIn.class);

	@Override
	public User signIn(String username, String password) throws ServiceException {
		
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
			
			SignInFactory daoFactory = SignInFactory.getInstance();
			
			SignInDAO logInDao = daoFactory.getSignInDAO();			
			
			String encryptedPassword = MD5Encryptor.getHashCode(password);
			User user = logInDao.signIn(username, encryptedPassword);
			return user;
			
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
