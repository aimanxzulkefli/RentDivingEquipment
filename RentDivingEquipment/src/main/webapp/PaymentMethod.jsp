<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Method</title>
    <link rel="stylesheet" type="text/css" href="css/styleListing.css">
    <style>
        /* Center the payment options and the confirm button horizontally */
        .payment-options-container {
            display: flex;
            flex-direction: column;  /* Stack the elements vertically */
            align-items: center;     /* Center the content horizontally */
            width: 100%;             /* Full width of the page */
            text-align: center;      /* Center the text inside the labels */
        }

        .payment-options {
            margin-bottom: 20px;     /* Add space between the options and the button */
        }

        .payment-options label {
            margin: 10px 0;          /* Add space between each radio button */
            font-size: 18px;         /* Adjust the font size for the labels */
        }

        .payment-options input[type="radio"] {
            margin-right: 10px;      /* Space between the radio button and the label */
        }

        .payment-options-container button {
            background-color: #4CAF50; /* Green background */
            color: white;
            font-size: 16px;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .payment-options-container button:hover {
            background-color: #45a049; /* Slightly darker green on hover */
            transform: scale(1.05);     /* Slight zoom-in effect */
        }

        .payment-options-container button:focus {
            outline: none;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Subtle shadow on focus */
        }

        .payment-options-container button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
            box-shadow: none;
        }
        
        
    </style>
    <script>
        function handlePaymentOption() {
            const onlineOption = document.getElementById("onlinePayment").checked;
            if (onlineOption) {
                document.querySelector(".popup").classList.add("active");
                document.querySelector(".popup-overlay").classList.add("active");
            } else {
                alert("Successfully rented! Payment confirmed with cash.");
                document.getElementById("checkoutForm").submit(); // Redirect after cash confirmation
            }
        }

        function closePopup() {
            document.querySelector(".popup").classList.remove("active");
            document.querySelector(".popup-overlay").classList.remove("active");
        }

        function confirmOnlinePayment() {
            const receiptFile = document.getElementById("receipt").files[0];
            if (!receiptFile) {
                alert("Please upload the receipt.");
            } else {
                alert("Successfully rented! Payment confirmed with receipt uploaded.");
                closePopup();
                document.getElementById("checkoutForm").submit(); // Redirect after online payment confirmation
            }
        }
    </script>
</head>
<body>
    <h1>Choose Payment Method</h1>

    
    <form id="checkoutForm" action="Confirmation" method="post">
        <!-- User details -->
            <input type="hidden" name="hiddenFullName" value="<%= request.getParameter("fullName") %>">
		    <input type="hidden" name="hiddenICNumber" value="<%= request.getParameter("icNumber") %>">
		    <input type="hidden" name="hiddenPhoneNumber" value="<%= request.getParameter("phoneNumber") %>">
		    <input type="hidden" name="startDate" value="<%= request.getParameter("startDate") %>">
		    <input type="hidden" name="endDate" value="<%= request.getParameter("endDate") %>">
        
        <!-- Cart details -->
        <%
        Map<String, String[]> parameterMap = request.getParameterMap();

        // Iterate over cart items based on parameters
        for (int i = 0; i < parameterMap.size(); i++) {
            String id = request.getParameter("cart[" + i + "].id");
            String name = request.getParameter("cart[" + i + "].name");
            String quantity = request.getParameter("cart[" + i + "].quantity");

            if (id != null && name != null && quantity != null) {
    %>
    <input type="hidden" name="cart[<%= i %>].id" value="<%= id %>">
    <input type="hidden" name="cart[<%= i %>].name" value="<%= name %>">
    <input type="hidden" name="cart[<%= i %>].quantity" value="<%= quantity %>">
    <% } } %>


        
       

        <!-- Payment options -->
        <div class="payment-options-container">
            <div class="payment-options">
                <label>
                    <input type="radio" name="paymentOption" id="cashPayment" value="cash" required> Cash
                </label>
                <label>
                    <input type="radio" name="paymentOption" id="onlinePayment" value="online" required> Online Payment
                </label>
            </div>

            <button type="button" onclick="handlePaymentOption()">Confirm Renting</button>
        </div>

        <!-- Popup for Online Payment -->
        <div class="popup-overlay" onclick="closePopup()"></div>
        <div class="popup">
            <h2>Upload Receipt</h2>
            <input type="file" id="receipt" name="receipt" accept=".jpg,.jpeg,.png,.pdf">
            <button type="button" onclick="confirmOnlinePayment()">Confirm Payment</button>
            <button type="button" onclick="closePopup()">Cancel</button>
        </div>
    </form>
</body>
</html>
