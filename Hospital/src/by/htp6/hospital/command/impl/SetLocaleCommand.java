package by.htp6.hospital.command.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;

/**
 * Команда предназначенная для изменения языка интерфейса
 * 
 * Command designed to change interface language
 * 
 * @author Begench Shamuradov, 2017
 */
public class SetLocaleCommand implements Command {

	private static final String RUSSIAN_LOCALE = "ru_RU";
	private static final String ENGLISH_LOCALE = "en_US";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String locale = request.getParameter(ParameterName.LOCALE_NAME);
		String redirect = request.getParameter(ParameterName.REDIRECT);
		HttpSession session = request.getSession(true);		
		switch(locale){
			case RUSSIAN_LOCALE:
				session.setAttribute(ParameterName.LOCALE_NAME, RUSSIAN_LOCALE);
				response.sendRedirect(redirect);
				break;
			case ENGLISH_LOCALE:
				session.setAttribute(ParameterName.LOCALE_NAME, ENGLISH_LOCALE);
				response.sendRedirect(redirect);
				break;
		}
	}

}
