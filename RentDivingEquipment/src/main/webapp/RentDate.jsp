<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Diving Equipment Renting</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="form-container">
        <h2>Diving Equipment Renting</h2>
        <!-- Form that submits to /checkEquipmentAvailability -->
        <form action="/RentDivingEquipment/checkEquipmentAvailability" method="get">
            <!-- Start Date -->
            <label for="startDate">Start Date</label>
            <input type="date" id="startDate" name="startDate" required>
            
            <!-- End Date -->
            <label for="endDate">End Date</label>
            <input type="date" id="endDate" name="endDate" required>
            
            <!-- Search Button -->
            <button type="submit">Search</button>
        </form>
    </div>
</body>
</html>
