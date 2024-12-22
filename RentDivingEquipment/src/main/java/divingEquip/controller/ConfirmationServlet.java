package divingEquip.controller;

import divingEquip.model.CartItem;
import divingEquip.model.RentalOrder;
import divingEquip.dao.RentalOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@WebServlet("/Confirmation")
public class ConfirmationServlet extends HttpServlet {
	
	public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	System.out.println("Account SID: " + ACCOUNT_SID);
    	System.out.println("Auth Token: " + AUTH_TOKEN);

        // Get data from the request (you should have sent these from the previous JSP page)
    	String fullName = request.getParameter("hiddenFullName");
        String icNumber = request.getParameter("hiddenICNumber");
        String phoneNumber = request.getParameter("hiddenPhoneNumber");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Extract cart details
        List<CartItem> cartItems = new ArrayList<>();
        int i = 0;

        // Loop through cart items until there are no more
        while (request.getParameter("cart[" + i + "].id") != null) {
            // Retrieve parameters as Strings
            String idStr = request.getParameter("cart[" + i + "].id");
            String name = request.getParameter("cart[" + i + "].name");
            String quantityStr = request.getParameter("cart[" + i + "].quantity");

            // Convert id and quantity to int, if they are valid
            if (idStr != null && name != null && quantityStr != null) {
                try {
                    int id = Integer.parseInt(idStr); // Convert id to int
                    int quantity = Integer.parseInt(quantityStr); // Convert quantity to int

                    // Create a CartItem object and add it to the list
                    CartItem item = new CartItem(id, name, quantity);
                    cartItems.add(item);
                   
                } catch (NumberFormatException e) {
                    // Handle invalid number format (e.g., log the error or skip the item)
                    System.err.println("Invalid format for id or quantity: " + e.getMessage());
                }
            }
            i++; // Increment to check the next cart item
            
        }
        
       
        System.out.println("Full Name: " + fullName);
        System.out.println("IC Number: " + icNumber);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Cart Items: " + cartItems);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    

        // Prepare rental order list
        List<RentalOrder> rentalOrders = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            RentalOrder order = new RentalOrder();
            order.setEquipmentId(cartItem.getId());
            order.setQuantity(cartItem.getQuantity());
            order.setStartDate(startDate);
            order.setEndDate(endDate);
            order.setEquipmentName(cartItem.getName());
            order.setFullName(fullName);    // Set the full name
            order.setIcNumber(icNumber);    // Set the IC number
            order.setPhoneNumber(phoneNumber);  // Set the phone number
            rentalOrders.add(order);
            
        }
        
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Hello ").append(fullName).append(",\n\n")
                      .append("Thank you for your order! Your rental will be started from ")
                      .append(startDate).append(" until ").append(endDate).append(".\n")
                      .append("Below is the list of equipment you have rented:\n\n");

        for (CartItem cartItem : cartItems) {
            messageBuilder.append("- ").append(cartItem.getName())
                          .append(" (Quantity: ").append(cartItem.getQuantity()).append(")\n");
        }

        messageBuilder.append("\nWe appreciate your business and look forward to serving you again!");
        String message = messageBuilder.toString();
        String[] phoneNumbers = {"+601123860367", "+6" + phoneNumber};

        boolean isMessageSentSuccessfully = true;
        for (String number : phoneNumbers) {
            try {
                // Send message to each phone number
                Message whatsapp = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + number),  // To number
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), // From number (your Twilio WhatsApp number)
                        message // Message body
                ).create();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error sending WhatsApp message to " + number + ": " + e.getMessage());

                // Handle other errors
                response.getWriter().write("<html><body>");
                response.getWriter().write("<p>Invalid phone number, Transaction failed!</p>");
                response.getWriter().write("<script>");
                response.getWriter().write("setTimeout(function(){ window.location.href = 'RentDate.jsp'; }, 3000);");
                response.getWriter().write("</script>");
                response.getWriter().write("</body></html>");
                isMessageSentSuccessfully = false;
                break;  // Exit loop on error
            }
        }
       
        // Use DAO to save rental orders and order details
        RentalOrderDAO orderDAO = new RentalOrderDAO();
        if (isMessageSentSuccessfully) {
        try {
            // Save rental orders
            orderDAO.saveRentalOrders(rentalOrders);

            
            request.getSession().invalidate();

            response.getWriter().write("<html><body>");
            response.getWriter().write("<p>Order confirmed successfully!</p>");
            response.getWriter().write("<script>");
            response.getWriter().write("setTimeout(function(){ window.location.href = 'RentDate.jsp'; }, 3000);");
            response.getWriter().write("</script>");
            response.getWriter().write("</body></html>");
            
           
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while processing the order.");
        }
        }
    }

    
    
}
