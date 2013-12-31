package program.trigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class TimeTrigger extends Trigger {

	protected Date executeTime;
	
	public TimeTrigger(String executeTime) {
		
		super(Trigger.TIME_TRIGGER);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			this.executeTime = formatter.parse(executeTime);
		} catch (ParseException e) {
			System.out.println("Data format error!");
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 * 		true when time up.
	 */
	@Override
	public boolean Do() {
		long delay = executeTime.getTime() - new Date().getTime();
		if(Math.abs(delay) < 5 * 1000)
			return true;
		return false;
	}

	@Override
	public String[] getParams() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] params = {formatter.format(executeTime)};
		return params;
	}

	@Override
	public String getDescription() {
		return "Timing (" + getParams()[0] + ")";
	}
}
