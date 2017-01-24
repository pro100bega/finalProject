package by.htp6.hospital.service;

import java.util.List;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.service.exception.ServiceException;

public interface GetUserListService {
	List<User> getUserList(String userType, int offset, int count)
			throws ServiceException;
}
