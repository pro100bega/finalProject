package by.htp6.hospital.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.command.Command;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.SuccessMessage;
import by.htp6.hospital.constant.Url;
import by.htp6.hospital.service.AddNewPatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для добавления нового пациента
 * 
 * Command designed to add new patient
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddNewPatientCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String name = request.getParameter(ParameterName.NAME);
		String surname = request.getParameter(ParameterName.SURNAME);
		String sex = request.getParameter(ParameterName.SEX);
		String birthDate = request.getParameter(ParameterName.BIRTH_DATE);
		String note = request.getParameter(ParameterName.NOTE);
		String diagnosis = request.getParameter(ParameterName.DIAGNOSIS);
		
		User doctor = (User)session.getAttribute(ParameterName.AUTHORISED_USER);
		int doctorId = doctor.getId();
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		AddNewPatientService addNewPatientService = serviceFactory.getAddNewPatient();
		
		try {
			addNewPatientService.addNewPatient(name, surname, sex, birthDate,
					diagnosis, doctorId, note);
			
			request.setAttribute(ParameterName.SUCCESS_MESSAGE, SuccessMessage.PATIENT_ADD);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.SUCCESS);
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			
			response.sendRedirect(Url.ERROR);
		}
	}

}
