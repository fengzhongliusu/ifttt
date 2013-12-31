package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

public abstract class Data {
	
	public static  final String driver = "com.mysql.jdbc.Driver";
	public static final String sae_username = SaeUserInfo.getAccessKey();
	public static final String sae_password = SaeUserInfo.getSecretKey();;
	public static final String sae_writeURL = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_vivifirst";
	public static final String sae_readURL = "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_vivifirst";
	public static final String local_username = "root";
	public static final String local_password = "";
	public static final String local_URL = "jdbc:mysql://localhost/ifttt";
	

	/**
	 * sb	get a database connection for read
 	 * @return		
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getReadConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		Class.forName(driver).newInstance();
		//Connection con=DriverManager.getConnection(sae_readURL,sae_username,sae_password);
		Connection con=DriverManager.getConnection(local_URL, local_username, local_password);
		
		return con;
	}
	
	
	/**
	 * get a database connection for write
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getWriteConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		Class.forName(driver).newInstance();
		//Connection con=DriverManager.getConnection(sae_writeURL,sae_username,sae_password);
		Connection con=DriverManager.getConnection(local_URL, local_username, local_password);
		
		return con;	
	}

	
}
