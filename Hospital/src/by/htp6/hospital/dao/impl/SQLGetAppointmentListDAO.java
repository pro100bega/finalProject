package by.htp6.hospital.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.htp6.hospital.bean.Appointment;
import by.htp6.hospital.constant.DatabaseColumnName;
import by.htp6.hospital.constant.DateFormat;
import by.htp6.hospital.constant.ErrorMessage;
import by.htp6.hospital.constant.SqlQuery;
import by.htp6.hospital.dao.GetAppointmentListDAO;
import by.htp6.hospital.dao.exception.DAOException;
import by.htp6.hospital.dao.pool.ConnectionPool;

/**
 * Класс для получения списка назначений из базы данных
 * 
 * Class for getting appointments list from database
 * 
 * @author Begench Shamuradov, 2017
 */
public class SQLGetAppointmentListDAO implements GetAppointmentListDAO{
	private static final Logger log = LogManager.getLogger(SQLGetAppointmentListDAO.class);
	
	@Override
	public List<Appointment> getAppointmentList(int patientId, String status) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.take();
			
			preparedStatement = connection.prepareStatement(SqlQuery.GET_APPOINTMENT_LIST);
			preparedStatement.setInt(1, patientId);
			preparedStatement.setString(2, status);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.RU_DATE_FROMAT_WC);
			Timestamp date;
			Appointment appointment;
			List<Appointment> appointments = new ArrayList<>();
			while (resultSet.next()){
				int id = resultSet.getInt(DatabaseColumnName.ID);
				int doctorId = resultSet.getInt(DatabaseColumnName.DOCTOR_ID);
				String type = resultSet.getString(DatabaseColumnName.TYPE);
				String name = resultSet.getString(DatabaseColumnName.NAME);
				date = resultSet.getTimestamp(DatabaseColumnName.APPOINTMENT_DATE);
				String appointmentDate = dateFormat.format(date);
				date = resultSet.getTimestamp(DatabaseColumnName.APPOINTMENT_TERM);
				String appointmentTerm = dateFormat.format(date);
				date = resultSet.getTimestamp(DatabaseColumnName.PERFORM_DATE);
				String performDate = (date == null) ? null : dateFormat.format(date);
				appointment = new Appointment(id, patientId, doctorId, type, name,
						status, appointmentDate, appointmentTerm, performDate);
				appointments.add(appointment);
			}
			
			if (appointments.isEmpty()){
				appointments = Collections.emptyList();
			}
			
			return appointments;
			
		} catch (InterruptedException e) {
			log.error(ErrorMessage.UNABLE_TO_TAKE_CONNECTION, e);
			throw new DAOException(e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				log.error(ErrorMessage.UNABLE_TO_CLOSE_STATEMENT, e);
			}
			
			try {
				if (connection != null) {
					connectionPool.free(connection);
				}
			} catch (InterruptedException e) {
				log.error(ErrorMessage.UNABLE_TO_FREE_CONNECTION, e);
			}

		}
	}
}
