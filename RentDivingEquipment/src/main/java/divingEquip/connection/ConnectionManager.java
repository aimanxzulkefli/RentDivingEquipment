package divingEquip.connection;
/*
 * Author: FES (March 2024)
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	static Connection con;
	//define and initialize database driver
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	//define and initialize database url
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@IKON-LAPTOP:1521:XE";
	//define and initialize database user
	private static final String DB_USER = "aiman";
	//define and initialize database password
	private static final String DB_PASSWORD = "aiman";
	
	public static Connection getConnection() {
	
		try {
			//1. load the driver
			Class.forName(DB_DRIVER);
			
			try {
				//2. create connection
				con = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
				System.out.println("Connected");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}		
		return con;
	}
}