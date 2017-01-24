package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetPatientsCountDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetPatientsCountDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения количества пациентов
 * 
 * Factory that contains patients count getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientsCountFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetPatientsCountFactory instance = new GetPatientsCountFactory();

	private static final GetPatientsCountDAO sqlGetPatientsCount = new SQLGetPatientsCountDAO();

	private GetPatientsCountFactory() {
	}

	public static GetPatientsCountFactory getInstance() {
		return instance;
	}

	public GetPatientsCountDAO getGetPatientsCountDAO() {
		GetPatientsCountDAO getPatientsCountDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getPatientsCountDAO = sqlGetPatientsCount;
			break;
			default : 
				getPatientsCountDAO = null;
				break;
		}
		
		if (getPatientsCountDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getPatientsCountDAO;
	}
}
