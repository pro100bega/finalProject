package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface PerformAppointmentService {
	void performAppointment(int appointmentId,
			String userType, String appointmentType) throws ServiceException;
}
