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
import by.htp6.hospital.service.DischargePatientService;
import by.htp6.hospital.service.exception.ServiceException;
import by.htp6.hospital.service.factory.ServiceFactory;

/**
 * Команда предназначенная для выписки пациента из больницы
 * 
 * Command designed to discharge patient
 * 
 * @author Begench Shamuradov, 2017
 */
public class DischargePatientCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int patientId = Integer.parseInt(request.getParameter(ParameterName.PATIENT_ID));
		String finalDiagnosis = request.getParameter(ParameterName.FINAL_DIAGNOSIS);
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		DischargePatientService dischargePatientService = serviceFactory.getDischargePatient();
		try {
			dischargePatientService.dischargePatient(patientId, finalDiagnosis);
			
			request.setAttribute(ParameterName.SUCCESS_MESSAGE, SuccessMessage.DISCHARGE);
			RequestDispatcher dispatcher = request.getRequestDispatcher(Url.SUCCESS);
			dispatcher.forward(request, response);
			
		} catch (ServiceException e) {

			response.sendRedirect(Url.ERROR);
		}
	}
}
