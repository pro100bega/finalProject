package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.SignInDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLSignInDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для входа пользователя в систему
 * 
 * Factory that contains user signing in classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class SignInFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final SignInFactory instance = new SignInFactory();

	private static final SignInDAO sqlSignIn = new SQLSignInDAO();

	private SignInFactory() {
	}

	public static SignInFactory getInstance() {
		return instance;
	}

	public SignInDAO getSignInDAO() {
		SignInDAO SignInDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			SignInDAO = sqlSignIn;
			break;
			default : 
				SignInDAO = null;
				break;
		}
		
		if (SignInDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return SignInDAO;
	}
}
