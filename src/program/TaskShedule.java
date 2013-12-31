package program;

import java.util.ArrayList;

import database.TaskData;

public class TaskShedule {
	
	public static ArrayList<Task> taskList = new ArrayList<Task>();
	
	
	/**
	 * traverse the task list to get the task object  
	 * @param taskID the destination task to get
	 * @return
	 * 		the task object(null when error)
	 */
	public static Task getTaskObjectByID(int taskID){
		
		for (Task t : taskList) {
			if(t.getID() == taskID){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * add a task
	 * @param userID the user id of the task
	 * @param t the task body
	 */
	public static void addTask(int userID,Task t){
		//add task to list
		taskList.add(t);
		
		//add task to database
		int taskID = TaskData.addTask(userID, t);
		t.setID(taskID);
		
		//run it
		t.start();
	}


	/**
	 * @param taskID 
	 */
	public static void deleteTask(int taskID) {
	
		Task task = getTaskObjectByID(taskID);
		
		if (task != null) {
			//stop task and delete it from task list
			task.stop();
			taskList.remove(task);
			
			//delete it from database
			TaskData.deleteTask(taskID);
		} else
			System.out.println("get task object error when delete task.");
		
	}


	public static void stopTask(int taskID) {
		Task task = getTaskObjectByID(taskID);
		task.stop();
	}


	public static void pauseTask(int taskID) {
		
		Task task = getTaskObjectByID(taskID);
		task.pause();
	}


	public static void resumeTask(int taskID) {
		Task task = getTaskObjectByID(taskID);
		task.resume();
	}

	
}
