package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface PerformAppointmentDAO {
	void performAppointment(int appointmentId) throws DAOException;
}
