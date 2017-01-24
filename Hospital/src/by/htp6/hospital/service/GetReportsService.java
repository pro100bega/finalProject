package by.htp6.hospital.service;

import java.util.List;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetReportsService {
	List<Report> getReports(String userType, int offset, int count) 
			throws ServiceException;
}
