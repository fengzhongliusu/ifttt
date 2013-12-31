package assist;


/**
 * Program throw a GmailException when gmail functions fail
 */
@SuppressWarnings("serial")
public class GmailException extends Exception {

	public int type;
	
	public final static int SEND_ERROR = 0;
	
	public final static int RECV_ERROR = 1;
	
	
	public GmailException(int type) {
		this.type = type;
	}
}
