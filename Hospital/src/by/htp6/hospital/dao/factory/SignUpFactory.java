package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.SignUpDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLSignUpDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для регистрации пользователя
 * 
 * Factory that contains user signign up classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignUpFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final SignUpFactory instance = new SignUpFactory();

	private static final SignUpDAO sqlSignUp = new SQLSignUpDAO();

	private SignUpFactory() {
	}

	public static SignUpFactory getInstance() {
		return instance;
	}

	public SignUpDAO getSignUpDAO() {
		SignUpDAO SignUpDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			SignUpDAO = sqlSignUp;
			break;
			default : 
				SignUpDAO = null;
				break;
		}
		
		if (SignUpDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return SignUpDAO;
	}
}
