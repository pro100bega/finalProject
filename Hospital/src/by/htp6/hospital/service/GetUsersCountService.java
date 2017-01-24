package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface GetUsersCountService {
	int getUsersCount() throws ServiceException;
}
