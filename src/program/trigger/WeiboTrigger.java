package program.trigger;

import java.util.Date;

import weibo4j.model.Status;
import assist.Weibo;
import assist.WeiboException;

/**
 * This trigger will get the user's latest weibo content¡£
 * If the content contains a specific pattern string, the trigger returns true.
 */
public class WeiboTrigger extends Trigger {
	

	String pattern;
	Weibo weibo;
	Date lastTime;

	/**
	 * initial a weibo trigger
	 * @param pattern the pattern string to be checked¡£
	 */
	public WeiboTrigger(String pattern) {
		super(Trigger.WEIBO_TRIGGER);
		this.pattern = pattern;
		weibo = new Weibo();
		lastTime = new Date();
	}

	@Override
	public String[] getParams() {
		String[] params = {pattern};
		return params;
	}

	@Override
	public String getDescription() {
		return "Weibo (" + pattern + ")";
	}

	@Override
	public boolean Do() throws WeiboException{
		Status latestStatus = weibo.getLatestStatus();
		Date createAt = latestStatus.getCreatedAt();//get status create time
		
		long delay = createAt.getTime() - lastTime.getTime();
		System.out.println("delay is " + delay);
		if(delay <= 0)
			return false;
		else if(latestStatus.getText().contains(pattern)){
			lastTime = createAt;
			System.out.println("Find a weibo satisfied!");
			return true;
		}
		return false;
	}


}
