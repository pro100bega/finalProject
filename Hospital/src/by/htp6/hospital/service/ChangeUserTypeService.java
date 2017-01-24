package by.htp6.hospital.service;

import by.htp6.hospital.service.exception.ServiceException;

public interface ChangeUserTypeService {
	void changeUserType(String authorisedUserType, String type,
			int userId) throws ServiceException;
}
