package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface SignUpService {
	void signUp(String username, String password) throws ServiceException;
}
