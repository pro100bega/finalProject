package by.htp6.hospital.dao;

import java.util.List;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.dao.exception.DAOException;

public interface GetUserListDAO {
	List<User> getUserList(int offset, int count) throws DAOException;
}
