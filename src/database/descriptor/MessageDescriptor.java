package database.descriptor;

import database.UserData;

/**
 * It is a descriptor of a message recovered from database, not a message to be sent.
 */
public class MessageDescriptor {

	public int messageID;
	public int senderID;
	public String senderName;
	public int recipientID;
	public String recipientName;
	public String deliveryTime;
	public String content;
	public boolean unread;
	
	public MessageDescriptor(int messageID, int senderID, int recipientID, String deliveryTime, String content, boolean unread){
		this.messageID = messageID;
		this.senderID = senderID;
		this.recipientID = recipientID;
		this.deliveryTime = deliveryTime;
		this.content = content;
		this.unread = unread;
		this.senderName = (senderID == 1)? "admin" : UserData.getUsername(senderID);
		this.recipientName = UserData.getUsername(recipientID);
	}
}
