package by.htp6.hospital.command.impl;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.FindLogService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для поиска информации в логе
 * 
 * Command designed to find log
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindLogCommand implements Command {

	private static final String GET_ADMIN_INFO_COMMAND = 
			"controller?command=GET_ADMIN_INFO";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String searchData = request.getParameter(ParameterName.SEARCH_DATA);
		User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
		String userType = user.getType();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();

		if ("".equals(searchData) || null == searchData) {
			response.sendRedirect(GET_ADMIN_INFO_COMMAND);
		} else {
			FindLogService findLog = serviceFactory.getFindLog();

			try {
				List<Log> logList = findLog.findLog(searchData, userType);
				request.setAttribute(ParameterName.SEARCH_DATA, searchData);
				request.setAttribute(ParameterName.FOUND_LOG, logList);

				RequestDispatcher dispatcher = request.getRequestDispatcher(Url.FOUND_LOG);
				dispatcher.forward(request, response);
			} catch (ServiceException e) {
				
				response.sendRedirect(Url.ERROR);
			}
		}
	}
}
