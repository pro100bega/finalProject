package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.GetSingleReportDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLGetSingleReportDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для получения конкретной жалобы и предложения
 * 
 * Factory that contains single report getting classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class GetSingleReportFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final GetSingleReportFactory instance = new GetSingleReportFactory();

	private static final GetSingleReportDAO sqlGetSingleReport = new SQLGetSingleReportDAO();

	private GetSingleReportFactory() {
	}

	public static GetSingleReportFactory getInstance() {
		return instance;
	}

	public GetSingleReportDAO getGetSingleReportDAO() {
		GetSingleReportDAO getSingleReportDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			getSingleReportDAO = sqlGetSingleReport;
			break;
			default : 
				getSingleReportDAO = null;
				break;
		}
		
		if (getSingleReportDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return getSingleReportDAO;
	}
}
