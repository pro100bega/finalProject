package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.AddNewPatientDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLAddNewPatientDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для добавления нового пациента
 * 
 * Factory that contains patient adding classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddNewPatientFactory {
	private static final Logger log = LogManager.getLogger(AddNewPatientFactory.class);

	private static final AddNewPatientFactory instance = 
			new AddNewPatientFactory();
	
	private static final AddNewPatientDAO sqlAddNewPatient =
			new SQLAddNewPatientDAO();
	
	private AddNewPatientFactory() {
	}
	
	public static AddNewPatientFactory getInstance() {
		return instance;
	}
	
	public AddNewPatientDAO getAddNewPatientDAO() {
		AddNewPatientDAO addNewPatientDAO;
		
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL :
			addNewPatientDAO = sqlAddNewPatient;
			break;
			default :
				addNewPatientDAO = null;
				break;
		}
		
		if (addNewPatientDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return addNewPatientDAO;
	}
}

