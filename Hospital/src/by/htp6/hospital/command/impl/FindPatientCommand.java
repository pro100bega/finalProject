package by.htp6.hospital.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.service.FindPatientService;
import by.htp6.hospital.service.GetPatientsCountService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для поиска пациента
 * 
 * Command designed to find patient
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindPatientCommand implements Command {

	private static final String GET_PATIENT_LIST_COMMAND =
			"controller?command=GET_PATIENT_LIST";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
		String userType = user.getType();

		String requestCurrentPage = request.getParameter(ParameterName.CURRENT_PAGE);
		int currentPage;
		if (requestCurrentPage == null){
			String sessionCurrentPage = ""+session.getAttribute(ParameterName.CURRENT_PAGE);
			currentPage = (sessionCurrentPage == null) 
					? 1	: Integer.parseInt(sessionCurrentPage);
		} else {
			currentPage = Integer.parseInt(requestCurrentPage);
		}
		
		String requestPatientsPerPage = request.getParameter(ParameterName.PATIENTS_PER_PAGE);
		int patientsPerPage;
		if (requestPatientsPerPage == null) {
			String sessionPatientsPerPage = 
					""+session.getAttribute(ParameterName.PATIENTS_PER_PAGE);
			patientsPerPage = (sessionPatientsPerPage == null) 
					? 10 : Integer.parseInt(sessionPatientsPerPage);
		} else {
			patientsPerPage = Integer.parseInt(requestPatientsPerPage);
		}
		
		String searchData = request.getParameter(ParameterName.SEARCH_DATA);
		if (searchData == null || "".equals(searchData)) {
				response.sendRedirect(GET_PATIENT_LIST_COMMAND);
		} else {
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			FindPatientService findPatientService = serviceFactory.getFindPatient();
			GetPatientsCountService getPatientsCount = serviceFactory.getGetPatientsCount();

			try {
				int patientsCount = 0;
				int offset = patientsPerPage * (currentPage - 1);
				List<Patient> patients = null;
				String url = Url.ERROR;
				if (userType.equals(UserType.DOCTOR)){
					int doctorId = user.getId();
					patientsCount = getPatientsCount.getPatientsCountByDoctorId(searchData, 
							doctorId);
					patients = findPatientService.findPatientsByDoctorId(searchData, doctorId,
							offset, patientsPerPage);
					url = Url.DOCTOR_FOUND_PATIENTS;
				} else if (userType.equals(UserType.NURSE)) {
					patientsCount = getPatientsCount.getPatientsCount(searchData);
					patients = findPatientService.findPatients(searchData, offset,
							patientsPerPage);
					url = Url.NURSE_FOUND_PATIENTS;
				}

				request.setAttribute(ParameterName.FOUND_PATIENTS, patients);
				session.setAttribute(ParameterName.CURRENT_PAGE, currentPage);
				session.setAttribute(ParameterName.PATIENTS_PER_PAGE, patientsPerPage);
				request.setAttribute(ParameterName.PATIENTS_COUNT, patientsCount);
				request.setAttribute(ParameterName.SEARCH_DATA, searchData);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}
}
