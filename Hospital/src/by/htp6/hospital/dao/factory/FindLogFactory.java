package by.htp6.hospital.dao.factory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.Technology;
import by.htp6.hospital.dao.FindLogDAO;
import by.htp6.hospital.dao.factory.exception.WrongTechnologyException;
import by.htp6.hospital.dao.impl.SQLFindLogDAO;
import by.htp6.hospital.tool.TechnologyContainer;

/**
 * Фабрика содержащая классы поиска лога базы данных
 * 
 * Factory that contains database log searching classes
 * 
 * @author Begench Shamuradov, 2017
 */
public class FindLogFactory {
	private static final Logger log = LogManager.getLogger(ChangeUserTypeFactory.class);

	private static final FindLogFactory instance = new FindLogFactory();

	private static final FindLogDAO sqlFindLog = new SQLFindLogDAO();

	private FindLogFactory() {
	}

	public static FindLogFactory getInstance() {
		return instance;
	}

	public FindLogDAO getFindLogDAO() {
		FindLogDAO findLogDAO;
		
		switch(TechnologyContainer.getTechnologyName()) {
		case Technology.MYSQL:
			findLogDAO = sqlFindLog;
			break;
			default : 
				findLogDAO = null;
				break;
		}
		
		if (findLogDAO == null) {
			log.error(ErrorMessage.WRONG_TECHNOLOGY);
			throw new WrongTechnologyException();
		}
		
		return findLogDAO;
	}
}
