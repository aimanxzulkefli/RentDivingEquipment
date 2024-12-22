package divingEquip.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import divingEquip.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            String quantityParam = request.getParameter("quantity");
            String name = request.getParameter("name");
            String startDate = request.getParameter("startDate"); // Get startDate from request
            String endDate = request.getParameter("endDate");

            System.out.println(idParam);
            System.out.println(quantityParam);
            System.out.println(name);
            System.out.println(startDate);
            System.out.println(endDate);

            int id = Integer.parseInt(idParam);
            int quantity = Integer.parseInt(quantityParam);

            CartItem newItem = new CartItem(id, name, quantity);

            HttpSession session = request.getSession();
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }

            cart.add(newItem);
            
            session.setAttribute("startDate", startDate);
            session.setAttribute("endDate", endDate);

            // Add a success message as a request attribute
            session.setAttribute("message", "Item added to cart successfully!");
            session.setAttribute("messageType", "success");

            String url = "/RentDivingEquipment/checkEquipmentAvailability?startDate=" + startDate + "&endDate=" + endDate;
            response.sendRedirect(url);


        } catch (NumberFormatException e) {
            // If error occurs, send error message
            request.setAttribute("message", "Invalid number format.");
            request.setAttribute("messageType", "error");

            // Redirect back to the equipment listing page with the error message
            request.getRequestDispatcher("/RentDate.jsp").forward(request, response);
        }
    }
}
