package program.action;

import assist.WeiboException;
import assist.GmailException;


/**
 * This class describes a abstract action. The method Do() will be done when triggered.
 */
public abstract class Action {
	
	protected int type;
	
	public final static int GMAIL_ACTION = 0;
	public final static int WEIBO_ACTION = 1;

	public Action(int actionType) {
		type = actionType;
	}
	
	public int getType(){
		return type;
	}
	
	public abstract String[] getParams();
	
	public abstract String getDescription();
	
	public abstract void Do() throws GmailException, WeiboException;

	

}
