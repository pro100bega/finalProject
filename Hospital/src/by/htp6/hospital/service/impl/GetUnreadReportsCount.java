package by.htp6.hospital.service.impl;

import by.htp6.hospital.dao.GetUnreadReportsCountDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetUnreadReportsCountFactory;
import by.htp6.hospital.service.GetUnreadReportsCountService;
import by.htp6.hospital.service.exception.ServiceException;

/**
 * Сервис получения количества новых жалоб
 * 
 * New reports count getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUnreadReportsCount implements GetUnreadReportsCountService{
	
	@Override
	public int getUnreadReportsCountService() throws ServiceException {

		GetUnreadReportsCountFactory daoFactory = GetUnreadReportsCountFactory.getInstance();
		GetUnreadReportsCountDAO getUnreadReportsCountDAO = 
				daoFactory.getGetUnreadReportsCountDAO();
		
		try {
			int unreadReportsCount = getUnreadReportsCountDAO.getUnreadReportsCount();
			
			return unreadReportsCount;
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
