<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" type="text/css" href="css/styleListing.css">
    <style>
        /* Styling for the summary container and its button */
    .summary-container {
    display: flex;
    justify-content: flex-end; /* Align content to the right */
    align-items: center;
    padding: 20px;
    margin-top: 20px;
    gap: 10px; /* Add space between the items */
}


        .summary-container .button {
            background-color: #4CAF50; /* Green background */
            color: white;
            font-size: 16px;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .summary-container .button:hover {
            background-color: #45a049; /* Slightly darker green */
            transform: scale(1.05); /* Slight zoom-in effect */
        }

        .summary-container .button:focus {
            outline: none;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Subtle shadow on focus */
        }

        .summary-container .button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
            box-shadow: none;
        }
        /* Remove button - red style */
.remove-btn {
    background-color: #ff4d4d; /* Red background */
    color: white; /* White text */
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.remove-btn:hover {
    background-color: #e60000; /* Darker red on hover */
}
        
    </style>
    <script>
        // Remove item from cart
        function removeFromCart(itemId, itemIndex) {
    window.location.href = 'RemoveFromCartServlet?id=' + itemId + '&index=' + itemIndex;
}

        // Checkout action
        function openCheckoutPopup() {
            document.querySelector(".popup").classList.add("active");
            document.querySelector(".popup-overlay").classList.add("active");
        }

        function closeCheckoutPopup() {
            document.querySelector(".popup").classList.remove("active");
            document.querySelector(".popup-overlay").classList.remove("active");
        }
        
        function confirmCheckout() {
            const fullName = document.getElementById("fullName").value;
            const icNumber = document.getElementById("icNumber").value;
            const phoneNumber = document.getElementById("phoneNumber").value;

            if (fullName && icNumber && phoneNumber) {
                // Navigate to PaymentMethod.jsp or handle form submission here
                document.getElementById("hiddenFullName").value = fullName;
		        document.getElementById("hiddenICNumber").value = icNumber;
		        document.getElementById("hiddenPhoneNumber").value = phoneNumber;
		        
		        document.getElementById("checkoutForm").submit();
                //window.location.href = "PaymentMethod.jsp";
            } else {
                alert("Please fill in all fields.");
            }
        }
    </script>
</head>
<body>
    <h1>My Cart</h1>

    <table>
    <thead>
        <tr>
            <th>No</th> <!-- Changed "Equipment ID" to "No" -->
            <th>Equipment Name</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${cart}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td> <!-- Display the list number (1, 2, 3, ...) -->
                <td>${item.name}</td>
                <td>
                    <input type="number" value="${item.quantity}" min="1">
                </td>
                <td>
                    <button class="button remove-btn" 
        onclick="removeFromCart(${item.id}, ${status.index})">Remove</button>

                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


    <div class="summary-container">
        <span>Total Items: ${cart.size()}</span>
        <button class="button" onclick="openCheckoutPopup()">Checkout</button>
    </div>

<form id="checkoutForm" action="PaymentMethod.jsp" method="POST">
    <!-- Hidden input fields for user details -->
    <input type="hidden" id="hiddenFullName" name="fullName">
    <input type="hidden" id="hiddenICNumber" name="icNumber">
    <input type="hidden" id="hiddenPhoneNumber" name="phoneNumber">
    <input type="hidden" name="startDate" value="${sessionScope.startDate}">
    <input type="hidden" name="endDate" value="${sessionScope.endDate}">

    <!-- Hidden input fields for cart details -->
    <c:forEach var="item" items="${cart}" varStatus="status">
        <input type="hidden" name="cart[${status.index}].id" value="${item.id}">
        <input type="hidden" name="cart[${status.index}].name" value="${item.name}">
        <input type="hidden" name="cart[${status.index}].quantity" value="${item.quantity}">
    </c:forEach>
</form>

    <!-- Popup for Checkout Details -->
    <div class="popup-overlay" onclick="closeCheckoutPopup()"></div>
    <div class="popup">
        <h2>Enter Your Details</h2>
        <input type="text" id="fullName" placeholder="Full Name" required>
        <input type="text" id="icNumber" placeholder="IC Number" required>
        <input type="text" id="phoneNumber" placeholder="Phone Number" required>
        <button onclick="confirmCheckout()">Confirm</button>
        <button onclick="closeCheckoutPopup()">Cancel</button>
    </div>
</body>
</html>
