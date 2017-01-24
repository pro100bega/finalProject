package by.htp6.hospital.tool;

import java.util.ResourceBundle;

/**
 * Класс, предназначенный для хранения информации об используемой технологии
 * доступа к базе данных
 * 
 * Class that contains name of using database technolohy
 * 
 * @author Begench Shamuradov, 2017
 */
public class TechnologyContainer {

private static final String BUNDLE_NAME = "resources.dbResource";
	
	private static final String TECHNOLOGY_KEY = "db.technology";
	
	private static final ResourceBundle BUNDLE = 
			ResourceBundle.getBundle(BUNDLE_NAME);
	
	private static final String TECHNOLOGY = BUNDLE.getString(TECHNOLOGY_KEY);
	
	public static String getTechnologyName() {
		return TECHNOLOGY;
	}
}
