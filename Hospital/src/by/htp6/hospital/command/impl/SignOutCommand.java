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
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error("Unable to sign out! User hasn`t signed in.");
			response.sendRedirect(Url.ERROR);
			
		} else {
			User user = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
			log.info("User " + user.getUsername() + " has signed out.");
			session.invalidate();
			response.sendRedirect(Url.INDEX);
		}
	}

}
