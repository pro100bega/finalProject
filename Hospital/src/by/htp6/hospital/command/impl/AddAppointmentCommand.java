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
import by.htp6.hospital.service.AddAppointmentService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для добавления нового назначения
 * 
 * Command designed to add new appointment
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddAppointmentCommand implements Command {

	private static final Logger log = LogManager.getLogger(AddAppointmentCommand.class);
	
	private static final String GET_PATIENT_INFO_COMMAND = "controller?command=GET_PATIENT_INFO&status=undone&patientId=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {

			User user = (User) session.getAttribute(ParameterName.AUTHORISED_USER);
			int doctorId = user.getId();
			int patientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));

			String type = request.getParameter(ParameterName.TYPE);
			String name = request.getParameter(ParameterName.NAME);
			String termDate = request.getParameter(ParameterName.TERM_DATE);
			String termTime = request.getParameter(ParameterName.TERM_TIME);

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			AddAppointmentService addAppointmentService = serviceFactory.getAddAppointment();

			try {
				addAppointmentService.addAppointment(patientId, doctorId, type, name, termDate, termTime);

				String url = GET_PATIENT_INFO_COMMAND + patientId;
				response.sendRedirect(url);

			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
