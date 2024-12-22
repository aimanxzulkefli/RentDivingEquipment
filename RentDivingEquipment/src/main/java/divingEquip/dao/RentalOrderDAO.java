package divingEquip.dao;

import divingEquip.model.RentalOrder;
import divingEquip.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RentalOrderDAO {

	public void saveRentalOrders(List<RentalOrder> rentalOrders) throws SQLException {
	    String insertOrderQuery = "INSERT INTO rentalorders (EQUIPMENT_ID, QUANTITY, START_DATE, END_DATE, EQUIPMENT_NAME, FULLNAME, IC_NUMBER, PHONE_NUMBER) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?)";

	    try (Connection con = ConnectionManager.getConnection();
	         PreparedStatement ps = con.prepareStatement(insertOrderQuery)) {
	        for (RentalOrder order : rentalOrders) {
	            ps.setInt(1, order.getEquipmentId());
	            ps.setInt(2, order.getQuantity());
	            ps.setString(3, order.getStartDate());
	            ps.setString(4, order.getEndDate());
	            ps.setString(5, order.getEquipmentName()); // Set Equipment Name
	            ps.setString(6, order.getFullName());      // Set full name
	            ps.setString(7, order.getIcNumber());      // Set IC number
	            ps.setString(8, order.getPhoneNumber());   // Set phone number
	            ps.addBatch();
	        }
	        ps.executeBatch(); // Execute all the inserts in a batch
	    }
	}



//    public int getNextOrderId() throws SQLException {
//        String query = "SELECT ORDER_ID_SEQ.NEXTVAL FROM dual"; // Assuming a sequence is used
//        try (Connection con = ConnectionManager.getConnection();
//             PreparedStatement ps = con.prepareStatement(query);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//            return 0;
//        }
//    }
}
