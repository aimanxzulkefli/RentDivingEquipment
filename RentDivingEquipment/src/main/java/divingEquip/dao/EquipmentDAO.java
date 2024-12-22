package divingEquip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import divingEquip.connection.ConnectionManager;
import divingEquip.model.Equipment;

public class EquipmentDAO {
    public static List<Equipment> getAvailableEquipment(String startDate, String endDate) {
        List<Equipment> equipmentList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            
            // Query to check if there are any overlapping rentals
            String query = 
                    "SELECT e.EQUIPMENT_ID AS id, " +
                    "e.EQUIPMENT_NAME AS name, " +
                    "e.AVAILABILITY - COALESCE(SUM(ro.QUANTITY), 0) AS available_quantity " +
                    "FROM EQUIPMENT_LISTING e " +
                    "LEFT JOIN RENTALORDERS ro ON e.EQUIPMENT_ID = ro.EQUIPMENT_ID " +
                    "AND ((ro.START_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd')) " +
                    "OR (ro.END_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd'))) " +
                    "GROUP BY e.EQUIPMENT_ID, e.EQUIPMENT_NAME, e.AVAILABILITY " +
                    "HAVING e.AVAILABILITY - COALESCE(SUM(ro.QUANTITY), 0) > 0";

            System.out.println(connection);
            System.out.println(query);
            
            // Prepare the statement with the dates
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, startDate); // Set start date
                statement.setString(2, endDate);   // Set end date
                statement.setString(3, startDate); // Set start date again for checking overlaps
                statement.setString(4, endDate);   // Set end date again for checking overlaps
                
                ResultSet resultSet = statement.executeQuery();
                
                // If no rentals are found, show all equipment
                if (!resultSet.next()) {
                    // Query to fetch all equipment if no rentals are found
                    query = "SELECT EQUIPMENT_ID AS id, EQUIPMENT_NAME AS name, AVAILABILITY AS available_quantity " +
                            "FROM EQUIPMENT_LISTING";
                    try (PreparedStatement allStatement = connection.prepareStatement(query)) {
                        resultSet = allStatement.executeQuery();
                        while (resultSet.next()) {
                            Equipment equipment = new Equipment();
                            equipment.setId(resultSet.getInt("id"));
                            equipment.setName(resultSet.getString("name"));
                            equipment.setQuantity(resultSet.getInt("available_quantity"));
                            equipmentList.add(equipment);
                        }
                    }
                } else {
                    // If rentals exist, calculate available quantity
                    do {
                        Equipment equipment = new Equipment();
                        equipment.setId(resultSet.getInt("id"));
                        equipment.setName(resultSet.getString("name"));
                        equipment.setQuantity(resultSet.getInt("available_quantity"));
                        equipmentList.add(equipment);
                    } while (resultSet.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }
}
