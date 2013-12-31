package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MessageData;
import database.TaskData;
import database.UserData;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String delete = request.getParameter("delete");
		int ID = Integer.parseInt(request.getParameter("id"));
		if (delete.equals("user")) {
			MessageData.deleteMessageByUserID(ID);
			TaskData.deleteTaskByUserID(ID);
			UserData.deleteUser(ID);
		}
		if (delete.equals("task")) {
			TaskData.deleteTask(ID);
		}
		
		response.sendRedirect("/admin/?deletesucess");
	}
}
