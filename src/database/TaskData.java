package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.descriptor.TaskDescriptor;
import program.Task;

public class TaskData extends Data {

	/**
	 * add a line in task sheet
	 * 
	 * @param userID
	 * @return the task ID -1 when add fails
	 */
	public static int addTask(int userID, Task t) {
		int taskID = -1;
		try {
			// connect
			Connection connection = getWriteConnection();
			// insert a line
			String insertSQL = "insert into task values (null,?,?,?,?,?,null)";
			PreparedStatement statement = connection
					.prepareStatement(insertSQL);
			statement.setInt(1, userID);
			statement.setInt(2, t.getTrigger().getType());
			statement.setInt(3, t.getAction().getType());
			statement.setInt(4, -1);
			statement.setString(5, t.getDescription());
			statement.executeUpdate();
			// get the taskID
			String selectSQL = "select taskID from task";
			ResultSet result = statement.executeQuery(selectSQL);
			result.last();
			taskID = result.getInt(1);
			// close
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskID;
	}

	/**
	 * delete a line in task sheet
	 * 
	 * @param ID
	 *            the ID of the task to be deleted
	 */
	public static void deleteTask(int taskID) {
		try {
			Connection connection = getWriteConnection();
			String sqlString = "DELETE FROM `task` WHERE `taskID`=?";
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, taskID);
			statement.executeUpdate();// TODO: improve to get delete info
										// (success or failed)
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * delete a line in task sheet by userID
	 * 
	 * @param userID
	 */
	public static void deleteTaskByUserID(int userID) {
		try {
			Connection connection = getWriteConnection();
			String sqlString = "DELETE FROM `task` WHERE `userID`=?";
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, userID);
			statement.executeUpdate();// TODO: improve to get delete info
										// (success or failed)
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get all tasks' id belongs to a particular user
	 * 
	 * @param userID
	 *            the user's ID
	 * @return
	 * @return the task ID array
	 */
	public static ArrayList<Integer> getAllTaskID(int userID) {
		ArrayList<Integer> taskIDList = new ArrayList<Integer>();
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT `taskID` FROM `task` WHERE `userID`=?";
				// Attention:'taskID' `taskID` will get different result
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				taskIDList.add(result.getInt("taskID"));
				// System.out.println(result.getString("taskID"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return taskIDList;
	}

	/**
	 * get the number of tasks belong to a particular user
	 * @param userID
	 * @return
	 * 		the taks number
	 */
	public static int getAllTaskNumber(int userID){
		int num = 0;
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `task` WHERE `userID`=?";
				// Attention:'taskID' `taskID` will get different result
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				num++;
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * get the number of running tasks belong to a particular user
	 * @param userID
	 * @return
	 * 		the taks number
	 */
	public static int getAllRunningTaskNumber(int userID){
		int num = 0;
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `task` WHERE `userID`=? and `status`=?";
				// Attention:'taskID' `taskID` will get different result
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, userID);
			statement.setInt(2, Task.RUNNING);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				num++;
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * update the task status field in database
	 * @param taskID
	 * @param status
	 * @return
	 * 		true when updated
	 */
	public static boolean updateTaskStatus(int taskID, int status) {
		// UPDATE `task` SET `status`=? WHERE `taskID` =?
		try {
			Connection connection = getReadConnection();
			String sqlString = "UPDATE `task` SET `status`= ? WHERE `taskID` = ?";
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, status);
			statement.setInt(2, taskID);
			statement.executeUpdate();// TODO:
			connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get a container of a task in database
	 * 
	 * @param taskID
	 * @return 
	 * 		a task container object to describe a task
	 */
	public static TaskDescriptor getTask(int taskID) {
		TaskDescriptor td = null;
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `task` WHERE `taskID`=?";
			PreparedStatement statement = connection
					.prepareStatement(sqlString);
			statement.setInt(1, taskID);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				td = new TaskDescriptor(taskID, result.getInt("userID"),
						result.getInt("triggerType"),
						result.getInt("actionType"), result.getInt("status"),
						result.getString("description"),
						result.getString("execInfo"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return td;
	}

	
	

}
