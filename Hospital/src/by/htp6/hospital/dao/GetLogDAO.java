package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.Log;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetLogDAO {
	List<Log> getLog() throws DAOException;
}
