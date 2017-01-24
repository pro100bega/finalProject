package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.ChangeUserTypeService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для изменения типа пользователя
 * 
 * Command designed to change user type
 * 
 * @author Begench Shamuradov, 2017
 */
public class ChangeUserTypeCommand implements Command{
	
	private static final String GET_USER_LIST_COMMAND = 
			"controller?command=GET_USER_LIST";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		User user = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
		String authorisedUserType = user.getType();
		
		String newType = request.getParameter(ParameterName.NEW_TYPE);
		int userId = Integer.parseInt(request.getParameter(ParameterName.ID));
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ChangeUserTypeService changeUserType = serviceFactory.getChangeUserType();
		
		try {
			changeUserType.changeUserType(authorisedUserType, newType, userId);
			
			response.sendRedirect(GET_USER_LIST_COMMAND);
			
		} catch (ServiceException e) {
			
			response.sendRedirect(Url.ERROR);
		}
	}

}
