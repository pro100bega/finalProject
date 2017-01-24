package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.AddAppointmentDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLAddAppointmentDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для добавления назначений
 * 
 * Factory that contains appointment adding classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class AddAppointmentFactory {
	private static final Logger log = LogManager.getLogger(AddAppointmentFactory.class);

	private static final AddAppointmentFactory instance = new AddAppointmentFactory();

	private static final AddAppointmentDAO sqlAddAppointment = new SQLAddAppointmentDAO();

	private AddAppointmentFactory() {
	}

	public static AddAppointmentFactory getInstance() {
		return instance;
	}

	public AddAppointmentDAO getAddAppointmentDAO() throws WrongTechnologyException {
		AddAppointmentDAO addAppointmentDAO;

		switch (TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			addAppointmentDAO = sqlAddAppointment;
			break;
		default:
			addAppointmentDAO = null;
			break;
		}

		if (addAppointmentDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}

		return addAppointmentDAO;
	}
}
