package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetUsersCountDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetUsersCountDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения количества пользователей
 * 
 * Factory that contains users count getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUsersCountFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetUsersCountFactory instance = new GetUsersCountFactory();

	private static final GetUsersCountDAO sqlGetUsersCount = new SQLGetUsersCountDAO();

	private GetUsersCountFactory() {
	}

	public static GetUsersCountFactory getInstance() {
		return instance;
	}

	public GetUsersCountDAO getGetUsersCountDAO() {
		GetUsersCountDAO GetUsersCountDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			GetUsersCountDAO = sqlGetUsersCount;
			break;
			default : 
				GetUsersCountDAO = null;
				break;
		}
		
		if (GetUsersCountDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return GetUsersCountDAO;
	}
}
