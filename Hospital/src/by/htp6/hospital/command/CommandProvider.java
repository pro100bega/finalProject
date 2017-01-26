package by.htp6.hospital.command;

import java.util.HashMap;

import java.util.Map;

import by.htp6.hospital.command.impl.SignUpCommand;
import by.htp6.hospital.constant.CommandName;
import by.htp6.hospital.command.impl.SignInCommand;
import by.htp6.hospital.command.impl.SignOutCommand;
import by.htp6.hospital.command.impl.AddNewPatientCommand;
import by.htp6.hospital.command.impl.ChangeUserTypeCommand;
import by.htp6.hospital.command.impl.AddAppointmentCommand;
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
	
	private Map<String, Command> commands = new HashMap<>();
	
	private CommandProvider(){
		commands.put(CommandName.SIGN_IN, new SignInCommand());
		commands.put(CommandName.SIGN_UP, new SignUpCommand());
		commands.put(CommandName.SIGN_OUT, new SignOutCommand());
		commands.put(CommandName.SET_LOCALE, new SetLocaleCommand());
		commands.put(CommandName.GET_PATIENT_LIST, new GetPatientListCommand());
		commands.put(CommandName.DISCHARGE_PATIENT, new DischargePatientCommand());
		commands.put(CommandName.FIND_PATIENT, new FindPatientCommand());
		commands.put(CommandName.ADD_NEW_PATIENT, new AddNewPatientCommand());
		commands.put(CommandName.GET_ADMIN_INFO, new GetAdministratorInfoCommand());
		commands.put(CommandName.FIND_LOG, new FindLogCommand());
		commands.put(CommandName.GET_PATIENT_INFO, new GetPatientInfoCommand());
		commands.put(CommandName.ADD_APPOINTMENT, new AddAppointmentCommand());
		commands.put(CommandName.PERFORM_APPOINTMENT, new PerformAppointmentCommand());
		commands.put(CommandName.GET_EDIT_PATIENT_PAGE, new GetEditPatientPageCommand());
		commands.put(CommandName.EDIT_PATIENT, new EditPatientCommand());
		commands.put(CommandName.SEND_REPORT, new SendReportCommand());
		commands.put(CommandName.GET_REPORTS, new GetReportsCommand());
		commands.put(CommandName.GET_SINGLE_REPORT, new GetSingleReportCommand());
		commands.put(CommandName.GET_USER_LIST, new GetUserListCommand());
		commands.put(CommandName.CHANGE_USER_TYPE, new ChangeUserTypeCommand());
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
