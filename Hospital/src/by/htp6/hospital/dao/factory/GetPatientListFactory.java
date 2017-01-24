package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetPatientListDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetPatientListDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения списка пациентов
 * 
 * Factory that contains patient list getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetPatientListFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetPatientListFactory instance = new GetPatientListFactory();

	private static final GetPatientListDAO sqlGetPatientList = new SQLGetPatientListDAO();

	private GetPatientListFactory() {
	}

	public static GetPatientListFactory getInstance() {
		return instance;
	}

	public GetPatientListDAO getGetPatientListDAO() {
		GetPatientListDAO getPatientListDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getPatientListDAO = sqlGetPatientList;
			break;
			default : 
				getPatientListDAO = null;
				break;
		}
		
		if (getPatientListDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getPatientListDAO;
	}
}
