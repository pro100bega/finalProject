package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.SendReportDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLSendReportDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы для отправки жалобы и предложения
 * 
 * Factory that contains report sending classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class SendReportFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final SendReportFactory instance = new SendReportFactory();

	private static final SendReportDAO sqlSendReport = new SQLSendReportDAO();

	private SendReportFactory() {
	}

	public static SendReportFactory getInstance() {
		return instance;
	}

	public SendReportDAO getSendReportDAO() {
		SendReportDAO SendReportDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			SendReportDAO = sqlSendReport;
			break;
			default : 
				SendReportDAO = null;
				break;
		}
		
		if (SendReportDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return SendReportDAO;
	}
}
