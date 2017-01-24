package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetReportsDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetReportsDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения списка жалоб и предложений
 * 
 * Factory that contains reports list getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetReportsFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetReportsFactory instance = new GetReportsFactory();

	private static final GetReportsDAO sqlGetReports = new SQLGetReportsDAO();

	private GetReportsFactory() {
	}

	public static GetReportsFactory getInstance() {
		return instance;
	}

	public GetReportsDAO getGetReportsDAO() {
		GetReportsDAO getReportsDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getReportsDAO = sqlGetReports;
			break;
			default : 
				getReportsDAO = null;
				break;
		}
		
		if (getReportsDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getReportsDAO;
	}
}
