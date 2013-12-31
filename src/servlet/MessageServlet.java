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
 * Servlet implementation class MessageServlet
 */
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageID = Integer.parseInt(request.getParameter("message_id"));
		MessageData.deleteMessage(messageID);
		response.sendRedirect("/account/message.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userID = (Integer) session.getAttribute("user_id");
		String recipientName = request.getParameter("message_user_name");
		String messageContent = request.getParameter("message_content");
		
		//send a private message from admin.
		if (request.getParameter("private") != null) {
			int recipientID = UserData.getUserID(recipientName);
			if(recipientID == -1){
				response.sendRedirect("/admin/message.jsp?sendfailed");
				return;
			}
			MessageData.addMessage(userID, recipientID, messageContent);
			response.sendRedirect("/admin/message.jsp?sendsucess");
		}
		
		//send a public message from admin.
		else if (request.getParameter("public") != null) {
			MessageData.AddPublicMessage(messageContent);
			response.sendRedirect("/admin/message.jsp?sendsucess");
		}
		else {
			int recipientID = UserData.getUserID(recipientName);
			if(recipientID == -1){
				response.sendRedirect("/account/message.jsp?sendfailed");
				return;
			}
			MessageData.addMessage(userID, recipientID, messageContent);
			response.sendRedirect("/account/message.jsp?sendsuccess");
		}
		
	}
}
