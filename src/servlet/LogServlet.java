package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UserData;

/**
 * Servlet implementation class LogServlet
 */
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		if (uri.endsWith("logout")) {
			session.setAttribute("log_condition", false);
			response.sendRedirect("/index.jsp");
		}
		
		if (uri.endsWith("login")) {
			if (session.getAttribute("log_condition") != null) {
				boolean logCondition = (Boolean)session.getAttribute("log_condition");
				if (logCondition == true)
					response.sendRedirect("/account/index.jsp");
				if (logCondition == false)
					response.sendRedirect("/index.jsp");
			}
			else response.sendRedirect("/index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("log_condition", false);
		
		String username = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		
		boolean logSuccess = UserData.accountCheck(username, password);
		
		if (logSuccess == true) {
			session.setAttribute("log_condition", true);
			int userID = UserData.getUserID(username);
			session.setAttribute("user_id", userID);    //save user id in session
			if (userID == 1) 
				response.sendRedirect("/admin/index.jsp");	//user id 1 is admin.
			else response.sendRedirect("/account/index.jsp");
		}
		
		if (logSuccess == false) {
			session.setAttribute("log_condition", false);
			response.sendRedirect("/index.jsp?logerror");
		}
	}
}
