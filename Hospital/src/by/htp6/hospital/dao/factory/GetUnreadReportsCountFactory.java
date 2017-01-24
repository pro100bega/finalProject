package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetUnreadReportsCountDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetUnreadReportsCountDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения количества непрочитанных жалоб
 * 
 * Factory that contains unread reports count getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUnreadReportsCountFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetUnreadReportsCountFactory instance = new GetUnreadReportsCountFactory();

	private static final GetUnreadReportsCountDAO sqlGetUnreadReportsCount = new SQLGetUnreadReportsCountDAO();

	private GetUnreadReportsCountFactory() {
	}

	public static GetUnreadReportsCountFactory getInstance() {
		return instance;
	}

	public GetUnreadReportsCountDAO getGetUnreadReportsCountDAO() {
		GetUnreadReportsCountDAO getUnreadReportsCountDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getUnreadReportsCountDAO = sqlGetUnreadReportsCount;
			break;
			default : 
				getUnreadReportsCountDAO = null;
				break;
		}
		
		if (getUnreadReportsCountDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getUnreadReportsCountDAO;
	}
}
