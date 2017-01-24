package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.GetSingleReportService;
import by.htp6.hospital.service.GetUnreadReportsCountService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда получения конкретного сообщения о неполадках
 * 
 * Command designed to get single report message
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetSingleReportCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		User user = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
		String userType = user.getType();
		
		int reportId = Integer.parseInt(request.getParameter(ParameterName.ID));
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		GetSingleReportService getSingleReport = serviceFactory.getGetSingleReport();
		GetUnreadReportsCountService getUnreadReportsCount = 
				serviceFactory.getGetUnreadReportsCount();
		
		try {
			Report report = getSingleReport.getReport(userType, reportId);
			int unreadReportsCount = 
					getUnreadReportsCount.getUnreadReportsCountService();
			session.setAttribute(ParameterName.UNREAD_REPORTS_COUNT, unreadReportsCount);
			request.setAttribute(ParameterName.REPORT, report);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.REPORT);
			dispatcher.forward(request, response);
			
		} catch (ServiceException e) {
			
			response.sendRedirect(Url.ERROR);
		}
	}

}
