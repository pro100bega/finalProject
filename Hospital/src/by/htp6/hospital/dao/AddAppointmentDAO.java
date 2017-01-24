package by.htp6.hospital.dao;

import java.sql.Timestamp;

import by.htp6.hospital.dao.exception.DAOException;

public interface AddAppointmentDAO {
	void addAppointment(int patientId, int doctorId, String type,
			String name, Timestamp term) throws DAOException;
}
