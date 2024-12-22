package divingEquip.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
        try {
            // Get the connection
            con = ConnectionManager.getConnection();

            // Check if the connection is valid
            if (con != null && !con.isClosed()) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
