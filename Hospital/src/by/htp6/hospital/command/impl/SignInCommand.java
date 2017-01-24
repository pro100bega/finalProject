package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.service.SignInService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для входа пользователя в систему
 * 
 * Command designed to sign in user
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignInCommand implements Command{

	private static final String GET_ADMIN_PANEL_COMMAND =
			"controller?command=GET_ADMIN_INFO";
	private static final String GET_PATIENT_LIST_COMMAND =
			"controller?command=GET_PATIENT_LIST" +
						"&currentPage=1&patientsPerPage=13";
	private static final String TRUE_MESSAGE = "true";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String username = request.getParameter(ParameterName.USERNAME);
		String password = request.getParameter(ParameterName.PASSWORD);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SignInService loginUserService = serviceFactory.getSignInUser();
		
		User user = null;
		try {
			user = loginUserService.signIn(username, password);
			String url = null;
			switch(user.getType()){
			case UserType.DOCTOR:
				url = GET_PATIENT_LIST_COMMAND;
				break;
			case UserType.ADMIN:
				url = GET_ADMIN_PANEL_COMMAND;
				break;
			case UserType.NURSE:
				url = GET_PATIENT_LIST_COMMAND;
				break;
			case UserType.GUEST :
				url = Url.INDEX;
				break;
			default :
				url = Url.INDEX;
				break;
			}
			
			session.setAttribute(ParameterName.AUTHORISED_USER, user);
			
			response.sendRedirect(url);
			
		} catch (ServiceException e) {
			
			request.setAttribute(ParameterName.ERROR, TRUE_MESSAGE);
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.INDEX);
			dispatcher.forward(request, response);
		}
		
		
	}
	
}
