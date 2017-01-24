package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetAppointmentListDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetAppointmentListDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения списка назначений
 * 
 * Factory that contains appointment list getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetAppointmentListFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetAppointmentListFactory instance = new GetAppointmentListFactory();

	private static final GetAppointmentListDAO sqlGetAppointmentList = new SQLGetAppointmentListDAO();

	private GetAppointmentListFactory() {
	}

	public static GetAppointmentListFactory getInstance() {
		return instance;
	}

	public GetAppointmentListDAO getGetAppointmentListDAO() {
		GetAppointmentListDAO getAppointmentListDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getAppointmentListDAO = sqlGetAppointmentList;
			break;
			default : 
				getAppointmentListDAO = null;
				break;
		}
		
		if (getAppointmentListDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getAppointmentListDAO;
	}
}
