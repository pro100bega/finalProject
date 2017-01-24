package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.ChangeUserTypeDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLChangeUserTypeDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для изменения типа пользователя
 * 
 * Factory that contains user type changing classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class ChangeUserTypeFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final ChangeUserTypeFactory instance = new ChangeUserTypeFactory();

	private static final ChangeUserTypeDAO sqlChangeUserType = new SQLChangeUserTypeDAO();

	private ChangeUserTypeFactory() {
	}

	public static ChangeUserTypeFactory getInstance() {
		return instance;
	}

	public ChangeUserTypeDAO getChangeUserTypeDAO() {
		ChangeUserTypeDAO changeUserTypeDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			changeUserTypeDAO = sqlChangeUserType;
			break;
			default : 
				changeUserTypeDAO = null;
				break;
		}
		
		if (changeUserTypeDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return changeUserTypeDAO;
	}
}
