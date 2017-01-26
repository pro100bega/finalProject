package by.htp6.hospital.command.impl;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
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
import by.htp6.hospital.service.GetUserListService;
import by.htp6.hospital.service.GetUsersCountService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда получения списка пользователей
 * 
 * Command designed to get users list
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUserListCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetUserListCommand.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {
			User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
			String userType = user.getType();

			String requestCurrentPage = request.getParameter(ParameterName.CURRENT_PAGE);
			int currentPage;
			if (requestCurrentPage == null) {
				String sessionCurrentPage = "" + session.getAttribute(ParameterName.CURRENT_PAGE);
				currentPage = (sessionCurrentPage == null) ? 1 : Integer.parseInt(sessionCurrentPage);
			} else {
				currentPage = Integer.parseInt(requestCurrentPage);
			}

			String requestUsersPerPage = request.getParameter(ParameterName.USERS_PER_PAGE);
			int usersPerPage;
			if (requestUsersPerPage == null) {
				String sessionUsersPerPage = "" + session.getAttribute(ParameterName.USERS_PER_PAGE);
				usersPerPage = (sessionUsersPerPage == null) ? 10 : Integer.parseInt(sessionUsersPerPage);
			} else {
				usersPerPage = Integer.parseInt(requestUsersPerPage);
			}

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			GetUsersCountService getUsersCount = serviceFactory.getGetUsersCount();
			GetUserListService getUserList = serviceFactory.getGetUserList();

			try {
				int usersCount = getUsersCount.getUsersCount();
				int offset = usersPerPage * (currentPage - 1);
				List<User> users = getUserList.getUserList(userType, offset, usersPerPage);

				session.setAttribute(ParameterName.CURRENT_PAGE, currentPage);
				session.setAttribute(ParameterName.USERS_PER_PAGE, usersPerPage);
				request.setAttribute(ParameterName.USERS, users);
				request.setAttribute(ParameterName.USERS_COUNT, usersCount);

				RequestDispatcher dispatcher = request.getRequestDispatcher(Url.USERS);
				dispatcher.forward(request, response);

			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
