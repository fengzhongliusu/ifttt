/*reference list.
 * send mail: http://www.cnblogs.com/codeplus/archive/2011/10/30/2229391.html
 * receive mail: http://blog.csdn.net/xyang81/article/details/7675162
 **/

package assist;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import assist.GmailException;

/**
 * This class contains necessary functions of a gmail.
 * It also provides a default gmail account to use.
 */
public class Gmail {

	private String username;
	private String password;
	private GmailAuthenticator authenticator;
	private int newMessageCount;// how many new messages in mailbox
	private boolean onFirstCheck;

	/**
	 * By default, use this gmail account to send gmail
	 */
	public final static Gmail DEFAULT_GMAIL = new Gmail("inner.vivi@gmail.com","google9321");

	/**
	 * initial
	 * 
	 * @param username
	 * @param password
	 */
	public Gmail(String username, String password) {
		this.username = username;
		this.password = password;
		authenticator = new GmailAuthenticator(username, password);
		newMessageCount = 0;
		onFirstCheck = true;
	}

	/**
	 * send a email
	 * 
	 * @param recipient
	 *            recipient of the email
	 * @param subject
	 *            subject of the email
	 * @param content
	 *            content of the email
	 * @throws GmailException 
	 **/
	public void send(String recipient, String subject, String content) throws GmailException {

		// create a property for send mail session
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smpt.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.connectiontimeout", "30000");

		// create session
		Session session = Session.getInstance(prop, authenticator);
		//session.setDebug(true);

		try {
			// create a message, set addresser, recipient, subject and content
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(authenticator.getUsername()));
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject(subject);
			msg.setContent(content, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (AddressException e) {
			throw new GmailException(GmailException.SEND_ERROR);
		} catch (MessagingException e) {
			throw new GmailException(GmailException.SEND_ERROR);
		}
	}

	/**
	 * Open inbox to check whether new message arrived. Also update the class
	 * variable "newMessageCount" as currentNewMessageCount
	 * 
	 * @return return true when receive new messages
	 * @throws GmailException 
	 **/
	public boolean isNewMessage() throws GmailException {
		
		if(onFirstCheck){
			newMessageCount = getCurrentNewMessageCount();
			onFirstCheck = false;
			System.out.println("no new message on first check.");
			return false;
		}

		int currentNewMessageCount = getCurrentNewMessageCount();
		boolean isNew = (currentNewMessageCount > newMessageCount);
		newMessageCount = currentNewMessageCount;
		if(isNew)
			System.out.println("get new message!");
		else 
			System.out.println("no new message.");
		return isNew;
	}

	/**
	 * open inbox to get current new message number
	 * 
	 * @return current new message numbers
	 * @throws GmailException 
	 */
	private int getCurrentNewMessageCount() throws GmailException {

		int currentNewMessageCount = -1;

		// prepare property and create session
		Properties prop = new Properties();
		prop.put("mail.store.protocol", "imaps");
		Session session = Session.getInstance(prop, null);
		// session.setDebug(true);

		try {
			// create store to fetch inbox
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", username, password);

			// get Inbox folder as read only
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

			// check whether new message
			currentNewMessageCount = folder.getUnreadMessageCount();
			//System.out.println("unread message: " + newMessageCount);

			folder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			throw new GmailException(GmailException.RECV_ERROR);
			
		} catch (MessagingException e) {
			throw new GmailException(GmailException.RECV_ERROR);
		}
		return currentNewMessageCount;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
}


// this priavte class is used for authentication
class GmailAuthenticator extends Authenticator {

	String username;
	String password;

	// initial
	GmailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	String getUsername() {
		return username;
	}

	// override: javax.mail.Authenticator.getPasswordAuthentication
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}
