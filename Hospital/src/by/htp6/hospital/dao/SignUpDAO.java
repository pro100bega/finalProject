package by.htp6.hospital.dao;

import by.htp6.hospital.dao.exception.DAOException;

public interface SignUpDAO {
	void signUp(String username, String password) throws DAOException;
}
