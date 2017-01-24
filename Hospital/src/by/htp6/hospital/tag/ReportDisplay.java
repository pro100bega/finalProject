package by.htp6.hospital.tag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp6.hospital.bean.Report;

/**
 * Пользовательский тег, предназначеный для отображения списка жалоб и
 * предложений с помощью технологии пагинации
 * 
 * Custom tag created for display reports with pagination technology
 * 
 * @author Begench Shamuradov, 2017
 */
public class ReportDisplay extends TagSupport {
	private static final long serialVersionUID = 1L;

	private List<Report> reports;

	private int currentPage;

	private int reportsCount;

	private int reportsPerPage;

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setReportsCount(int reportsCount) {
		this.reportsCount = reportsCount;
	}

	public void setReportsPerPage(int reportsPerPage) {
		this.reportsPerPage = reportsPerPage;
	}

	public int doStartTag() throws JspException {
		int pageCount = (int) Math.ceil((double) reportsCount / reportsPerPage);
		HttpSession session = pageContext.getSession();

		String localeName = (String) session.getAttribute("localeName");
		Locale locale = Locale.getDefault();
		ResourceBundle bundle = null;
		if (localeName != null) {
			switch (localeName) {
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

		String id = "id";
		String header = bundle.getString("message.header");
		String message = bundle.getString("message.message");
		String time = bundle.getString("message.time");
		String learnMore = bundle.getString("message.learnMore");

		JspWriter out = pageContext.getOut();

		try {
			out.write("<table class=\"table\">");
			out.write("<thead>");
			out.write("<tr>");
			out.write("<td><strong>" + id + "</strong></td>");
			out.write("<td><strong>" + header + "</strong></td>");
			out.write("<td><strong>" + message + "</strong></td>");
			out.write("<td><strong>" + time + "</strong></td>");
			out.write("<td></td>");
			out.write("</tr>");
			out.write("</thead>");
			out.write("<tbody>");
			for (Report report : reports) {
				String shortMessage = null;
				if (report.getMessage().length() >= 50) {
					shortMessage = report.getMessage().substring(0, 50) + "...";
				} else {
					shortMessage = report.getMessage();
				}
				if ("unread".equals(report.getStatus())) {
					out.write("<tr class=\"info\">");
				} else {
					out.write("<tr>");
				}
				out.write("<td>" + report.getId() + "</td>");
				out.write("<td>" + report.getHeader() + "</td>");
				out.write("<td>" + shortMessage + "</td>");
				out.write("<td>" + report.getTime() + "</td>");
				out.write("<td><a href=\"controller?command=GET_SINGLE_REPORT"
						+ "&id=" + report.getId() + "\">" + learnMore + "</td>");
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
						out.append(
								"<a href=\"controller?command=GET_REPORTS" + "&currentPage=" + i + "\">" + i + " </a>");
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
