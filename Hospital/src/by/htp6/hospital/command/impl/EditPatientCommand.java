package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.EditPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для изменения информации о пациенте
 * 
 * Command designed to change patient info
 * 
 * @author Begench Shamuradov, 2017
 */
public class EditPatientCommand implements Command {

	private static final Logger log = LogManager.getLogger(EditPatientCommand.class);
	
	private static final String GET_PATIENT_INFO_COMMAND = "controller?command=GET_PATIENT_INFO&status=undone&patientId=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			log.error(ErrorMessage.SESSION_EXPIRED);
			response.sendRedirect(Url.INDEX);
		} else {
			int patientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));

			String name = request.getParameter(ParameterName.NAME);
			String surname = request.getParameter(ParameterName.SURNAME);
			String sex = request.getParameter(ParameterName.SEX);
			String birthDate = request.getParameter(ParameterName.BIRTH_DATE);
			String diagnosis = request.getParameter(ParameterName.DIAGNOSIS);
			String note = request.getParameter(ParameterName.NOTE);

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			EditPatientService editPatient = serviceFactory.getEditPatient();

			try {
				editPatient.editPatitnt(patientId, name, surname, sex, birthDate, diagnosis, note);

				response.sendRedirect(GET_PATIENT_INFO_COMMAND + patientId);
			} catch (ServiceException e) {

				response.sendRedirect(Url.ERROR);
			}
		}
	}

}
