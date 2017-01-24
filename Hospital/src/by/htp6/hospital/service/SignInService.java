package by.htp6.hospital.service;

import by.htp6.hospital.bean.User;
import by.htp6.hospital.service.exception.ServiceException;

public interface SignInService {
	User signIn(String username, String password) throws ServiceException;
}
