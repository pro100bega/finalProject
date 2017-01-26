package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.GetPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для отображения страницы изменения информации о
 * пациенте
 * 
 * Command designed to display edit patient page
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetEditPatientPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetEditPatientPageCommand.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {
			int patientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			GetPatientService getPatient = serviceFactory.getGetPatient();

			try {
				Patient selectedPatient = getPatient.getPatient(patientId);

				request.setAttribute(ParameterName.SELECTED_PATIENT, selectedPatient);
				RequestDispatcher dispatcher = request.getRequestDispatcher(Url.EDIT_PATIENT);
				dispatcher.forward(request, response);

			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
