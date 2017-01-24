package by.htp6.hospital.dao;

import by.htp6.hospital.bean.Report;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetSingleReportDAO {
	Report getReport(int reportId) throws DAOException;
}
