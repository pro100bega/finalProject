package by.htp6.hospital.tag;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp6.hospital.bean.Patient;

/**
 * Пользовательский тег, предназначеный для отображения списка пациентов
 * с помощью технологии пагинации
 * 
 * Custom tag created for display patients with pagination technology
 * 
 * @author Begench Shamuradov, 2017
 */
@SuppressWarnings("serial")
public class PatientDisplay extends TagSupport {

	private List<Patient> patients;

	private int currentPage;

	private int patientsCount;

	private int patientsPerPage;

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPatientsCount(int patientsCount) {
		this.patientsCount = patientsCount;
	}

	public void setPatientsPerPage(int patientsPerPage) {
		this.patientsPerPage = patientsPerPage;
	}

	public int doStartTag() throws JspException {
		int pageCount = (int) Math.ceil((double) patientsCount / patientsPerPage);
		HttpSession session = pageContext.getSession();
		
		String localeName = (String) session.getAttribute("localeName");
		Locale locale = Locale.getDefault();
		ResourceBundle bundle = null;
		if (localeName != null) {
			switch (localeName){
			case "ru_RU":
				locale = new Locale("ru", "RU");
				bundle = ResourceBundle.getBundle("resources.localization", locale);
				break;
			case "en_US":
				locale = new Locale("en", "US");
				bundle = ResourceBundle.getBundle("resources.localization", locale);
				break;
			}
		} else {
			bundle = ResourceBundle.getBundle("resources.localization");
		}

		String name = bundle.getString("message.name");
		String surname = bundle.getString("message.surname");
		String diagnosis = bundle.getString("message.diagnosis");
		String receiptDate = bundle.getString("message.receiptDate");
		String learnMore = bundle.getString("message.learnMore");
		
		JspWriter out = pageContext.getOut();

		try {
			out.write("<table class=\"table\">");
			out.write("<thead>");
			out.write("<tr>");
			out.write("<td><strong>" + name + "</strong></td>");
			out.write("<td><strong>" + surname + "</strong></td>");
			out.write("<td><strong>" + diagnosis + "</strong></td>");
			out.write("<td><strong>" + receiptDate + "</strong></td>");
			out.write("<td></td>");
			out.write("</tr>");
			out.write("</thead>");
			out.write("<tbody>");
			for (Patient patient : patients) {
				out.write("<tr>");
				out.write("<td>" + patient.getName() + "</td>");
				out.write("<td>" + patient.getSurname() + "</td>");
				out.write("<td>" + patient.getDiagnosis() + "</td>");
				out.write("<td>" + patient.getReceiptDate() + "</td>");
				out.write("<td><a href=\"controller?command=GET_PATIENT_INFO&status=undone"
						+ "&patientId=" + patient.getId() + "\">" + learnMore + "</td>");
				out.write("</tr>");
			}
			out.write("</tbody>");
			out.write("</table>");
			out.write("<center>");
			if (pageCount != 1) {
				for (int i = 1; i <= pageCount; i++) {
					if (i == currentPage) {
						out.append(i + " ");
					} else {
						out.append("<a href=\"controller?command=GET_PATIENT_LIST" + "&currentPage=" + i
								+ "\">" + i + " </a>");
					}
				}
			}
			out.write("</center>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
