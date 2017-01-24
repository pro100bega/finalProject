package by.htp6.hospital.tool;

import java.util.regex.Pattern;

/**
 * Класс, содержащий регулярные выражения для проверки полей
 * 
 * Regular expression container class
 * 
 * @author Begench Shamuradov, 2017
 */
public class PatternContainer {
	//User name format pattern
	public static final Pattern usernamePattern = 
			Pattern.compile("[0-9a-zA-Z_]{6,20}");
	
	//Password format pattern
	public static final Pattern passwordPattern = 
			Pattern.compile("[0-9a-zA-Z]{6,20}");
	
	//Diagnosis format pattern
	public static final Pattern diagnosisPattern =
			Pattern.compile("^[А-Яа-я \\-]{1,50}$|^[A-Za-z \\-]{1,50}$");
	
	//Patient name format pattern
	public static final Pattern patientNamePattern = 
			Pattern.compile("^[А-Яа-я]{1,20}$|^[A-Za-z]{1,20}$");
	
	//Log search format pattern
	public static final Pattern logSearchPattern =
			Pattern.compile("^[A-Za-z0-9\\._: ]{1,18}$");
	
	//Appointment name format pattern
	public static final Pattern appointmentNamePattern =
			Pattern.compile("^[А-Яа-я ё\\-,\\.]{2,50}$");
	
	//Ru date format pattern
	public static final Pattern enDateFormatPattern =
			Pattern.compile("^(19|20)\\d\\d-((0[1-9]|1[012])-"
					+ "(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)$");
	
	//En date format pattern
	public static final Pattern ruDateFormatPattern = 
			Pattern.compile("((0[1-9]|[12]\\d)\\.(0[1-9]|1[012])|30\\.(0[13-9]|1[012])"
					+ "|31\\.(0[13578]|1[02]))\\.(19|20)\\d\\d");
	
	//Note format pattern
	public static final Pattern notePatern =
			Pattern.compile("^[А-Яа-я0-9\\. \\-,]{0,100}$");
	
	//Bug report header format pattern
	public static final Pattern headerPattern =
			Pattern.compile("^[A-Za-z \\.,\\-!\\?]{5,20}$|^[А-Яа-я ё\\.,\\-!\\?]{5,20}$");
	
	//Bug report message format pattern
	public static final Pattern messagePattern =
			Pattern.compile("^[A-Za-z \\.,\\-!\\?]{5,255}$|^[А-Яа-я ё\\.,\\-!\\?]{5,255}$");
}
