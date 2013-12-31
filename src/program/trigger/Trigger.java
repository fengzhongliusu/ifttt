package program.trigger;

import assist.GmailException;
import assist.WeiboException;

/**
 * An abstract trigger class. It describes a trigger that 
 * 	when condition satisfied, returns true.
 */
public abstract class Trigger {

	protected int type;
	
	public final static int TIME_TRIGGER = 0;
	public final static int GMAIL_TRIGGER = 1;
	public final static int WEIBO_TRIGGER = 2;
	
	public Trigger(int triggerType) {
		type = triggerType;
	}
	
	public int getType(){
		return type;
	}
	
	public abstract String[] getParams();
	
	public abstract String getDescription();
	
	/**
	 * @return
	 * 		true when contidion satisfied
	 * @throws WeiboException 
	 */
	public abstract boolean Do() throws GmailException, WeiboException;

}
