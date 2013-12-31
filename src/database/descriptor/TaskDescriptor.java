package database.descriptor;
/**
 * It is a container of a task recovered from database, not a task to be executed.
 */
public class TaskDescriptor {
	public int taskID;
	public int userID;
	public int triggerType;
	public int actionType;
	public int status;
	public String description;
	public String executionInfo;
	
	public TaskDescriptor(int taskID, int userID, int triggerType, int actionType, int status, String description, String executionInfo){
		this.taskID = taskID;
		this.userID = userID;
		this.triggerType = triggerType;
		this.actionType = actionType;
		this.status = status;
		this.description = description;
		this.executionInfo = executionInfo;
	}
}
