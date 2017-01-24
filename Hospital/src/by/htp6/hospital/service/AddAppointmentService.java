package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface AddAppointmentService {
	void addAppointment(int patientId, int doctorId, String type,
			String name, String termDate, String termTime)
		throws ServiceException;
}
