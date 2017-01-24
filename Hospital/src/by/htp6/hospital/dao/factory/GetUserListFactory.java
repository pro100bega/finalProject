package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetUserListDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetUserListDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения списка пользователей
 * 
 * Factory that contains users list getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetUserListFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetUserListFactory instance = new GetUserListFactory();

	private static final GetUserListDAO sqlGetUserList = new SQLGetUserListDAO();

	private GetUserListFactory() {
	}

	public static GetUserListFactory getInstance() {
		return instance;
	}

	public GetUserListDAO getGetUserListDAO() {
		GetUserListDAO GetUserListDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			GetUserListDAO = sqlGetUserList;
			break;
			default : 
				GetUserListDAO = null;
				break;
		}
		
		if (GetUserListDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return GetUserListDAO;
	}
}
