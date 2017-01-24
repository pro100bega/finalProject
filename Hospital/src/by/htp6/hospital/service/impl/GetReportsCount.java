package by.htp6.hospital.service.impl;

import by.htp6.hospital.dao.GetReportsCountDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.factory.GetReportsCountFactory;
import by.htp6.hospital.service.GetReportsCountService;
import by.htp6.hospital.service.exception.ServiceException;

/**
 * Сервис получения количества жалоб и предложений
 * 
 * Report count getting service
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetReportsCount implements GetReportsCountService {

	@Override
	public int getReportsCountService() throws ServiceException {
		GetReportsCountFactory daoFactory = GetReportsCountFactory.getInstance();
		GetReportsCountDAO getReportsCountDAO = daoFactory.getGetReportsCountDAO();

		try {
			int ReportsCount = getReportsCountDAO.getReportsCount();

			return ReportsCount;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
