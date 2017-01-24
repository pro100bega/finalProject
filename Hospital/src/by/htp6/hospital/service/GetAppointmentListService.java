package by.htp6.hospital.service;

import java.util.List;

import by.htp6.hospital.bean.Appointment;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetAppointmentListService {
	List<Appointment> getAppointmentList(int patientId, String status) throws ServiceException;
}
