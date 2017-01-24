package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.SuccessMessage;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.SendReportService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для отправки сообщения об ошибке
 * 
 * Command designed to send report
 * 
 * @author Begench Shamuradov, 2017
 */
public class SendReportCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String header = request.getParameter(ParameterName.HEADER);
		String message = request.getParameter(ParameterName.MESSAGE);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SendReportService sendReportService = serviceFactory.getSendReport();
		
		try {
			sendReportService.sendReport(header, message);
			request.setAttribute(ParameterName.SUCCESS_MESSAGE, SuccessMessage.SEND_REPORT);
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(Url.SUCCESS);
			dispatcher.forward(request, response);
			
		} catch (ServiceException e) {
			
			response.sendRedirect(Url.ERROR);
		}
		
	}

}
