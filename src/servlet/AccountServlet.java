package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UserData;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		int userID = (Integer)session.getAttribute("user_id");
		if (uri.endsWith("info")) {
			
			String newPassword = request.getParameter("new_password");
			UserData.updatePassword(userID, newPassword);
			response.sendRedirect("/account/info.jsp");
		}
		
		if (uri.endsWith("recharge")) {
			int points = Integer.parseInt(request.getParameter("points"));
			UserData.updatePoints(userID, points);
			response.sendRedirect("/account/info.jsp");
		}
	}

}
