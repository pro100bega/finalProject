package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.Appointment;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetAppointmentListDAO {
	List<Appointment> getAppointmentList(int patientId, String status) throws DAOException;
}
