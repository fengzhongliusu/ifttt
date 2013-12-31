package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.descriptor.UserDescriptor;


public class UserData extends Data{
	
	/**
	 * search the user sheet where username = name
	 * @param name the name to search
	 * @return
	 * 		true when name exist
	 */
	public static boolean searchUsername(String username) {
		try {
			Connection connection = getReadConnection();
			String sqlString = "select * from user where username =?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				connection.close();
				return true;
			}
			else {
				connection.close();
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * add a line in user sheet
	 * @param name
	 * @param password
	 * @return
	 * 		user ID
	 * 		-1 when error
	 */
	public static int addUser(String username, String password) {
		try {
			Connection connection = getWriteConnection();
			String sqlString = "insert into user values (null,?,?,1000,0)";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.executeUpdate();
			connection.close();
			return getUserID(username);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * get user ID where username == "username"
	 * @param username
	 * @return
	 * 		user ID
	 * 		-1 when error
	 */
	public static int getUserID(String username){
		
		int ID = -1;
		try{
			Connection connection = getReadConnection();
			String sqlString = "select id from user where username=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if(result.next())
				ID = result.getInt(1);
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ID;
	}

	/**
	 * whether there is a line in user where username == "" and password == ""
	 * @param username
	 * @param password
	 * @return
	 * 		true when exist
	 */
	public static boolean accountCheck(String username, String password){
		try {
			Connection connection = getReadConnection();
			String sqlString = "select * from user where username=? and password=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			if(result.next()){
				connection.close();
				return true;//exist
			}
			else{
				connection.close();
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * get user name where id == "ID"
	 * @param ID
	 * @return
	 * 		user name
	 * 		"" when error
	 */
	public static String getUsername(int ID) {
		String name = null;
		try{
			Connection connection = getReadConnection();
			String sqlString = "select username from user where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, ID);
			ResultSet result = statement.executeQuery();
			result.last();
			name = result.getString(1);
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	
	/**
	 * get all user's id
	 * @return
	 * 		the user id list. The id "1" (the admin's id) is not included.
	 */
	public static ArrayList<Integer> getAllUserID(){
		ArrayList<Integer> userIDList = new ArrayList<Integer>();
		
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT `ID` FROM `user`";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				int userID = result.getInt("ID");
				if(userID != 1)
					userIDList.add(userID);
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return userIDList;
	}
	
	/**
	 * get all user's name
	 * @return
	 * 		the user name list. The id "1" (the admin's id) is not included.
	 */
	public static ArrayList<String> getAllUserName(){
		ArrayList<String> userNameList = new ArrayList<String>();
		
		try {
			Connection connection = getReadConnection();
			String sqlString = "SELECT * FROM `user`";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				int userID = result.getInt("ID");
				String userName = result.getString("username");
				if(userID != 1)
					userNameList.add(userName);
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return userNameList;
	}
	
	
	/**
	 * get a user descriptor object from database
	 * @param userID the object user id
	 * @return
	 * 		a user descriptor object, contains the user's information
	 */
	public static UserDescriptor getUser(int userID){
		UserDescriptor ud = null;
		try {
			Connection connection = getReadConnection();
			String sqlString = "select * from user where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			if(result.next())
				ud = new UserDescriptor(userID, result.getString("username"), result.getString("password"), result.getInt("points"), result.getInt("experience"));
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ud;
	}
	
	public static void updatePassword(int userID, String password) {
		try {
			Connection connection = getReadConnection();
			String sqlString = "update user set password=? where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setString(1, password);
			statement.setInt(2, userID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePoints(int userID, int points) {
		try {
			Connection connection = getReadConnection();
			String sqlString = "UPDATE `user` SET `points`= `points` + ? WHERE `id` = ?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, points);
			statement.setInt(2, userID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void costPoints(int userID, int points) {
		try {
			Connection connection = getReadConnection();
			String sqlString = "UPDATE `user` SET `points`= `points` - ? WHERE `id` = ?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, points);
			statement.setInt(2, userID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateExperience(int userID, int points) {
		try {
			Connection connection = getReadConnection();
			String sqlString = "UPDATE `user` SET `experience`= `experience` + ? WHERE `id` = ?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, points);
			statement.setInt(2, userID);
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(int userID) {
		try {
			Connection connection = getWriteConnection();
			String sqlString = "DELETE FROM `user` WHERE `id`=?";
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, userID);
			statement.executeUpdate();//TODO: improve to get delete info (success or failed)
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
