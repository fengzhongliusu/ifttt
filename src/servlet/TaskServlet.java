package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UserData;
import program.Task;
import program.TaskShedule;
import program.action.Action;
import program.trigger.Trigger;

/**
 * Servlet implementation class TaskServlet
 */
public class TaskServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String control = request.getParameter("control");
		String taskID = request.getParameter("task_id");
		
		if (control.equals("delete")) {
			int ID = Integer.valueOf(taskID);
			TaskShedule.deleteTask(ID);
		}
		if (control.equals("start")) {
			int ID = Integer.valueOf(taskID);
			TaskShedule.resumeTask(ID);
		}
		if (control.equals("pause")) {
			int ID = Integer.valueOf(taskID);
			TaskShedule.pauseTask(ID);
		}
		if (control.equals("stop")) {
			int ID = Integer.valueOf(taskID);
			TaskShedule.stopTask(ID);
		}
		response.sendRedirect("/task/browse.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int userID = (Integer)session.getAttribute("user_id");
		
		int points = UserData.getUser(userID).points;
		if (points >= 100) {							//100 points for a task.
			UserData.costPoints(userID, 100);
			UserData.updateExperience(userID, 100);
			int triggerType = Integer.parseInt(request.getParameter("triggertype"));
			int actionType = Integer.parseInt(request.getParameter("actiontype"));
			String[] triggerParams = getTriggerParams(triggerType, request);
			String[] actionParams = getActionParams(actionType, request);
			Task task = new Task(triggerType, actionType, triggerParams, actionParams);
			TaskShedule.addTask(userID, task);
			response.sendRedirect("/account/index.jsp?sucess");
		}
		else {
			response.sendRedirect("/account/index.jsp?fail");
		}
	}
	
	
	/**
	 * get trigger params from the post
	 * 
	 * @param triggerType
	 * @param request
	 * @return the trigger params string array
	 */
	private String[] getTriggerParams(int triggerType, HttpServletRequest request) {
		String[] triggerParams = new String[3];
		if (triggerType == Trigger.TIME_TRIGGER) {
			triggerParams[0] = request.getParameter("reaching_time");
		} else if (triggerType == Trigger.GMAIL_TRIGGER) {
			triggerParams[0] = request.getParameter("receive_mail_address");
			triggerParams[1] = request.getParameter("receive_mail_password");
		} else if (triggerType == Trigger.WEIBO_TRIGGER) {
			triggerParams[0] = request.getParameter("receive_weibo_content");
		}
		else 
			System.out.print("trigger type error when get trigger params");
		return triggerParams;
	}

	/**
	 * get action params from the post
	 * 
	 * @param actionType
	 * @param request
	 * @return the action params string array
	 */
	private String[] getActionParams(int actionType, HttpServletRequest request) {
		String[] actionrParams = new String[3];
		if (actionType == Action.GMAIL_ACTION) {
			actionrParams[0] = request.getParameter("send_mail_address");
			actionrParams[1] = request.getParameter("send_mail_subject");
			actionrParams[2] = request.getParameter("send_mail_content");

		} else if (actionType == Action.WEIBO_ACTION) {
			actionrParams[0] = request.getParameter("send_weibo_content");
		} else
			System.out.print("action type error when get trigger params");
		return actionrParams;
	}
}
