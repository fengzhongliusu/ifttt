package database.descriptor;

/**
 * It is a container to describe the user properties, recovered from database.
 */
public class UserDescriptor {
	public int userID;
	public String username;
	public String password;
	public int points;
	public int experience;
	public int level;
	public String levelString;
	
	public UserDescriptor(int userID, String username, String password, int points, int experience){
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.points = points;
		this.experience = experience;
		this.level = (int)Math.sqrt((experience/50 - 0.5));
		switch (level) {
		case 0:
			levelString = "Newbie";break;
		case 1:
			levelString = "Beginner";break;
		case 2:
			levelString = "Bachelor";break;
		case 3:
			levelString = "Master";break;
		default:
			assert level > 3;
			levelString = "Doctor";break;
		}
	}
	
	
}
