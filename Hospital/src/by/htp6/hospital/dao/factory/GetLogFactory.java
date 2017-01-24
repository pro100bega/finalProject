package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetLogDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetLogDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения лога базы данных
 * 
 * Factory that contains database log getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetLogFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetLogFactory instance = new GetLogFactory();

	private static final GetLogDAO sqlGetLog = new SQLGetLogDAO();

	private GetLogFactory() {
	}

	public static GetLogFactory getInstance() {
		return instance;
	}

	public GetLogDAO getGetLogDAO() {
		GetLogDAO getLogDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getLogDAO = sqlGetLog;
			break;
			default : 
				getLogDAO = null;
				break;
		}
		
		if (getLogDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getLogDAO;
	}
}
