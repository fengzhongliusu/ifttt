package program.trigger;

import assist.Gmail;
import assist.GmailException;

/**
 * a specific trigger of gmail. Do() is the core method
 */
public class GmailTrigger extends Trigger {

	private Gmail gmail;//check this mailbox for new message
	
	public GmailTrigger(String username, String password) {
	
		super(Trigger.GMAIL_TRIGGER);
		gmail = new Gmail(username, password);
	}

	/**
	 * @return
	 * 		true when get new message.
	 * @throws GmailException
	 * 		when network error or authentication failed
	 */
	@Override
	public boolean Do() throws GmailException {
		return gmail.isNewMessage();//only check for one time on each invoke
	}

	@Override
	public String[] getParams() {
		String[] params = {gmail.getUsername(), gmail.getPassword()};
		return params;
	}

	@Override
	public String getDescription() {
		return "Gmail (" + gmail.getUsername() + ")";
	}

}
