package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.DischargePatientDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLDischargePatientDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для выписки пациента
 * 
 * Factory that contains patient discharging classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class DischargePatientFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final DischargePatientFactory instance = new DischargePatientFactory();

	private static final DischargePatientDAO sqlDischargePatient = new SQLDischargePatientDAO();

	private DischargePatientFactory() {
	}

	public static DischargePatientFactory getInstance() {
		return instance;
	}

	public DischargePatientDAO getDischargePatientDAO() {
		DischargePatientDAO dischargePatientDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			dischargePatientDAO = sqlDischargePatient;
			break;
			default : 
				dischargePatientDAO = null;
				break;
		}
		
		if (dischargePatientDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return dischargePatientDAO;
	}
}
