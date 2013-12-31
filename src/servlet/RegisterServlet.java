package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.MessageData;
import database.UserData;

/**
 * Servlet implementation class TestServlet
 */

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String username = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		String confirmPassword = request.getParameter("user_password_confirm");
		
		if (!password.equals(confirmPassword)) {
			session.setAttribute("log_condition", false);
			response.sendRedirect("/index.jsp");
			return ;
		}
		
		if(UserData.searchUsername(username)) {
			/*username already exist*/
			session.setAttribute("log_condition", false);
			response.sendRedirect("/index.jsp?registererror");
			return;
		}
		int userID = UserData.addUser(username, password);
		session.setAttribute("log_condition", true);
		session.setAttribute("user_id", userID);//save user id in session
		/*send a welcome message*/
		String content = "Hello! This is a IFTTT like website. "
				+ "You are welcomed to create a task in the create pages. "
				+ "Explore and enjoy yourself.";
		MessageData.addMessage(1, userID, content);
		response.sendRedirect("/account/index.jsp");
	}
}
