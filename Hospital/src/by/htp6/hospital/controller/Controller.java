package by.htp6.hospital.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.command.Command;
import by.htp6.hospital.command.CommandProvider;
import by.htp6.hospital.command.CommandWasNotFoundException;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.ParameterName;
import by.htp6.hospital.constant.Url;

/**
 * Front controller
 * 
 * Главный контроллер 
 * 
 * @author Begench Shamuradov, 2017
 */
@WebServlet("/controller")
public final class Controller extends HttpServlet {
	private static final Logger log = LogManager.getLogger(Controller.class);
	
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getAndExecuteCommand(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getAndExecuteCommand(request, response);
	}
	
	private void getAndExecuteCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommandProvider commandProvider = CommandProvider.getInstance();
		String commandName = request.getParameter(ParameterName.COMMAND);
		Command command;
		try {
			command = commandProvider.getCommand(commandName);
			command.execute(request, response);
			
		} catch (CommandWasNotFoundException e) {
			log.error(ErrorMessage.COMMAND_WAS_NOT_FOUND + commandName, e);
			response.sendRedirect(Url.ERROR);
		}
	}

}
