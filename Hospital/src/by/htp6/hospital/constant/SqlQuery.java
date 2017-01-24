package by.htp6.hospital.constant;

/**
 * Предподготовленные запросы к базе данных
 * 
 * Prepared database queries
 * 
 * @author Begench Shamuradov, 2017
 */
public class SqlQuery {

	public static final String ADD_APPOINTMENT = "INSERT INTO appointment(patient_id,"
			+ " doctor_id, type, name, status, appointment_term) VALUES (?,?,?,?,?,?)";
	public static final String ADD_NEW_PATIENT = "INSERT INTO patient(`name`, `surname`,"
			+ " `sex`, `birth_date`, `diagnosis`, `doctor_id`, `note`) VALUES (?,?,?,?,?,?,?)";
	public static final String CHANGE_USER_TYPE = "UPDATE user SET type = ? WHERE id = ?";
	public static final String DISCHARGE_PATIENT = "DELETE FROM patient WHERE id = ?";
	public static final String SET_FINAL_DIAGNOSIS = "UPDATE patient SET diagnosis = ? "
			+ "WHERE id = ?";
	public static final String EDIT_PATIENT = "UPDATE patient SET name = ?, surname = ?,"
			+ " sex = ?, birth_date = ?, diagnosis = ?, note = ? WHERE id = ?";
	public static final String FIND_LOG = "CALL find_log(?)";
	public static final String FIND_PATIENT_BY_DOCTOR_ID = "CALL find_patients_by_doctor_"
			+ "id(?,?,?,?)";
	public static final String FIND_PATIENT = "CALL find_patients(?,?,?)";
	public static final String GET_APPOINTMENT_LIST = "SELECT * FROM appointment WHERE "
			+ "patient_id = ? AND status = ? ORDER BY appointment_term";
	public static final String GET_LOG = "SELECT * FROM log";
	public static final String GET_PATIENT = "SELECt * FROM patient WHERE id = ? limit 1";
	public static final String GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_NAME = 
			"SELECT * FROM patient WHERE doctor_id = ? ORDER BY name limit ?, ?";
	public static final String GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_SURNAME = 
			"SELECT * FROM patient WHERE doctor_id = ? ORDER BY surname limit ?, ?";
	public static final String GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_DIAGNOSIS = 
			"SELECT * FROM patient WHERE doctor_id = ? ORDER BY diagnosis limit ?, ?";
	public static final String GET_PATIENT_LIST_FOR_DOCTOR_ORDER_BY_RECEIPT = 
			"SELECT * FROM patient WHERE doctor_id = ? ORDER BY receipt_date DESC limit ?, ?";
	public static final String GET_ALL_PATIENTS_LIST_ORDER_BY_NAME = "SELECT * FROM patient"
			+ " ORDER BY name LIMIT ?, ?";
	public static final String GET_ALL_PATIENTS_LIST_ORDER_BY_SURNAME = "SELECT * FROM patient "
			+ "ORDER BY surname LIMIT ?, ?";
	public static final String GET_ALL_PATIENTS_LIST_ORDER_BY_DIAGNOSIS = "SELECT * FROM patient "
			+ "ORDER BY diagnosis LIMIT ?, ?";
	public static final String GET_ALL_PATIENTS_LIST_ORDER_BY_RECEIPT = "SELECT * FROM patient "
			+ "ORDER BY receipt_date DESC LIMIT ?, ?";
	public static final String GET_PATIENTS_COUNT_FOR_NURSE = "CALL get_patient_count(?)";
	public static final String GET_PATIENTS_COUNT_FOR_DOCTOR = "CALL get_patient_count_by_"
			+ "doctor_id(?,?)";
	public static final String GET_ALL_PATIENTS_COUNT_FOR_NURSE = "SELECT COUNT(*) FROM "
			+ "patient";
	public static final String GET_ALL_PATIENTS_COUNT_FOR_DOCTOR = "SELECT COUNT(*) FROM "
			+ "patient WHERE doctor_id = ?";
	public static final String GET_REPORTS_COUNT = "SELECT COUNT(*) FROM report";
	public static final String GET_REPORTS = "SELECT * FROM report ORDER BY time DESC "
			+ "limit ?, ?";
	public static final String GET_SINGLE_REPORT = "SELECT * FROM report WHERE id = ? "
			+ "limit 1";
	public static final String CHANGE_REPORT_STATUS = "UPDATE report SET status = 'read' "
			+ "WHERE id = ?";
	public static final String GET_UNREAD_REPORTS_COUNT = "SELECT COUNT(*) FROM report "
			+ "WHERE status = 'unread'";
	public static final String GET_USER_LIST = "SELECT * FROM user LIMIT ?, ?";
	public static final String GET_USERS_COUNT = "SELECT COUNT(*) FROM user";
	public static final String PERFORM_APPOINTMENT = "UPDATE appointment SET status = "
			+ "'done', perform_date = CURRENT_TIMESTAMP WHERE id = ?";
	public static final String SEND_REPORT = "INSERT INTO report(`header`, `message`, "
			+ "`status`) VALUES (?, ?, 'unread')";
	public static final String SIGN_IN = "SELECT * FROM user WHERE username = ?";
	public static final String SIGN_UP = "INSERT INTO user(username, password, type) "
			+ "VALUES(?,?,?)";
	public static final String CHECK_USERNAME = "CALL check_username(?)";
}
