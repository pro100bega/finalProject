package by.htp6.hospital.tag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp6.hospital.bean.User;

/**
 * Пользовательский тег, предназначеный для отображения списка пользователей
 * с помощью технологии пагинации
 * 
 * Custom tag created for display users with pagination technology
 * 
 * @author Begench Shamuradov, 2017
 */
public class UserDisplay extends TagSupport {
	private static final long serialVersionUID = 1L;

	private List<User> users;

	private int currentPage;

	private int usersCount;

	private int usersPerPage;

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public void setUsersPerPage(int usersPerPage) {
		this.usersPerPage = usersPerPage;
	}

	public int doStartTag() throws JspException {
		int pageCount = (int) Math.ceil((double) usersCount / usersPerPage);
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

		String username = bundle.getString("placeholder.username");
		String type = bundle.getString("message.userType");
		String registrationTime = bundle.getString("message.registrationTime");
		String changeType = bundle.getString("message.changeType");
		String userMessage = bundle.getString("message.user");
		String admin = bundle.getString("message.admin");
		String doctor = bundle.getString("message.doctor");
		String nurse = bundle.getString("message.nurse");
		String guest = bundle.getString("message.guest");
		String changeTypeButton = bundle.getString("button.changeType");

		JspWriter out = pageContext.getOut();

		try {
			out.write("<div class='row'>");
			for (User user : users) {
				out.write("<div class='col-sm-4'>");
				out.write("<div class='panel panel-default'>");
				out.write("<div class='panel-heading'>");
				out.write("<h3 class='panel-title'>" + userMessage + 
						" №" + user.getId() + "</h3>");
				out.write("</div>");
				out.write("<div class='panel-body'>");
				out.write("<p>");
				out.write("<label>" + username + ":</label><br>");
				out.write(user.getUsername());
				out.write("</p>");
				out.write("<p>");
				out.write("<label>" + type + ":</label><br>");
				out.write(user.getType());
				out.write("</p>");
				out.write("<p>");
				out.write("<label>" + registrationTime + ":</label><br>");
				out.write(user.getCreateTime());
				out.write("</p>");
				out.write("<p>");
				out.write("<label>" + changeType + ":</label><br>");
				out.write("<form action='controller' method='post'>");
				out.write("<input type='hidden' name='command' value='CHANGE_USER_TYPE'>");
				out.write("<input type='hidden' name='id' value=" + user.getId() + ">");
				out.write("<select name='newType' class='form-control'>");
				out.write("<option value='admin' selected>" + admin + "</option>");
				out.write("<option value='doctor'>" + doctor + "</option>");
				out.write("<option value='nurse'>" + nurse + "</option>");
				out.write("<option value='guest'>" + guest + "</option>");
				out.write("</select>");
				out.write("</p>");
				out.write("<button type='submit' class='btn btn btn-success'>" + changeTypeButton + "</button>");
				out.write("</form>");
				out.write("</div>");
				out.write("</div>");
				out.write("</div>");
			}
			out.write("</div>");

			out.write("<center>");
			if (pageCount != 1) {
				for (int i = 1; i <= pageCount; i++) {
					if (i == currentPage) {
						out.append(i + " ");
					} else {
						out.append("<a href=\"controller?command=GET_USER_LIST" + "&currentPage=" + i + "\">" + i
								+ " </a>");
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
