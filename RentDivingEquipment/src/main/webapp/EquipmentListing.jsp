<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Equipment Listing</title>
    <link rel="stylesheet" type="text/css" href="css/styleListing.css">
    <script>
    function openPopup(id, name, maxQuantity) {
        // Set hidden field values
        document.getElementById("equipmentId").value = id;
        document.getElementById("equipmentName").value = name;
        document.getElementById("quantity").value = 1; // Default value
        document.getElementById("quantity").max = maxQuantity; // Maximum quantity

        // Show popup
        document.getElementById("popupEquipmentName").innerText = name;
        document.querySelector(".popup").classList.add("active");
        document.querySelector(".popup-overlay").classList.add("active");
    }

    function closePopup() {
        // Hide popup
        document.querySelector(".popup").classList.remove("active");
        document.querySelector(".popup-overlay").classList.remove("active");
    }

    // Function to show alert or popup message
    function showMessage(message, type) {
        if (type === 'success') {
            alert(message);  // Or you can use a custom modal if you want
        } else {
            alert(message);  // For errors, you can also use a different style of alert or modal
        }
    }
    </script>
</head>
<body>
    <!-- Cart Link -->
    <a href="Cart.jsp" class="cart-link">View Cart</a>

    <h1>Equipment Listing</h1>
    <table>
    <thead>
        <tr>
            <th>No</th>
            <th>Equipment Name</th>
            <th>Availability</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="equipment" items="${equipmentList}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${equipment.name}</td>
                <td>${equipment.quantity}</td>
                <td>
                    <button onclick="openPopup(${equipment.id}, '${equipment.name}', ${equipment.quantity})">
                        Select Item
                    </button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
    </table>

    <!-- Show message if exists -->
    <c:if test="${not empty sessionScope.message}">
	    <script>
	        showMessage("${sessionScope.message}", "${sessionScope.messageType}");
	        // Optionally, clear the message after showing it (so it doesn't display next time)
	        session.removeAttribute('message');
	        session.removeAttribute('messageType');
	    </script>
	</c:if>

    <!-- Popup -->
    <div class="popup-overlay" onclick="closePopup()"></div>
    <div class="popup">
        <form id="addToCartForm" action="/RentDivingEquipment/AddToCartServlet" method="post">
            <!-- Display Equipment Name -->
            <h2 id="popupEquipmentName"></h2>
            <!-- Hidden fields to store the selected data -->
            <input type="hidden" id="equipmentId" name="id" />
            <input type="hidden" id="equipmentName" name="name" />
            <!-- Hidden fields for startDate and endDate -->
		    <input type="hidden" id="startDate" name="startDate" value="${param.startDate}" />
		    <input type="hidden" id="endDate" name="endDate" value="${param.endDate}" />
		    
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1" />
            <!-- Form buttons -->
            <button type="submit">Add to Cart</button>
            <button type="button" onclick="closePopup()">Cancel</button>
        </form>
    </div>

</body>
</html>
