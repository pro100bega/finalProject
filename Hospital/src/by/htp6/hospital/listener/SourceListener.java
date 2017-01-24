package by.htp6.hospital.listener;

import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.htp6.hospital.dao.pool.SourceInit;
import by.htp6.hospital.dao.pool.exception.ConnectionPoolException;

/**
 * Слушатель контекста сервлета. Инициализирует пул соединений,
 * при создании контекста и закрывает пул соединений при закрытии контекста.
 * 
 * Servlet context listener. Initializes connection pool when servlet context
 * is created and destroys connection pool when servlet context is destroys.
 * 
 * @author Begench Shamuradov, 2017
 */
@WebListener
public class SourceListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		SourceInit sourceInit = SourceInit.getInstance();
		try {
			sourceInit.destroy();
		} catch (IOException e) {
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SourceInit sourceInit = SourceInit.getInstance();
		try {
			sourceInit.init();
		} catch (ConnectionPoolException e) {
		}
	}
}
