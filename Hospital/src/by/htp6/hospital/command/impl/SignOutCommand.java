package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;

/**
 * Команда предназначенная для выхода пользователя из системы
 * 
 * Command designed to sign out user
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignOutCommand implements Command {

	private static final Logger log = LogManager.getLogger(SignOutCommand.class);
	
	private static final String USER = "User ";
	
	private static final String SIGNED_OUT = " has signed out";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			log.error(ErrorMessage.UNABLE_TO_SIGN_OUT);
			response.sendRedirect(Url.INDEX);
			
		} else {
			User user = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
			log.info(USER + user.getUsername() + SIGNED_OUT);
			session.invalidate();
			response.sendRedirect(Url.INDEX);
		}
	}

}
