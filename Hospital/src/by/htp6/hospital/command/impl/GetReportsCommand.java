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

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.GetReportsCountService;
import by.htp6.hospital.service.GetReportsService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда получения списка сообщений о неполадках
 * 
 * Command designed to get reports list
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetReportsCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetReportsCommand.class);
	
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

			String requestReportsPerPage = request.getParameter(ParameterName.REPORTS_PER_PAGE);
			int reportsPerPage;
			if (requestReportsPerPage == null) {
				String sessionReportsPerPage = "" + session.getAttribute(ParameterName.REPORTS_PER_PAGE);
				reportsPerPage = (sessionReportsPerPage == null) ? 10 : Integer.parseInt(sessionReportsPerPage);
			} else {
				reportsPerPage = Integer.parseInt(requestReportsPerPage);
			}

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			GetReportsCountService getReportsCount = serviceFactory.getGetReportsCount();
			GetReportsService getReports = serviceFactory.getGetReports();

			try {
				int reportsCount = getReportsCount.getReportsCountService();
				int offset = reportsPerPage * (currentPage - 1);
				List<Report> reports = getReports.getReports(userType, offset, reportsPerPage);

				session.setAttribute(ParameterName.CURRENT_PAGE, currentPage);
				session.setAttribute(ParameterName.REPORTS_PER_PAGE, reportsPerPage);
				request.setAttribute(ParameterName.REPORTS, reports);
				request.setAttribute(ParameterName.REPORTS_COUNT, reportsCount);

				RequestDispatcher dispatcher = request.getRequestDispatcher(Url.REPORTS);
				dispatcher.forward(request, response);

			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
