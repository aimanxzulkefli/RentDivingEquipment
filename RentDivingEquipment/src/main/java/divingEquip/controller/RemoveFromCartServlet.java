package divingEquip.controller;

import java.io.IOException;
import java.util.List;

import divingEquip.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the cart from the session
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            // Get the item ID and index from the request parameters
            String itemId = request.getParameter("id");  // This is a String
            String itemIndex = request.getParameter("index");  // This is a String
            
            // Convert the index to an integer
            int index = Integer.parseInt(itemIndex);

            // Check if the index is within range
            if (index >= 0 && index < cart.size()) {
                // Get the item at the specified index
                CartItem item = cart.get(index);

                // Compare the IDs directly as they are both primitive types
                if (item.getId() == Integer.parseInt(itemId)) {
                    cart.remove(index); // Remove the item at the specified index
                }
            }

            // Save the updated cart back to the session
            session.setAttribute("cart", cart);
        }

        // Redirect back to the cart page after removing the item
        response.sendRedirect("Cart.jsp"); // Change this to the appropriate page
    }
}



