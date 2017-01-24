package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.FindPatientDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLFindPatientDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы поиска пациента
 * 
 * Factory that contains patient searching classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindPatientFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final FindPatientFactory instance = new FindPatientFactory();

	private static final FindPatientDAO sqlFindPatient = new SQLFindPatientDAO();

	private FindPatientFactory() {
	}

	public static FindPatientFactory getInstance() {
		return instance;
	}

	public FindPatientDAO getFindPatientDAO() {
		FindPatientDAO findPatientDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			findPatientDAO = sqlFindPatient;
			break;
			default : 
				findPatientDAO = null;
				break;
		}
		
		if (findPatientDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return findPatientDAO;
	}
}
