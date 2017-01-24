package by.htp6.hospital.service.impl;

import by.htp6.hospital.dao.GetUsersCountDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetUsersCountFactory;
import by.htp6.hospital.service.GetUsersCountService;
import by.htp6.hospital.service.exception.ServiceException;

/**
 * Сервис получения количества пользователей
 * 
 * User count getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUsersCount implements GetUsersCountService {

	@Override
	public int getUsersCount() throws ServiceException {
		GetUsersCountFactory daoFactory = GetUsersCountFactory.getInstance();
		GetUsersCountDAO getUsersCountDAO = daoFactory.getGetUsersCountDAO();
		
		try {
			int usersCount = getUsersCountDAO.getUsersCount();
			return usersCount;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
