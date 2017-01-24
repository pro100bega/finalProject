package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface ChangeUserTypeDAO {
	void changeUserType(String type, int userId) throws DAOException;
}
