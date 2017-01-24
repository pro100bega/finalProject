package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetReportsCountDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetReportsCountDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения количества жалоб и предложений
 * 
 * Factory that contains reports count getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetReportsCountFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetReportsCountFactory instance = new GetReportsCountFactory();

	private static final GetReportsCountDAO sqlGetReportsCount = new SQLGetReportsCountDAO();

	private GetReportsCountFactory() {
	}

	public static GetReportsCountFactory getInstance() {
		return instance;
	}

	public GetReportsCountDAO getGetReportsCountDAO() {
		GetReportsCountDAO getReportsCountDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getReportsCountDAO = sqlGetReportsCount;
			break;
			default : 
				getReportsCountDAO = null;
				break;
		}
		
		if (getReportsCountDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getReportsCountDAO;
	}
}
