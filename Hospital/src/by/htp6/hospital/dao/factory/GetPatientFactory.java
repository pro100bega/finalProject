package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetPatientDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetPatientDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения конкретного пациента
 * 
 * Factory that contains single patient getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetPatientFactory instance = new GetPatientFactory();

	private static final GetPatientDAO sqlGetPatient = new SQLGetPatientDAO();

	private GetPatientFactory() {
	}

	public static GetPatientFactory getInstance() {
		return instance;
	}

	public GetPatientDAO getGetPatientDAO() {
		GetPatientDAO getPatientDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getPatientDAO = sqlGetPatient;
			break;
			default : 
				getPatientDAO = null;
				break;
		}
		
		if (getPatientDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getPatientDAO;
	}
}
