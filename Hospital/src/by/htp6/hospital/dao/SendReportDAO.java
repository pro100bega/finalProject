package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface SendReportDAO {
	void sendReport(String header, String message) throws DAOException;
}
