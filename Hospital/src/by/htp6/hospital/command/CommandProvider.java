package by.htp6.hospital.command;

import java.util.HashMap;

import java.util.Map;

import by.htp6.hospital.command.impl.SignUpCommand;
import by.htp6.hospital.command.impl.SignInCommand;
import by.htp6.hospital.command.impl.SignOutCommand;
import by.htp6.hospital.command.impl.AddNewPatientCommand;
import by.htp6.hospital.command.impl.ChangeUserTypeCommand;
import by.htp6.hospital.command.impl.AddAddpointmentCommand;
import by.htp6.hospital.command.impl.DischargePatientCommand;
import by.htp6.hospital.command.impl.EditPatientCommand;
import by.htp6.hospital.command.impl.GetPatientListCommand;
import by.htp6.hospital.command.impl.GetReportsCommand;
import by.htp6.hospital.command.impl.GetSingleReportCommand;
import by.htp6.hospital.command.impl.GetUserListCommand;
import by.htp6.hospital.command.impl.FindLogCommand;
import by.htp6.hospital.command.impl.FindPatientCommand;
import by.htp6.hospital.command.impl.GetAdministratorInfoCommand;
import by.htp6.hospital.command.impl.GetEditPatientPageCommand;
import by.htp6.hospital.command.impl.GetPatientInfoCommand;
import by.htp6.hospital.command.impl.PerformAppointmentCommand;
import by.htp6.hospital.command.impl.SendReportCommand;
import by.htp6.hospital.command.impl.SetLocaleCommand;

/**
 * Класс, предназначенный для хранения комманд
 * 
 * Command providing class
 * 
 * @author Begench Shamuradov, 2017
 */
public class CommandProvider {
	private static final CommandProvider instance = new CommandProvider();
	
	private static final String SIGN_IN_COMMAND = "SIGN_IN";
	private static final String SIGN_UP_COMMAND = "SIGN_UP";
	private static final String SIGN_OUT_COMMAND = "SIGN_OUT";
	private static final String SET_LOCALE_COMMAND = "SET_LOCALE";
	private static final String GET_PATIENT_LIST_COMMAND = "GET_PATIENT_LIST";
	private static final String DISCHARGE_PATIENT_COMMAND = "DISCHARGE_PATIENT";
	private static final String FIND_PATIENT_COMMAND = "FIND_PATIENT";
	private static final String ADD_NEW_PATIENT_COMMAND = "ADD_NEW_PATIENT";
	private static final String GET_ADMIN_INFO_COMMAND = "GET_ADMIN_INFO";
	private static final String FIND_LOG_COMMAND = "FIND_LOG";
	private static final String GET_PATIENT_INFO_COMMAND = "GET_PATIENT_INFO";
	private static final String ADD_APPOINTMENT_COMMAND = "ADD_APPOINTMENT";
	private static final String PERFORM_APPOINTMENT_COMMAND = "PERFORM_APPOINTMENT";
	private static final String GET_EDIT_PATIENT_PAGE_COMMAND = "GET_EDIT_PATIENT_PAGE";
	private static final String EDIT_PATIENT_COMMAND = "EDIT_PATIENT";
	private static final String SEND_REPORT_COMMAND = "SEND_REPORT";
	private static final String GET_REPORTS_COMMAND = "GET_REPORTS";
	private static final String GET_SINGLE_REPORT_COMMAND = "GET_SINGLE_REPORT";
	private static final String GET_USER_LIST_COMMAND = "GET_USER_LIST";
	private static final String CHANGE_USER_TYPE_COMMAND = "CHANGE_USER_TYPE";
	
	
	private Map<String, Command> commands = new HashMap<>();
	
	private CommandProvider(){
		commands.put(SIGN_IN_COMMAND, new SignInCommand());
		commands.put(SIGN_UP_COMMAND, new SignUpCommand());
		commands.put(SIGN_OUT_COMMAND, new SignOutCommand());
		commands.put(SET_LOCALE_COMMAND, new SetLocaleCommand());
		commands.put(GET_PATIENT_LIST_COMMAND, new GetPatientListCommand());
		commands.put(DISCHARGE_PATIENT_COMMAND, new DischargePatientCommand());
		commands.put(FIND_PATIENT_COMMAND, new FindPatientCommand());
		commands.put(ADD_NEW_PATIENT_COMMAND, new AddNewPatientCommand());
		commands.put(GET_ADMIN_INFO_COMMAND, new GetAdministratorInfoCommand());
		commands.put(FIND_LOG_COMMAND, new FindLogCommand());
		commands.put(GET_PATIENT_INFO_COMMAND, new GetPatientInfoCommand());
		commands.put(ADD_APPOINTMENT_COMMAND, new AddAddpointmentCommand());
		commands.put(PERFORM_APPOINTMENT_COMMAND, new PerformAppointmentCommand());
		commands.put(GET_EDIT_PATIENT_PAGE_COMMAND, new GetEditPatientPageCommand());
		commands.put(EDIT_PATIENT_COMMAND, new EditPatientCommand());
		commands.put(SEND_REPORT_COMMAND, new SendReportCommand());
		commands.put(GET_REPORTS_COMMAND, new GetReportsCommand());
		commands.put(GET_SINGLE_REPORT_COMMAND, new GetSingleReportCommand());
		commands.put(GET_USER_LIST_COMMAND, new GetUserListCommand());
		commands.put(CHANGE_USER_TYPE_COMMAND, new ChangeUserTypeCommand());
	}
	
	public static CommandProvider getInstance(){
		return instance;
	}
	
	public Command getCommand(String commandName) throws CommandWasNotFoundException{
		Command command;
		command = commands.get(commandName);
		
		if(command == null) {
			throw new CommandWasNotFoundException();
		}
		return command;
	}
	
}
