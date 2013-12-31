package program.action;

import assist.GmailException;
import assist.Gmail;

/**
 * The specific action of send a gmail. 
 */
public class GmailAction extends Action {

	private String recipient;
	private String subject;
	private String content;
	private Gmail gmail;//use this gmail account to send gmail
	
	public GmailAction(String recipient, String subject, String content) {
		super(Action.GMAIL_ACTION);
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
		this.gmail = Gmail.DEFAULT_GMAIL;
	}

	/**
	 * send a gmail
	 */
	@Override
	public void Do() throws GmailException {
		gmail.send(recipient, subject, content);
		System.out.println("send a mail");
	}

	@Override
	public String[] getParams() {
		String[] params = {recipient, subject, content};
		return params;
	}

	@Override
	public String getDescription() {
		return "Send a mail to " + recipient;
	}


}
