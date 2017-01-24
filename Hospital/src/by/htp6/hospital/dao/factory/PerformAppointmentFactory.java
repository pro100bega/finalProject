package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.PerformAppointmentDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLPerformAppointmentDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы выполненя назначения
 * 
 * Factory that contains appointment performing classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class PerformAppointmentFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final PerformAppointmentFactory instance = new PerformAppointmentFactory();

	private static final PerformAppointmentDAO sqlPerformAppointment = new SQLPerformAppointmentDAO();

	private PerformAppointmentFactory() {
	}

	public static PerformAppointmentFactory getInstance() {
		return instance;
	}

	public PerformAppointmentDAO getPerformAppointmentDAO() {
		PerformAppointmentDAO PerformAppointmentDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			PerformAppointmentDAO = sqlPerformAppointment;
			break;
			default : 
				PerformAppointmentDAO = null;
				break;
		}
		
		if (PerformAppointmentDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return PerformAppointmentDAO;
	}
}
