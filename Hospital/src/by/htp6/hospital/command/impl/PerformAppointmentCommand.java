package by.htp6.hospital.command.impl;

import java.io.IOException;

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
import by.htp6.hospital.service.PerformAppointmentService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда выполнения назначения
 * 
 * Command designed to perform appointment
 * 
 * @author Begench Shamuradov, 2017
 */
public class PerformAppointmentCommand implements Command {

	private static final Logger log = LogManager.getLogger(PerformAppointmentCommand.class);
	
	private static final String APPOINTMENTS_DISPLAY_COMMAND = "controller?command=GET_PATIENT_INFO&status=undone&patientId=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {
			User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
			String userType = user.getType();
			int appointmentId = Integer.parseInt(request.getParameter(ParameterName.ID));
			String appointmentType = request.getParameter(ParameterName.TYPE);

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			PerformAppointmentService performAppointment = serviceFactory.getPerformAppointment();
			try {
				performAppointment.performAppointment(appointmentId, userType, appointmentType);
				int selectedPatientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));
				String url = APPOINTMENTS_DISPLAY_COMMAND + selectedPatientId;
				response.sendRedirect(url);
			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
