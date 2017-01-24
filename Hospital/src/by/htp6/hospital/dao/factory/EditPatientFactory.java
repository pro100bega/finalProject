package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.EditPatientDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLEditPatientDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для изменения информации о пациенте
 * 
 * Factory that contains patient info changing classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class EditPatientFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final EditPatientFactory instance = new EditPatientFactory();

	private static final EditPatientDAO sqlEditPatient = new SQLEditPatientDAO();

	private EditPatientFactory() {
	}

	public static EditPatientFactory getInstance() {
		return instance;
	}

	public EditPatientDAO getEditPatientDAO() {
		EditPatientDAO editPatientDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			editPatientDAO = sqlEditPatient;
			break;
			default : 
				editPatientDAO = null;
				break;
		}
		
		if (editPatientDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return editPatientDAO;
	}
}
