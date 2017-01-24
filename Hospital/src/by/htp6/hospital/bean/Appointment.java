package by.htp6.hospital.bean;

import java.io.Serializable;

/**
 * Класс, описывающий сущность "назначение"
 * 
 * Class describing "appointment" bean
 * 
 * @author Begench Shamuradov, 2017
 */
public class Appointment implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private int patientId;

	private int doctorId;

	private String type;

	private String name;

	private String status;

	private String appointmentDate;

	private String appointmentTerm;

	private String performDate;

	public Appointment() {
		super();
	}
	
	public Appointment(int id, int patientId, int doctorId, String type, String name, String status,
			String appointmentDate, String appointmentTerm, String performDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.type = type;
		this.name = name;
		this.status = status;
		this.appointmentDate = appointmentDate;
		this.appointmentTerm = appointmentTerm;
		this.performDate = performDate;
	}
	
	public Appointment(int patientId, int doctorId, String type, String name, 
			String status, String appointmentTerm) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.type = type;
		this.name = name;
		this.status = status;
		this.appointmentTerm = appointmentTerm;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTerm() {
		return appointmentTerm;
	}

	public void setAppointmentTerm(String appointmentTerm) {
		this.appointmentTerm = appointmentTerm;
	}

	public String getPerformDate() {
		return performDate;
	}

	public void setPerformDate(String performDate) {
		this.performDate = performDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appointmentDate == null) ? 0 : appointmentDate.hashCode());
		result = prime * result + ((appointmentTerm == null) ? 0 : appointmentTerm.hashCode());
		result = prime * result + doctorId;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + patientId;
		result = prime * result + ((performDate == null) ? 0 : performDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (appointmentDate == null) {
			if (other.appointmentDate != null)
				return false;
		} else if (!appointmentDate.equals(other.appointmentDate))
			return false;
		if (appointmentTerm == null) {
			if (other.appointmentTerm != null)
				return false;
		} else if (!appointmentTerm.equals(other.appointmentTerm))
			return false;
		if (doctorId != other.doctorId)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (patientId != other.patientId)
			return false;
		if (performDate == null) {
			if (other.performDate != null)
				return false;
		} else if (!performDate.equals(other.performDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
