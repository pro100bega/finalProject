package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.SuccessMessage;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.SignUpService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для регистрации нового пользователя
 * 
 * Command designed to sign up new user
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignUpCommand implements Command{
	private static final Logger log = LogManager.getLogger(SignUpCommand.class);

	private static final String TRUE_MESSAGE = "true";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
				
		String username = request.getParameter(ParameterName.USERNAME);
		String password = request.getParameter(ParameterName.PASSWORD);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SignUpService logUpUserService = serviceFactory.getSignUpUser();

		try {
			logUpUserService.signUp(username, password);
			request.setAttribute(ParameterName.SUCCESS_MESSAGE, SuccessMessage.SIGN_UP);
			
			log.info("User " + username + " was successfully signed up.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.SUCCESS);
			dispatcher.forward(request, response);
			
		} catch (ServiceException e) {
			
			request.setAttribute(ParameterName.ERROR, TRUE_MESSAGE);
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.SIGN_UP);
			dispatcher.forward(request, response);
		}
				
	}
	
}
