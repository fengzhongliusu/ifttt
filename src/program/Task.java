package program;

import program.action.*;
import program.trigger.*;
import assist.*;
import database.TaskData;//this is bad.


/**
 * A task is a runnable thread which contains a trigger and an action.
 * The action will be done when triggered.
 *
 */
public class Task implements Runnable{
	
	private int ID;
	private int status;
	private Trigger trigger;
	private Action action;

	
	public final static int INITIAL = -1;
	public final static int RUNNING = 0;
	public final static int WAITING = 1;
	public final static int PAUSED = 2;
	public final static int FINISHED = 3;
	public final static int STOPPED = 4;
	
	
	public Task(int triggerType, int actionType, String[] triggerParams, String[]actionParams) {
		status  = INITIAL;
		initTrigger(triggerType,triggerParams);
		initAction(actionType, actionParams);
	}
	
	private void initTrigger(int triggerType, String[] params) {
		if(triggerType == Trigger.TIME_TRIGGER)
			trigger = new TimeTrigger(params[0]);
		else if(triggerType == Trigger.GMAIL_TRIGGER)
			trigger = new GmailTrigger(params[0], params[1]);
		else if(triggerType == Trigger.WEIBO_TRIGGER)
			trigger = new WeiboTrigger(params[0]);
		else
			System.out.println("trigger type error when form a task!");
	}

	private void initAction(int actionType, String[] params) {
		if(actionType == Action.WEIBO_ACTION)
			action = new WeiboAction(params[0]);
		else if(actionType == Action.GMAIL_ACTION)
			action = new GmailAction(params[0], params[1],params[2]);
		else 
			//throw
			System.out.println("action type error when form a task!!");
	}
	
	/**
	 * When a task starts , this method will be invoked.
	 * It will not finish until task done or deleted.
	 */
	public void run() {
		
		labelA: 
		while(true){
			switch (status) {
			case RUNNING:
					try {
						if(trigger.Do()){
							action.Do();
							
							status = (status == RUNNING) ? 
									(status = (trigger.getType() == Trigger.TIME_TRIGGER) ? 
											FINISHED : WAITING) : status;            
						}
						else{
							status = (status == RUNNING)? WAITING:status;
					}	
					} catch (WeiboException e) {//TODO: improve
						System.out.println("微博发送失败，请检查网络连接");
						status = STOPPED;
					} catch (GmailException e){
						if(e.type == GmailException.SEND_ERROR)
							System.out.println("邮件发送失败，请检查网络连接");
						if(e.type == GmailException.RECV_ERROR)
							System.out.println("邮箱连接失败，请确检查邮箱账号密码,并确认网络连接正常");
						status = STOPPED;
					}
					break;
			case WAITING:
				try {
					/*due to the weibo api restrict, weibo trigger task will sleep 30 seconds per cycle*/
					int waitSeconds = (trigger.getType() == Trigger.WEIBO_TRIGGER)? 30 : 10;
					Thread.sleep(waitSeconds * 1000);//sleep for seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				status = (status == WAITING) ? RUNNING : status;
				break;
			case PAUSED:
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case FINISHED:
				finish();
				break labelA;
			case STOPPED:
				stop();
				break labelA;
			default:
				System.out.println("task status error!");break;
			}
			//end of a task execution loop
		}
		//end of a task(thread)
	}
	
	/**
	 * Create a thread and start the task. 
	 */
	public void start(){
		if(status == INITIAL){
			System.out.println("task \"" + ID + "\" start!");
			status = RUNNING;
			Thread thread = new Thread(this, "thread:" + ID);
			thread.start();
			//update database
			TaskData.updateTaskStatus(ID, RUNNING);
		}
	}
	
	public void stop() {
		System.out.println("task \"" + ID + "\" stopped!");
		status = STOPPED;
		
		TaskData.updateTaskStatus(ID, STOPPED);
	}
	
	public void finish() {
		System.out.println("task \"" + ID + "\" finish!");
		status = Task.FINISHED;
		
		TaskData.updateTaskStatus(ID, FINISHED);
	}
	
	public void pause(){
		if (status == Task.RUNNING || status == Task.WAITING) {
			System.out.println("task \"" + ID + "\" paused!");
			status = Task.PAUSED;
		
		TaskData.updateTaskStatus(ID, PAUSED);
		}
	}

	public void resume(){
		if(status == Task.PAUSED){
			System.out.println("task \"" + ID + "\" resume!");
			status = Task.RUNNING;
			
			TaskData.updateTaskStatus(ID, RUNNING);
		}
	}

	public int getStatus(){
		return status;
	}
	
	public void setID(int taskID) {
		ID = taskID;
	}
	
	public int getID(){
		return ID;
	}
	
	public Trigger getTrigger(){
		return trigger;
	}
	
	public Action getAction(){
		return action;
	}

	public String getDescription() {
		return trigger.getDescription() + " split " + action.getDescription();
	}
	
	/*
	public static void main(String[] args){
		String[] triggerParams = {"123"};
		String[] actionParams = {"i've posted a status containting a particular pattern."};
		Task task = new Task(Trigger.WEIBO_TRIGGER, Action.WEIBO_ACTION, triggerParams, actionParams);
		//System.out.print(task.getTrigger().getDescription());
		task.start();
	}*/

}