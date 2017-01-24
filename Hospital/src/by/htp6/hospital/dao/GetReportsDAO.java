package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetReportsDAO {
	List<Report> getReports(int offset, int count) throws DAOException;
}
