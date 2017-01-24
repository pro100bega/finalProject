package by.htp6.hospital.bean;

import java.io.Serializable;

/**
 * Класс, описывающий сущность "пациент"
 * 
 * Class describing "patient" bean
 * 
 * @author Begench Shamuradov, 2017
 */
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String surname;
	
	private String sex;
	
	private String birthDate;
	
	private String diagnosis;
	
	private int doctorId;
	
	private String receiptDate;
	
	private String note;
	
	public Patient() {
		super();
	}
	
	public Patient(int id, String name, String surname, String sex, String birthDate, String diagnosis, int doctorId,
			String receiptDate, String note) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.diagnosis = diagnosis;
		this.doctorId = doctorId;
		this.receiptDate = receiptDate;
		this.note = note;
	}

	public Patient(String name, String surname, String sex, String birthDate, String diagnosis, int doctorId,
			String note) {
		super();
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.diagnosis = diagnosis;
		this.doctorId = doctorId;
		this.note = note;
	}

	public Patient(String name, String surname, String sex, String birthDate, String diagnosis, int doctorId) {
		super();
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.diagnosis = diagnosis;
		this.doctorId = doctorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((diagnosis == null) ? 0 : diagnosis.hashCode());
		result = prime * result + doctorId;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((receiptDate == null) ? 0 : receiptDate.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode()); 
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Patient other = (Patient) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (diagnosis == null) {
			if (other.diagnosis != null)
				return false;
		} else if (!diagnosis.equals(other.diagnosis))
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
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (receiptDate == null) {
			if (other.receiptDate != null)
				return false;
		} else if (!receiptDate.equals(other.receiptDate))
			return false;
		if (sex == null) { 
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
}
