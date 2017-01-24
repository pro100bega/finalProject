package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface SendReportService {
	void sendReport(String header, String message) throws ServiceException;
}
