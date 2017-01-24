package by.htp6.hospital.service;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetSingleReportService {
	Report getReport(String userType, int reportId)
			throws ServiceException;
}
