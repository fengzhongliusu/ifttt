package database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import database.descriptor.MessageDescriptor;



public class MessageData extends Data{
	
	
	/*
	CREATE TABLE message
	(
		ID INT AUTO_INCREMENT,
		senderID INT,
		recipientID INT,
		deliveryTime CHAR(30),
		content TEXT,
		unread BOOLEAN,
	    PRIMARY KEY(ID)
	);
	*/
	
	/**
	 * add a line in message sheet
	 * 
	 * @param senderID
	 * @param recipientID
	 * @param content
	 * @return
	 * 		the message id. -1 when error
	 */
	public static int addMessage(int senderID, int recipientID, String content){
		int messageID = -1;
		try {
			Connection connection = getWriteConnection();
			String sqlString = "insert into message values (null,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, senderID);
			statement.setInt(2, recipientID);
			statement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));//set delivery time as current time
			statement.setString(4, content);
			statement.setBoolean(5, true);
			statement.executeUpdate();
			
			String selectSQL = "select messageID from message";
			ResultSet result = statement.executeQuery(selectSQL); 
			result.last();
			messageID = result.getInt(1);
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return messageID;
	}

	
	/**
	 * add lines in message sheet
	 * @param senderID  
	 * @param recipientIDList
	 * @param content
	 */
	private static void addMultiMessage(int senderID, ArrayList<Integer>recipientIDList,String content){
		try {
			Connection connection = getWriteConnection();
			for (int recipientID : recipientIDList) {
				String sqlString = "insert into message values (null,?,?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sqlString);
				statement.setInt(1, senderID);
				statement.setInt(2, recipientID);
				statement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));//set delivery time as current time
				statement.setString(4, content);
				statement.setBoolean(5, true);
				statement.executeUpdate();
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get a line in message sheet
	 * @param messageID
	 * @return
	 * 		An message descriptor object, the container of a message.
	 */
	public static MessageDescriptor getMessage(int messageID){
		MessageDescriptor message = null;
		try {
			Connection connection = getReadConnection();
			String sqlString = "select * from message where messageID=? ";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, messageID);
			ResultSet result = statement.executeQuery();
			result.last();
			message = new MessageDescriptor(messageID, result.getInt("senderID"), result.getInt("recipientID"), result.getString("deliveryTime"), result.getString("content"), result.getBoolean("unread"));
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * get all messages the recipient of which is a particular user.
	 * @param recipientID the recipient user id
	 * @return
	 * 	the message descriptor list.
	 */
	public static ArrayList<MessageDescriptor> getInboxMessage(int recipientID){
		ArrayList<MessageDescriptor> messageList = new ArrayList<MessageDescriptor>();
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `message` WHERE `recipientID`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, recipientID);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				MessageDescriptor message = new MessageDescriptor(result.getInt("messageID"), result.getInt("senderID"), result.getInt("recipientID"), result.getString("deliveryTime"), result.getString("content"), result.getBoolean("unread"));
				messageList.add(message);
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return messageList;
	}
	
	/**
	 * get all messages the sender of which is a particular user.
	 * @param recipientID the sender user id
	 * @return
	 * 	the message descriptor list.
	 */
	public static ArrayList<MessageDescriptor> getOutboxMessage(int senderID){
		ArrayList<MessageDescriptor> messageList = new ArrayList<MessageDescriptor>();
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `message` WHERE `senderID`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, senderID);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				MessageDescriptor message = new MessageDescriptor(result.getInt("messageID"), result.getInt("senderID"), result.getInt("recipientID"), result.getString("deliveryTime"), result.getString("content"), result.getBoolean("unread"));
				messageList.add(message);
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return messageList;
	}
	
	/**
	 * get the number of unread messages of a user.
	 * @param recipientID
	 * @return
	 */
	public static int getUnreadeMessageCount(int recipientID){
		try {
			int count = 0;
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `message` WHERE `recipientID`=? and `unread`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, recipientID);
			statement.setBoolean(2, true);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				count++;
			}
			connection.close();
			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * set a particular message's unread field as false.
	 * @param messageID
	 */
	public static void setMessageRead(int messageID){
		try {
			Connection connection = getWriteConnection();
			String sqlString = "UPDATE `message` SET `unread`= ? WHERE `messageID` = ?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setBoolean(1, false);
			statement.setInt(2, messageID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * set all messages read under a user.  
	 * @param userID
	 */
	public static void setAllMessageRead(int userID){
		try {
			Connection connection = getWriteConnection();
			String sqlString = "UPDATE `message` SET `unread`= ? WHERE `recipientID` = ?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setBoolean(1, false);
			statement.setInt(2, userID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * delete a line in message sheet
	 * @param messageID the id of the message to delete 
	 */
	public static void deleteMessage(int messageID){
		try {
			Connection connection = getWriteConnection();
			String sqlString = "DELETE FROM `message` WHERE `messageID`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, messageID);
			statement.executeUpdate();//TODO: improve to get delete info (success or failed)
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * delete a line in message sheet that contains a speical userID.
	 * @param userID
	 */
	public static void deleteMessageByUserID(int userID){
		try {
			Connection connection = getWriteConnection();
			String sqlString = "DELETE FROM `message` WHERE `senderID`=? or `recipientID`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, userID);
			statement.setInt(2, userID);
			statement.executeUpdate();//TODO: improve to get delete info (success or failed)
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void AddPublicMessage(String content){
		ArrayList<Integer> alluserIDList = UserData.getAllUserID();
		MessageData.addMultiMessage(1, alluserIDList, content);
	}
	
	
	
	/*
	public static void main(String[] args){
		//addPublicMessage("public");
		//setMessageRead(5);
		//System.out.print(getMessageList(4).get(1).ID);
	}*/
	
}
