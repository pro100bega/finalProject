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

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.service.GetPatientListService;
import by.htp6.hospital.service.GetPatientsCountService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для получения списка пациентов
 * 
 * Command designed to get patients list
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientListCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetPatientListCommand.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {
			User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
			String userType = user.getType();

			String orderBy = request.getParameter(ParameterName.ORDER_BY);
			;
			if (orderBy == null) {
				orderBy = (String) session.getAttribute(ParameterName.ORDER_BY);
			}

			String requestCurrentPage = request.getParameter(ParameterName.CURRENT_PAGE);
			int currentPage;
			if (requestCurrentPage == null) {
				String sessionCurrentPage = "" + session.getAttribute(ParameterName.CURRENT_PAGE);
				currentPage = (sessionCurrentPage == null) ? 1 : Integer.parseInt(sessionCurrentPage);
			} else {
				currentPage = Integer.parseInt(requestCurrentPage);
			}

			String requestPatientsPerPage = request.getParameter(ParameterName.PATIENTS_PER_PAGE);
			int patientsPerPage;
			if (requestPatientsPerPage == null) {
				String sessionPatientsPerPage = "" + session.getAttribute(ParameterName.PATIENTS_PER_PAGE);
				patientsPerPage = (sessionPatientsPerPage == null) ? 10 : Integer.parseInt(sessionPatientsPerPage);
			} else {
				patientsPerPage = Integer.parseInt(requestPatientsPerPage);
			}

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			GetPatientsCountService getPatientsCount = serviceFactory.getGetPatientsCount();
			GetPatientListService getPatientList = serviceFactory.getGetPatientList();

			try {
				int patientsCount = 0;
				int offset = patientsPerPage * (currentPage - 1);
				List<Patient> patients = null;
				String url = Url.ERROR;
				if (userType.equals(UserType.DOCTOR)) {
					int doctorId = user.getId();
					patientsCount = getPatientsCount.getPatientsCountByDoctorId(doctorId);
					patients = getPatientList.getPatientsListForDoctor(doctorId, offset, patientsPerPage, orderBy);
					url = Url.DOCTOR_PANEL;
				} else if (userType.equals(UserType.NURSE)) {
					patientsCount = getPatientsCount.getPatientsCount();
					patients = getPatientList.getAllPatientsList(offset, patientsPerPage, orderBy);
					url = Url.NURSE_PANEL;
				}

				session.setAttribute(ParameterName.CURRENT_PAGE, currentPage);
				session.setAttribute(ParameterName.PATIENTS_PER_PAGE, patientsPerPage);
				request.setAttribute(ParameterName.PATIENTS, patients);
				request.setAttribute(ParameterName.PATIENTS_COUNT, patientsCount);
				if (orderBy != null) {
					session.setAttribute(ParameterName.ORDER_BY, orderBy);
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
