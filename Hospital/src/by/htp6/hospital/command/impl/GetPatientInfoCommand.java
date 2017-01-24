package by.htp6.hospital.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.Appointment;
import by.htp6.hospital.bean.Patient;
import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.constant.UserType;
import by.htp6.hospital.service.GetAppointmentListService;
import by.htp6.hospital.service.GetPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для получения полной
 * информации о пациенте
 * 
 * Command designed to get full patient info
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientInfoCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
		String userType = user.getType();
		int patientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		GetPatientService getPatient = serviceFactory.getGetPatient();
		GetAppointmentListService getAppointmentList = serviceFactory.getGetAppointmentList();
		
		try {
			String url = Url.ERROR;
			if (userType.equals(UserType.DOCTOR)){
				url = Url.DOCTOR_PATIENT_PAGE;
			} else if (userType.equals(UserType.NURSE)) {
				url = Url.NURSE_PATIENT_PAGE;
			}
			
			Patient selectedPatient = getPatient.getPatient(patientId);
			String status = request.getParameter(ParameterName.STATUS);
			List<Appointment> appointments = getAppointmentList.
					getAppointmentList(selectedPatient.getId(), status);

			request.setAttribute(ParameterName.SELECTED_PATIENT, selectedPatient);
			
			if (!appointments.isEmpty()) {
				request.setAttribute(ParameterName.APPOINTMENTS, appointments);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			
		} catch (ServiceException e) {
			
			response.sendRedirect(Url.ERROR);
		} 
	}

}
